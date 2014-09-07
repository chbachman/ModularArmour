package chbachman.armour.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import chbachman.api.IModularItem;
import chbachman.api.IUpgrade;
import chbachman.armour.ModularArmour;
import chbachman.armour.gui.GuiHandler;
import chbachman.armour.objects.VariableInt;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.util.NBTHelper;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.ItemEnergyContainer;
import cofh.core.util.CoreUtils;
import cofh.lib.util.helpers.StringHelper;

public class ItemBauble extends ItemEnergyContainer implements IBauble, IModularItem, IEnergyContainerItem{

	private Map<String, VariableInt> intMap = new HashMap<String, VariableInt>();
	
	private BaubleType type;
	
	public ItemBauble(){
		super();
		intMap.put("Capacity", new VariableInt("Capacity", 100));
		intMap.put("MaxTransfer", new VariableInt("MaxTransfer", 10));
		intMap.put("EnergyPerDamage", new VariableInt("EnergyPerDamage", 10));
		setCreativeTab(CreativeTabs.tabTools);
	}

	public ItemBauble setBaubleType(BaubleType type){
		this.type = type;
		return this;
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return this.type;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		
		if (CoreUtils.isFakePlayer(player)) {
			return stack;
		}

		if (player.isSneaking() && !world.isRemote) {
			InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(player);
			for(int i = 0; i < baubles.getSizeInventory(); i++){
				if(baubles.getStackInSlot(i) == null && baubles.isItemValidForSlot(i, stack)) {
					baubles.setInventorySlotContents(i, stack.copy());
					if(!player.capabilities.isCreativeMode){
						player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
					}
					onEquipped(stack, player);
					break;
				}
			}
		}

		if (world.isRemote == false) {
			player.openGui(ModularArmour.instance, GuiHandler.ARMOUR_ID, world, 0, 0, 0);
		}
		return stack;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		int energy = 0;
		for(IUpgrade upgrade : NBTHelper.getNBTUpgradeList(itemstack)){
			energy += upgrade.onTick(player.worldObj, (EntityPlayer) player, itemstack, ArmourSlot.getArmourSlot(this.getSlot()));
		}
		this.extractEnergy(itemstack, energy, false);
		
		if (itemstack.stackTagCompound.getInteger("Energy") == 0) {
            for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(itemstack.stackTagCompound)) {
                
                if (upgrade != null) {
                    upgrade.onNoEnergy(player.worldObj, (EntityPlayer) player, itemstack, ArmourSlot.getArmourSlot(this.getSlot()));
                }
            }
        }
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean check) {
        NBTHelper.createDefaultStackTag(stack);
        if (!StringHelper.isShiftKeyDown()) {
            list.add(StringHelper.shiftForDetails());
        } else {
            
            list.add(StringHelper.localize("info.cofh.charge") + ": " + stack.stackTagCompound.getInteger("Energy") + " / " + this.intMap.get("Capacity").get(stack) + " RF");
        }
        
        if (!StringHelper.isControlKeyDown() && NBTHelper.getNBTUpgradeList(stack.stackTagCompound).size() != 0) {
            list.add(StringHelper.LIGHT_GRAY + StringHelper.localize("info.cofh.hold") + " " + StringHelper.YELLOW + StringHelper.ITALIC + StringHelper.localize("info.chbachman.control") + " " + StringHelper.END + StringHelper.LIGHT_GRAY + StringHelper.localize("info.chbachman.upgradeList") + StringHelper.END);
        } else if (NBTHelper.getNBTUpgradeList(stack.stackTagCompound).size() != 0) {
            for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {
                
                list.add(upgrade.getName());
                
            }
        }
    }
	
	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.rare;
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		if (!player.worldObj.isRemote) {
			player.worldObj.playSoundAtEntity(player, "random.orb", 0.1F, 1.3f);
		}
		
		for(IUpgrade upgrade : NBTHelper.getNBTUpgradeList(itemstack)){
			upgrade.onEquip(player.worldObj, (EntityPlayer) player, itemstack, ArmourSlot.getArmourSlot(this.getSlot()));
		}
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		for(IUpgrade upgrade : NBTHelper.getNBTUpgradeList(itemstack)){
			upgrade.onDequip(player.worldObj, (EntityPlayer) player, itemstack, ArmourSlot.getArmourSlot(this.getSlot()));
		}
	}
	
	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}
	
	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public VariableInt getInt(String name) {
		return this.intMap.get(name);
	}

	@Override
	public int getSlot() {
		return ArmourSlot.getArmourSlot(this.type).id;
	}

	@Override
	public void onArmorDequip(World worldObj, EntityPlayer player, ItemStack stack) {
		
	}


}
