package chbachman.armour.gui;

import java.util.List;

import modulararmour.cofh.core.render.IconRegistry;
import modulararmour.cofh.lib.gui.GuiBase;
import modulararmour.cofh.lib.gui.GuiColor;
import modulararmour.cofh.lib.gui.element.ElementBase;
import modulararmour.cofh.lib.gui.element.ElementButtonManaged;
import modulararmour.cofh.lib.render.RenderHelper;
import modulararmour.cofh.lib.util.helpers.StringHelper;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

public class ElementButtonIcon extends ElementBase{
	
	protected IIcon icon;
	protected int spriteSheet;
	protected GuiColor color = new GuiColor(-1);
	
	private String tooltip;

	public ElementButtonIcon(GuiBase gui, int posX, int posY, String name, String icon) {
		this(gui, posX, posY, name, IconRegistry.getIcon(icon), 0);
	}
	
	public ElementButtonIcon(GuiBase gui, int posX, int posY, String name, IIcon icon, int spriteSheet) {
		super(gui, posX, posY);
		setName(name);
		setSize(icon.getIconWidth(), icon.getIconHeight());
		
		this.spriteSheet = spriteSheet;
		
		this.icon = icon;
	}
	
	@Override
	public void drawBackground(int mouseX, int mouseY, float gameTicks) {
		
		RenderHelper.bindTexture(texture);
		
		if (!isEnabled()) {
			gui.bindTexture(ElementButtonManaged.DISABLED);
		} else if (intersectsWith(mouseX, mouseY)) {
			gui.bindTexture(ElementButtonManaged.HOVER);
		} else {
			gui.bindTexture(ElementButtonManaged.ENABLED);
		}
		
		drawIcon();
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(posX, posY, 0, 0, sizeX / 2, sizeY / 2);
		drawTexturedModalRect(posX, posY + sizeY / 2, 0, 256 - sizeY / 2, sizeX / 2, sizeY / 2);
		drawTexturedModalRect(posX + sizeX / 2, posY, 256 - sizeX / 2, 0, sizeX / 2, sizeY / 2);
		drawTexturedModalRect(posX + sizeX / 2, posY + sizeY / 2, 256 - sizeX / 2, 256 - sizeY / 2, sizeX / 2, sizeY / 2);
	}

	public void drawIcon() {

		if (icon != null) {
			GL11.glColor4f(color.getFloatR(), color.getFloatG(), color.getFloatB(), color.getFloatA());
			
			gui.drawColorIcon(icon, posX, posY, spriteSheet);
			
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
		}
	}
	
	@Override
	public void drawForeground(int mouseX, int mouseY) {

	}

	@Override
	public void addTooltip(List<String> list) {
		if (tooltip != null) {
			list.add(StringHelper.localize(tooltip));
		}
	}

	@Override
	public boolean onMousePressed(int x, int y, int mouseButton) {
		if (isEnabled()) {
			gui.handleElementButtonClick(getName(), mouseButton);
		}
		return false;
	}
	
	public ElementButtonIcon setColor(Number color) {

		this.color = new GuiColor(color.intValue());
		return this;
	}

	public ElementButtonIcon setIcon(IIcon icon) {

		this.icon = icon;
		return this;
	}
	
	public ElementButtonIcon setSpriteSheet(int spriteSheet) {

		this.spriteSheet = spriteSheet;
		return this;
	}

	public int getColor() {

		return color.getColor();
	}
	
	public ElementButtonIcon clearToolTip() {
		this.tooltip = null;
		return this;
	}

	public ElementButtonIcon setToolTip(String tooltip) {
		this.tooltip = tooltip;
		return this;
	}

}
