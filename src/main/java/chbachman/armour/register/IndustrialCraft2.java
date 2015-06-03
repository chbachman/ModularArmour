package chbachman.armour.register;

import ic2.api.item.IC2Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import chbachman.api.item.IModularItem;
import chbachman.api.nbt.helper.NBTHelper;
import chbachman.api.registry.ModularItemRegistry;
import chbachman.api.upgrade.IUpgrade;
import chbachman.armour.items.armour.ICModularArmour;
import chbachman.armour.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;

public class IndustrialCraft2 implements Module{

	public static IUpgrade metalArmor;
	
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
		helmetModular = new ICModularArmour(Vanilla.materialModular, 0).setUnlocalizedName("chbachman.armour.helmetModularIC").setTextureName(Reference.ITEM_LOCATION + "ModularHelmetIC");
		chestplateModular = new ICModularArmour(Vanilla.materialModular, 1).setUnlocalizedName("chbachman.armour.chestplateModularIC").setTextureName(Reference.ITEM_LOCATION + "ModularChestplateIC");
		leggingsModular = new ICModularArmour(Vanilla.materialModular, 2).setUnlocalizedName("chbachman.armour.leggingsModularIC").setTextureName(Reference.ITEM_LOCATION + "ModularLegsIC");
		bootsModular = new ICModularArmour(Vanilla.materialModular, 3).setUnlocalizedName("chbachman.armour.bootsModularIC").setTextureName(Reference.ITEM_LOCATION + "ModularBootsIC");
		
		GameRegistry.registerItem(helmetModular, "helmetModularIC");
		GameRegistry.registerItem(chestplateModular, "chestplateModularIC");
		GameRegistry.registerItem(leggingsModular, "leggingsModularIC");
		GameRegistry.registerItem(bootsModular, "bootsModularIC");
	}

	@Override
	public void init(){
		stackHelmetModular = NBTHelper.createDefaultStackTag(new ItemStack(helmetModular));
		stackChestplateModular = NBTHelper.createDefaultStackTag(new ItemStack(chestplateModular));
		stackLeggingsModular = NBTHelper.createDefaultStackTag(new ItemStack(leggingsModular));
		stackBootsModular = NBTHelper.createDefaultStackTag(new ItemStack(bootsModular));
		
		ModularItemRegistry.registerItem((IModularItem) helmetModular);
		ModularItemRegistry.registerItem((IModularItem) chestplateModular);
		ModularItemRegistry.registerItem((IModularItem) leggingsModular);
		ModularItemRegistry.registerItem((IModularItem) bootsModular);
	}

	@Override
	public void postInit(){
		
		ItemStack lapotron = IC2Items.getItem("lapotronCrystal");
		ItemStack carbon = IC2Items.getItem("carbonPlate");
		ItemStack hardenedElectrum = Vanilla.temperedElectrum;
		
		GameRegistry.addRecipe(new ShapedOreRecipe(stackHelmetModular, "HCH", "F F", 'F', carbon, 'H', hardenedElectrum, 'c', lapotron));
		GameRegistry.addRecipe(new ShapedOreRecipe(stackChestplateModular, "H H", "FCF", "HHH", 'F', carbon, 'H', hardenedElectrum, 'c', lapotron));
		GameRegistry.addRecipe(new ShapedOreRecipe(stackLeggingsModular,  "HCH", "F F", "H H", 'F', carbon, 'H', hardenedElectrum, 'c', lapotron));
		GameRegistry.addRecipe(new ShapedOreRecipe(stackBootsModular,  "F F", "HCH", 'F', carbon, 'H', hardenedElectrum, 'c', lapotron));
	}

	@Override
	public void registerUpgrades(){
		
	}

	@Override
	public void registerUpgradeRecipes(){
		
	}

}
