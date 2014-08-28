package chbachman.armour.upgrade.upgradeList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import chbachman.api.Upgrade;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.reference.ArmourSlot;

public class UpgradeBasePotion extends Upgrade {
    
    public UpgradeBasePotion() {
        super("Potion");
    }
    
    @Override
    public Recipe getRecipe() {
        return new Recipe(this, "iri", "gwg", "igi", 'i', Items.iron_ingot, 'g', Item.getItemFromBlock(Blocks.glass), 'r', Items.redstone, 'w', Items.water_bucket);
    }
    
    @Override
    public boolean isCompatible(ArmourSlot slot) {
        return true;
    }
    
}
