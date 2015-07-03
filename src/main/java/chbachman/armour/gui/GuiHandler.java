package chbachman.armour.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import chbachman.api.item.IModularItem;
import chbachman.armour.gui.crafting.ArmourContainer;
import chbachman.armour.gui.crafting.ArmourGui;
import chbachman.armour.gui.recipe.RecipeContainer;
import chbachman.armour.gui.recipe.RecipeGui;
import chbachman.armour.gui.tablet.TabletGui;
import cofh.lib.gui.container.ContainerFalse;
import cofh.lib.util.helpers.ItemHelper;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    
    public static final int ARMOUR_ID = 0;
    public static final int RECIPE_ID = 1;
    public static final int TABLET_ID = 2;
    
    
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
        
        case ARMOUR_ID:
            if (ItemHelper.isPlayerHoldingItem(IModularItem.class, player)) {
                return new ArmourContainer(player.getHeldItem(), player.inventory);
            }
        case RECIPE_ID:
            return new RecipeContainer(player.getHeldItem(), player.inventory, world);
        case TABLET_ID:
        	return new ContainerFalse();
        default:
            return null;
        }
    }
    
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
        
        case ARMOUR_ID:
            if (ItemHelper.isPlayerHoldingItem(IModularItem.class, player)) {
                return new ArmourGui(new ArmourContainer(player.getHeldItem(), player.inventory), player.inventory);
            }
        case RECIPE_ID:
                return new RecipeGui(new RecipeContainer(player.getHeldItem(), player.inventory, world), player.inventory);
        case TABLET_ID:
        	return new TabletGui(new ContainerFalse());
        default:
            return null;
        }
    }
    
}
