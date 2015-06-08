package chbachman.armour.gui.crafting;

import java.util.List;

import modulararmour.cofh.core.network.PacketHandler;
import modulararmour.cofh.lib.gui.GuiBase;
import modulararmour.cofh.lib.gui.GuiProps;
import modulararmour.cofh.lib.gui.element.TabBase;
import modulararmour.cofh.lib.gui.element.listbox.SliderHorizontal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import chbachman.api.configurability.ConfigurableField;
import chbachman.api.configurability.FieldList;
import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.IUpgrade;
import chbachman.armour.gui.ElementText;
import chbachman.armour.network.ArmourPacket;
import chbachman.armour.network.ArmourPacket.PacketTypes;

public class TabConfig extends TabBase{

	public static ResourceLocation GRID_TEXTURE = new ResourceLocation(GuiProps.PATH_ELEMENTS + "Slot_Grid_Augment.png");

	ArmourGui armourGui;

	ConfigurableField[] storages;

	public TabConfig(ArmourGui gui) {
		super(gui, 0);
		this.backgroundColor = 0x000FF;

		this.armourGui = gui;

		this.maxHeight = 110;
		this.maxWidth = 100;

		this.storages = new ConfigurableField[0];
	}

	@Override
	public void drawForeground(){

		if (!this.isVisible()){
			return;
		}

		this.drawTabIcon("IconUpgrade");
		if (!this.isFullyOpened()){
			return;
		}

		this.getFontRenderer().drawStringWithShadow("Upgrades", this.posXOffset() + 18, this.posY + 8, this.headerColor);

	}

	@Override
	public void addTooltip(List<String> list){

		super.addTooltip(list);

		if (!this.isFullyOpened()){
			list.add("Config");
		}
	}

	
	
	@Override
	public void setFullyOpen(){
		super.setFullyOpen();
		
		this.onUpgradeSelected(this.armourGui.selectedUpgrade);
	}

	public void onUpgradeSelected(IUpgrade upgrade){
		
		if(!this.fullyOpen){
			return;
		}
		
		if (upgrade == null){
			return;
		}

		this.storages = UpgradeRegistry.getListenerForUpgrade(upgrade, FieldList.class).getFieldList(upgrade);

		if (storages == null){
			storages = new ConfigurableField[0];
		}

		this.elements.clear();

		for (int i = 0; i < storages.length; i++){
			ConfigurableField s = storages[i];
			this.addElement(new SliderUpgrade(this.gui, s, this.armourGui.stack, 10, 30 + 20 * i, 60, 10, 100).setValue(s.get(this.armourGui.stack).getAmount()));
			this.addElement(new ElementText(this.gui, 10, 20 + 20 * i, 100, 10).setString(s.displayName));
		}

	}

	private static class SliderUpgrade extends SliderHorizontal{

		final ConfigurableField field;
		final ItemStack armourPiece;

		public SliderUpgrade(GuiBase containerScreen, ConfigurableField field, ItemStack armour, int x, int y, int width, int height, int maxValue) {
			super(containerScreen, x, y, width, height, maxValue);
			this.field = field;
			this.armourPiece = armour;
		}

		public SliderUpgrade(GuiBase containerScreen, ConfigurableField field, ItemStack armour, int x, int y, int width, int height, int maxValue, int minValue) {
			super(containerScreen, x, y, width, height, maxValue, minValue);
			this.field = field;
			this.armourPiece = armour;
		}

		@Override
		public void onValueChanged(int value){
			field.set(armourPiece, value);
			PacketHandler.sendToServer(ArmourPacket.getPacket(PacketTypes.BUTTON).addString("ValueChanged").addString(field.getKey()).addInt(value));
		}

		@Override
		public void drawForeground(int mouseX, int mouseY){
			super.drawForeground(mouseX, mouseY);
			this.getFontRenderer().drawString(this._value + "%", this.posX + this.getWidth() + 5, this.posY, 0xFFFFFFFF);
		}

	}

}
