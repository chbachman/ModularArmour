package chbachman.armour.gui.element;

import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementButtonBase;


public class ElementButtonIcon extends ElementButtonBase{
	
	private IIcon icon;
	
	private int spriteSheet;
	
	public ElementButtonIcon(GuiBase containerScreen, IIcon icon, int posX, int posY, int sizeX, int sizeY) {
		this(containerScreen, icon, posX, posY, sizeX, sizeY, 0);
	}
	
	public ElementButtonIcon(GuiBase containerScreen, IIcon icon, int posX, int posY, int sizeX, int sizeY, int spriteSheet) {
		super(containerScreen, posX, posY, sizeX, sizeY);
		this.icon = icon;
		this.spriteSheet = spriteSheet;
	}
	
	protected void bindTexture(int mouseX, int mouseY) {

		if (!isEnabled()) {
			gui.bindTexture(DISABLED);
		} else if (intersectsWith(mouseX, mouseY)) {
			gui.bindTexture(HOVER);
		} else {
			gui.bindTexture(ENABLED);
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
	}

	@Override
	public void drawBackground(int mouseX, int mouseY, float gameTicks) {

		bindTexture(mouseX, mouseY);

		drawTexturedModalRect(posX, posY, 0, 0, sizeX / 2, sizeY / 2);
		drawTexturedModalRect(posX, posY + sizeY / 2, 0, 256 - sizeY / 2, sizeX / 2, sizeY / 2);
		drawTexturedModalRect(posX + sizeX / 2, posY, 256 - sizeX / 2, 0, sizeX / 2, sizeY / 2);
		drawTexturedModalRect(posX + sizeX / 2, posY + sizeY / 2, 256 - sizeX / 2, 256 - sizeY / 2, sizeX / 2, sizeY / 2);
		
		this.gui.drawIcon(icon, posX, posY, spriteSheet);
	}

	@Override
	public void drawForeground(int mouseX, int mouseY) {
		
	}

}
