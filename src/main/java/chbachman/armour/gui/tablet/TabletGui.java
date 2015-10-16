package chbachman.armour.gui.tablet;

import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.math.MathUtils;

import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.IUpgrade;
import chbachman.api.util.Array;
import chbachman.armour.gui.element.ElementBackground;
import chbachman.armour.reference.Reference;
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
    
    public static Array<TabletPage> pages = new Array<TabletPage>();
    private Array<Arrow> arrows = new Array<Arrow>();

    public TabletGui(Container container) {
        super(container, TEXTURE);

        this.container = container;

        this.xSize = 200;
        this.ySize = 200;
        this.drawInventory = false;

        background = new ElementBackground(this, 0, 0, xSize, ySize, new ResourceLocation(Reference.TEXTURE_LOCATION + "/gui/tabletBackground.png")) {

            @Override
            public void onDrag(int shiftedX, int shiftedY) {
                shiftX = shiftedX;
                shiftY = shiftedY;
            }

        };

    }
    
    public static void registerPage(IUpgrade upgrade, int xPos, int yPos){
    	pages.add(new TabletPage(xPos, yPos, upgrade));
    }
    
    private static boolean dependenciesInit = false;
    
    @Override
    public void initGui() {
        super.initGui();

        this.addElement(background);
        
        if(!dependenciesInit){
        	for(TabletPage page : pages){
        		page.initDependencies(pages);
        	}
        	dependenciesInit = true;
        }
        
        for (TabletPage page : this.pages) {
            
            for(TabletPage dependency : page.dependencies){
            	
            	boolean vertical = false;
            	
            	if(Math.abs(page.posX - dependency.posX) < page.sizeX){
            		vertical = true;
            	}
            	
            	if(Math.abs(page.posY - dependency.posY) < page.sizeY){
            		vertical = false;
            	}
            	
            	if(dependency.depdendents.size == 0){
            		vertical = true;
            	}
            	
            	this.arrows.add(new Arrow(this, dependency, page, vertical));
            	
            }
        }

        for (int i = 0; i < container.inventorySlots.size(); i++) {
            ((Slot) this.container.inventorySlots.get(i)).xDisplayPosition = -this.getGuiLeft() - 16; // Hide all the slots.
            ((Slot) this.container.inventorySlots.get(i)).yDisplayPosition = -this.getGuiTop() - 16;
        }
    }
    
    

    private void addPages() {
    	
    	Array<IUpgrade> upgrades = UpgradeRegistry.sortedRecipeList().getArray();
    	
    	int size = 0;
    	
    	{
    		IUpgrade upgrade;
    		
    		Iterator<IUpgrade> iterator = upgrades.iterator();
        	
        	while(iterator.hasNext() && ((upgrade = iterator.next()).getDependencies() == null || upgrade.getDependencies().length == 0)){	
        		size++;
        	}
    	}
    	
    	/*
    	
    	{ // Add all the non-dependent upgrades.
    		IUpgrade upgrade;
    		
    		Iterator<IUpgrade> iterator = upgrades.iterator();
        	int i = 0;
        	
        	while(iterator.hasNext() && ((upgrade = iterator.next()).getDependencies() == null || upgrade.getDependencies().length == 0)){
        		
        		pages.add(createPage(i, size, 50, 50, upgrade)); // This is the start of a cluster.
        		iterator.remove();    		
        		i++;
        	}
    	}
    	
    	*/
    	
    	/*
    	
    	for(IUpgrade upgrade : upgrades){
    		
    		IUpgrade[] dependencies = upgrade.getDependencies(); //No Null checking because above we already got rid of all of them.
    		
    		IUpgrade dependency = dependencies[0];
    		
    		
    		TabletPage dependencyPage = getPageForUpgrade(dependency);
    		
    		if(dependencyPage == null){
    			continue;
    		}
    		
    		
    		
    		TabletPage page = createPage(0, dependencyPage.posX + 50, 50 + dependencyPage.depdendents.size * 50, upgrade);
    		
    		dependencyPage.depdendents.add(page);
    		
    		pages.add(page);
    		
    		
    		
    		
    	}
    	
    	*/
    	
    	
    	
	}
    
    private TabletPage createPage(int i, int total, int currX, int currY, IUpgrade upgrade){
    	return createPage(i, total, currX, currY, upgrade, 1);
    }
    
    private TabletPage createPage(int i, int total, int currX, int currY, IUpgrade upgrade, int scalingFactor){
    	
    	int sqrt = ((int) Math.sqrt(total));
    	
    	int half = (total / 2) - 1;
    	
    	i += i < half ? 0 : 1;
    	
    	int xShift = (i % sqrt) * 50;
    	int yShift = (i / sqrt) * 50;
    	
    	return new TabletPage(currX + xShift, currY + yShift, upgrade);
    }
    
    private TabletPage getPageForUpgrade(IUpgrade upgrade){
    	for(TabletPage page : this.pages){
    		if(page.upgrade == upgrade){
    			return page;
    		}
    	}
    	
    	return null;
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

        if (page != null && page.isVisible(this)) {
            page.getTooltip(tooltip);
        }
    }

    public void drawBackgroundFeatures(float partialTick, int x, int y) {
    	
    	for(Arrow arrow: this.arrows){
    		arrow.render();
    	}
        
        for(TabletPage page : pages){
        	if (page.isVisible(this)) {
                page.render(this, x, y);
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
