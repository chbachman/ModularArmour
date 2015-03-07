package chbachman.armour.register;

import chbachman.api.IUpgrade;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.upgrade.upgradeList.UpgradeCamelPack;
import chbachman.armour.upgrade.upgradeList.UpgradeGasMask;
import cpw.mods.fml.common.registry.GameRegistry;

public class Enviromine implements Module{

	public static IUpgrade camelPack;
	public static IUpgrade gasMask;
	
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
		camelPack = new UpgradeCamelPack();
		gasMask = new UpgradeGasMask();
	}

	@Override
	public void registerUpgradeRecipes() {
		Recipe.recipeList.add(new Recipe(camelPack, "iii", "ici", "iii", 'i', "ingotIron", 'c', GameRegistry.findItemStack("enviromine", "camelPack", 1)));
		Recipe.recipeList.add(new Recipe(gasMask, "iii", "ici", "iii", 'i', "ingotIron", 'c', GameRegistry.findItemStack("enviromine", "gasMask", 1)));
	}

}
