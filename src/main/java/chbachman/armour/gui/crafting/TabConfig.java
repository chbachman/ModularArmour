package chbachman.armour.gui.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import chbachman.api.IUpgrade;
import chbachman.armour.configurability.FieldList;
import chbachman.armour.configurability.FieldList.Storage;
import cofh.lib.gui.GuiProps;
import cofh.lib.gui.element.ElementBase;
import cofh.lib.gui.element.ElementSlider;
import cofh.lib.gui.element.TabBase;
import cofh.lib.gui.element.listbox.SliderHorizontal;
import cofh.lib.render.RenderHelper;

public class TabConfig extends TabBase{

	public static ResourceLocation GRID_TEXTURE = new ResourceLocation(GuiProps.PATH_ELEMENTS + "Slot_Grid_Augment.png");

	protected ArrayList<ElementBase> elements = new ArrayList<ElementBase>();

	ArmourGui armourGui;

	Storage[] storages;
	ElementSlider[] sliders;

	int slotsBorderX1 = 7;
	int slotsBorderY1 = 20;

	ElementSlider slider;

	public TabConfig(ArmourGui gui) {
		super(gui, 1);
		this.backgroundColor = 0x00333;

		this.armourGui = gui;

		this.maxHeight = 110;
		this.maxWidth = 93;

		this.storages = new Storage[0];
		this.sliders = new ElementSlider[0];

		this.slider = new SliderHorizontal(this, 100, 100, 100, 100, 100, 100);
		this.slider.setValue(50);
		this.addElement(this.slider);
	}

	@Override
	public void drawBackground(int mouseX, int mouseY, float gameTicks){
		super.drawBackground();
		if (!this.isFullyOpened()){
			return;
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		RenderHelper.bindTexture(GRID_TEXTURE);

		for (int i = 0; i < this.elements.size(); i++){
			ElementBase element = this.elements.get(i);
			if (element.isVisible()){
				element.drawBackground(mouseX, mouseY, gameTicks);
			}
		}
	}

	@Override
	public void drawForeground(int mouseX, int mouseY){
		for (int i = 0; i < this.elements.size(); i++){
			ElementBase element = this.elements.get(i);
			if (element.isVisible()){
				element.drawForeground(mouseX, mouseY);
			}
		}
	}

	@Override
	public void draw(){

		if (!this.isVisible()){
			return;
		}

		this.drawBackground(this.armourGui.getMouseX(), this.armourGui.getMouseY(), this.armourGui.gameTick);
		this.drawForeground(this.armourGui.getMouseX(), this.armourGui.getMouseY());
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

			// this.getFontRenderer().drawStringWithShadow("No Upgrade",
			// this.posX + 3, 87, -1);
			// this.getFontRenderer().drawStringWithShadow("Selected Yet!",
			// this.posX + 3, 97, -1);
		}

		this.getFontRenderer().drawStringWithShadow("Upgrades", this.posXOffset() + 18, this.posY + 8, this.headerColor);

	}

	@Override
	public void addTooltip(List<String> list){

		if (!this.isFullyOpened()){
			list.add("Upgrade");
		}
	}

	public boolean isCoordsInBorders(int xCoord, int yCoord, int x, int x2, int y, int y2){
		return xCoord > x && xCoord < x2 && yCoord > y && yCoord < y2;
	}

	@Override
	protected void drawBackground(){
		super.drawBackground();

		if (!this.isFullyOpened()){
			return;
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		RenderHelper.bindTexture(GRID_TEXTURE);
	}

	@Override
	public void setFullyOpen(){
		super.setFullyOpen();

	}

	@Override
	public void toggleOpen(){
		super.toggleOpen();
	}

	public void onUpgradeSelected(){
		IUpgrade upgrade = this.armourGui.selectedUpgrade;

		if (upgrade == null){
			return;
		}

		this.storages = FieldList.fieldList.get(upgrade);

	}

	@Override
	public boolean onMouseWheel(int mouseX, int mouseY, int movement){
		super.onMouseWheel(mouseX, mouseY, movement);
		int wheelMovement = Mouse.getEventDWheel();

		if (wheelMovement != 0){
			for (int i = this.elements.size(); i-- > 0;){
				ElementBase c = this.elements.get(i);
				if (!c.isVisible() || !c.isEnabled() || !c.intersectsWith(mouseX, mouseY)){
					continue;
				}
				if (c.onMouseWheel(mouseX, mouseY, wheelMovement)){
					return true;
				}
			}
		}

		return true;
	}

	@Override
	public boolean onKeyTyped(char characterTyped, int keyPressed){
		super.onKeyTyped(characterTyped, keyPressed);
		for (int i = this.elements.size(); i-- > 0;){
			ElementBase c = this.elements.get(i);
			if (!c.isVisible() || !c.isEnabled()){
				continue;
			}
			if (c.onKeyTyped(characterTyped, keyPressed)){
				return true;
			}
		}
		return super.onKeyTyped(characterTyped, keyPressed);
	}

	@Override
	public boolean onMousePressed(int mouseX, int mouseY, int mouseButton){
		super.onMousePressed(mouseX, mouseY, mouseButton);
		for (int i = this.elements.size(); i-- > 0;){
			ElementBase c = this.elements.get(i);
			if (!c.isVisible() || !c.isEnabled() || !c.intersectsWith(mouseX, mouseY)){
				continue;
			}
			if (c.onMousePressed(mouseX, mouseY, mouseButton)){
				return true;
			}
		}

		return super.onMousePressed(mouseX, mouseY, mouseButton);
	}

	@Override
	public void onMouseReleased(int mouseX, int mouseY){

		super.onMouseReleased(mouseX, mouseY);

		for (int i = this.elements.size(); i-- > 0;){
			ElementBase c = this.elements.get(i);
			if (!c.isVisible() || !c.isEnabled()){ // no bounds checking on
													// mouseUp events
				continue;
			}
			c.onMouseReleased(mouseX, mouseY);
		}
	}

	/* Elements */
	public ElementBase addElement(ElementBase element){

		this.elements.add(element);
		return element;
	}

	protected ElementBase getElementAtPosition(int mX, int mY){

		for (int i = this.elements.size(); i-- > 0;){
			ElementBase element = this.elements.get(i);
			if (element.intersectsWith(mX, mY)){
				return element;
			}
		}
		return null;
	}

}
