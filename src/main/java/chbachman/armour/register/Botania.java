package chbachman.armour.register;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import chbachman.api.item.IModularItem;
import chbachman.api.nbt.NBTHelper;
import chbachman.api.upgrade.IUpgrade;
import chbachman.armour.ModularArmour;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.items.armour.BotaniaModularArmour;
import chbachman.armour.reference.Reference;
import chbachman.armour.upgrade.upgradeList.UpgradeBasic;
import cpw.mods.fml.common.registry.GameRegistry;

public class Botania implements Module{

	public static IUpgrade pixie;
	public static IUpgrade woodenEnergy;
	public static IUpgrade manaSteelEnergy;
	public static IUpgrade terraSteelEnergy;
	public static IUpgrade elvenEnergy;

	public static Item helmetModular;
	public static Item chestplateModular;
	public static Item leggingsModular;
	public static Item bootsModular;

	public static ItemStack stackHelmetModular;
	public static ItemStack stackChestplateModular;
	public static ItemStack stackLeggingsModular;
	public static ItemStack stackBootsModular;

	@Override
	public void preInit(){
		helmetModular = new BotaniaModularArmour(Vanilla.materialModular, 0).setUnlocalizedName("chbachman.armour.helmetModularMana").setTextureName(Reference.ITEM_LOCATION + "ModularHelmetMana");
		chestplateModular = new BotaniaModularArmour(Vanilla.materialModular, 1).setUnlocalizedName("chbachman.armour.chestplateModularMana").setTextureName(Reference.ITEM_LOCATION + "ModularChestplateMana");
		leggingsModular = new BotaniaModularArmour(Vanilla.materialModular, 2).setUnlocalizedName("chbachman.armour.leggingsModularMana").setTextureName(Reference.ITEM_LOCATION + "ModularLegsMana");
		bootsModular = new BotaniaModularArmour(Vanilla.materialModular, 3).setUnlocalizedName("chbachman.armour.bootsModularMana").setTextureName(Reference.ITEM_LOCATION + "ModularBootsMana");

		GameRegistry.registerItem(helmetModular, "helmetModularMana");
		GameRegistry.registerItem(chestplateModular, "chestplateModularMana");
		GameRegistry.registerItem(leggingsModular, "leggingsModularMana");
		GameRegistry.registerItem(bootsModular, "bootsModularMana");
	}

	@Override
	public void init(){
		stackHelmetModular = NBTHelper.createDefaultStackTag(new ItemStack(helmetModular));
		stackChestplateModular = NBTHelper.createDefaultStackTag(new ItemStack(chestplateModular));
		stackLeggingsModular = NBTHelper.createDefaultStackTag(new ItemStack(leggingsModular));
		stackBootsModular = NBTHelper.createDefaultStackTag(new ItemStack(bootsModular));

		ModularArmour.modularHandler.register((IModularItem) helmetModular, stackHelmetModular);
		ModularArmour.modularHandler.register((IModularItem) chestplateModular, stackChestplateModular);
		ModularArmour.modularHandler.register((IModularItem) leggingsModular, stackLeggingsModular);
		ModularArmour.modularHandler.register((IModularItem) bootsModular, stackBootsModular);
	}

	@Override
	public void postInit(){
		ItemStack manaTablet = GameRegistry.findItemStack("Botania", "manaTablet", 1);
		ItemStack elementiumIngot = GameRegistry.findItemStack("Botania", "manaResource", 1);
		
		GameRegistry.addRecipe(new ShapedOreRecipe(stackHelmetModular, "IVI", "I I", 'I', elementiumIngot, 'V', manaTablet));
		GameRegistry.addRecipe(new ShapedOreRecipe(stackChestplateModular, "I I", "IVI", "III", 'I', elementiumIngot, 'V', manaTablet));
		GameRegistry.addRecipe(new ShapedOreRecipe(stackLeggingsModular, "IVI", "I I", "I I", 'I', elementiumIngot, 'V', manaTablet));
		GameRegistry.addRecipe(new ShapedOreRecipe(stackBootsModular, "I I", "IVI", 'I', elementiumIngot, 'V', manaTablet));
	}

	@Override
	public void registerUpgrades(){
		pixie = new UpgradeBasic("pixie");
		
		//Character.isLetter(ch)
		
		//woodenEnergy = new UpgradeMana("woodenEnergy", );
		//manaSteelEnergy = new UpgradeMana("manasteelEnergy", );
		//terraSteelEnergy = new UpgradeMana("terrasteelEnergy", );
		//elvenEnergy = new UpgradeMana("elvenEnergy", );
		
	}

	@Override
	public void registerUpgradeRecipes(){
		Recipe.recipeList.add(new Recipe(pixie, "tet", "e e", "tet", 'e', "ingotElvenElementium", 't', "ingotManasteel"));
	}

}
