package chbachman.armour.items.armour;

import ic2.api.item.IElectricItem;
import ic2.api.item.IMetalArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import chbachman.armour.items.armour.logic.ICUpgradeLogic;

public class ICModularArmour extends ItemModularArmour implements IElectricItem{

	public ICModularArmour(ArmorMaterial material, int type) {
		super(material, type);
		this.holder = new ICUpgradeLogic(this, this);
	}
	
	public ICUpgradeLogic getHolder(){
		return (ICUpgradeLogic) this.holder;
	}
	
	@Override
	public boolean canProvideEnergy(ItemStack itemStack){
		return this.getHolder().canProvideEnergy(itemStack);
	}

	@Override
	public Item getChargedItem(ItemStack itemStack){
		return this.getHolder().getChargedItem(itemStack);
	}

	@Override
	public Item getEmptyItem(ItemStack itemStack){
		return this.getHolder().getEmptyItem(itemStack);
	}

	@Override
	public double getMaxCharge(ItemStack itemStack){
		return this.getHolder().getMaxCharge(itemStack);
	}

	@Override
	public int getTier(ItemStack itemStack){
		return this.getHolder().getTier(itemStack);
	}

	@Override
	public double getTransferLimit(ItemStack itemStack){
		return this.getHolder().getTransferLimit(itemStack);
	}

	@Override
	public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player){
		return true;
	}
	
	

}
