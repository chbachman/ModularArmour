package chbachman.armour.gui.element;

import net.minecraft.item.ItemStack;
import chbachman.api.nbt.helper.NBTHelper;
import chbachman.api.nbt.helper.NBTList;
import chbachman.api.upgrade.IUpgrade;
import chbachman.armour.gui.crafting.ArmourGui;
import cofh.lib.gui.element.ElementListBox;
import cofh.lib.gui.element.listbox.IListBoxElement;
import cofh.lib.gui.element.listbox.ListBoxElementText;

public class ElementUpgradeListBox extends ElementListBox{

	public ElementUpgradeListBox(ArmourGui containerScreen, int x, int y, int width, int height) {
		super(containerScreen, x, y, width, height);
	}

	public void loadStack(ItemStack stack){
		NBTList<IUpgrade> textLines = NBTHelper.getNBTUpgradeList(stack);
		
		for(IUpgrade upgrade : textLines){
			this.add(new ListBoxElementUpgrade(upgrade));
		}
	}
	
	@Override
	protected void onElementClicked(IListBoxElement element) {
		
	}

	@Override
	protected void onScrollH(int newStartIndex) {
		
	}

	@Override
	protected void onScrollV(int newStartIndex) {
		
	}

	@Override
	protected void onSelectionChanged(int newIndex, IListBoxElement newElement) {
		((ArmourGui) this.gui).onUpgradeSelected(getUpgrade(newElement));
	}
	
	public IUpgrade getSelectedUpgrade(){
		return getUpgrade(this.getSelectedElement());
	}
	
	private IUpgrade getUpgrade(IListBoxElement element){
		return ((ListBoxElementUpgrade) element).upgrade;
	}
	
	private static class ListBoxElementUpgrade extends ListBoxElementText{
		
		private final IUpgrade upgrade;
		
		public ListBoxElementUpgrade(IUpgrade upgrade){
			super(upgrade.getName());
			this.upgrade = upgrade;
		}
		
	}

}
