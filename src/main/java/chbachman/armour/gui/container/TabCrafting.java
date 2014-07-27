package chbachman.armour.gui.container;

import java.util.List;

import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cofh.gui.GuiBase;
import cofh.gui.GuiProps;
import cofh.gui.element.TabBase;
import cofh.render.RenderHelper;

public class TabCrafting extends TabBase{
    
    public static boolean enable;
    public static int defaultSide = 1;
    public static int defaultHeaderColor = 0xe1c92f;
    public static int defaultSubHeaderColor = 0xaaafb8;
    public static int defaultTextColor = 0x000000;
    public static int defaultBackgroundColor = 0x0033FF;
    
    public static ResourceLocation GRID_TEXTURE = new ResourceLocation(GuiProps.PATH_ELEMENTS + "Slot_Grid_Augment.png");
    
    ArmourContainer myContainer;
    
    int slotsBorderX1 = 7;
    int slotsBorderY1 = 20;
    
    int numAugments = 0;
    
    public static void initialize() {
        //defaultSide = MathHelper.clampI(defaultSide, 0, 1);
        //defaultHeaderColor = MathHelper.clampI(defaultHeaderColor, 0, 0xffffff);
        //defaultSubHeaderColor = MathHelper.clampI(defaultSubHeaderColor, 0, 0xffffff);
        //defaultTextColor = MathHelper.clampI(defaultTextColor, 0, 0xffffff);
        //defaultBackgroundColor = MathHelper.clampI(defaultBackgroundColor, 0, 0xffffff);
    }
    
    public TabCrafting(GuiBase gui, ArmourContainer container) {
        this(gui, defaultSide, container);
    }

    public TabCrafting(GuiBase gui, int side, ArmourContainer container) {
        super(gui, side);

        //headerColor = defaultHeaderColor;
        //subheaderColor = defaultSubHeaderColor;
        //textColor = defaultTextColor;
        backgroundColor = defaultBackgroundColor;

        myContainer = container;
        
        numAugments = 9;
        
        maxHeight = 120;
        maxWidth = 80;
    }
    
    @Override
    public void draw() {

        drawBackground();
        drawTabIcon("IconRedstone");
        if (!isFullyOpened()) {
            return;
        }
        
        getFontRenderer().drawStringWithShadow("Upgrades", posXOffset() + 18, posY + 8, headerColor);
    }

    @Override
    public void addTooltip(List<String> list) {

        
    }

    @Override
    public boolean onMousePressed(int mouseX, int mouseY, int mouseButton) {

        if (!isFullyOpened()) {
            return false;
        }
        if (side == LEFT) {
            mouseX += currentWidth;
        }
        mouseX -= currentShiftX;
        mouseY -= currentShiftY;
        
        if (mouseX < 0 || mouseX >= 22 || mouseY < 0 || mouseY >= 22) {
            return true;
        }
       
        return false;
    }

    @Override
    protected void drawBackground() {
        super.drawBackground();

        if (!isFullyOpened()) {
            return;
        }
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        RenderHelper.bindTexture(GRID_TEXTURE);
        
        drawSlots(0, 0, 3);
        drawSlots(0, 1, 3);
        drawSlots(0, 2, 3);
    }
    
    @Override
    public void setFullyOpen() {
        super.setFullyOpen();

        for (int i = 0; i < numAugments; i++) {
            ((Slot) myContainer.inventorySlots.get(i)).xDisplayPosition = posXOffset() + slotsBorderX1 + 4 + 18 * (i % 3);
            ((Slot) myContainer.inventorySlots.get(i)).yDisplayPosition = posY + slotsBorderY1 + 4 + 18 * (i / 3);
        }
    }

    @Override
    public void toggleOpen() {
        if (open) {
            for (int i = 0; i < numAugments; i++) {
                ((Slot) myContainer.inventorySlots.get(i)).xDisplayPosition = -gui.getGuiLeft() - 16;
                ((Slot) myContainer.inventorySlots.get(i)).yDisplayPosition = -gui.getGuiTop() - 16;
            }
        }
        super.toggleOpen();
    }
    
    private void drawSlots(int xOffset, int yOffset, int slots) {
        gui.drawSizedTexturedModalRect(posXOffset() + slotsBorderX1 + 3 + 9 * xOffset, posY + slotsBorderY1 + 3 + 18 * yOffset, 0, 0, 18 * slots, 18, 72, 18);
    }
    
}
