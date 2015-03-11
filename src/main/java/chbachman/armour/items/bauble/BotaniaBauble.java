package chbachman.armour.items.bauble;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import vazkii.botania.api.mana.IManaItem;
import chbachman.armour.items.holder.ManaHolder;
import cpw.mods.fml.common.Optional;

@Optional.Interface(modid = "Botania", iface = "vazkii.botania.api.mana.IManaItem")
public class BotaniaBauble extends ItemBauble implements IManaItem{

	public BotaniaBauble() {
		this.holder = new ManaHolder(this);
	}

	public ManaHolder getHolder(){
		return (ManaHolder) this.getHolder();
	}

	// IManaItem
	@Override
	@Optional.Method(modid = "Botania")
	public int getMana(ItemStack stack){
		return this.getHolder().getMana(stack);
	}

	@Override
	@Optional.Method(modid = "Botania")
	public int getMaxMana(ItemStack stack){
		return this.getHolder().getMaxMana(stack);
	}

	@Override
	@Optional.Method(modid = "Botania")
	public void addMana(ItemStack stack, int mana){
		this.getHolder().addMana(stack, mana);
	}

	@Override
	@Optional.Method(modid = "Botania")
	public boolean canReceiveManaFromPool(ItemStack stack, TileEntity pool){
		return this.getHolder().canReceiveManaFromPool(stack, pool);
	}

	@Override
	@Optional.Method(modid = "Botania")
	public boolean canReceiveManaFromItem(ItemStack stack, ItemStack otherStack){
		return this.getHolder().canReceiveManaFromItem(stack, otherStack);
	}

	@Override
	@Optional.Method(modid = "Botania")
	public boolean canExportManaToPool(ItemStack stack, TileEntity pool){
		return this.getHolder().canExportManaToPool(stack, pool);
	}

	@Override
	@Optional.Method(modid = "Botania")
	public boolean canExportManaToItem(ItemStack stack, ItemStack otherStack){
		return this.getHolder().canExportManaToItem(stack, otherStack);
	}

	@Override
	@Optional.Method(modid = "Botania")
	public boolean isNoExport(ItemStack stack){
		return this.getHolder().isNoExport(stack);
	}

}
