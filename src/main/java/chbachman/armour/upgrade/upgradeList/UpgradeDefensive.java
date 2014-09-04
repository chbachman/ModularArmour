package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import chbachman.api.IModularItem;
import chbachman.api.Upgrade;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.upgrade.UpgradeList;

public class UpgradeDefensive extends Upgrade {
    
    private final DefensiveUpgrades upgrade;
    
    public UpgradeDefensive(DefensiveUpgrades upgrade) {
        super(upgrade.name);
        
        this.upgrade = upgrade;
        
        this.recipe = this.getRecipe();
    }
    
    @Override
    public boolean shouldRegisterRecipes() {
        return false;
    }
    
    @Override
    public int getArmourDisplay() {
        return this.upgrade.display;
    }
    
    @Override
    public void onUpgradeAddition(IModularItem armour, ItemStack stack) {
        armour.getInt("energyPerDamage").set(stack, this.upgrade.energyPerDamage);
    }
    
    @Override
    public boolean isCompatible(ArmourSlot slot) {
        return true;
    }
    
    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot) {
        if (source.isUnblockable()) {
            return new ArmorProperties(0, 0, 0);
        }
        
        return new ArmorProperties(0, this.upgrade.ratio, Integer.MAX_VALUE);
    }
    
    @Override
    public Recipe getRecipe() {
        new Recipe(this, "iii", "iii", "   ", 'i', this.upgrade.material);
        return new Recipe(this, "   ", "iii", "iii", 'i', this.upgrade.material);
    }
    
    public static enum DefensiveUpgrades {
        
        IRON("Iron Plating", Items.iron_ingot, 0.1D, 2, 100),; //DIAMOND("diamondPlating", Items.diamond, 0.2D, 2, 150), ENERGY("energyPlating", ItemRegister.temperedElectrum, 1D, 2, 1000);
        
        public final String name;
        public final ItemStack material;
        public final double ratio;
        public final int display;
        public final int energyPerDamage;
        
        private DefensiveUpgrades(String name, Item material, double ratio, int display, int energyPerDamage) {
            this.name = name;
            this.material = new ItemStack(material);
            this.ratio = ratio;
            this.display = display;
            this.energyPerDamage = energyPerDamage;
        }
        
        private DefensiveUpgrades(String name, ItemStack material, double ratio, int display, int energyPerDamage) {
            this.name = name;
            this.material = material;
            this.ratio = ratio;
            this.display = display;
            this.energyPerDamage = energyPerDamage;
        }
        
        public static void init() {
            
            for (DefensiveUpgrades upgrade : DefensiveUpgrades.values()) {
                UpgradeList.list.add(new UpgradeDefensive(upgrade));
            }
        }
    }
}
