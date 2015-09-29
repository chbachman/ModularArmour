package chbachman.armour.gui.tablet;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.math.MathUtils;

import chbachman.api.upgrade.IUpgrade;
import chbachman.api.util.Array;
import chbachman.armour.gui.element.ElementBackground;
import chbachman.armour.reference.Reference;
import chbachman.armour.register.Vanilla;
import cofh.core.gui.GuiBaseAdv;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class TabletGui extends GuiBaseAdv {

    Container container;

    public ElementBackground background;

    public int shiftX;
    public int shiftY;

    public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURE_LOCATION + "/gui/tabletGui.png");

    public Array<TabletPage> pages = new Array<TabletPage>();
    private Array<Arrow> arrows = new Array<Arrow>();

    public TabletGui(Container container) {
        super(container, TEXTURE);

        this.container = container;

        this.xSize = 128;
        this.ySize = 200;
        this.drawInventory = false;

        background = new ElementBackground(this, 6, 6, 128, 200, new ResourceLocation(Reference.TEXTURE_LOCATION + "/gui/tabletBackground.png")) {

            @Override
            public void onDrag(int shiftedX, int shiftedY) {
                shiftX = shiftedX;
                shiftY = shiftedY;
            }

        };

    }

    @Override
    public void initGui() {
        super.initGui();

        this.addElement(background);
        pages.add(new TabletPage(this, 50, 50, 16, 16, Vanilla.basePotion));
        pages.add(new TabletPage(this, 30, 100, 16, 16, Vanilla.basePotion));
        //pages.add(new TabletPage(this, 20, 100, 16, 16, Vanilla.invisibility));
        //pages.add(new TabletPage(this, 100, 20, 16, 16, Vanilla.invisibility));
        pages.add(new TabletPage(this, 100, 100, 16, 16, Vanilla.invisibility));
        pages.add(new TabletPage(this, 20, 20, 16, 16, Vanilla.invisibility));
        
        this.addPage(Vanilla.basePotion);

        for (TabletPage page : this.pages) {
            page.gui = this; //For some reason, I need this.
            
            for(TabletPage dependency : page.dependencies){
            	
            	boolean vertical = MathUtils.randomBoolean();
            	
            	if(Math.abs(page.posX - dependency.posX) < 16){
            		vertical = true;
            	}
            	
            	if(Math.abs(page.posY - dependency.posY) < 16){
            		vertical = false;
            	}
            	
            	this.arrows.add(new Arrow(this, dependency, page, vertical));
            	
            }
        }

        for (int i = 0; i < container.inventorySlots.size(); i++) {
            ((Slot) this.container.inventorySlots.get(i)).xDisplayPosition = -this.getGuiLeft() - 16; // Hide all the slots.
            ((Slot) this.container.inventorySlots.get(i)).yDisplayPosition = -this.getGuiTop() - 16;
        }
    }
    
    

    private void addPage(IUpgrade basePotion) {
    	pages.add(new TabletPage(this, 50, 50, 16, 16, Vanilla.basePotion));
	}

	@Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        super.drawGuiContainerForegroundLayer(x, y);

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int x, int y) {

        background.renderBackground(); // The background needs to render before
                                       // everything else.

        GL11.glPushMatrix();
        GL11.glTranslatef(guiLeft + getShiftX(), guiTop + getShiftY(), 0.0F);
        drawBackgroundFeatures(partialTick, x, y);

        GL11.glPopMatrix();

        super.drawGuiContainerBackgroundLayer(partialTick, x, y);

    }

    @Override
    public void addTooltips(List<String> tooltip) {
        super.addTooltips(tooltip);

        TabletPage page = getPageAtPosition(mouseX - getShiftX(), mouseY - getShiftY());

        if (page != null && page.isVisible()) {
            page.getTooltip(tooltip);
        }
    }

    public void drawBackgroundFeatures(float partialTick, int x, int y) {
    	
    	for(Arrow arrow: this.arrows){
    		arrow.render();
    	}
        
        for(TabletPage page : pages){
        	if (page.isVisible()) {
                page.render(x, y);
            }
        }

    }

    protected TabletPage getPageAtPosition(int mX, int mY) {

        for (int i = 0; i < pages.size; i++) {
            TabletPage page = pages.get(i);
            if (page.intersectsWith(mX, mY)) {
                return page;
            }
        }
        return null;
    }


    public int getShiftX() {
        return shiftX;
    }

    public int getShiftY() {
        return shiftY;
    }

    public int getXSize() {
        return this.xSize;
    }

    public int getYSize() {
        return this.ySize;
    }
    
    private class Arrow{
    	
    	private final TabletGui gui;
    	
    	private final int startX;
    	private final int startY;
    	private final int endX;
    	private final int endY;
    	
    	private final boolean isVertical;
    	
    	public Arrow(TabletGui gui, int xStart, int yStart, int xEnd, int yEnd, boolean vertical) {
			this.gui = gui;
			this.startX = xStart;
			this.startY = yStart;
			this.endX = xEnd;
			this.endY = yEnd;
			this.isVertical = vertical;
		}
    	
    	public Arrow(TabletGui gui, TabletPage start, TabletPage end, boolean vertical){
			this.gui = gui;
			this.isVertical = vertical;
			
			this.startX = start.posX + start.sizeX / 2; //We want to start them all out in the middle.
			this.startY = start.posY + start.sizeY / 2;
			
			if(vertical){ //Vertical Arrows
        		if(start.posY > end.posY){
        			this.endX = end.posX + end.sizeX / 2;
        			this.endY = end.posY + end.sizeY + 2;
        			
            	}else{
        			this.endX = end.posX + end.sizeX / 2;
        			this.endY = end.posY;
        			
            	}
        	}else{ //Horizontal Arrows
        		if(start.posX > end.posX){
        			this.endX = end.posX + end.sizeX + 2;
        			this.endY = end.posY + end.sizeY / 2;

            	}else{
            		this.endX = end.posX - 2;
        			this.endY = end.posY + end.sizeY / 2;
        			
            	}
        	}
		}	
    	
		public void render() {
			if(isVertical){ //Vertical Arrows
        		this.drawHorizontalArrowSegment(startX, startY, endX);
                

                if(startY > endY){	
                	this.drawArrowHead(endX, endY, 0); //Up
                	this.drawVerticalArrowSegment(endX, startY, endY);
                }else{
                	this.drawArrowHead(endX, endY, 1); //Down
                	this.drawVerticalArrowSegment(endX, startY -1, endY - 1);
                }
                
        	}else{ //Horizontal Arrows
        		
        		this.drawVerticalArrowSegment(startX, startY - 1, endY);
                

                if(startX > endX){	
                	this.drawArrowHead(endX, endY, 2); //Left
                	this.drawHorizontalArrowSegment(startX, endY, endX);
                }else{
                	this.drawArrowHead(endX, endY, 3); //Right
                	this.drawHorizontalArrowSegment(startX - 1, endY, endX - 1);
                }
        	}
		}

        private void drawArrowHead(int startX, int startY, int direction) {
        	
        	final int size = 4;
        	
            switch (direction) {

            case 0: { // Up

                for (int i = -1; i < size; i++) {
                    this.drawRectWithCheck(startX - (2 + i), startY + i, startX + (1 + i), startY + size, 0xFF33FF11);
                }
                break;
            }
            case 1: { // Down
                for (int i = -1; i < size; i++) {
                    this.drawRectWithCheck(startX - (2 + i), (startY - i) - 2, startX + (1 + i), (startY - size) - 2, 0xFF33FF11);
                }
                break;
            }
            case 2: { // Left
                for (int i = -1; i < size; i++) {
                    this.drawRectWithCheck(startX + i, startY - (2 + i), startX + size, startY + (1 + i), 0xFF33FF11);
                }
                break;
            }
            case 3: { // Right
                for (int i = -1; i < size; i++) {
                    this.drawRectWithCheck(startX - i, startY - (2 + i), startX - size, startY + (1 + i), 0xFF33FF11);
                }
                break;
            }

            }
        }

        private void drawHorizontalArrowSegment(int startX, int startY, int endX) {
        	this.drawRectWithCheck(startX, startY - 1, endX, startY, 0xFF33FF11);

        }

        private void drawVerticalArrowSegment(int startX, int startY, int endY) {
            this.drawRectWithCheck(startX - 1, startY, startX, endY, 0xFF33FF11);
        }

        private void drawRectWithCheck(int startX, int startY, int endX, int endY, int color) {

            startX = MathUtils.clamp(startX + getShiftX(), 5, gui.xSize - 5) - getShiftX();
            endX = MathUtils.clamp(endX + getShiftX(), 5, gui.xSize - 5) - getShiftX();

            startY = MathUtils.clamp(startY + getShiftY(), 5, gui.ySize - 5) - getShiftY();
            endY = MathUtils.clamp(endY + getShiftY(), 5, gui.ySize - 5) - getShiftY();

            if (startX == endX) {
                return;
            }

            if (startY == endY) {
                return;
            }

            gui.drawSizedModalRect(startX, startY, endX, endY, color);

        }
    	
    }

}
