package chbachman.armour.items.armour.logic;

import ic2.api.item.IMetalArmor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import repack.cofh.core.util.CoreUtils;
import thaumcraft.api.IGoggles;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.IRevealer;
import vazkii.botania.api.item.IPixieSpawner;
import chbachman.api.item.IModularItem;
import chbachman.api.item.UpgradeLogic;
import chbachman.api.nbt.NBTHelper;
import chbachman.api.nbt.NBTList;
import chbachman.api.upgrade.IUpgrade;
import chbachman.api.util.ArmourSlot;
import chbachman.armour.ModularArmour;
import chbachman.armour.gui.GuiHandler;
import chbachman.armour.register.Botania;
import chbachman.armour.register.IndustrialCraft2;
import chbachman.armour.register.Thaumcraft;
import chbachman.armour.util.ConfigHelper;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Interface;

@Optional.InterfaceList(value = { 
		@Interface(iface = "thaumcraft.api.IGoggles", modid = "Thaumcraft"), 
		@Interface(iface = "thaumcraft.api.IVisDiscountGear", modid = "Thaumcraft"), 
		@Interface(iface = "thaumcraft.api.nodes.IRevealer", modid = "Thaumcraft"), 
		@Interface(iface = "vazkii.botania.api.item.IPixieSpawner", modid = "Botania"),
		@Interface(iface = "ic2.api.item.IMetalArmor", modid = "IC2"),
		})
public abstract class UpgradeLogicAdv extends UpgradeLogic implements IRevealer, IGoggles, IVisDiscountGear, IPixieSpawner, IMetalArmor{

	public UpgradeLogicAdv(IModularItem item){
		super(item);
	}
	
	//Item
	
	/**
	 * Called on right click.
	 * @param stack
	 * @param world
	 * @param player
	 * @return stack passed in.
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		if (CoreUtils.isFakePlayer(player)) {
			return stack;
		}

		if (world.isRemote == false) {
			player.openGui(ModularArmour.instance, GuiHandler.ARMOUR_ID, world, 0, 0, 0);
		}
		return stack;
	}
	
	//IRevealer
	@Override
	@Optional.Method(modid = "Thaumcraft")
	public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {
		NBTList<IUpgrade> list = NBTHelper.getNBTUpgradeList(itemstack);

		return list.contains(Thaumcraft.gogglesOfRevealing);
	}

	//IVisDiscountGear
	@Override
	@Optional.Method(modid = "Thaumcraft")
	public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect) {
		NBTList<IUpgrade> list = NBTHelper.getNBTUpgradeList(stack);
		
		if(list.contains(Thaumcraft.visDiscount)){
			if(stack.getItem() instanceof IModularItem){
				IModularItem armour = (IModularItem) stack.getItem();
				
				switch(ArmourSlot.getArmourSlot(armour.getSlot())){
				case BELT: return 4;
				case BOOTS: return 2;
				case CHESTPLATE: return 5;
				case HELMET: return 3;
				case LEGS: return 4;
				case PENDANT: return 3;
				case RING: return 1;
				default: return 0;
				}
			}
		}
		
		return 0;
	}

	//IGoggles
	@Override
	@Optional.Method(modid = "Thaumcraft")
	public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase player) {
		NBTList<?> list = NBTHelper.getNBTUpgradeList(itemstack);

		return list.contains(Thaumcraft.gogglesOfRevealing);
	}
	
	@Override
	@Optional.Method(modid = "Botania")
	public float getPixieChance(ItemStack stack) {
		return ConfigHelper.get(ConfigHelper.SPEED,Botania.pixie, "Pixie Chance", .05F);
	}

	
	@Override
	@Optional.Method(modid = "IC2")
	public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player){
		NBTList<?> list = NBTHelper.getNBTUpgradeList(itemstack);

		return list.contains(IndustrialCraft2.metalArmor);
	}
	
	/**
	 * Called to heal the armour.
	 * @param stack
	 * @param toHeal
	 */
	@Override
	public abstract void healArmour(ItemStack stack, int toHeal);

	/**
	 * Called to damage the armour.
	 * @param stack
	 * @param damage
	 */
	@Override
	public abstract void damageArmour(ItemStack stack, int damage);

}
