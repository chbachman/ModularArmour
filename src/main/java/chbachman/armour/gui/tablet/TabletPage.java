package chbachman.armour.gui.tablet;

import java.util.List;

import chbachman.api.upgrade.IUpgrade;
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

    public TabletPage(TabletGui gui, int posX, int posY, int sizeX, int sizeY, UpgradePage upgradePage) {
        this.gui = gui;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.posX = posX;
        this.posY = posY;
        
        if(upgradePage.icon != null){
        	icon = upgradePage.icon;
        }else if(upgradePage.iconName != null){
        	icon = gui.getIcon(upgradePage.iconName);
        }else{
        	icon = null; //Shouldn't ever happen
        }
        
        upgrade = upgradePage.upgrade;
    }

    public void render(int mouseX, int mouseY) {
    	
    	if(icon == null){
    		this.gui.drawRectWithCheck(posX, posY, posX + this.sizeX, posY + this.sizeY, 0xFFFFFFFF);
    		return;
    	}
    	
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

        if (x > gui.getXSize()) { // Right of the screen
            return false;
        }

        if (y > gui.getYSize()) { // Below the screen
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
