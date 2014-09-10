package chbachman.armour.register;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import chbachman.api.IUpgrade;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.items.ItemModularArmourModded;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.reference.Reference;
import chbachman.armour.upgrade.upgradeList.UpgradeAutoFeeder;
import chbachman.armour.upgrade.upgradeList.UpgradeBasic;
import chbachman.armour.upgrade.upgradeList.UpgradeFallDamage;
import chbachman.armour.upgrade.upgradeList.UpgradeHoverJetpack;
import chbachman.armour.upgrade.upgradeList.UpgradeJumpBoost;
import chbachman.armour.upgrade.upgradeList.UpgradeSolar;
import chbachman.armour.upgrade.upgradeList.UpgradeSpeed;
import chbachman.armour.upgrade.upgradeList.UpgradeStepAssist;
import chbachman.armour.util.NBTHelper;
import cofh.api.modhelpers.ThermalExpansionHelper;
import cofh.core.item.ItemBase;
import cofh.lib.util.helpers.ItemHelper;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class Vanilla extends Module{

	public Vanilla() {
		super("Vanilla");
		// TODO Auto-generated constructor stub
	}

	public static ArmorMaterial materialModular;
    
    public static Item helmetModular;
    public static Item chestplateModular;
    public static Item leggingsModular;
    public static Item bootsModular;

    
    public static ItemStack stackHelmetModular;
    public static ItemStack stackChestplateModular;
    public static ItemStack stackLeggingsModular;
    public static ItemStack stackBootsModular;
    
    public static ItemBase material;
    
    public static ItemStack temperedElectrum;
    public static ItemStack heatedElectrum;
    
	public static IUpgrade calfShields;
	public static IUpgrade basePotion;
	public static IUpgrade hoverJetpack;
	public static IUpgrade fallDamage;
	public static IUpgrade solar;
	public static IUpgrade speed;
	public static IUpgrade stepAssist;
	public static IUpgrade autoFeeder;
	public static IUpgrade jumpBoost;
    
    public static final String[] TEXTURE_MODULAR = { Reference.ARMOUR_LOATION + "Modular_1.png", Reference.ARMOUR_LOATION + "Modular_2.png" };
    
    public void preInit() {
    	
    	
        material = (ItemBase) new ItemBase("modulararmour").setUnlocalizedName("material").setCreativeTab(CreativeTabs.tabMaterials);
        
        materialModular = EnumHelper.addArmorMaterial("", 25, new int[] { 3, 7, 5, 3 }, 10);
        
    	helmetModular = new ItemModularArmourModded(materialModular, 0).setArmorTextures(TEXTURE_MODULAR).setUnlocalizedName("chbachman.armour.helmetModular").setTextureName(Reference.ITEM_LOCATION + "ModularHelmet");
        chestplateModular = new ItemModularArmourModded(materialModular, 1).setArmorTextures(TEXTURE_MODULAR).setUnlocalizedName("chbachman.armour.chestplateModular").setTextureName(Reference.ITEM_LOCATION + "ModularChestplate");
        leggingsModular = new ItemModularArmourModded(materialModular, 2).setArmorTextures(TEXTURE_MODULAR).setUnlocalizedName("chbachman.armour.leggingsModular").setTextureName(Reference.ITEM_LOCATION + "ModularLegs");
        bootsModular = new ItemModularArmourModded(materialModular, 3).setArmorTextures(TEXTURE_MODULAR).setUnlocalizedName("chbachman.armour.bootsModular").setTextureName(Reference.ITEM_LOCATION + "ModularBoots");
        
        GameRegistry.registerItem(helmetModular, "helmetModular");
        GameRegistry.registerItem(chestplateModular, "chestplateModular");
        GameRegistry.registerItem(leggingsModular, "leggingsModular");
        GameRegistry.registerItem(bootsModular, "bootsModular");
    	
    	registerUpgrades();
        
    }
    
    public void registerUpgrades(){
		calfShields = new UpgradeBasic("calfShields").setArmourSlot(ArmourSlot.LEGS);
		hoverJetpack = new UpgradeHoverJetpack();
		basePotion = new UpgradeBasic("potion");
		fallDamage = new UpgradeFallDamage();
		solar = new UpgradeSolar();
		speed = new UpgradeSpeed();
		stepAssist = new UpgradeStepAssist();
		autoFeeder = new UpgradeAutoFeeder();
		jumpBoost = new UpgradeJumpBoost();
    }
    
    public void init() {
        
        heatedElectrum = material.addOreDictItem(1, "heatedElectrum", 1);
        temperedElectrum = material.addOreDictItem(0, "temperedElectrum", 1);
        
        stackHelmetModular = NBTHelper.createDefaultStackTag(new ItemStack(helmetModular));
        stackChestplateModular = NBTHelper.createDefaultStackTag(new ItemStack(chestplateModular));
        stackLeggingsModular = NBTHelper.createDefaultStackTag(new ItemStack(leggingsModular));
        stackBootsModular = NBTHelper.createDefaultStackTag(new ItemStack(bootsModular));
        
        if(Loader.isModLoaded("ThermalFoundation")){
        	ThermalExpansionHelper.addTransposerFill(4000, GameRegistry.findItemStack("ThermalFoundation", "ingotElectrum", 1), heatedElectrum, new FluidStack(FluidRegistry.getFluid("pyrotheum"), 1000), false);
            ThermalExpansionHelper.addTransposerFill(4000, heatedElectrum, temperedElectrum, new FluidStack(FluidRegistry.getFluid("cryotheum"), 1000), false);
        }
    }
    
    public void registerUpgradeRecipes(){
    	Recipe.addRecipe(new Recipe(autoFeeder, "igi", "igi", "iii", 'i', "ingotIron", 'g', Items.golden_apple));
		Recipe.addRecipe(new Recipe(basePotion, "iri", "gwg", "igi", 'i', "ingotIron", 'g', "blockGlass", 'r', "dustRedstone", 'w', Items.water_bucket));
		Recipe.addRecipe(new Recipe(calfShields, "i i", "i i", "i i", 'i', "ingotIron"));
		Recipe.addRecipe(new Recipe(fallDamage, "   ", "   ", "iwi", 'w', Blocks.wool, 'i', "ingotIron"));
		Recipe.addRecipe(new Recipe(hoverJetpack, "igi", "ini", "r r", 'i', "ingotIron", 'g', "ingotGold", 'r', "dustRedstone", 'n', Items.nether_star));
		Recipe.addRecipe(new Recipe(solar, "ggg", "ici", "iii", 'g', "blockGlass", 'i', "ingotIron", 'c', Items.coal));
		Recipe.addRecipe(new Recipe(speed, "pip", "i i", "i i", 'i', "ingotIron", 'p', Blocks.piston));
		Recipe.addRecipe(new Recipe(stepAssist, "pip", "i i", "   ", 'i', "ingotIron", 'p', Blocks.piston));
		Recipe.addRecipe(new Recipe(jumpBoost, "i i", "i i", "p p", 'i', "ingotIron", 'p', Blocks.piston));
    }
    
    public void postInit() {
    	
        if (!Loader.isModLoaded("ThermalExpansion")) {
            if (ItemHelper.oreNameExists("ingotElectrum")) {
                for (ItemStack stack : OreDictionary.getOres("ingotElectrum")) {
                    GameRegistry.addSmelting(stack, heatedElectrum, 0.0F);
                }
            } else {
                for (ItemStack stack : OreDictionary.getOres("ingotGold")) {
                    GameRegistry.addSmelting(stack, heatedElectrum, 0.0F);
                }
            }
            
            GameRegistry.addRecipe(new ShapelessOreRecipe(temperedElectrum, Items.water_bucket, "heatedElectrum"));
        }
        
        GameRegistry.addRecipe(new ShapedOreRecipe(stackHelmetModular, new Object[] { "III", "I I", 'I', temperedElectrum }));
        GameRegistry.addRecipe(new ShapedOreRecipe(stackChestplateModular, new Object[] { "I I", "III", "III", 'I', temperedElectrum }));
        GameRegistry.addRecipe(new ShapedOreRecipe(stackLeggingsModular, new Object[] { "III", "I I", "I I", 'I', temperedElectrum }));
        GameRegistry.addRecipe(new ShapedOreRecipe(stackBootsModular, new Object[] { "I I", "I I", 'I', temperedElectrum }));
    }
    
}
