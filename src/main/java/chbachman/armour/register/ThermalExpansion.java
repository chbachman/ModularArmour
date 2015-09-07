package chbachman.armour.register;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.Recipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class ThermalExpansion extends Vanilla {

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

        UpgradeRegistry.registerRecipe(new Recipe(autoFeeder, "cac", "gag", "ccc", 'c', "ingotCopper", 'a', Items.golden_apple, 'g', "gearCopper"));
        UpgradeRegistry.registerRecipe(new Recipe(basePotion, "iri", "gwg", "igi", 'i', "ingotGold", 'g', "blockGlass", 'r', "dustRedstone", 'w', Items.water_bucket));
        UpgradeRegistry.registerRecipe(new Recipe(calfShields, "l l", "l l", "l l", 'l', "ingotLead"));
        UpgradeRegistry.registerRecipe(new Recipe(fallDamage, "   ", "   ", "iwi", 'w', Blocks.wool, 'i', "ingotInvar"));
        UpgradeRegistry.registerRecipe(new Recipe(hoverJetpack, "iti", "ini", "c c", 'i', "ingotInvar", 't', tesseract, 'c', electrumCoil, 'n', Items.nether_star));
        UpgradeRegistry.registerRecipe(new Recipe(solar, "ggg", "isi", "iii", 'g', "blockGlass", 'i', "ingotInvar", 's', silverCoil));
        UpgradeRegistry.registerRecipe(new Recipe(speed, "pip", "i i", "i i", 'i', "ingotInvar", 'p', Blocks.piston));
        UpgradeRegistry.registerRecipe(new Recipe(stepAssist, "pip", "i i", "e e", 'i', "ingotInvar", 'p', Blocks.piston, 'e', electrumCoil));
        UpgradeRegistry.registerRecipe(new Recipe(jumpBoost, "i i", "i i", "p p", 'i', "ingotInvar", 'p', Blocks.piston));
        UpgradeRegistry.registerRecipe(new Recipe(leadstoneEnergy, "iri", "rbr", "iri", 'i', "ingotIron", 'r', "dustRedstone", 'b', "blockIron"));
        UpgradeRegistry.registerRecipe(new Recipe(hardenedEnergy, "lrl", "rbr", "lrl", 'l', "gemLapis", 'r', "dustRedstone", 'b', "blockLapis"));
        UpgradeRegistry.registerRecipe(new Recipe(reinforcedEnergy, "grg", "rbr", "grg", 'g', "ingotGold", 'r', "dustRedstone", 'b', "blockGold"));
        UpgradeRegistry.registerRecipe(new Recipe(resonantEnergy, "drd", "rbr", "drd", 'd', "gemDiamond", 'r', "dustRedstone", 'b', "blockDiamond"));
    }

}
