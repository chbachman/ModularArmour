package chbachman.armour.gui.element;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementBase;
import cofh.lib.render.RenderHelper;
import net.minecraft.util.ResourceLocation;

public abstract class ElementBackground extends ElementBase {

    private boolean isDragging;

    public int shiftedX;
    public int shiftedY;

    private int prevMouseX;
    private int prevMouseY;

    public final ResourceLocation texture;

    public ElementBackground(GuiBase containerScreen, int x, int y, int width, int height, ResourceLocation texture) {
        super(containerScreen, x, y, width, height);
        this.texture = texture;
    }

    @Override
    public void drawBackground(int mouseX, int mouseY, float gameTicks) {

        if (isDragging) {
            this.shiftedX -= prevMouseX - mouseX;
            this.shiftedY -= prevMouseY - mouseY;

            onDrag(shiftedX, shiftedY);

            prevMouseX = mouseX;
            prevMouseY = mouseY;
        }

    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {

    }

    public void renderBackground() {
        RenderHelper.bindTexture(texture);
        int startX = shiftedX % 256;
        int startY = shiftedY % 256;

        this.gui.drawSizedTexturedModalRect(this.gui.guiLeft, this.gui.guiTop, this.sizeX - startX, 0 - startY, this.sizeX, this.sizeY, 128, 198);
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

    /**
     * Called when the drag changes.
     * 
     * @param shiftedX
     *            - The total shift in X
     * @param shiftedY
     *            - The total shift in Y
     */
    public abstract void onDrag(int shiftedX, int shiftedY);

}
