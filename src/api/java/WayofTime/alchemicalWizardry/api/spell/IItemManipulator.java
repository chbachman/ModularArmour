package WayofTime.alchemicalWizardry.api.spell;

import java.util.List;

import net.minecraft.item.ItemStack;

public interface IItemManipulator
{
    public List<ItemStack> handleItemsOnBlockBroken(ItemStack toolStack, List<ItemStack> itemList);
}
