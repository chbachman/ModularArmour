package chbachman.armour.items;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import chbachman.api.IModularItem;
import chbachman.api.IUpgrade;
import chbachman.armour.objects.VariableInt;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.util.NBTHelper;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.ItemEnergyContainer;
import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRing extends ItemEnergyContainer implements IBauble, IModularItem, IEnergyContainerItem{

	private Map<String, VariableInt> dataMap = new HashMap<String, VariableInt>();
	private List<IUpgrade> upgradeList = new LinkedList<IUpgrade>();
	
	public ItemRing(){
		super();
		this.setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabTools);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister ir) {
		this.itemIcon = ir.registerIcon("baubles:ring");
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.RING;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(!par2World.isRemote) { 
			InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(par3EntityPlayer);
			for(int i = 0; i < baubles.getSizeInventory(); i++)
				if(baubles.getStackInSlot(i) == null && baubles.isItemValidForSlot(i, par1ItemStack)) {
					baubles.setInventorySlotContents(i, par1ItemStack.copy());
					if(!par3EntityPlayer.capabilities.isCreativeMode){
						par3EntityPlayer.inventory.setInventorySlotContents(par3EntityPlayer.inventory.currentItem, null);
					}
					onEquipped(par1ItemStack, par3EntityPlayer);
					break;
				}
		}

		return par1ItemStack;	
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		int energy = 0;
		for(IUpgrade upgrade : upgradeList){
			energy += upgrade.onArmourTick(player.worldObj, (EntityPlayer) player, itemstack, ArmourSlot.getArmourSlot(this.getSlot()));
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
            
            list.add(StringHelper.localize("info.cofh.charge") + ": " + stack.stackTagCompound.getInteger("Energy") + " / " + this.dataMap.get("Capacity").get(stack) + " RF");
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
		
		for(IUpgrade upgrade : upgradeList){
			upgrade.onArmourEquip(player.worldObj, (EntityPlayer) player, itemstack, ArmourSlot.getArmourSlot(this.getSlot()));
		}
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		for(IUpgrade upgrade : upgradeList){
			upgrade.onArmourDequip(player.worldObj, (EntityPlayer) player, itemstack, ArmourSlot.getArmourSlot(this.getSlot()));
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
		return this.dataMap.get(name);
	}

	@Override
	public int getSlot() {
		return ArmourSlot.RING.id;
	}

	@Override
	public void onArmorDequip(World worldObj, EntityPlayer player, ItemStack stack) {
		
	}


}
