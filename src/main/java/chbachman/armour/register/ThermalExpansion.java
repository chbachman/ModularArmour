package chbachman.armour.register;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import chbachman.armour.crafting.Recipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class ThermalExpansion extends Vanilla{
	
	public static final String TE = "ThermalExpansion";

	ItemStack tesseract;
	ItemStack electrumCoil;
	ItemStack silverCoil;
	ItemStack cellLeadstone;
	ItemStack cellHardened;
	ItemStack cellRedstone;
	ItemStack cellResonant;
	
	@Override
	public void registerUpgradeRecipes() {
		
		cellResonant = GameRegistry.findItemStack(TE, "cellResonant", 1);
		cellLeadstone = GameRegistry.findItemStack(TE, "cellLeadstone", 1);
		cellHardened = GameRegistry.findItemStack(TE, "cellHardened", 1);
		cellRedstone = GameRegistry.findItemStack(TE, "cellReinforced", 1);
		
		Recipe.addRecipe(new Recipe(autoFeeder, "cac", "gag", "ccc", 'c', "ingotCopper", 'a', Items.golden_apple, 'g', "gearCopper"));
		Recipe.addRecipe(new Recipe(basePotion, "iri", "gwg", "igi", 'i', "ingotGold", 'g', "blockGlass", 'r', "dustRedstone", 'w', Items.water_bucket));
		Recipe.addRecipe(new Recipe(calfShields, "l l", "l l", "l l", 'l', "ingotLead"));
		Recipe.addRecipe(new Recipe(fallDamage, "   ", "   ", "iwi", 'w', Blocks.wool, 'i', "ingotInvar"));
		Recipe.addRecipe(new Recipe(hoverJetpack, "iti", "ini", "c c", 'i', "ingotInvar", 't', tesseract, 'c', electrumCoil, 'n', Items.nether_star));
		Recipe.addRecipe(new Recipe(solar, "ggg", "isi", "iii", 'g', "blockGlass", 'i', "ingotInvar", 's', silverCoil));
		Recipe.addRecipe(new Recipe(speed, "pip", "i i", "i i", 'i', "ingotInvar", 'p', Blocks.piston));
		Recipe.addRecipe(new Recipe(stepAssist, "pip", "i i", "e e", 'i', "ingotInvar", 'p', Blocks.piston, 'e', electrumCoil));
		Recipe.addRecipe(new Recipe(jumpBoost, "i i", "i i", "p p", 'i', "ingotInvar", 'p', Blocks.piston));
		Recipe.addRecipe(new Recipe(leadstoneEnergy, "iri", "rbr", "iri", 'i', "ingotIron", 'r', "dustRedstone", 'b', "blockIron"));
		Recipe.addRecipe(new Recipe(hardenedEnergy, "lrl", "rbr", "lrl", 'l', "gemLapis", 'r', "dustRedstone", 'b', "blockLapis"));
		Recipe.addRecipe(new Recipe(reinforcedEnergy, "grg", "rbr", "grg", 'g', "ingotGold", 'r', "dustRedstone", 'b', "blockGold"));
		Recipe.addRecipe(new Recipe(resonantEnergy, "drd", "rbr", "drd", 'd', "gemDiamond", 'r', "dustRedstone", 'b', "blockDiamond"));
	}

}
