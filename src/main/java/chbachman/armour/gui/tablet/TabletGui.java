package chbachman.armour.gui.tablet;

import java.util.List;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import chbachman.api.util.Array;
import chbachman.armour.gui.element.ElementBackground;
import chbachman.armour.reference.Reference;
import cofh.core.gui.GuiBaseAdv;

import com.badlogic.gdx.math.MathUtils;

public class TabletGui extends GuiBaseAdv {
	
	Container container;
	
	public ElementBackground background;
	
	public int shiftX;
	public int shiftY;
	
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURE_LOCATION + "/gui/tabletGui.png");
	
	public static Array<TabletPage> pages = new Array<TabletPage>();
	
	public TabletGui(Container container) {
		super(container, TEXTURE);
		
		this.container = container;

		this.xSize = 128;
		this.ySize = 200;
		this.drawInventory = false;
		
		background = new ElementBackground(this, 6, 6, 128, 200, new ResourceLocation(Reference.TEXTURE_LOCATION + "/gui/tabletBackground.png")){

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
		pages.add(new TabletPage(this, 50, 50, 10, 10, "aaah", "blah"));
		
		for(TabletPage page : this.pages){ //For some reason, I need this. Otherwise the gui instance in page doesn't update.
		    page.gui = this;
		}
		
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
		this.drawArrow(8, 8, 108, 108);
		
		for(TabletPage page : pages){
		    
		    //page.gui = this;
		    
		    if(page.isVisible()){
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
    
	public void drawArrow(int startX, int startY, int endX, int endY){
		
		
		
		this.drawRectWithCheck(endX - 2, startY - 2, endX + 1, endY - 4, 0xFF33FF11);
		this.drawHorizontalArrowSegment(startX, startY, endX);
		this.drawVerticalArrowSegment(endX, startY, endY - 5);
		
		this.drawArrowHead(endX, endY, 1);
	}
	
	private void drawArrowHead(int startX, int startY, int direction){
	    
	    switch(direction){
	    
	    case 0:{ //Up
	        
	        for(int i = -1; i < 6; i++){
	            this.drawRectWithCheck(startX - (2 + i), startY + i, startX + (1 + i), startY + 5, 0xFF33FF11);
	        }
	        break;
	    }
	    case 1:{ //Down
	        for(int i = -1; i < 6; i++){
                this.drawRectWithCheck(startX-(2 + i), startY - i, startX + (1 + i), startY - 5, 0xFF33FF11);
            }
            break;
	    }
	    case 2:{ //Left
	        for(int i = -1; i < 6; i++){
                this.drawRectWithCheck(startX + i, startY - (2 + i), startX  + 5, startY + (1 + i), 0xFF33FF11);
            }
            break;
	    }
	    case 3:{ //Right
	        for(int i = -1; i < 6; i++){
                this.drawRectWithCheck(startX- i, startY - (2 + i), startX  - 5, startY + (1 + i), 0xFF33FF11);
            }
            break;
	    }
	    
	    }
	}
	
	private void drawHorizontalArrowSegment(int startX, int startY, int endX){
		
		this.drawRectWithCheck(startX, startY - 2, endX, startY + 1, 0xFF33FF11);
		this.drawRectWithCheck(startX, startY - 1, endX, startY, 0xFF33FFFF);
		
	}
	
	private void drawVerticalArrowSegment(int startX, int startY, int endY){
		this.drawRectWithCheck(startX - 2, startY, startX + 1, endY, 0xFF33FF11);
		this.drawRectWithCheck(startX - 1, startY, startX, endY, 0xFF33FFFF);
	}
	
	public void drawRectWithCheck(int startX, int startY, int endX, int endY, int color){
	    
	    startX = MathUtils.clamp(startX + getShiftX(), 5, this.xSize - 5) - getShiftX();
        endX =   MathUtils.clamp(endX   + getShiftX(), 5, this.xSize - 5) - getShiftX();
        
        startY = MathUtils.clamp(startY + getShiftY(), 5, this.ySize - 5) - getShiftY();
        endY =   MathUtils.clamp(endY   + getShiftY(), 5, this.ySize - 5) - getShiftY();
        
        if(startX == endX){
            return;
        }
        
        if(startY == endY){
            return;
        }
        
        this.drawSizedModalRect(startX, startY, endX, endY, color);
	    
	}
	
	public int getShiftX(){
	    return shiftX;
	}
	
	public int getShiftY(){
	    return shiftY;
	}
	
	public int getXSize(){
	    return this.xSize;
	}
	
	public int getYSize(){
	    return this.ySize;
	}
	
	
	
	
}
