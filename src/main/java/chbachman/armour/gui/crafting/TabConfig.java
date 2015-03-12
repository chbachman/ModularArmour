package chbachman.armour.gui.crafting;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import chbachman.api.configurability.ConfigurableField;
import chbachman.api.configurability.FieldList;
import chbachman.api.upgrade.IUpgrade;
import chbachman.api.util.VariableInt;
import chbachman.armour.gui.ElementText;
import chbachman.armour.network.ArmourPacket;
import chbachman.armour.network.ArmourPacket.PacketTypes;
import cofh.core.network.PacketHandler;
import cofh.lib.gui.GuiBase;
import cofh.lib.gui.GuiProps;
import cofh.lib.gui.element.TabBase;
import cofh.lib.gui.element.listbox.SliderHorizontal;

public class TabConfig extends TabBase{

	public static ResourceLocation GRID_TEXTURE = new ResourceLocation(GuiProps.PATH_ELEMENTS + "Slot_Grid_Augment.png");

	ArmourGui armourGui;

	ConfigurableField[] storages;

	public TabConfig(ArmourGui gui) {
		super(gui, 0);
		this.backgroundColor = 0x000FF;

		this.armourGui = gui;

		this.maxHeight = 110;
		this.maxWidth = 130;

		this.storages = new ConfigurableField[0];

		// this.addElement(new SliderHorizontal(this.gui, 10, 30, 60, 10, 100));
	}

	@Override
	public void draw(){
		super.draw();

		if (!this.isVisible()){
			return;
		}

		this.drawTabIcon("IconUpgrade");
		if (!this.isFullyOpened()){
			return;
		}

		if (this.armourGui.container.upgrade != null){

			@SuppressWarnings("unchecked")
			List<String> list = this.getFontRenderer().listFormattedStringToWidth(this.armourGui.container.upgrade.getName(), 70);

			for (int i = 0; i < list.size(); i++){
				String lineToDraw = this.getFontRenderer().trimStringToWidth(list.get(i), 90);
				this.getFontRenderer().drawStringWithShadow(lineToDraw, this.posX + 3, 87 + 10 * i, -1);
			}

		}else{

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

	public boolean isCoordsInBorders(int xCoord, int yCoord, int x, int x2, int y, int y2){
		return xCoord > x && xCoord < x2 && yCoord > y && yCoord < y2;
	}

	public void onUpgradeSelected(){
		IUpgrade upgrade = this.armourGui.selectedUpgrade;

		if (upgrade == null){
			return;
		}

		this.storages = FieldList.getFieldList(upgrade);

		if (storages == null){
			storages = new ConfigurableField[0];
		}

		this.elements.clear();

		for (int i = 0; i < storages.length; i++){
			ConfigurableField s = storages[i];
			this.addElement(new SliderUpgrade(this.gui, s, this.armourGui.stack, 10, 30 + 20 * i, 60, 10, 100).setValue(s.get(this.armourGui.stack)));
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

		public void onValueChanged(int value){
			field.set(armourPiece, value);
			PacketHandler.sendToServer(ArmourPacket.getPacket(PacketTypes.BUTTON).addString("ValueChanged").addString(field.key).addInt(value));
		}

		@Override
		public void drawForeground(int mouseX, int mouseY){
			super.drawForeground(mouseX, mouseY);
			this.getFontRenderer().drawString(this._value + "%", this.posX + this.getWidth() + 5, this.posY, 12);
		}

	}

}
