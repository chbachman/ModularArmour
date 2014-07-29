package chbachman.armour.gui.client;

import java.util.List;

import cofh.gui.GuiBase;
import cofh.gui.element.TabInfo;

public class TabError extends TabInfo{

    public boolean hasError;
    private List<String> myTextSave;
    
    public TabError(GuiBase gui, String infoString) {
        super(gui, infoString);
    }
    
    @SuppressWarnings("unchecked")
    public void setString(String string){
        
        myTextSave = myText;
        
        myText = getFontRenderer().listFormattedStringToWidth(string, maxWidth - 16);
        numLines = Math.min(myText.size(), (maxHeight - 24) / getFontRenderer().FONT_HEIGHT);
        maxFirstLine = myText.size() - numLines;
        
        this.hasError = true;
        
    }
    
    public void reset(){
        
        myText = myTextSave;
        
        numLines = Math.min(myText.size(), (maxHeight - 24) / getFontRenderer().FONT_HEIGHT);
        maxFirstLine = myText.size() - numLines;
        
        this.hasError = false;
    }
    
}
