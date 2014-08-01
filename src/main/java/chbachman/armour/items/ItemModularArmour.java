package chbachman.armour.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import chbachman.armour.ModularArmour;
import chbachman.armour.gui.GuiHandler;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.upgrade.Upgrade;
import chbachman.armour.util.NBTHelper;
import cofh.api.item.IInventoryContainerItem;
import cofh.util.CoreUtils;
import cofh.util.StringHelper;

public class ItemModularArmour extends ItemSpecialArmour implements IInventoryContainerItem {
    
    public ItemModularArmour(ArmorMaterial material, int type) {
        super(material, type);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean check) {
        NBTHelper.createDefaultStackTag(stack);
        if (!StringHelper.isShiftKeyDown()) {
            list.add(StringHelper.shiftForDetails());
        } else {
            
            list.add(StringHelper.localize("info.cofh.charge") + ": " + stack.stackTagCompound.getInteger("Energy") + " / " + this.capacity.get(stack) + " RF");
        }
        
        if (!StringHelper.isControlKeyDown() && NBTHelper.getNBTTagList(stack.stackTagCompound).tagCount() != 0) {
            list.add(StringHelper.LIGHT_GRAY + StringHelper.localize("info.cofh.hold") + " " + StringHelper.YELLOW + StringHelper.ITALIC + StringHelper.localize("info.chbachman.control") + " " + StringHelper.END + StringHelper.LIGHT_GRAY + StringHelper.localize("info.chbachman.upgradeList") + StringHelper.END);
        } else if (NBTHelper.getNBTTagList(stack.stackTagCompound).tagCount() != 0) {
            for (Upgrade upgrade : NBTHelper.getUpgradeListFromNBT(stack.stackTagCompound)) {
                
                list.add(upgrade.getName());
                
            }
        }
    }
    
    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int x, boolean y) {
        stack.stackTagCompound.setBoolean("HasPutOn", false);
    }
    
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        
        NBTHelper.createDefaultStackTag(stack);
        
        NBTTagList list = NBTHelper.getNBTTagList(stack.stackTagCompound);
        
        int energy = 0;
        
        for (int i = 0; i < list.tagCount(); i++) {
            Upgrade upgrade = Upgrade.getUpgradeFromNBT(list.getCompoundTagAt(i));
            
            if (upgrade != null) {
                energy += upgrade.onArmourTick(world, player, stack, ArmourSlot.getArmourSlot(this.armorType));
            }
            
        }
        
        this.extractEnergy(stack, energy, false);
        
        if (!stack.stackTagCompound.getBoolean("HasPutOn")) {
            stack.stackTagCompound.setBoolean("HasPutOn", true);
            
            for (int i = 0; i < list.tagCount(); i++) {
                Upgrade upgrade = Upgrade.getUpgradeFromNBT(list.getCompoundTagAt(i));
                
                if (upgrade != null) {
                    upgrade.onArmourEquip(world, player, stack, ArmourSlot.getArmourSlot(this.armorType));
                }
            }
        }
        
        if (stack.stackTagCompound.getInteger("Energy") == 0) {
            for (int i = 0; i < list.tagCount(); i++) {
                Upgrade upgrade = Upgrade.getUpgradeFromNBT(list.getCompoundTagAt(i));
                
                if (upgrade != null) {
                    upgrade.onNoEnergy(world, player, stack, ArmourSlot.getArmourSlot(this.armorType));
                }
            }
        }
        
    }
    
    public void onArmorTakeOff(World world, EntityPlayer player, ItemStack stack) {
        
        if (stack == null) {
            return;
        }
        
        NBTHelper.createDefaultStackTag(stack);
        
        stack.stackTagCompound.setBoolean("HasPutOn", false);
        
        NBTTagList list = NBTHelper.getNBTTagList(stack.stackTagCompound);
        
        for (int i = 0; i < list.tagCount(); i++) {
            Upgrade upgrade = Upgrade.getUpgradeFromNBT(list.getCompoundTagAt(i));
            
            upgrade.onArmourDequip(world, player, stack, ArmourSlot.getArmourSlot(this.armorType));
        }
    }
    
    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        if (this.getEnergyStored(armor) >= this.energyPerDamage.get(armor)) {
            int sum = 0;
            NBTTagList nbt = NBTHelper.getNBTTagList(armor.stackTagCompound);
            for (int i = 0; i < nbt.tagCount(); i++) {
                sum += Upgrade.getUpgradeFromNBT(nbt.getCompoundTagAt(i)).getArmourDisplay();
            }
            return sum;
        } else {
            return 0;
        }
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        
        if (CoreUtils.isFakePlayer(player)) {
            return stack;
        }
        
        if (player.isSneaking()) {
            return super.onItemRightClick(stack, world, player);
        }
        
        if (world.isRemote == false) {
            player.openGui(ModularArmour.instance, GuiHandler.ARMOUR_ID, world, 0, 0, 0);
        }
        return stack;
    }
    
    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armour, DamageSource source, double damage, int slot) {
        NBTTagList list = NBTHelper.getNBTTagList(armour.stackTagCompound);
        
        ArmorProperties output = new ArmorProperties(0, 0, 0);
        
        for (int i = 0; i < list.tagCount(); i++) {
            ArmorProperties prop = Upgrade.getUpgradeFromNBT(list.getCompoundTagAt(i)).getProperties(player, armour, source, damage, ArmourSlot.getArmourSlot(slot));
            
            if (prop == null) {
                continue;
            }
            
            if (prop.Priority > output.Priority) {
                output = prop;
            } else if (prop.Priority == output.Priority && prop.AbsorbRatio > output.AbsorbRatio) {
                output = prop;
            }
            
        }
        
        return new ArmorProperties(output.Priority, output.AbsorbRatio, this.getEnergyStored(armour) / this.energyPerDamage.get(armour));
    }
    
    // IInventoryContainerItem
    @Override
    public int getSizeInventory(ItemStack container) {
        return 9;
    }
    
}
