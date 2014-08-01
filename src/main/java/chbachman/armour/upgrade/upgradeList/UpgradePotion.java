package chbachman.armour.upgrade.upgradeList;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.items.ItemModularArmour;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.upgrade.Upgrade;
import chbachman.armour.upgrade.UpgradeList;
import chbachman.armour.util.UpgradeUtil;
import cofh.util.StringHelper;

public class UpgradePotion extends Upgrade {
    
    private final PotionUpgrades upgrade;
    private final int level;
    
    public UpgradePotion(PotionUpgrades upgrade, int level) {
        super(level != 0 ? "strong " : "" + upgrade.name);
        
        this.level = level;
        this.upgrade = upgrade;
        
        this.recipe = this.getRecipe();
        Recipe.addToList(this.recipe);
        
    }
    
    @Override
    public String getName() {
        if (this.level != 0) {
            String strong = StringHelper.localize("info.chbachman.strong");
            
            return strong + " " + this.upgrade.name;
            
        } else {
            return this.upgrade.name;
        }
    }
    
    @Override
    public boolean isCompatible(ArmourSlot slot) {
        return true;
    }
    
    @Override
    public Recipe getRecipe() {
        if (this.level == 0) {
            return new Recipe(this, "iri", "gug", "igi", 'i', Items.iron_ingot, 'g', Item.getItemFromBlock(Blocks.glass), 'r', Items.redstone, 'u', this.upgrade.recipe);
        } else {
            return new Recipe(this, "iri", "gug", "igi", 'i', Items.iron_ingot, 'g', Item.getItemFromBlock(Blocks.glass), 'r', Item.getItemFromBlock(Blocks.redstone_block), 'u', this.upgrade.recipe);
        }
    }
    
    @Override
    public boolean shouldRegisterRecipes() {
        return false;
    }
    
    @Override
    public int onArmourTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        player.addPotionEffect(new PotionEffect(this.upgrade.potion.id, 0, this.level));
        
        return 10;
    }
    
    @Override
    public List<String> getDependencies() {
        if (this.level != 0) {
            return UpgradeUtil.getDependencyList(UpgradeList.list.get(this.getId() - 1));
        } else {
            return UpgradeUtil.getDependencyList("Potion");
        }
    }
    
    @Override
    public void onUpgradeAddition(ItemModularArmour armour, ItemStack stack) {
        UpgradeUtil.removeUpgrade(stack, this);
    }
    
    public static enum PotionUpgrades {
        
        DAMAGE("Strength Potion", Potion.damageBoost, Items.blaze_powder), SPEED("Speed Potion", Potion.moveSpeed, Items.sugar), HASTE("Haste Potion", Potion.digSpeed, Items.iron_pickaxe), JUMP("Jump Potion", Potion.jump, Items.feather), REGENERATION("Regen Potion", Potion.regeneration, Items.ghast_tear), RESISTANCE("Resistance Potion", Potion.resistance, Items.iron_chestplate), FIRERESISTANCE("Fire Resistance Potion", Potion.fireResistance, Items.magma_cream), WATERBREATHING(
                "Water Breathing Potion", Potion.waterBreathing, Items.glass_bottle), NIGHTVISION("NightVision Potion", Potion.nightVision, Items.golden_carrot), ;
        
        public final String name;
        public final Potion potion;
        public final Item recipe;
        
        private PotionUpgrades(String name, Potion potion, Item recipe) {
            this.name = name;
            this.potion = potion;
            this.recipe = recipe;
        }
        
        public static void init() {
            
            for (PotionUpgrades upgrade : PotionUpgrades.values()) {
                for (int i = 0; i < 2; i++) {
                    UpgradeList.list.add(new UpgradePotion(upgrade, i));
                }
            }
            
        }
        
    }
}
