package chbachman.armour.register;

import cpw.mods.fml.common.registry.GameRegistry;
import chbachman.api.IUpgrade;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.upgrade.upgradeList.UpgradeBasic;

public class Botania implements Module{

	public static IUpgrade manaConverter;
	public static IUpgrade pixie;
	
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
		manaConverter = new UpgradeBasic("manaConverter");
		pixie = new UpgradeBasic("pixie").setDependencies(manaConverter);
	}

	@Override
	public void registerUpgradeRecipes() {
		Recipe.recipeList.add(new Recipe(pixie, "tet", "e e", "tet", 'e', "ingotElvenElementium", 't', "ingotManasteel"));
		Recipe.recipeList.add(new Recipe(manaConverter, "ttt", "tpt", "ttt", 't', "ingotManasteel", 'p', GameRegistry.findItemStack("Botania", "pool", 1)));
	}

	
	
}
