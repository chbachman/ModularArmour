package chbachman.armour.gui.element;

import java.util.List;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import chbachman.api.item.IModularItem;
import chbachman.api.registry.ModularItemRegistry;
import chbachman.api.upgrade.IUpgrade;
import chbachman.api.util.Array;
import chbachman.api.util.ImmutableArray;
import chbachman.armour.gui.recipe.RecipeContainer;
import cofh.lib.gui.GuiBase;
import cofh.lib.gui.GuiProps;
import cofh.lib.gui.element.TabBase;
import cofh.lib.render.RenderHelper;

public class TabCompatible extends TabBase {

    RecipeContainer container;

    Array<ElementItem> items;
    ImmutableArray<IModularItem> modularItems = ModularItemRegistry.getItemList();

    private int startIndex = 0;
    private int maxItems = 6;

    public static ResourceLocation GRID_TEXTURE = new ResourceLocation(GuiProps.PATH_ELEMENTS + "Slot_Grid_Augment.png");

    public TabCompatible(GuiBase gui, RecipeContainer c) {
        super(gui, 1);

        this.container = c;
        this.maxHeight = Math.min(modularItems.size(), maxItems) * 18 + 28;
        this.maxWidth = 42;

        items = new Array<ElementItem>();

        for (IModularItem item : modularItems) {
            ElementItem i = new ElementItem(this.gui, -this.gui.getGuiLeft() - 16, -this.gui.getGuiTop() - 16).setItem(item.getItem());
            items.add(i);
            this.addElement(i);
        }
    }

    @Override
    public void addTooltip(List<String> list) {
        super.addTooltip(list);
        if (!this.isFullyOpened()) {
            list.add("Compatible?");
        }
    }

    @Override
    public void drawForeground(int x, int y) {
        super.drawForeground(x, y);

        if (!this.isVisible()) {
            return;
        }

        if (this.isCompatible(container.item)) {
            this.drawTabIcon("IconAccept");
        } else {
            this.drawTabIcon("IconCancel");
        }

        if (!this.isFullyOpened()) {
            return;
        }

        for (int i = 0; i < Math.min(modularItems.size(), this.maxItems); i++) {

            IModularItem modularItem = modularItems.get(startIndex + i);

            String iconName;

            if (this.isCompatible(modularItem)) {
                iconName = "IconAccept";
            } else {
                iconName = "IconCancel";
            }

            this.gui.drawIcon(iconName, 21 + this.currentShiftX, 25 + i * 18 + this.currentShiftY, 1);

        }
    }

    @Override
    protected void drawBackground() {
        super.drawBackground();

        if (!this.isFullyOpened()) {
            return;
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        RenderHelper.bindTexture(GRID_TEXTURE);

        for (int i = 0; i < Math.min(modularItems.size(), this.maxItems); i++) {
            this.drawSlots(0, i, 1);
        }
    }

    @Override
    public void setFullyOpen() {
        super.setFullyOpen();

        this.displaySlots(true);
    }

    @Override
    public void toggleOpen() {
        if (this.open) {
            this.displaySlots(false);

        }
        super.toggleOpen();
    }

    int slotsBorderX1 = -2;
    int slotsBorderY1 = 20;

    @Override
    public boolean onMouseWheel(int mouseX, int mouseY, int movement) {
        if (!super.onMouseWheel(mouseX, mouseY, movement)) {
            return false;
        }

        this.startIndex += Math.signum(movement);

        if (startIndex > this.modularItems.size() - this.maxItems) {
            this.startIndex = this.modularItems.size() - this.maxItems;
        }

        if (this.startIndex < 0) {
            this.startIndex = 0;
        }

        displaySlots(this.open);
        return true;
    }

    private void updateSlots() {

        for (int i = startIndex; i < this.maxItems + startIndex; i++) {

            if (i >= this.items.size) {
                break;
            }

            this.items.get(i).setPosition(this.slotsBorderX1 + 6, this.slotsBorderY1 + 4 + 18 * i);
            this.items.get(i).setVisible(true);
        }

        for (int i = 0; i < this.items.size; i++) {

            if (i >= startIndex && i < startIndex + this.maxItems) {
                continue;
            }

            // this.items.get(i).setPosition(-this.gui.getGuiLeft() - 16,
            // -this.gui.getGuiTop() - 16);
            this.items.get(i).setVisible(false);
        }
    }

    public void displaySlots(boolean shouldDisplay) {
        if (shouldDisplay) {
            this.updateSlots();
        }
    }

    private void drawSlots(int xOffset, int yOffset, int slots) {
        this.gui.drawSizedTexturedModalRect(this.posXOffset() + this.slotsBorderX1 + 3 + 9 * xOffset, this.posY + this.slotsBorderY1 + 3 + 18 * yOffset, 0, 0, 18 * slots, 18, 96, 32);
    }

    boolean isCompatible(IModularItem item) {
        if (this.container.recipe == null) {
            return false;
        }

        IUpgrade upgrade = this.container.recipe.getRecipeOutput();
        return upgrade.isCompatible(item, this.container.stack, item.getSlot());
    }

}
