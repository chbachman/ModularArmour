package chbachman.armour.upgrade.upgradeList;

import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.items.ItemModularArmour;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.upgrade.Upgrade;
import chbachman.armour.upgrade.UpgradeList;
import chbachman.armour.util.UpgradeUtil;

public class UpgradeElectricCapacity extends Upgrade {
    
    private final ElectricUpgrades upgrade;
    
    public UpgradeElectricCapacity(ElectricUpgrades upgrade) {
        super(upgrade.name);
        this.upgrade = upgrade;
        
        this.recipe = this.getRecipe();
        
    }
    
    @Override
    public boolean isCompatible(ArmourSlot slot) {
        return true;
    }
    
    @Override
    public Recipe getRecipe() {
        
        return new Recipe(this, "iri", "rur", "iri", 'i', Items.iron_ingot, 'r', Items.redstone, 'u', this.upgrade.recipe);
    }
    
    @Override
    public boolean shouldRegisterRecipes() {
        return false;
    }
    
    @Override
    public void onUpgradeAddition(ItemModularArmour armour, ItemStack stack) {
        armour.capacity.set(stack, this.upgrade.capacity);
        armour.maxTransfer.set(stack, this.upgrade.maxTransfer);
    }
    
    @Override
    public List<String> getDependencies() {
        
        if (this.upgrade == ElectricUpgrades.COBBLESTONE) {
            return null;
        }
        
        return UpgradeUtil.getDependencyList(UpgradeList.list.get(this.id - 1));
    }
    
    public static enum ElectricUpgrades {
        
        COBBLESTONE("cobblestoneUpgrade", Item.getItemFromBlock(Blocks.cobblestone), 1000, 100), IRON("ironUpgrade", Items.iron_ingot, 10000, 1000), GOLD("goldUpgrade", Items.gold_ingot, 100000, 10000), DIAMOND("diamondUpgrade", Items.diamond, 1000000, 10000);
        
        public final String name;
        public final Item recipe;
        public final int maxTransfer;
        public final int capacity;
        
        private ElectricUpgrades(String name, Item recipe, int capacity, int maxTransfer) {
            this.name = name;
            this.recipe = recipe;
            this.maxTransfer = maxTransfer;
            this.capacity = capacity;
        }
        
        public static void init() {
            
            for (ElectricUpgrades upgrade : ElectricUpgrades.values()) {
                UpgradeList.list.add(new UpgradeElectricCapacity(upgrade));
            }
            
        }
        
    }
    
}
