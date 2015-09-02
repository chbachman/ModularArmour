package chbachman.armour.gui.tablet;

import java.util.List;

import net.minecraft.util.IIcon;

public class TabletPage {
	
	TabletGui gui;
	final String baseName;
	final IIcon icon;
	
	int sizeX;
	int sizeY;
	int posX;
	int posY;
	
	
	public TabletPage(TabletGui gui, int posX, int posY, int sizeX, int sizeY, String baseName, IIcon icon){
        this.baseName = baseName;
        this.gui = gui;
        this.icon = icon;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.posX = posX;
        this.posY = posY;
    }
    
    public TabletPage(TabletGui gui, int posX, int posY, int sizeX, int sizeY, String baseName, String icon){
        this.baseName = baseName;
        this.gui = gui;
        this.icon = gui.getIcon(icon);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.posX = posX;
        this.posY = posY;
    }
	
	public void render(int mouseX, int mouseY){
	    this.gui.drawRectWithCheck(posX, posY, posX + this.sizeX, posY + this.sizeY, 0xFFFFFFFF);
	}
	
	public void getTooltip(List<String> list){
	    list.add("AAAAH");
	}
	
	public boolean intersectsWith(int mouseX, int mouseY) {

        if (mouseX >= this.posX && mouseX <= this.posX + this.sizeX && mouseY >= this.posY && mouseY <= this.posY + this.sizeY) {
            return true;
        }
        return false;
    }
	
	public boolean isVisible(){
	    
	    int x = posX + gui.getShiftX();
	    int y = posY + gui.getShiftY();
	    
	    if(x > gui.getXSize()){ //Right of the screen
	        return false;
	    }
	    
	    if(y > gui.getYSize()){ //Below the screen
	        return false;
	    }
	    
	    if(y < 0){ //Above the screen
	        return false;
	    }
	    
	    if(x < 0){ //Left of the screen
	        return false;
	    }
        
	    return true;
	}

}
