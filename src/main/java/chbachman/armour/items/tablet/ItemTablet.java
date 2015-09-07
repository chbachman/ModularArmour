package chbachman.armour.items.tablet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.armour.ModularArmour;
import chbachman.armour.gui.GuiHandler;
import cofh.core.item.ItemBase;

public class ItemTablet extends ItemBase {

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.openGui(ModularArmour.instance, GuiHandler.TABLET_ID, world, 0, 0, 0);
        return stack;
    }

}
