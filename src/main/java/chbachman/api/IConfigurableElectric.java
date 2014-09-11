package chbachman.api;

import net.minecraft.item.ItemStack;

public interface IConfigurableElectric {
	
	public int getCapacity(ItemStack stack);
	
	public void setCapacity(ItemStack stack, int amount);
	
	public int getEnergyPerDamage(ItemStack stack);
	
	public void setEnergyPerDamage(ItemStack stack, int amount);
	
	public int getMaxTransfer(ItemStack stack);
	
	public void setMaxTransfer(ItemStack stack, int amount);

}
