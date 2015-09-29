package chbachman.armour.gui.tablet;

import java.util.List;

import chbachman.api.upgrade.IUpgrade;
import chbachman.api.util.Array;
import cofh.core.render.IconRegistry;
import cofh.lib.util.helpers.StringHelper;
import net.minecraft.util.IIcon;

public class TabletPage {

    TabletGui gui;
    
    final IUpgrade upgrade;
    final IIcon icon;

    int sizeX;
    int sizeY;
    int posX;
    int posY;
    
    boolean isEnabled;
    
    Array<TabletPage> dependencies = new Array<TabletPage>();

    public TabletPage(TabletGui gui, int posX, int posY, int sizeX, int sizeY, IUpgrade upgrade) {
        this.gui = gui;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.posX = posX;
        this.posY = posY;
        this.upgrade = upgrade;
        
        icon = upgrade.getIcon();
        
        if(upgrade.getDependencies() != null){
        	
        	for(IUpgrade dependency : upgrade.getDependencies()){
            	for(TabletPage page : gui.pages){
            		if(page.upgrade == dependency){
            			dependencies.add(page);
            		}
            	}
            }
        }
        
        
    }
    
    public void render(int mouseX, int mouseY) {
    	
    	gui.drawIcon(IconRegistry.getIcon("IconButton"), this.posX, this.posY, 1);
    	gui.drawIcon(icon, this.posX, this.posY, 1);
        
    }

    public void getTooltip(List<String> list) {
        list.add(StringHelper.localize(upgrade.getName()));
    }

    public boolean intersectsWith(int mouseX, int mouseY) {

        if (mouseX >= this.posX && mouseX <= this.posX + this.sizeX && mouseY >= this.posY && mouseY <= this.posY + this.sizeY) {
            return true;
        }
        return false;
    }

    public boolean isVisible() {

        int x = posX + gui.getShiftX();
        int y = posY + gui.getShiftY();

        if (x + this.sizeX > gui.getXSize()) { // Right of the screen
            return false;
        }

        if (y + this.sizeY > gui.getYSize()) { // Below the screen
            return false;
        }

        if (y < 0) { // Above the screen
            return false;
        }

        if (x < 0) { // Left of the screen
            return false;
        }

        return true;
    }

}
