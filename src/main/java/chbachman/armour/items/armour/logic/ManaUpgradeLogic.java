package chbachman.armour.items.armour.logic;

import java.util.List;

import modulararmour.cofh.lib.util.helpers.StringHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import vazkii.botania.api.mana.IManaItem;
import chbachman.api.item.IModularItem;
import chbachman.api.nbt.helper.NBTInteger;
import chbachman.armour.ModularArmour;
import cpw.mods.fml.common.Optional;

public class ManaUpgradeLogic extends UpgradeLogicAdv implements IManaItem{
	
	public NBTInteger mana = new NBTInteger("mana", 10);
	public NBTInteger maxMana = new NBTInteger("mana", 10);
	
	public ManaUpgradeLogic(IModularItem item) {
		super(item);
	}

	@Override
	public void damageArmour(ItemStack stack, int damage){
		this.mana.set(stack, this.mana.get(stack) - damage * rfToMana);
	}

	@Override
	public void healArmour(ItemStack stack, int toHeal){
		this.addMana(stack, toHeal * rfToMana);
	}

	@Override
	public void addInformation(List<String> list, ItemStack stack){
		if (!StringHelper.isShiftKeyDown()) {
			list.add(StringHelper.shiftForDetails());
		} else {

			list.add(StringHelper.localize("info.cofh.charge") + ": " + this.getMana(stack) + " / " + this.maxMana.get(stack) + " Mana");
		}
		
		super.addInformation(list, stack);
	}

	
	static int rfToMana = ModularArmour.config.get("Conversions", "RF to Mana Conversion Factor", 10);

	//IManaItem
	@Override
	@Optional.Method(modid = "Botania")
	public int getMana(ItemStack stack) {
		return this.mana.get(stack);
	}


	@Override
	@Optional.Method(modid = "Botania")
	public int getMaxMana(ItemStack stack) {
		return this.maxMana.get(stack);
	}


	@Override
	@Optional.Method(modid = "Botania")
	public void addMana(ItemStack stack, int mana) {
		this.mana.set(stack, this.mana.get(stack) + mana);
		
		if(this.mana.get(stack) > this.maxMana.get(stack)){
			this.mana.set(stack, this.maxMana.get(stack));
		}
	}


	@Override
	@Optional.Method(modid = "Botania")
	public boolean canReceiveManaFromPool(ItemStack stack, TileEntity pool) {
		return this.maxMana.get(stack) > this.mana.get(stack);
	}


	@Override
	@Optional.Method(modid = "Botania")
	public boolean canReceiveManaFromItem(ItemStack stack, ItemStack otherStack) {
		return this.maxMana.get(stack) > this.mana.get(stack);
	}


	@Override
	@Optional.Method(modid = "Botania")
	public boolean canExportManaToPool(ItemStack stack, TileEntity pool) {
		return false;
	}


	@Override
	@Optional.Method(modid = "Botania")
	public boolean canExportManaToItem(ItemStack stack, ItemStack otherStack) {
		return false;
	}


	@Override
	@Optional.Method(modid = "Botania")
	public boolean isNoExport(ItemStack stack){
		return true;
	}

}
