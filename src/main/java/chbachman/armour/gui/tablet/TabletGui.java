package chbachman.armour.gui.tablet;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import chbachman.api.upgrade.IUpgrade;
import chbachman.api.util.Array;
import chbachman.armour.gui.element.ElementBackground;
import chbachman.armour.gui.element.ElementText;
import chbachman.armour.reference.Reference;
import cofh.core.gui.GuiBaseAdv;

import com.badlogic.gdx.math.MathUtils;

public class TabletGui extends GuiBaseAdv {

	public int currentX;
	public int currentY; //TODO: Delete Me
	
	Container container;
	
	ElementBackground background;
	
	ElementText text;
	
	Array<IUpgrade> shiftedElements = new Array<IUpgrade>();
	
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURE_LOCATION + "/gui/tabletGui.png");
	
	public TabletGui(Container container) {
		super(container, TEXTURE);
		
		this.container = container;

		this.xSize = 128;
		this.ySize = 200;
		this.drawInventory = false;
		
		background = new ElementBackground(this, 6, 6, 128, 200); 
		text = new ElementText(this, 20, 20);
		text.setText("Hello");
		
	}

	@Override
	public void initGui() {
		super.initGui();
		
		this.addElement(background);
		
		for(int i = 0; i < container.inventorySlots.size(); i++){
			((Slot) this.container.inventorySlots.get(i)).xDisplayPosition = -this.getGuiLeft() - 16; //Hide all the slots.
            ((Slot) this.container.inventorySlots.get(i)).yDisplayPosition = -this.getGuiTop() - 16;
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		
		super.drawGuiContainerForegroundLayer(x, y);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int x, int y) {
		
		background.renderBackground(); //The background needs to render before everything else.
		
		GL11.glPushMatrix();
		GL11.glTranslatef(guiLeft + background.shiftedX, guiTop + background.shiftedY, 0.0F);
		drawBackgroundFeatures(partialTick, x, y);
		
		GL11.glPopMatrix();
		
		super.drawGuiContainerBackgroundLayer(partialTick, x, y);
		
		
	}
	
	public void drawBackgroundFeatures(float partialTick, int x, int y) {
		this.drawArrow(8, 8, 108, 108);
		this.drawIcon("IconAccept", 0, 0, 1);

		this.drawIcon("IconAccept", 100, 100, 1);
		
		
	}
	
	public void drawArrow(int startX, int startY, int endX, int endY){
		
		startX = MathUtils.clamp(startX + background.shiftedX, 5, this.xSize - 5) - background.shiftedX;
		endX = MathUtils.clamp(endX + background.shiftedX, 5, this.xSize - 5) - background.shiftedX;
		
		startY = MathUtils.clamp(startY + background.shiftedY, 5, this.ySize - 5) - background.shiftedY;
		endY = MathUtils.clamp(endY + background.shiftedY, 5, this.ySize - 5) - background.shiftedY;
		
		
		
		this.drawSizedModalRect(endX - 2, startY - 2, endX + 1, endY + 1, 0xFF33FF11);
		this.drawHorizontalArrowSegment(startX, startY, endX);
		this.drawVerticalArrowSegment(endX, startY, endY);
		
		
	}
	
	private void drawHorizontalArrowSegment(int startX, int startY, int endX){
		
		this.drawSizedModalRect(startX, startY - 2, endX, startY + 1, 0xFF33FF11);
		this.drawSizedModalRect(startX, startY - 1, endX, startY, 0xFF33FFFF);
		
	}
	
	private void drawVerticalArrowSegment(int startX, int startY, int endY){
		this.drawSizedModalRect(startX - 2, startY, startX + 1, endY, 0xFF33FF11);
		this.drawSizedModalRect(startX - 1, startY, startX, endY, 0xFF33FFFF);
	}
	
	
}
