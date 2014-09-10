package chbachman.armour.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import thaumcraft.api.IGoggles;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.IRevealer;
import chbachman.api.IModularItem;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.register.Thaumcraft;
import chbachman.armour.util.NBTHelper;
import chbachman.armour.util.NBTUpgradeList;

public class ItemModularArmourModded extends ItemModularArmour implements IGoggles, IVisDiscountGear, IRevealer{

	public ItemModularArmourModded(ArmorMaterial material, int type) {
		super(material, type);
	}

	@Override
	public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {
		NBTUpgradeList list = NBTHelper.getNBTUpgradeList(itemstack);

		return list.contains(Thaumcraft.gogglesOfRevealing);
	}

	@Override
	public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect) {
		NBTUpgradeList list = NBTHelper.getNBTUpgradeList(stack);
		
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
				case UNKNOWN: return 0;
				default: return 0;
				}
			}
		}
		
		return 0;
	}

	@Override
	public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase player) {
		NBTUpgradeList list = NBTHelper.getNBTUpgradeList(itemstack);

		return list.contains(Thaumcraft.gogglesOfRevealing);
	}

}
