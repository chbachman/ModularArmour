package chbachman.armour.util;

import baubles.api.BaublesApi;
import cpw.mods.fml.common.Loader;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.oredict.OreDictionary;

public class InventoryUtil {

    public static void givePlayerItem(EntityPlayer player, ItemStack stack) {
        EntityItem entityitem = player.dropPlayerItemWithRandomChoice(stack, false);
        entityitem.delayBeforeCanPickup = 0;
        entityitem.func_145797_a(player.getCommandSenderName());
    }

    public static void decrementItemStack(EntityPlayer player, IInventory inventory, int index) {

        ItemStack stack = inventory.getStackInSlot(index);

        if (stack != null) {
            inventory.decrStackSize(index, 1);

            if (stack.getItem().hasContainerItem(stack)) {
                ItemStack itemstack2 = stack.getItem().getContainerItem(stack);

                if (itemstack2 != null && itemstack2.isItemStackDamageable() && itemstack2.getItemDamage() > itemstack2.getMaxDamage()) {
                    MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(player, itemstack2));
                    return;
                }

                if (!stack.getItem().doesContainerItemLeaveCraftingGrid(stack) || !player.inventory.addItemStackToInventory(itemstack2)) {
                    if (inventory.getStackInSlot(index) == null) {
                        inventory.setInventorySlotContents(index, itemstack2);
                    } else {
                        player.dropPlayerItemWithRandomChoice(itemstack2, false);
                    }
                }
            }
        }
    }

    public static boolean doesInventoryContainItemStack(ItemStack[] inventory, ItemStack stack) {
        for (ItemStack inventoryStack : inventory) {
            if (InventoryUtil.itemMatches(inventoryStack, stack, false)) {
                return true;
            }
        }

        return false;
    }

    public static ItemStack getItemStackFromInventory(ItemStack[] inventory, ItemStack stack) {
        for (ItemStack inventoryStack : inventory) {
            if (InventoryUtil.itemMatches(inventoryStack, stack, false)) {
                return inventoryStack;
            }
        }

        return null;
    }

    public static boolean doesInventoryContainItemStack(IInventory inventory, ItemStack stack) {
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack inventoryStack = inventory.getStackInSlot(i);

            if (InventoryUtil.itemMatches(inventoryStack, stack, false)) {
                return true;
            }
        }

        return false;
    }

    public static ItemStack getItemStackFromInventory(IInventory inventory, ItemStack stack) {
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack inventoryStack = inventory.getStackInSlot(i);

            if (InventoryUtil.itemMatches(inventoryStack, stack, false)) {
                return inventoryStack;
            }
        }

        return null;
    }

    public static ItemStack[] getArmour(EntityPlayer player) {

        if (Loader.isModLoaded("Baubles")) {
            return BaublesUtil.getArmour(player);
        }

        ItemStack[] armour = new ItemStack[7];

        for (int i = 0; i < player.inventory.armorInventory.length; i++) {
            armour[i] = player.inventory.armorInventory[i];
        }

        return armour;
    }

    public static boolean itemMatches(ItemStack target, ItemStack input, boolean strict) {
        if (input == null && target != null || input != null && target == null) {
            return false;
        }

        if (input == null && target == null) {
            return true;
        }

        return (target.getItem() == input.getItem() && ((target.getItemDamage() == OreDictionary.WILDCARD_VALUE && !strict) || target.getItemDamage() == input.getItemDamage()));
    }

}
