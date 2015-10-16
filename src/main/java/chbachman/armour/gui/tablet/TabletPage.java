package chbachman.armour.gui.tablet;

import java.util.List;

import chbachman.api.upgrade.IUpgrade;
import chbachman.api.util.Array;
import cofh.core.render.IconRegistry;
import cofh.lib.util.helpers.StringHelper;
import net.minecraft.util.IIcon;

public class TabletPage {
    
    final IUpgrade upgrade;
    final IIcon icon;

    int sizeX;
    int sizeY;
    int posX;
    int posY;
    
    boolean isEnabled;
    
    Array<TabletPage> dependencies = new Array<TabletPage>();
    
    Array<TabletPage> depdendents = new Array<TabletPage>();

    public TabletPage(int posX, int posY, int sizeX, int sizeY, IUpgrade upgrade) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.posX = posX;
        this.posY = posY;
        this.upgrade = upgrade;
        
        icon = upgrade.getIcon();

    }
    
    public TabletPage(int posX, int posY, IUpgrade upgrade){
    	this(posX, posY, 10, 10, upgrade);
    }
    
    public void initDependencies(Array<TabletPage> others){
    	if(upgrade.getDependencies() != null){
        	
        	for(IUpgrade dependency : upgrade.getDependencies()){
            	for(TabletPage page : others){
            		if(page.upgrade == dependency){
            			dependencies.add(page);
            		}
            	}
            }
        }
    	
    	//if(this.dependencies.size == 0){
    	this.isEnabled = true;
    	//}
    }
    
    public void render(TabletGui gui, int mouseX, int mouseY) {
    	
    	if(!isEnabled){
    		boolean shouldEnable = true;
        	
        	for(TabletPage page : dependencies){
        		if(!page.isEnabled){
        			shouldEnable = false;
        			break;
        		}
        	}
        	
        	this.isEnabled = shouldEnable;
    	}
    	
    	gui.drawIcon(IconRegistry.getIcon("IconButton"), this.posX, this.posY, 1);
    	
    	if(icon == null){
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

    public boolean isVisible(TabletGui gui) {

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
