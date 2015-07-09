package chbachman.armour.gui.element;

import java.util.Collections;
import java.util.List;

import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import chbachman.armour.gui.crafting.ArmourGui;
import cofh.lib.gui.GuiProps;
import cofh.lib.gui.element.TabBase;
import cofh.lib.render.RenderHelper;
import cofh.lib.util.helpers.StringHelper;

public class TabCrafting extends TabBase {
    
    public static ResourceLocation GRID_TEXTURE = new ResourceLocation(GuiProps.PATH_ELEMENTS + "Slot_Grid_Augment.png");
    
    ArmourGui armourGui;
    
    int slotsBorderX1 = 7;
    int slotsBorderY1 = 20;
    
    ElementButtonIcon accept;
    ElementButtonIcon recipe;
    ElementButtonIcon cancel;
    
    public TabCrafting(ArmourGui gui) {
        super(gui, 1);
        this.backgroundColor = 0x0033FF;
        
        this.armourGui = gui;
        
        this.maxHeight = 110;
        this.maxWidth = 93;
        this.moveSlots(open);
        
        this.accept = new ElementButtonIcon(this.gui, gui.getIcon("IconAccept"), 70, 24, 16, 16);
        this.recipe = new ElementButtonIcon(this.gui, gui.getIcon("IconRecipe"), 70, 60, 16, 16);
        this.cancel = new ElementButtonIcon(this.gui, gui.getIcon("IconCancel"), 70, 42, 16, 16);
        
        accept.addTooltip(Collections.singletonList("Add Upgrade"));
        recipe.addTooltip(Collections.singletonList("Recipes"));
        cancel.addTooltip(Collections.singletonList("Remove Items"));
        
        this.addElement(accept);
        this.addElement(cancel);
        this.addElement(recipe);
        
        accept.setActionName("UpgradeAddition");
        cancel.setActionName("RemoveItems");
        recipe.setActionName("Recipe");
    }
    
    @Override
	public void drawBackground(int mouseX, int mouseY, float gameTicks) {
		super.drawBackground(mouseX, mouseY, gameTicks);
		this.drawTabIcon("IconUpgrade");
		
		if(this.isFullyOpened()){
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        
	        RenderHelper.bindTexture(GRID_TEXTURE);
	        
	        this.drawSlots(0, 0, 3);
	        this.drawSlots(0, 1, 3);
	        this.drawSlots(0, 2, 3);
		}
		
		this.accept.setEnabled(this.armourGui.container.upgrade != null);
	}

	@Override
	public void drawForeground(int mouseX, int mouseY) {
		super.drawForeground(mouseX, mouseY);
		
		if (!this.isFullyOpened()) {
            return;
        }
        
        if (this.armourGui.container.upgrade != null) {
            
            List<String> list = this.getFontRenderer().listFormattedStringToWidth(StringHelper.localize(this.armourGui.container.upgrade.getName()), 70);
            
            for (int i = 0; i < list.size(); i++) {
                String lineToDraw = this.getFontRenderer().trimStringToWidth(list.get(i), 90);
                this.getFontRenderer().drawStringWithShadow(lineToDraw, this.posX + 3, 87 + 10 * i, -1);
            }
            
            
        } else {
            
            this.getFontRenderer().drawStringWithShadow("No Upgrade", this.posX + 3, 87, -1);
            this.getFontRenderer().drawStringWithShadow("Selected Yet!", this.posX + 3, 97, -1);
        }
        
        this.getFontRenderer().drawStringWithShadow("Upgrades", this.posXOffset() + 18, this.posY + 8, this.headerColor);
	}
    
    @Override
    public void addTooltip(List<String> list) {
        super.addTooltip(list);
        
        if (!this.isFullyOpened()) {
            list.add("Upgrade");
        }
    }
    
    @Override
    public boolean onMousePressed(int mouseX, int mouseY, int mouseButton) {
    	super.onMousePressed(mouseX, mouseY, mouseButton);
    	
    	mouseX -= this.currentShiftX;
    	mouseY -= this.currentShiftY;
    	
    	return !isCoordsInBorders(mouseX, mouseY, 0, 16, 0, 16);
    }
    
    public boolean isCoordsInBorders(int xCoord, int yCoord, int x, int x2, int y, int y2) {
        return xCoord > x && xCoord < x2 && yCoord > y && yCoord < y2;
    }
    
    @Override
    protected void drawBackground() {
        super.drawBackground();
    }
    
    @Override
    public void setFullyOpen() {
        super.setFullyOpen();
        
        this.moveSlots(true);
    }
    
    @Override
    public void toggleOpen() {
        if (this.open) {
            this.moveSlots(false);
        }
        super.toggleOpen();
    }
    
    private void drawButton(String name, int xShift, int yShift, boolean active){
        if(active){
            if (this.isCoordsInBorders(this.gui.getMouseX() - this.currentShiftX, this.gui.getMouseY() - this.currentShiftY, xShift, xShift + 16, yShift, yShift + 16)) {
                this.gui.drawButton(name, this.posX + xShift, this.posY + yShift, 1, 1);
            } else {
                this.gui.drawButton(name, this.posX + xShift, this.posY + yShift, 1, 0);
            }
        }else{
            this.gui.drawButton(name, this.posX + xShift, this.posY + yShift, 1, 2);
        }
        
        
    }
    
    private void moveSlots(boolean isOpen){
    	if (!isOpen) {
            for (int i = 0; i < 9; i++) {
                ((Slot) this.armourGui.container.inventorySlots.get(i)).xDisplayPosition = -this.gui.getGuiLeft() - 16;
                ((Slot) this.armourGui.container.inventorySlots.get(i)).yDisplayPosition = -this.gui.getGuiTop() - 16;
            }
            
        }else{
        	for (int i = 0; i < 9; i++) {
                ((Slot) this.armourGui.container.inventorySlots.get(i)).xDisplayPosition = this.posXOffset() + this.slotsBorderX1 + 4 + 18 * (i % 3);
                ((Slot) this.armourGui.container.inventorySlots.get(i)).yDisplayPosition = this.posY + this.slotsBorderY1 + 4 + 18 * (i / 3);
            }
        }
    }
    
    private void drawSlots(int xOffset, int yOffset, int slots) {
        this.gui.drawSizedTexturedModalRect(this.posXOffset() + this.slotsBorderX1 + 3 + 9 * xOffset, this.posY + this.slotsBorderY1 + 3 + 18 * yOffset, 0, 0, 18 * slots, 18, 96, 32);
    }
    
}
