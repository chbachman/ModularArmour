package chbachman.armour.register;

import cpw.mods.fml.common.registry.GameRegistry;
import chbachman.api.IUpgrade;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.upgrade.upgradeList.UpgradeBloodMagic;

public class BloodMagic implements Module{

	public static IUpgrade bloodConverter;
	
	@Override
	public void preInit() {
		
	}

	@Override
	public void init() {
		
	}

	@Override
	public void postInit() {
		
	}

	@Override
	public void registerUpgrades() {
		bloodConverter = new UpgradeBloodMagic();
	}

	@Override
	public void registerUpgradeRecipes() {
		Recipe.recipeList.add(new Recipe(bloodConverter, "iii", "ioi", "iii", 'i', "ingotIron", 'o', GameRegistry.findItemStack("AWWayofTime", "magicianBloodOrb", 1)));
	}

}
