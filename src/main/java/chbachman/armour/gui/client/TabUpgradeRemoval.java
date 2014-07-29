package chbachman.armour.gui.client;

import java.util.List;

import chbachman.armour.gui.container.ArmourContainer;
import cofh.gui.element.TabBase;

public class TabUpgradeRemoval extends TabBase{
    
    public static final int defaultSide = 1; 
    public static int defaultBackgroundColor = 0x088A68;
    
    public final ArmourGui gui;
    public final ArmourContainer container;
    
    public TabUpgradeRemoval(ArmourGui gui) {
        super(gui, defaultSide);
        
        this.gui = gui;
        this.container = gui.container;
        
        this.backgroundColor = defaultBackgroundColor;
        
        maxHeight = 100;
        maxWidth = 110;
    }
    
    public void draw(){
        if (!isVisible()) {
            return;
        }
        drawBackground();
        
        drawTabIcon("IconUpgrade");
        drawTabIcon("IconNope");
        
        if (!isFullyOpened()) {
            return;
        }
        
        if(this.gui.selectedUpgrade == null){
            
            getFontRenderer().drawStringWithShadow("Please select an", posXOffset() + 2, posY + 22, -1);
            getFontRenderer().drawStringWithShadow("upgrade from the", posXOffset() + 2, posY + 32, -1);
            getFontRenderer().drawStringWithShadow("left.", posXOffset() + 2, posY + 42, -1);
            
            
        }else{
            
            if(this.isCoordsInBorders(this.gui.getMouseX() - this.currentShiftX, this.gui.getMouseY() - this.currentShiftY, 2, 2 + 16, 22, 22 + 16)){
                gui.drawButton("IconCancel", this.currentShiftX + 2, this.currentShiftY + 22, 1, 1);
            }else{
                gui.drawButton("IconCancel", this.currentShiftX + 2, this.currentShiftY + 22, 1, 0);
            }
            
            //gui.drawButton("IconCancel", this.currentShiftX + 2, this.currentShiftY + 22, 1, 1);
            getFontRenderer().drawStringWithShadow("Remove this ", posXOffset() + 18, posY + 22, -1);
            getFontRenderer().drawStringWithShadow("Upgrade?", posXOffset() + 18, posY + 32, -1);
            
            
        }
        
        getFontRenderer().drawStringWithShadow("Upgrade Removal", posXOffset() + 18, posY + 8, headerColor);
    }
    
    @Override
    public void addTooltip(List<String> list) {

        if(!isFullyOpened()){
            list.add("Upgrade Removal");
        }
        
        if(this.isCoordsInBorders(this.gui.getMouseX() - this.currentShiftX, this.gui.getMouseY() - this.currentShiftY, 2, 2 + 16, 22, 22 + 16)){
            list.add("Remove Upgrade");
        }
    }
    
    @Override
    public boolean onMousePressed(int mouseX, int mouseY, int mouseButton) {

        if (!isFullyOpened()) {
            return false;
        }

        mouseX -= currentShiftX;
        mouseY -= currentShiftY;
        
        if(this.isCoordsInBorders(mouseX, mouseY, 2, 2 + 16, 22, 22 + 16)){
            this.gui.onButtonClick("RemoveUpgrade");
        }
        
        if (isCoordsInBorders(mouseX, mouseY, 0, 22, 0 ,22)) {
            return false;
        }
       
        return true;
    }
    
    public boolean isCoordsInBorders(int xCoord, int yCoord, int x, int x2, int y, int y2){
        return xCoord > x && xCoord < x2 && yCoord > y && yCoord < y2;
    }
    
}
