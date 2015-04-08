package chbachman.armour.gui.recipe;

import java.util.List;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import chbachman.api.item.IModularItem;
import chbachman.api.upgrade.IUpgrade;
import cofh.lib.gui.GuiProps;
import cofh.lib.gui.element.TabBase;
import cofh.lib.render.RenderHelper;

public class TabCompatible extends TabBase{

	ArmourGuiRecipe armourGui;

	ItemStack[] modularItems = ArmourContainerRecipe.modularItems;

	private int startIndex = 0;
	private int maxItems = 6;
	
	public static ResourceLocation GRID_TEXTURE = new ResourceLocation(GuiProps.PATH_ELEMENTS + "Slot_Grid_Augment.png");

	public TabCompatible(ArmourGuiRecipe gui) {
		super(gui, 1);

		this.armourGui = gui;
		this.maxHeight = Math.min(modularItems.length * 18 + 28, 28 + 18 * maxItems);
		this.maxWidth = 42;
	}

	@Override
	public void draw() {
		super.draw();
		if (!this.isVisible()) {
			return;
		}

		this.drawBackground();

		if(this.fullyOpen == true)
			this.drawForeground(this.currentShiftX, this.currentShiftY);

		if(this.isCompatible(armourGui.container.item)){
			this.drawTabIcon("IconAccept");
		}else{
			this.drawTabIcon("IconCancel");
		}
		
		if (!this.isFullyOpened()) {
			return;
		}
	}

	@Override
    public void addTooltip(List<String> list) {
        
        if (!this.isFullyOpened()) {
            list.add("Compatible?");
        }
    }

	@Override
	public void drawForeground(int arg0, int arg1) {
		super.drawForeground(arg0, arg1);

		if(!this.isFullyOpened()){
			return;
		}
		
		for(int i = 0; i < maxItems; i++){

			IModularItem modularItem = (IModularItem) modularItems[startIndex + i].getItem();

			String iconName;

			if(this.isCompatible(modularItem)){
				iconName = "IconAccept";
			}else{
				iconName = "IconCancel";
			}

			this.gui.drawIcon(iconName, 21 + this.currentShiftX, 25 + i * 18 + this.currentShiftY, 1);

		}
	}

	@Override
	protected void drawBackground() {
		super.drawBackground();

		if (!this.isFullyOpened()) {
			return;
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		RenderHelper.bindTexture(GRID_TEXTURE);

		for(int i = 0; i < Math.min(modularItems.length, this.maxItems); i++){
			this.drawSlots(0, i, 1);
		}
	}

	@Override
	public void setFullyOpen() {
		super.setFullyOpen();

		for (int i = 9; i < 9 + modularItems.length; i++) {
			this.displaySlots(true);
		}
	}

	@Override
	public void toggleOpen() {
		if (this.open) {
			for (int i = 9; i < 9 + modularItems.length; i++) {
				this.displaySlots(false);
			}

		}
		super.toggleOpen();
	}

	int slotsBorderX1 = -2;
	int slotsBorderY1 = 20;

	@Override
	public boolean onMouseWheel(int mouseX, int mouseY, int movement){
		if(!super.onMouseWheel(mouseX, mouseY, movement)){
			return false;
		}
		
		this.startIndex += Math.signum(movement);
		
		if(startIndex > this.modularItems.length - this.maxItems){
			this.startIndex = this.modularItems.length - this.maxItems;
		}
		
		if(this.startIndex < 0){
			this.startIndex = 0;
		}
		
		displaySlots(this.open);
		return true;
	}
	
	private void updateSlots(){
		
		for(int i = 0; i < this.maxItems; i++){
			int slotNum = i + 9;
			
			((Slot) this.armourGui.container.inventorySlots.get(slotNum + startIndex)).xDisplayPosition = this.posXOffset() + this.slotsBorderX1 + 4;
			((Slot) this.armourGui.container.inventorySlots.get(slotNum + startIndex)).yDisplayPosition = this.posY + this.slotsBorderY1 + 4 + 18 * i;
		}
		
		for(int i = 0; i < this.modularItems.length; i++){
			int slotNum = i + 9;
			
			if(i >= startIndex && i < startIndex + this.maxItems){
				continue;
			}
			
			((Slot) this.armourGui.container.inventorySlots.get(slotNum)).xDisplayPosition = -this.gui.getGuiLeft() - 16;
			((Slot) this.armourGui.container.inventorySlots.get(slotNum)).yDisplayPosition = -this.gui.getGuiTop() - 16;
			
		}
	}
	
	private void displaySlots(boolean shouldDisplay){
		
		if(shouldDisplay){
			this.updateSlots();
		}else{
			for(int i = 0; i < modularItems.length; i++){
				((Slot) this.armourGui.container.inventorySlots.get(i + 9)).xDisplayPosition = -this.gui.getGuiLeft() - 16;
				((Slot) this.armourGui.container.inventorySlots.get(i + 9)).yDisplayPosition = -this.gui.getGuiTop() - 16;
			}
		}
		
	}
	
	private void drawSlots(int xOffset, int yOffset, int slots) {
		this.gui.drawSizedTexturedModalRect(this.posXOffset() + this.slotsBorderX1 + 3 + 9 * xOffset, this.posY + this.slotsBorderY1 + 3 + 18 * yOffset, 0, 0, 18 * slots, 18, 72, 18);
	}
	
	boolean isCompatible(IModularItem item){
    	IUpgrade upgrade = this.armourGui.container.recipe.getRecipeOutput();
    	return upgrade.isCompatible(item, this.armourGui.container.stack, item.getSlot());
    }


}
