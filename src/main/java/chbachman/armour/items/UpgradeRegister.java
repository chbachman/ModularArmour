package chbachman.armour.items;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import chbachman.api.IUpgrade;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.upgrade.upgradeList.UpgradeAutoFeeder;
import chbachman.armour.upgrade.upgradeList.UpgradeBasic;
import chbachman.armour.upgrade.upgradeList.UpgradeFallDamage;
import chbachman.armour.upgrade.upgradeList.UpgradeHoverJetpack;
import chbachman.armour.upgrade.upgradeList.UpgradeSolar;
import chbachman.armour.upgrade.upgradeList.UpgradeSpeed;
import chbachman.armour.upgrade.upgradeList.UpgradeStepAssist;

public class UpgradeRegister {
	
	
	public static IUpgrade calfShields;
	public static IUpgrade basePotion;
	public static IUpgrade hoverJetpack;
	public static IUpgrade fallDamage;
	public static IUpgrade solar;
	public static IUpgrade speed;
	public static IUpgrade stepAssist;
	public static IUpgrade autoFeeder;
	
	public static void preInit() {
        
		calfShields = new UpgradeBasic("calfShields").setArmourSlot(ArmourSlot.LEGS);
		hoverJetpack = new UpgradeHoverJetpack();
		basePotion = new UpgradeBasic("potion");
		fallDamage = new UpgradeFallDamage();
		solar = new UpgradeSolar();
		speed = new UpgradeSpeed();
		stepAssist = new UpgradeStepAssist();
		autoFeeder = new UpgradeAutoFeeder();
    }
	
	public static void init(){
		
	}
	
	public static void postInit(){
		Recipe.addRecipe(new Recipe(autoFeeder, "igi", "igi", "iii", 'i', "ingotIron", 'g', Items.golden_apple));
		Recipe.addRecipe(new Recipe(basePotion, "iri", "gwg", "igi", 'i', "ingotIron", 'g', "blockGlass", 'r', "dustRedstone", 'w', Items.water_bucket));
		Recipe.addRecipe(new Recipe(calfShields, "i i", "i i", "i i", 'i', "ingotIron"));
		Recipe.addRecipe(new Recipe(fallDamage, "   ", "   ", "iwi", 'w', Blocks.wool, 'i', "ingotIron"));
		Recipe.addRecipe(new Recipe(hoverJetpack, "igi", "ini", "r r", 'i', "ingotIron", 'g', "ingotGold", 'r', "dustRedstone", 'n', Items.nether_star));
		Recipe.addRecipe(new Recipe(solar, "ggg", "ici", "iii", 'g', "blockGlass", 'i', "ingotIron", 'c', Items.coal));
		Recipe.addRecipe(new Recipe(speed, "pip", "i i", "i i", 'i', "ingotIron", 'p', Blocks.piston));
		Recipe.addRecipe(new Recipe(stepAssist, "pip", "i i", "   ", 'i', "ingotIron", 'p', Blocks.piston));
	}

}
