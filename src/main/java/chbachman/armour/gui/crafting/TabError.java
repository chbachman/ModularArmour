package chbachman.armour.gui.crafting;

import java.util.List;

import cofh.core.gui.element.TabInfo;
import cofh.lib.gui.GuiBase;

public class TabError extends TabInfo {
    
    public boolean hasError;
    private List<String> myTextSave;
    
    public TabError(GuiBase gui, String infoString) {
        super(gui, infoString);
    }
    
    @SuppressWarnings("unchecked")
    public void setString(String string) {
        
        this.myTextSave = this.myText;
        
        this.myText = this.getFontRenderer().listFormattedStringToWidth(string, this.maxWidth - 16);
        this.numLines = Math.min(this.myText.size(), (this.maxHeight - 24) / this.getFontRenderer().FONT_HEIGHT);
        this.maxFirstLine = this.myText.size() - this.numLines;
        
        this.hasError = true;
        
    }
    
    public void reset() {
        
        this.myText = this.myTextSave;
        
        this.numLines = Math.min(this.myText.size(), (this.maxHeight - 24) / this.getFontRenderer().FONT_HEIGHT);
        this.maxFirstLine = this.myText.size() - this.numLines;
        
        this.hasError = false;
    }
    
}
