package chbachman.armour.items.armour.logic;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import chbachman.api.item.IModularItem;
import chbachman.api.util.VariableInt;
import chbachman.armour.ModularArmour;

public class ICUpgradeLogic extends UpgradeLogicAdv implements IElectricItem{

	Item i;
	
	VariableInt maxCharge = new VariableInt("maxCharge", 10000);
	VariableInt tier = new VariableInt("tier", 1);
	VariableInt limit = new VariableInt("transferLimit", 10000);
	
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
