package chbachman.armour.gui.crafting;

import java.util.List;

import repack.cofh.lib.gui.element.TabBase;
import chbachman.armour.gui.GuiHelper;

public class TabUpgradeRemoval extends TabBase {
    
    public final ArmourGui gui;
    public final ArmourContainer container;
    
    public TabUpgradeRemoval(ArmourGui gui) {
        super(gui, 1);
        
        this.gui = gui;
        this.container = gui.container;
        
        this.backgroundColor = 0x088A68;
        
        this.maxHeight = 100;
        this.maxWidth = 110;
    }
    
    @Override
    public void draw() {
        if (!this.isVisible()) {
            return;
        }
        this.drawBackground();
        
        this.drawTabIcon("IconUpgrade");
        this.drawTabIcon("IconNope");
        
        if (!this.isFullyOpened()) {
            return;
        }
        
        if (this.gui.selectedUpgrade == null) {
            
            this.getFontRenderer().drawStringWithShadow("Please select an", this.posXOffset() + 2, this.posY + 22, -1);
            this.getFontRenderer().drawStringWithShadow("upgrade from the", this.posXOffset() + 2, this.posY + 32, -1);
            this.getFontRenderer().drawStringWithShadow("left.", this.posXOffset() + 2, this.posY + 42, -1);
            
        } else {
            
            if (GuiHelper.isCoordsInBorders(this.gui.getMouseX() - this.currentShiftX, this.gui.getMouseY() - this.currentShiftY, 2, 2 + 16, 22, 22 + 16)) {
                this.gui.drawButton("IconCancel", this.currentShiftX + 2, this.currentShiftY + 22, 1, 1);
            } else {
                this.gui.drawButton("IconCancel", this.currentShiftX + 2, this.currentShiftY + 22, 1, 0);
            }
            
            //TODO: Add Upgrade Disabler Here
            
            // gui.drawButton("IconCancel", this.currentShiftX + 2,
            // this.currentShiftY + 22, 1, 1);
            this.getFontRenderer().drawStringWithShadow("Remove this ", this.posXOffset() + 18, this.posY + 22, -1);
            this.getFontRenderer().drawStringWithShadow("Upgrade?", this.posXOffset() + 18, this.posY + 32, -1);
            
        }
        
        this.getFontRenderer().drawStringWithShadow("Upgrade Removal", this.posXOffset() + 18, this.posY + 8, this.headerColor);
    }
    
    @Override
    public void addTooltip(List<String> list) {
        
        if (!this.isFullyOpened()) {
            list.add("Upgrade Removal");
        }
        
        if (GuiHelper.isCoordsInBorders(this.gui.getMouseX() - this.currentShiftX, this.gui.getMouseY() - this.currentShiftY, 2, 2 + 16, 22, 22 + 16)) {
            list.add("Remove Upgrade");
        }
    }
    
    @Override
    public boolean onMousePressed(int mouseX, int mouseY, int mouseButton) {
        
        if (!this.isFullyOpened()) {
            return false;
        }
        
        mouseX -= this.currentShiftX;
        mouseY -= this.currentShiftY;
        
        if (GuiHelper.isCoordsInBorders(mouseX, mouseY, 2, 2 + 16, 22, 22 + 16)) {
            this.gui.onButtonClick("RemoveUpgrade");
        }
        
        if (GuiHelper.isCoordsInBorders(mouseX, mouseY, 0, 22, 0, 22)) {
            return false;
        }
        
        return true;
    }
    
}
