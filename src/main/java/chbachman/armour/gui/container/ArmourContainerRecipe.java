package chbachman.armour.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.armour.items.ItemModularArmour;
import chbachman.armour.upgrade.Upgrade;

public class ArmourContainerRecipe extends Container {
    
    public Upgrade upgrade = null;
    public final ItemModularArmour item;
    public final EntityPlayer player;
    public final ItemStack stack;
    
    public ArmourContainerRecipe(ItemStack stack, InventoryPlayer inventory, World world) {
        this.item = (ItemModularArmour) stack.getItem();
        this.stack = stack;
        this.player = inventory.player;
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
    
}
