package chbachman.armour.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import chbachman.armour.register.Vanilla;

public class ModularCreativeTab extends CreativeTabs {

    List<ItemStack> itemList = new ArrayList<ItemStack>();

    public ModularCreativeTab() {
        super("modularCreativeTab");
    }

    @Override
    public Item getTabIconItem() {
        return Vanilla.chestplateModular;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void displayAllReleventItems(List toDisplay) {
        super.displayAllReleventItems(toDisplay);
        toDisplay.addAll(itemList);
    }

    public ItemStack registerItemStack(ItemStack stack) {
        itemList.add(stack);
        return stack;
    }

    public Block registerBlock(Block block) {
        itemList.add(new ItemStack(block));
        return block;
    }

    public Item registerItem(Item item) {
        itemList.add(new ItemStack(item));
        return item;
    }

}
