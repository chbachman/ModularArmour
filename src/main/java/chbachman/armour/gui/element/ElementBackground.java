package chbachman.armour.gui.element;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementBase;

public class ElementBackground extends ElementBase{
	
	private boolean isDragging;
	
	public int shiftedX;
	public int shiftedY;
	
	private int prevMouseX;
	private int prevMouseY;
	
	public ElementBackground(GuiBase containerScreen, int x, int y, int width, int height) {
		super(containerScreen, x, y, width, height);
	}

	@Override
	public void drawBackground(int mouseX, int mouseY, float gameTicks) {
		
		if(isDragging){
			this.shiftedX += prevMouseX - mouseX;
			this.shiftedY += prevMouseY - mouseY;
			
			prevMouseX = mouseX;
			prevMouseY = mouseY;
		}
		
		
	}

	@Override
	public void drawForeground(int mouseX, int mouseY) {
		this.drawTexturedModalRect(shiftedX, shiftedY, 64, 0, 64, 64);
	}
	
	@Override
	public boolean onMousePressed(int mouseX, int mouseY, int mouseButton) {

		isDragging = mouseButton == 0;
		
		return true;
	}
	
	@Override
	public void onMouseReleased(int mouseX, int mouseY) {
		isDragging = false;
	}
	
	
}
