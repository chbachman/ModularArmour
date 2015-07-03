package chbachman.armour.gui.tablet;

import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import chbachman.armour.reference.Reference;
import cofh.core.gui.GuiBaseAdv;

public class TabletGui extends GuiBaseAdv {

	public int currentX;
	public int currentY;
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURE_LOCATION + "/gui/tabletGui.png");
	
	public TabletGui(Container container) {
		super(container, TEXTURE);

		this.xSize = 64;
		this.ySize = 100;
	}

	@Override
	public void initGui() {
		super.initGui();

		currentX = 0;
		currentY = 0;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int x, int y) {
		super.drawGuiContainerBackgroundLayer(partialTick, x, y);

	}
	
	int prevX, prevY;
	
	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		this.prevX = x;
		this.prevY = y;
	}

	@Override
	protected void mouseMovedOrUp(int x, int y, int button){
		super.mouseMovedOrUp(x, y, button);
	}

	@Override
	protected void mouseClickMove(int x, int y, int button, long time) {
		super.mouseClickMove(x, y, button, time);
		
	}
}
