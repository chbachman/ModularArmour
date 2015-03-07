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

	public static ResourceLocation GRID_TEXTURE = new ResourceLocation(GuiProps.PATH_ELEMENTS + "Slot_Grid_Augment.png");

	public TabCompatible(ArmourGuiRecipe gui) {
		super(gui, 1);

		this.armourGui = gui;
		this.maxHeight = modularItems.length * 18 + 28;
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

		for(int i = 0; i < modularItems.length; i++){

			IModularItem modularItem = (IModularItem) modularItems[i].getItem();

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

		for(int i = 0; i < modularItems.length; i++){
			this.drawSlots(0, i, 1);
		}
	}

	@Override
	public void setFullyOpen() {
		super.setFullyOpen();

		for (int i = 9; i < 9 + modularItems.length; i++) {
			((Slot) this.armourGui.container.inventorySlots.get(i)).xDisplayPosition = this.posXOffset() + this.slotsBorderX1 + 4;
			((Slot) this.armourGui.container.inventorySlots.get(i)).yDisplayPosition = this.posY + this.slotsBorderY1 + 4 + 18 * (i - 9);
		}
	}

	@Override
	public void toggleOpen() {
		if (this.open) {
			for (int i = 9; i < 9 + modularItems.length; i++) {
				((Slot) this.armourGui.container.inventorySlots.get(i)).xDisplayPosition = -this.gui.getGuiLeft() - 16;
				((Slot) this.armourGui.container.inventorySlots.get(i)).yDisplayPosition = -this.gui.getGuiTop() - 16;
			}

		}
		super.toggleOpen();
	}

	int slotsBorderX1 = -2;
	int slotsBorderY1 = 20;

	private void drawSlots(int xOffset, int yOffset, int slots) {
		this.gui.drawSizedTexturedModalRect(this.posXOffset() + this.slotsBorderX1 + 3 + 9 * xOffset, this.posY + this.slotsBorderY1 + 3 + 18 * yOffset, 0, 0, 18 * slots, 18, 72, 18);
	}
	
	boolean isCompatible(IModularItem item){
    	IUpgrade upgrade = this.armourGui.container.recipe.getRecipeOutput();
    	return upgrade.isCompatible(item, this.armourGui.container.stack, item.getSlot());
    }


}
