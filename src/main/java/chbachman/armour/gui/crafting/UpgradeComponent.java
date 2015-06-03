package chbachman.armour.gui.crafting;

import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

import repack.cofh.lib.gui.GuiBase;
import repack.cofh.lib.gui.element.ElementBase;
import chbachman.api.nbt.helper.NBTHelper;
import chbachman.api.nbt.helper.NBTList;
import chbachman.api.upgrade.IUpgrade;

public class UpgradeComponent extends ElementBase{

	public static final int X_OFFSET = 4;
	public static final int Y_OFFSET = 2;

	public boolean drawBackground = true;
	public boolean drawBorder = true;

	/** Color Configuration */
	public int borderColor = 0xFF000000;
	public int backgroundColor = 0xFF000000;
	public int selectedLineColor = 0xFFFFFFFF;
	public int textColor = 0xFFFFFFFF;
	public int selectedTextColor = 0xFF000000;

	public int displayLines = 0;
	public int lineHeight = 10;

	public NBTList<IUpgrade> textLines;

	public int startLine = 0;
	public int selectedLine = -1;

	public boolean highlightSelectedLine = false;
	
	public UpgradeComponent(GuiBase gui, int x, int y, int w, int lines, ItemStack toLoad) {
		this(gui, x, y, w, lines * (gui.getFontRenderer().FONT_HEIGHT + 1) + Y_OFFSET, lines, toLoad);
	}

	public UpgradeComponent(GuiBase gui, int x, int y, int w, int h, int lines, ItemStack toLoad) {
		super(gui, x, y, w, h);

		this.displayLines = lines;
		this.lineHeight = getFontRenderer().FONT_HEIGHT + 1;

		textLines = NBTHelper.getNBTUpgradeList(toLoad);
	}

	@Override
	public void drawBackground(int mouseX, int mouseY, float gameTicks){

		if (this.drawBackground){
			Gui.drawRect(this.posX, this.posY, this.posX + this.sizeX, this.posY + this.sizeY, this.backgroundColor);
		}

		if (this.drawBorder){
			Gui.drawRect(this.posX - 1, this.posY - 1, this.posX + this.sizeX + 1, this.posY + this.sizeY + 1, this.borderColor);
		}

	}

	@Override
	public void drawForeground(int mouseX, int mouseY){
		// Drawing Text
		for (int i = this.startLine; i < this.startLine + this.displayLines; i++){
			if (this.textLines.size() > i){
				
				if(textLines.get(i) == null){
					System.out.println("WHYYYYYYYYYYYYYYYYYYYYYYYYYY F****** YOUUUUUUUUU");
					continue;
				}
				
				String lineToDraw = getFontRenderer().trimStringToWidth(this.textLines.get(i).getName(), this.sizeX);
				if (this.selectedLine == i){
					Gui.drawRect(this.posX, this.posY + 1 + this.lineHeight * (i - this.startLine), this.posX + this.sizeX, this.posY + this.lineHeight * (1 + i - this.startLine), this.selectedLineColor);
					getFontRenderer().drawString(lineToDraw, this.posX + X_OFFSET, this.posY + Y_OFFSET + this.lineHeight * (i - this.startLine), this.selectedTextColor);
				}else{
					getFontRenderer().drawStringWithShadow(lineToDraw, this.posX + X_OFFSET, this.posY + Y_OFFSET + this.lineHeight * (i - this.startLine), this.textColor);
				}
			}
		}
	}

	@Override
	public boolean onMouseWheel(int mouseX, int mouseY, int movement){
		if (movement > 0){
			this.scrollDown();
		}else{
			this.scrollUp();
		}

		return true;
	}

	@Override
	public boolean onKeyTyped(char characterTyped, int keyPressed){
		if (keyPressed == Keyboard.KEY_UP){
			this.scrollUp();
			return true;
		}

		if (keyPressed == Keyboard.KEY_DOWN){
			this.scrollDown();
			return true;
		}

		return false;
	}

	@Override
	public boolean onMousePressed(int mouseX, int mouseY, int mouseButton){
		int theLine = startLine + (mouseY + this.posY) / this.lineHeight - 1;

		if (theLine != this.selectedLine){
			this.selectedLine = theLine;
		}

		return true;
	}

	public IUpgrade getSelectedUpgrade(){
		try{
			return this.textLines.get(selectedLine);
		}catch (IndexOutOfBoundsException e){
			return null;
		}
	}

	public void scrollUp(){

		this.startLine++;
		if (this.startLine > this.textLines.size() - this.displayLines){
			this.startLine = this.textLines.size() - this.displayLines;
		}
		if (this.startLine < 0){
			this.startLine = 0;
		}
	}

	public void scrollDown(){

		this.startLine--;
		if (this.startLine < 0){
			this.startLine = 0;
		}
	}

}
