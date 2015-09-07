package chbachman.armour.gui.element;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementBase;
import cofh.lib.util.helpers.StringHelper;

public class ElementItem extends ElementBase {

    protected static RenderItem itemRender = new RenderItem();

    ItemStack toDraw;
    String unlocalizedName;

    public ElementItem(GuiBase gui, int posX, int posY) {
        super(gui, posX, posY, 16, 16);
    }

    public ElementItem setItem(Item item) {
        return setItem(new ItemStack(item));
    }

    public ElementItem setItem(Block block) {
        return setItem(new ItemStack(block));
    }

    public ElementItem setItem(ItemStack item) {
        toDraw = item;
        unlocalizedName = item.getUnlocalizedName();
        return this;
    }

    @Override
    public void drawBackground(int mouseX, int mouseY, float gameTicks) {
        this.drawItemStack(toDraw, this.posX, this.posY, null);
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {

    }

    @Override
    public void addTooltip(List<String> list) {
        super.addTooltip(list);

        list.add(StringHelper.localize(unlocalizedName + ".name"));
    }

    private void drawItemStack(ItemStack stack, int x, int y, String p_146982_4_) {
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        itemRender.zLevel = 200.0F;

        FontRenderer font = null;
        if (stack != null) {
            font = stack.getItem().getFontRenderer(stack);
        }

        if (font == null) {
            font = this.gui.getFontRenderer();
        }

        itemRender.renderItemAndEffectIntoGUI(font, this.gui.mc.getTextureManager(), stack, x, y);

        itemRender.zLevel = 0.0F;
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }

}
