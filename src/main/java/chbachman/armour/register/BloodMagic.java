package chbachman.armour.register;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import chbachman.api.item.IModularItem;
import chbachman.api.nbt.NBTHelper;
import chbachman.armour.ModularArmour;
import chbachman.armour.items.armour.LPModularArmour;
import chbachman.armour.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;

public class BloodMagic implements Module{

	public static Item helmetModular;
	public static Item chestplateModular;
	public static Item leggingsModular;
	public static Item bootsModular;
	
	public static ItemStack stackHelmetModular;
	public static ItemStack stackChestplateModular;
	public static ItemStack stackLeggingsModular;
	public static ItemStack stackBootsModular;
	
	@Override
	public void preInit() {
		helmetModular = new LPModularArmour(Vanilla.materialModular, 0).setUnlocalizedName("chbachman.armour.helmetModularLP").setTextureName(Reference.ITEM_LOCATION + "ModularHelmetLP");
		chestplateModular = new LPModularArmour(Vanilla.materialModular, 1).setUnlocalizedName("chbachman.armour.chestplateModularLP").setTextureName(Reference.ITEM_LOCATION + "ModularChestplateLP");
		leggingsModular = new LPModularArmour(Vanilla.materialModular, 2).setUnlocalizedName("chbachman.armour.leggingsModularLP").setTextureName(Reference.ITEM_LOCATION + "ModularLegsLP");
		bootsModular = new LPModularArmour(Vanilla.materialModular, 3).setUnlocalizedName("chbachman.armour.bootsModularLP").setTextureName(Reference.ITEM_LOCATION + "ModularBootsLP");
		
		GameRegistry.registerItem(helmetModular, "helmetModularLP");
		GameRegistry.registerItem(chestplateModular, "chestplateModularLP");
		GameRegistry.registerItem(leggingsModular, "leggingsModularLP");
		GameRegistry.registerItem(bootsModular, "bootsModularLP");
	}

	@Override
	public void init() {
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
	public void postInit() {
		ItemStack bloodSocket = GameRegistry.findItemStack("AWWayofTime", "bloodSocket", 1);
		GameRegistry.addRecipe(new ShapedOreRecipe(stackHelmetModular, new Object[] { "III", "I I", 'I', bloodSocket }));
		GameRegistry.addRecipe(new ShapedOreRecipe(stackChestplateModular, new Object[] { "I I", "III", "III", 'I', bloodSocket }));
		GameRegistry.addRecipe(new ShapedOreRecipe(stackLeggingsModular, new Object[] { "III", "I I", "I I", 'I', bloodSocket }));
		GameRegistry.addRecipe(new ShapedOreRecipe(stackBootsModular, new Object[] { "I I", "I I", 'I', bloodSocket }));
	}

	@Override
	public void registerUpgrades() {
		//bloodConverter = new UpgradeBloodMagic();
	}

	@Override
	public void registerUpgradeRecipes() {
		//Recipe.recipeList.add(new Recipe(bloodConverter, "iii", "ioi", "iii", 'i', "ingotIron", 'o', GameRegistry.findItemStack("AWWayofTime", "magicianBloodOrb", 1)));
	}

}
