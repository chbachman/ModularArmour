package chbachman.armour.gui.client;

import java.util.List;

import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cofh.gui.GuiBase;
import cofh.gui.GuiProps;
import cofh.gui.element.TabBase;
import cofh.render.RenderHelper;

public class TabCrafting extends TabBase{
    
    public static int defaultSide = 1;
    public static int defaultBackgroundColor = 0x0033FF;
    
    public static ResourceLocation GRID_TEXTURE = new ResourceLocation(GuiProps.PATH_ELEMENTS + "Slot_Grid_Augment.png");
    
    ArmourGui myContainer;
    
    int slotsBorderX1 = 7;
    int slotsBorderY1 = 20;

    public TabCrafting(ArmourGui container) {
        super(container, defaultSide);
        backgroundColor = defaultBackgroundColor;

        myContainer = container;

        maxHeight = 110;
        maxWidth = 93;
    }
    
    @Override
    public void draw() {

        if (!isVisible()) {
            return;
        }
        
        drawBackground();
        drawTabIcon("IconUpgrade");
        if (!isFullyOpened()) {
            return;
        }
        
        if(this.myContainer.container.upgrade != null){
            
            @SuppressWarnings("unchecked")
            List<String> list = this.getFontRenderer().listFormattedStringToWidth(this.myContainer.container.upgrade.getName(), 70);
            
            for (int i = 0; i < list.size(); i++) {
                String lineToDraw = this.getFontRenderer().trimStringToWidth(list.get(i), 90);
                this.getFontRenderer().drawStringWithShadow(lineToDraw, this.posX + 3, 87 + 10 * i, -1);
            }
            
            if (isCoordsInBorders(this.gui.getMouseX() - this.currentShiftX, this.gui.getMouseY() - this.currentShiftY, 70 ,70 + 16, 24 ,24 + 16)) {
                gui.drawButton("IconAccept", this.posX + 70, this.posY + 24, 1, 1);
            }else{
                gui.drawButton("IconAccept", this.posX + 70, this.posY + 24, 1, 0);
            }
            
            
        }else{
            gui.drawButton("IconAcceptInactive", this.posX + 70, this.posY + 24, 1, 2);
            
            this.getFontRenderer().drawStringWithShadow("No Upgrade", this.posX + 3, 87, -1);
            this.getFontRenderer().drawStringWithShadow("Selected Yet!", this.posX + 3, 97, -1);
        }
        
        if (isCoordsInBorders(this.gui.getMouseX() - this.currentShiftX, this.gui.getMouseY() - this.currentShiftY, 70 ,70 + 16, 42 ,42 + 16)) {
            gui.drawButton("IconCancel", this.posX + 70, this.posY + 42, 1, 1);
        }else{
            gui.drawButton("IconCancel", this.posX + 70, this.posY + 42, 1, 0);
        }
        
        getFontRenderer().drawStringWithShadow("Upgrades", posXOffset() + 18, posY + 8, headerColor);
        
    }

    @Override
    public void addTooltip(List<String> list) {

        if(!isFullyOpened()){
            list.add("Upgrade");
        }
        
        if (this.myContainer.container.upgrade != null && isCoordsInBorders(this.gui.getMouseX() - this.currentShiftX, this.gui.getMouseY() - this.currentShiftY, 70 ,70 + 16, 24 ,24 + 16)) {
            list.add("Add Upgrade");
        }
        
        if (isCoordsInBorders(this.gui.getMouseX() - this.currentShiftX, this.gui.getMouseY() - this.currentShiftY, 70 ,70 + 16, 42 ,42 + 16)) {
            list.add("Remove Items");
        }
    }

    @Override
    public boolean onMousePressed(int mouseX, int mouseY, int mouseButton) {

        if (!isFullyOpened()) {
            return false;
        }
        mouseX -= currentShiftX;
        mouseY -= currentShiftY;
        
        if (isCoordsInBorders(mouseX, mouseY, 0, 22, 0 ,22)) {
            return false;
        }
        
        if (isCoordsInBorders(mouseX, mouseY, 70 ,70 + 16, 24 ,24 + 16)) {
            this.myContainer.onButtonClick("UpgradeAddition");
            GuiBase.playSound("random.click", 1.0F, 0.4F);
        }
        
        if (isCoordsInBorders(mouseX, mouseY, 70 ,70 + 16, 42 ,42 + 16)) {
            this.myContainer.onButtonClick("RemoveItems");
            GuiBase.playSound("random.click", 1.0F, 0.4F);
        }
       
        return true;
    }

    public boolean isCoordsInBorders(int xCoord, int yCoord, int x, int x2, int y, int y2){
        return xCoord > x && xCoord < x2 && yCoord > y && yCoord < y2;
    }
    
    @Override
    protected void drawBackground() {
        super.drawBackground();

        if (!isFullyOpened()) {
            return;
        }
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        RenderHelper.bindTexture(GRID_TEXTURE);
        
        drawSlots(0, 0, 3);
        drawSlots(0, 1, 3);
        drawSlots(0, 2, 3);
    }
    
    @Override
    public void setFullyOpen() {
        super.setFullyOpen();

        for (int i = 0; i < 9; i++) {
            ((Slot) myContainer.container.inventorySlots.get(i)).xDisplayPosition = posXOffset() + slotsBorderX1 + 4 + 18 * (i % 3);
            ((Slot) myContainer.container.inventorySlots.get(i)).yDisplayPosition = posY + slotsBorderY1 + 4 + 18 * (i / 3);
        }
    }

    @Override
    public void toggleOpen() {
        if (open) {
            for (int i = 0; i < 9; i++) {
                ((Slot) myContainer.container.inventorySlots.get(i)).xDisplayPosition = -gui.getGuiLeft() - 16;
                ((Slot) myContainer.container.inventorySlots.get(i)).yDisplayPosition = -gui.getGuiTop() - 16;
            }
            
        }
        super.toggleOpen();
    }
    
    private void drawSlots(int xOffset, int yOffset, int slots) {
        gui.drawSizedTexturedModalRect(posXOffset() + slotsBorderX1 + 3 + 9 * xOffset, posY + slotsBorderY1 + 3 + 18 * yOffset, 0, 0, 18 * slots, 18, 72, 18);
    }
    
}
