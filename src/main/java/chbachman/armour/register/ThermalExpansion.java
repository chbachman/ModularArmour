package chbachman.armour.register;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import chbachman.armour.crafting.Recipe;

public class ThermalExpansion extends Vanilla{

	public ThermalExpansion() {
		super("ThermalExpansion");
	}

	@Override
	public void registerUpgradeRecipes() {
		Recipe.addRecipe(new Recipe(autoFeeder, "igi", "igi", "iii", 'i', "ingotGold", 'g', Items.golden_apple));
		Recipe.addRecipe(new Recipe(basePotion, "iri", "gwg", "igi", 'i', "ingotGold", 'g', "blockGlass", 'r', "dustRedstone", 'w', Items.water_bucket));
		Recipe.addRecipe(new Recipe(calfShields, "i i", "i i", "i i", 'i', "ingotIron"));
		Recipe.addRecipe(new Recipe(fallDamage, "   ", "   ", "iwi", 'w', Blocks.wool, 'i', "ingotGold"));
		Recipe.addRecipe(new Recipe(hoverJetpack, "igi", "ini", "r r", 'i', "ingotIron", 'g', "ingotGold", 'r', "dustRedstone", 'n', Items.nether_star));
		Recipe.addRecipe(new Recipe(solar, "ggg", "ici", "iii", 'g', "blockGlass", 'i', "ingotIron", 'c', Items.coal));
		Recipe.addRecipe(new Recipe(speed, "pip", "i i", "i i", 'i', "ingotGold", 'p', Blocks.piston));
		Recipe.addRecipe(new Recipe(stepAssist, "pip", "i i", "   ", 'i', "ingotGold", 'p', Blocks.piston));
		Recipe.addRecipe(new Recipe(jumpBoost, "i i", "i i", "p p", 'i', "ingotGold", 'p', Blocks.piston));
	}

}
