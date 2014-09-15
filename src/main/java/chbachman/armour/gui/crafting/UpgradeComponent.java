package chbachman.armour.gui.crafting;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import chbachman.api.IUpgrade;
import chbachman.armour.util.NBTUpgradeList;

public class UpgradeComponent extends Gui {
    
    public static final int X_OFFSET = 4;
    public static final int Y_OFFSET = 2;
    
    private final FontRenderer fRend;
    public int xPos = 0;
    public int yPos = 0;
    
    public int width = 12;
    public int height = 12;
    
    public boolean drawBackground = true;
    public boolean drawBorder = true;
    public boolean isEnabled = true;
    public boolean isFocused = false;
    
    /** Color Configuration */
    public int borderColor = -6250336;
    public int backgroundColor = -16777216;
    public int selectedLineColor = -16777216;
    public int textColor = 14737632;
    public int selectedTextColor = 14737632;
    
    public int displayLines = 0;
    public int lineHeight = 10;
    
    public NBTUpgradeList textLines = new NBTUpgradeList();
    public int startLine = 0;
    public int selectedLine = -1;
    
    public boolean highlightSelectedLine = false;
    
    public UpgradeComponent(FontRenderer fontRenderer, int x, int y, int w, int lines) {
        
        this.fRend = fontRenderer;
        
        this.xPos = x;
        this.yPos = y;
        this.displayLines = lines;
        this.lineHeight = fontRenderer.FONT_HEIGHT + 1;
        this.width = w;
        this.height = this.displayLines * this.lineHeight + Y_OFFSET;
    }
    
    public void drawText() {
        
        for (int i = this.startLine; i < this.startLine + this.displayLines; i++) {
            if (this.textLines.size() > i) {
                String lineToDraw = this.fRend.trimStringToWidth(this.textLines.get(i).getName(), this.width);
                if (this.selectedLine == i) {
                    drawRect(this.xPos, this.yPos + 1 + this.lineHeight * (i - this.startLine), this.xPos + this.width, this.yPos + this.lineHeight * (1 + i - this.startLine), this.selectedLineColor);
                    this.fRend.drawStringWithShadow(lineToDraw, this.xPos + X_OFFSET, this.yPos + Y_OFFSET + this.lineHeight * (i - this.startLine), this.selectedTextColor);
                } else {
                    this.fRend.drawStringWithShadow(lineToDraw, this.xPos + X_OFFSET, this.yPos + Y_OFFSET + this.lineHeight * (i - this.startLine), this.textColor);
                }
            }
        }
    }
    
    public IUpgrade mouseClicked(int mouseX, int mouseY, int mouseButton, int offsetY) {
        
        int theLine = (mouseY - offsetY) / this.lineHeight;
        
        if (theLine != this.selectedLine) {
            this.selectedLine = theLine;
        }
        
        try {
            return this.textLines.get(theLine);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        
    }
    
    public void drawBackground() {
        
        if (this.drawBorder) {
            drawRect(this.xPos - 1, this.yPos - 1, this.xPos + this.width + 1, this.yPos + this.height + 1, this.borderColor);
        }
        if (this.drawBackground) {
            drawRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, this.backgroundColor);
        }
    }
    
    public int getSelectedLineYPos() {
        
        if (this.selectedLine < this.startLine || this.selectedLine > this.startLine + this.displayLines - 1) {
            return -1;
        }
        return this.yPos + 1 + this.lineHeight * (this.selectedLine - this.startLine);
    }
    
    public void setEnabled(boolean enable) {
        
        this.isEnabled = enable;
    }
    
    public void setFocused(boolean focus) {
        
        this.isFocused = focus;
    }
    
    public void addLine(IUpgrade theLine) {
        
        this.textLines.add(theLine);
    }
    
    public void selectLine(int i) {
        
        this.selectedLine = i;
    }
    
    public void scrollUp() {
        
        this.startLine++;
        if (this.startLine > this.textLines.size() - this.displayLines) {
            this.startLine = this.textLines.size() - this.displayLines;
        }
        if (this.startLine < 0) {
            this.startLine = 0;
        }
    }
    
    public void scrollDown() {
        
        this.startLine--;
        if (this.startLine < 0) {
            this.startLine = 0;
        }
    }
}
