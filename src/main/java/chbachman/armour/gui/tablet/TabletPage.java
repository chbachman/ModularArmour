package chbachman.armour.gui.tablet;

import net.minecraft.util.IIcon;
import cofh.lib.gui.GuiBase;

public class TabletPage {
	
	final GuiBase gui;
	final String baseName;
	final IIcon icon;
	
	public TabletPage(GuiBase gui, String baseName, IIcon icon){
		this.baseName = baseName;
		this.gui = gui;
		this.icon = icon;
	}
	
	public TabletPage(GuiBase gui, String baseName, String icon){
		this.baseName = baseName;
		this.gui = gui;
		this.icon = gui.getIcon(icon);
	}
	
	public void renderBackground(int mouseX, int mouseY){
		
	}
	
	public void renderForeground(int mouseX, int mouseY){
		
	}
	
	
	
	//Basically, make this a class that handles everything, and we only have to contruct it. 
	
	//This way, we don't have to spend a lot of time making it.

}
