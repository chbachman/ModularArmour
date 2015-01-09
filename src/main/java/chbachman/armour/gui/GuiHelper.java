package chbachman.armour.gui;

import java.util.List;

import cofh.lib.gui.GuiBase;

public class GuiHelper {
	
	public static boolean isCoordsInBorders(int xCoord, int yCoord, int x, int x2, int y, int y2) {
        return xCoord > x && xCoord < x2 && yCoord > y && yCoord < y2;
    }
	
	@SuppressWarnings("unchecked")
    public static void drawStringBounded(GuiBase gui, String name, int width, int x, int y, int color){
        List<String> strings = gui.getFontRenderer().listFormattedStringToWidth(name, width);
        
        for(int i = 0; i < strings.size(); i++){
            gui.drawString(gui.getFontRenderer(), strings.get(i), x, y + i * 10, color);
        }
    }

}
