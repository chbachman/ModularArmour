package chbachman.armour.gui.element;

import net.minecraft.util.ResourceLocation;
import chbachman.armour.reference.Reference;
import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementBase;
import cofh.lib.render.RenderHelper;

public class ElementBackground extends ElementBase{
	
	public static ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURE_LOCATION + "/gui/tabletBackground.png");
	
	private boolean isDragging;
	
	public int shiftedX;
	public int shiftedY;
	
	private int prevMouseX;
	private int prevMouseY;
	
	public float zoom = 1;
	
	public int sizeX;
	public int sizeY;
	
	public ElementBackground(GuiBase containerScreen, int x, int y, int width, int height) {
		super(containerScreen, x, y, width, height);
	}
	
	@Override
	public void drawBackground(int mouseX, int mouseY, float gameTicks) {
		
		if(isDragging){
			this.shiftedX -= prevMouseX - mouseX;
			this.shiftedY -= prevMouseY - mouseY;
			
			prevMouseX = mouseX;
			prevMouseY = mouseY;
		}
		
		
	}

	@Override
	public void drawForeground(int mouseX, int mouseY) {
		
	}
	
	public void renderBackground(){
		RenderHelper.bindTexture(TEXTURE);
		int startX = shiftedX % 256;
		int startY = shiftedY % 256;
		
		this.gui.drawSizedTexturedModalRect(this.gui.guiLeft, this.gui.guiTop, 128 - startX, 0 - startY, 128, 198, 128 / zoom, 198 / zoom);
	}
	
	@Override
	public boolean onMousePressed(int mouseX, int mouseY, int mouseButton) {

		isDragging = mouseButton == 0;
		prevMouseX = mouseX;
		prevMouseY = mouseY;
		
		return true;
	}
	
	@Override
	public void onMouseReleased(int mouseX, int mouseY) {
		isDragging = false;
	}
	
	@Override
	public boolean onMouseWheel(int mouseX, int mouseY, int movement) {
		
		//this.zoom = MathUtils.clamp(zoom + .1F * Math.signum(movement), .1F, 3);
		
		return true;
	}
	
	
	
}
