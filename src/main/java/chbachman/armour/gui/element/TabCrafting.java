package chbachman.armour.gui.element;

import java.util.List;

import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import chbachman.armour.gui.crafting.ArmourGui;
import cofh.lib.gui.GuiBase;
import cofh.lib.gui.GuiProps;
import cofh.lib.gui.element.TabBase;
import cofh.lib.render.RenderHelper;

public class TabCrafting extends TabBase {
    
    public static ResourceLocation GRID_TEXTURE = new ResourceLocation(GuiProps.PATH_ELEMENTS + "Slot_Grid_Augment.png");
    
    ArmourGui armourGui;
    
    int slotsBorderX1 = 7;
    int slotsBorderY1 = 20;
    
    public TabCrafting(ArmourGui gui) {
        super(gui, 1);
        this.backgroundColor = 0x0033FF;
        
        this.armourGui = gui;
        
        this.maxHeight = 110;
        this.maxWidth = 93;
        this.moveSlots(open);
    }
    
    @Override
    public void draw() {
        
        if (!this.isVisible()) {
            return;
        }
        
        this.drawBackground();
        
        this.drawTabIcon("IconUpgrade");
        if (!this.isFullyOpened()) {
            return;
        }
        
        if (this.armourGui.container.upgrade != null) {
            
            @SuppressWarnings("unchecked")
            List<String> list = this.getFontRenderer().listFormattedStringToWidth(this.armourGui.container.upgrade.getName(), 70);
            
            for (int i = 0; i < list.size(); i++) {
                String lineToDraw = this.getFontRenderer().trimStringToWidth(list.get(i), 90);
                this.getFontRenderer().drawStringWithShadow(lineToDraw, this.posX + 3, 87 + 10 * i, -1);
            }
            
            this.drawButton("IconAccept", 70, 24, true);
            
        } else {
            this.drawButton("IconAcceptInactive", 70, 24, false);
            
            this.getFontRenderer().drawStringWithShadow("No Upgrade", this.posX + 3, 87, -1);
            this.getFontRenderer().drawStringWithShadow("Selected Yet!", this.posX + 3, 97, -1);
        }
        
        this.drawButton("IconRecipe", 70, 60, true);
        
        this.drawButton("IconCancel", 70, 42, true);
        
        this.getFontRenderer().drawStringWithShadow("Upgrades", this.posXOffset() + 18, this.posY + 8, this.headerColor);
        
    }
    
    @Override
    public void addTooltip(List<String> list) {
        
        if (!this.isFullyOpened()) {
            list.add("Upgrade");
        }
        
        if (this.armourGui.container.upgrade != null && this.isCoordsInBorders(this.gui.getMouseX() - this.currentShiftX, this.gui.getMouseY() - this.currentShiftY, 70, 70 + 16, 24, 24 + 16)) {
            list.add("Add Upgrade");
        }
        
        if (this.isCoordsInBorders(this.gui.getMouseX() - this.currentShiftX, this.gui.getMouseY() - this.currentShiftY, 70, 70 + 16, 42, 42 + 16)) {
            list.add("Remove Items");
        }
        
        if (this.isCoordsInBorders(this.gui.getMouseX() - this.currentShiftX, this.gui.getMouseY() - this.currentShiftY, 70, 70 + 16, 60, 60 + 16)) {
            list.add("Recipes");
        }
    }
    
    @Override
    public boolean onMousePressed(int mouseX, int mouseY, int mouseButton) {
        
        if (!this.isFullyOpened()) {
            return false;
        }
        mouseX -= this.currentShiftX;
        mouseY -= this.currentShiftY;
        
        if (this.isCoordsInBorders(mouseX, mouseY, 0, 22, 0, 22)) {
            return false;
        }
        
        if (this.isCoordsInBorders(mouseX, mouseY, 70, 70 + 16, 24, 24 + 16)) {
            this.armourGui.onButtonClick("UpgradeAddition");
            GuiBase.playSound("random.click", 1.0F, 0.4F);
        }
        
        if (this.isCoordsInBorders(mouseX, mouseY, 70, 70 + 16, 42, 42 + 16)) {
            this.armourGui.onButtonClick("RemoveItems");
            GuiBase.playSound("random.click", 1.0F, 0.4F);
        }
        
        if (this.isCoordsInBorders(mouseX, mouseY, 70, 70 + 16, 60, 60 + 16)) {
            this.armourGui.onButtonClick("Recipe");
            GuiBase.playSound("random.click", 1.0F, 0.4F);
        }
        
        return true;
    }
    
    public boolean isCoordsInBorders(int xCoord, int yCoord, int x, int x2, int y, int y2) {
        return xCoord > x && xCoord < x2 && yCoord > y && yCoord < y2;
    }
    
    @Override
    protected void drawBackground() {
        super.drawBackground();
        
        if (!this.isFullyOpened()) {
            return;
        }
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        RenderHelper.bindTexture(GRID_TEXTURE);
        
        this.drawSlots(0, 0, 3);
        this.drawSlots(0, 1, 3);
        this.drawSlots(0, 2, 3);
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
