package chbachman.armour.items.armour.logic;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import chbachman.api.item.IModularItem;
import chbachman.api.nbt.NBTStorage;
import chbachman.armour.ModularArmour;

public class ICUpgradeLogic extends UpgradeLogicAdv implements IElectricItem{

	Item i;
	
	NBTStorage<Double> maxCharge = new NBTStorage<Double>("maxCharge", 10000D);
	NBTStorage<Integer> tier = new NBTStorage<Integer>("tier", 1);
	NBTStorage<Double> limit = new NBTStorage<Double>("transferLimit", 10000D);
	
	static int rfToEU = ModularArmour.config.get("Conversions", "RF to EU Conversion Factor", 3);
	
	public ICUpgradeLogic(IModularItem item, Item i) {
		super(item);
		this.i = i;
	}
	
	public boolean canProvideEnergy(ItemStack itemStack){
		return true;
	}

	public double getMaxCharge(ItemStack itemStack){
		return maxCharge.get(itemStack);
	}

	public int getTier(ItemStack itemStack){
		return tier.get(itemStack);
	}

	public double getTransferLimit(ItemStack itemStack){
		return limit.get(itemStack);
	}
	

	@Override
	public Item getChargedItem(ItemStack itemStack){
		return i;
	}

	@Override
	public Item getEmptyItem(ItemStack itemStack){
		return i;
	}

	@Override
	public void healArmour(ItemStack stack, int toHeal){
		ElectricItem.manager.charge(stack, toHeal * rfToEU, tier.get(stack), false, false);
	}

	@Override
	public void damageArmour(ItemStack stack, int damage){
		ElectricItem.manager.discharge(stack, damage * rfToEU, tier.get(stack), false, false, false);
	}

}
