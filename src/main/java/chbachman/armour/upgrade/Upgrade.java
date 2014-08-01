package chbachman.armour.upgrade;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.items.ItemModularArmour;
import chbachman.armour.reference.ArmourSlot;
import cofh.util.StringHelper;

public abstract class Upgrade implements Comparable<Upgrade> {
    
    protected final int id;
    protected String name;
    
    public int amount = 1;
    
    protected Recipe recipe;
    
    public Upgrade(String name) {
        this.id = this.getNextAvailableId();
        
        this.name = name;
        
        if (this.shouldRegisterRecipes()) {
            this.recipe = this.getRecipe();
            Recipe.addToList(this.recipe);
        }
    }
    
    protected void init() {
        
    }
    
    private int getNextAvailableId() {
        return UpgradeList.list.size();
    }
    
    public NBTTagCompound getNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("ID", this.id);
        nbt.setInteger("amount", amount);
        return nbt;
    }
    
    public static Upgrade getUpgradeFromNBT(NBTTagCompound nbt) {
        
        if (nbt == null) {
            return null;
        }
        
        try {
            Upgrade upgrade = UpgradeList.list.get(nbt.getInteger("ID"));
            
            upgrade.amount = nbt.getInteger("amount");
            
            return upgrade;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    
    public String getName() {
        
        if(this.amount > 1){
            return StringHelper.localize(this.getUnlocalizedName()) + "(" + this.amount + ")";
        }
        
        return StringHelper.localize(this.getUnlocalizedName());
    }
    
    public String getUnlocalizedName() {
        return this.getLocalizationString(this.name);
    }
    
    protected String getLocalizationString(String name) {
        return "upgrade.chbachman." + StringHelper.camelCase(name).replace(" ", "") + ".name";
    }
    
    public int getId() {
        return this.id;
    }
    
    public boolean shouldRegisterRecipes() {
        return true;
    }
    
    @Override
    public int compareTo(Upgrade upgrade) {
        return this.getName().compareTo(upgrade.getName());
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
    
    // Api for Upgrades here
    public boolean isCompatible(int slot) {
        return this.isCompatible(ArmourSlot.getArmourSlot(slot));
    }
    
    public abstract boolean isCompatible(ArmourSlot slot);
    
    public abstract Recipe getRecipe();
    
    public int getArmourDisplay() {
        return 0;
    }
    
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot) {
        return null;
    }
    
    public List<String> getDependencies() {
        return null;
    }
    
    public void onUpgradeAddition(ItemModularArmour armour, ItemStack stack) {
        
    }
    
    public void onArmourEquip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        
    }
    
    public int onArmourTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        return 0;
    }
    
    public void onArmourDequip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        
    }
    
    public void onNoEnergy(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        
    }
    
    public boolean isRepeatable() {
        return false;
    }
    
}
