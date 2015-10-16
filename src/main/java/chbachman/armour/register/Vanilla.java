package chbachman.armour.register;

import chbachman.api.configurability.FieldList;
import chbachman.api.item.IModularItem;
import chbachman.api.nbt.helper.NBTHelper;
import chbachman.api.registry.ModularItemRegistry;
import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.IUpgrade;
import chbachman.api.upgrade.Recipe;
import chbachman.api.util.ArmourSlot;
import chbachman.api.util.ImmutableArray;
import chbachman.armour.ModularArmour;
import chbachman.armour.gui.tablet.TabletGui;
import chbachman.armour.handler.UpgradeHandler;
import chbachman.armour.items.armour.RFModularArmour;
import chbachman.armour.items.tablet.ItemTablet;
import chbachman.armour.reference.Reference;
import chbachman.armour.upgrade.UpgradeProtective.UpgradeExplosion;
import chbachman.armour.upgrade.UpgradeProtective.UpgradeFire;
import chbachman.armour.upgrade.UpgradeProtective.UpgradeGeneralProtection;
import chbachman.armour.upgrade.UpgradeProtective.UpgradeLava;
import chbachman.armour.upgrade.UpgradeProtective.UpgradeMagic;
import chbachman.armour.upgrade.UpgradeProtective.UpgradeProjectileProtection;
import chbachman.armour.upgrade.UpgradeProtective.UpgradeUnblockable;
import chbachman.armour.upgrade.UpgradeProtective.UpgradeWither;
import chbachman.armour.upgrade.upgradeList.UpgradeArthropod;
import chbachman.armour.upgrade.upgradeList.UpgradeAutoFeeder;
import chbachman.armour.upgrade.upgradeList.UpgradeBasic;
import chbachman.armour.upgrade.upgradeList.UpgradeDecorative;
import chbachman.armour.upgrade.upgradeList.UpgradeElectrolyzer;
import chbachman.armour.upgrade.upgradeList.UpgradeEnergy;
import chbachman.armour.upgrade.upgradeList.UpgradeFallDamage;
import chbachman.armour.upgrade.upgradeList.UpgradeHoverJetpack;
import chbachman.armour.upgrade.upgradeList.UpgradeJumpBoost;
import chbachman.armour.upgrade.upgradeList.UpgradeMagnet;
import chbachman.armour.upgrade.upgradeList.UpgradeModel;
import chbachman.armour.upgrade.upgradeList.UpgradePlayerProtection;
import chbachman.armour.upgrade.upgradeList.UpgradePotion;
import chbachman.armour.upgrade.upgradeList.UpgradeSolar;
import chbachman.armour.upgrade.upgradeList.UpgradeSpeed;
import chbachman.armour.upgrade.upgradeList.UpgradeStepAssist;
import chbachman.armour.upgrade.upgradeList.UpgradeUndead;
import chbachman.armour.util.EnergyUtil;
import cofh.api.modhelpers.ThermalExpansionHelper;
import cofh.core.item.ItemBase;
import cofh.lib.util.helpers.ItemHelper;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Vanilla implements Module {

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
    public static ItemBase tablet;

    public static ItemStack temperedElectrum;
    public static ItemStack heatedElectrum;

    public static ItemStack defaultTablet;
    
    
    //Potions
    public static IUpgrade basePotion;
    
    public static IUpgrade nightVision;
    public static IUpgrade invisibility;
    
    //Flight
    public static IUpgrade flight;
    
    public static IUpgrade calfShields;
    public static IUpgrade hoverJetpack;
    
    //Movement
    public static IUpgrade pistons;
    
    public static IUpgrade speed;
    public static IUpgrade stepAssist;
    public static IUpgrade fallDamage;
    public static IUpgrade autoFeeder;
    public static IUpgrade jumpBoost;
    
    //Electricity
    public static IUpgrade battery;
    
    public static IUpgrade solar;
    public static IUpgrade magnet;
    public static IUpgrade electrolyzer;
    public static IUpgrade leadstoneEnergy;
    public static IUpgrade hardenedEnergy;
    public static IUpgrade reinforcedEnergy;
    public static IUpgrade resonantEnergy;

    //Cosmetic
    public static IUpgrade visualDisplay;
    
    public static IUpgrade decorative;
    public static IUpgrade invisible;
    public static IUpgrade model;
    
    //Protection
    public static IUpgrade generalProtection;
    
    public static IUpgrade undeadProtection;
    public static IUpgrade arthropodProtection;
    public static IUpgrade projectileProtection;
    public static IUpgrade fireProtection;
    public static IUpgrade explosionProtection;
    public static IUpgrade unblockableProtection;
    public static IUpgrade magicalProtection;
    public static IUpgrade witherProtection;
    public static IUpgrade lavaProtection;
    public static IUpgrade playerProtection;
    

    @Override
    public final void preInit() {

        material = (ItemBase) new ItemBase("modulararmour").setUnlocalizedName("material").setCreativeTab(ModularArmour.creativeTab);

        tablet = (ItemBase) new ItemTablet().setUnlocalizedName("tablet").setCreativeTab(ModularArmour.creativeTab);

        materialModular = EnumHelper.addArmorMaterial("", 25, new int[] { 0, 0, 0, 0 }, 0);

        helmetModular = new RFModularArmour(materialModular, 0).setUnlocalizedName("chbachman.armour.helmetModular").setTextureName(Reference.ITEM_LOCATION + "ModularHelmet");
        chestplateModular = new RFModularArmour(materialModular, 1).setUnlocalizedName("chbachman.armour.chestplateModular").setTextureName(Reference.ITEM_LOCATION + "ModularChestplate");
        leggingsModular = new RFModularArmour(materialModular, 2).setUnlocalizedName("chbachman.armour.leggingsModular").setTextureName(Reference.ITEM_LOCATION + "ModularLegs");
        bootsModular = new RFModularArmour(materialModular, 3).setUnlocalizedName("chbachman.armour.bootsModular").setTextureName(Reference.ITEM_LOCATION + "ModularBoots");

        GameRegistry.registerItem(helmetModular, "helmetModular");
        GameRegistry.registerItem(chestplateModular, "chestplateModular");
        GameRegistry.registerItem(leggingsModular, "leggingsModular");
		GameRegistry.registerItem(bootsModular, "bootsModular");

		UpgradeRegistry.registerListener(new FieldList());

	}

	@Override
	public final void registerUpgrades() {
		
		basePotion = new UpgradeBasic("potion").setIcon("IconRecipe");
		
		nightVision = new UpgradePotion("nightVision", Potion.nightVision, 1, 10, 250);
        invisibility = new UpgradePotion("invisibility", Potion.invisibility, 10, 500);
        
        flight = new UpgradeBasic("flight");
        
		calfShields = new UpgradeBasic("calfShields").setArmourSlot(ArmourSlot.LEGS);
		hoverJetpack = new UpgradeHoverJetpack();
		
		pistons = new UpgradeBasic("piston");
		
		fallDamage = new UpgradeFallDamage();
		speed = new UpgradeSpeed();
		stepAssist = new UpgradeStepAssist();
		autoFeeder = new UpgradeAutoFeeder();
        jumpBoost = new UpgradeJumpBoost();
        
        
        
        battery = new UpgradeBasic("battery");
        
        solar = new UpgradeSolar("solar", 1);
        electrolyzer = new UpgradeElectrolyzer();
        magnet = new UpgradeMagnet();
        leadstoneEnergy = new UpgradeEnergy("leadstone", 800, 400000);
        hardenedEnergy = new UpgradeEnergy("hardened", 4000, 2000000).setDependencies(leadstoneEnergy);
        reinforcedEnergy = new UpgradeEnergy("reinforced", 20000, 10000000).setDependencies(hardenedEnergy);
        resonantEnergy = new UpgradeEnergy("resonant", 100000, 50000000).setDependencies(reinforcedEnergy);
        
        visualDisplay = new UpgradeBasic("visual");
        
        decorative = new UpgradeDecorative("thomazm").setTextureName("Thomaz");
        invisible = new UpgradeDecorative("sb").setTextureName("Shad0wB1ade");
        model = new UpgradeModel();
        
        generalProtection = new UpgradeGeneralProtection();
        
        undeadProtection = new UpgradeUndead();
        arthropodProtection = new UpgradeArthropod();
        fireProtection = new UpgradeFire();
        explosionProtection = new UpgradeExplosion();
        unblockableProtection = new UpgradeUnblockable();
        magicalProtection = new UpgradeMagic();
        witherProtection = new UpgradeWither();
        lavaProtection = new UpgradeLava();
        playerProtection = new UpgradePlayerProtection();
        projectileProtection = new UpgradeProjectileProtection();
        

        
    }
	
    @Override
    public final void init() {

        heatedElectrum = material.addOreDictItem(1, "heatedElectrum", 1);
        temperedElectrum = material.addOreDictItem(0, "temperedElectrum", 1);

        defaultTablet = tablet.addItem(0, "tablet");

        stackHelmetModular = NBTHelper.createDefaultStackTag(new ItemStack(helmetModular));
        stackChestplateModular = NBTHelper.createDefaultStackTag(new ItemStack(chestplateModular));
        stackLeggingsModular = NBTHelper.createDefaultStackTag(new ItemStack(leggingsModular));
        stackBootsModular = NBTHelper.createDefaultStackTag(new ItemStack(bootsModular));

        ModularItemRegistry.registerItem((IModularItem) helmetModular);
        ModularItemRegistry.registerItem((IModularItem) chestplateModular);
        ModularItemRegistry.registerItem((IModularItem) leggingsModular);
        ModularItemRegistry.registerItem((IModularItem) bootsModular);

        if (Loader.isModLoaded("ThermalFoundation")) {
            ThermalExpansionHelper.addTransposerFill(4000, GameRegistry.findItemStack("ThermalFoundation", "ingotElectrum", 1), heatedElectrum, new FluidStack(FluidRegistry.getFluid("pyrotheum"), 500), false);
            ThermalExpansionHelper.addTransposerFill(4000, heatedElectrum, temperedElectrum, new FluidStack(FluidRegistry.getFluid("cryotheum"), 500), false);
        }
    }

    @Override
    public void registerUpgradeRecipes() {
    	
    	//Potions
    	UpgradeRegistry.registerRecipe(new Recipe(basePotion, "iri", "gwg", "igi", 'i', "ingotIron", 'g', "blockGlass", 'r', "dustRedstone", 'w', Items.water_bucket));
		
    	UpgradeRegistry.registerRecipe(new Recipe(nightVision, "gig", "bpb", "gig", 'g', "ingotGold", 'b', "blockGlass", 'i', "ingotIron", 'p', Items.golden_carrot));
        UpgradeRegistry.registerRecipe(new Recipe(invisibility, "gig", "bpb", "gig", 'g', "ingotGold", 'b', "blockGlass", 'i', "ingotIron", 'p', Items.fermented_spider_eye));
        
        //Flight
        UpgradeRegistry.registerRecipe(new Recipe(flight, "fff", "fff", "f f", 'f', Items.feather));
        
        UpgradeRegistry.registerRecipe(new Recipe(calfShields, "i i", "i i", "i i", 'i', "ingotIron"));
        UpgradeRegistry.registerRecipe(new Recipe(hoverJetpack, "igi", "ini", "r r", 'i', "ingotIron", 'g', "ingotGold", 'r', "dustRedstone", 'n', Items.nether_star));
		
        //Movement
        UpgradeRegistry.registerRecipe(new Recipe(pistons, "www", "cic", "cpc", 'w', "plankWood", 'i', "ingotIron", 'c', "cobblestone", 'p', Blocks.piston));
		
		UpgradeRegistry.registerRecipe(new Recipe(fallDamage, "   ", "   ", "iwi", 'w', Blocks.wool, 'i', "ingotIron"));
		UpgradeRegistry.registerRecipe(new Recipe(speed, "pip", "i i", "i i", 'i', "ingotIron", 'p', Blocks.piston));
		UpgradeRegistry.registerRecipe(new Recipe(stepAssist, "pip", "i i", "   ", 'i', "ingotIron", 'p', Blocks.piston));
        UpgradeRegistry.registerRecipe(new Recipe(jumpBoost, "i i", "i i", "p p", 'i', "ingotIron", 'p', Blocks.piston));
        UpgradeRegistry.registerRecipe(new Recipe(autoFeeder, "igi", "igi", "iii", 'i', "ingotIron", 'g', Items.golden_apple));
        
        //Energy
        UpgradeRegistry.registerRecipe(new Recipe(battery, " g ", "iri", "iri", 'i', "ingotIron", 'g', "ingotGold", 'r', "dustRedstone"));
        
        UpgradeRegistry.registerRecipe(new Recipe(solar, "ggg", "ici", "iii", 'i', "ingotIron", 'g', "blockGlass", 'c', Items.coal));
        UpgradeRegistry.registerRecipe(new Recipe(electrolyzer, "iii", "g g", "iii", 'i', "ingotIron", 'g', "blockGlass"));
        UpgradeRegistry.registerRecipe(new Recipe(magnet, "g g", "i i", " i ", 'i', "ingotIron", 'g', "ingotGold"));
        UpgradeRegistry.registerRecipe(new Recipe(leadstoneEnergy, "iri", "rbr", "iri", 'i', "ingotIron", 'r', "dustRedstone", 'b', "blockIron"));
        UpgradeRegistry.registerRecipe(new Recipe(hardenedEnergy, "lrl", "rbr", "lrl", 'l', "gemLapis", 'r', "dustRedstone", 'b', "blockLapis"));
        UpgradeRegistry.registerRecipe(new Recipe(reinforcedEnergy, "grg", "rbr", "grg", 'g', "ingotGold", 'r', "dustRedstone", 'b', "blockGold"));
        UpgradeRegistry.registerRecipe(new Recipe(resonantEnergy, "drd", "rbr", "drd", 'd', "gemDiamond", 'r', "dustRedstone", 'b', "blockDiamond"));
        
        //Decorative
        UpgradeRegistry.registerRecipe(new Recipe(visualDisplay, "roy", "gbi", "dlw", 	'r', "dyeRed", 'o', "dyeOrange", 'y', "dyeYellow", 
        																		'g', "dyeGreen", 'b', "dyeBlue", 'i', "dyePurple", 
        																		'd', "dyeBlack", 'l', "dyeLightGray", 'w', "dyeWhite"));
        
        UpgradeRegistry.registerRecipe(new Recipe(decorative, "w w", "www", "www", 'w', Blocks.wool));
        UpgradeRegistry.registerRecipe(new Recipe(invisible, "g g", "ggg", "ggg", 'g', Blocks.glass));
        UpgradeRegistry.registerRecipe(new Recipe(model, "isi", "isi", "isi", 'i', "ingotIron", 's', Items.slime_ball));
        
        //Protection
        UpgradeRegistry.registerRecipe(new Recipe(generalProtection, "i i", "iii", "iii", 'i', "ingotIron"));
        
        UpgradeRegistry.registerRecipe(new Recipe(magicalProtection, "gig", "igi", "gig", 'i', "ingotIron", 'g', "ingotGold"));
        UpgradeRegistry.registerRecipe(new Recipe(arthropodProtection, "sps", "psp", "sps", 's', Items.string, 'p', Items.spider_eye));
        UpgradeRegistry.registerRecipe(new Recipe(fireProtection, "lgl", "lgl", "lgl", 'l', Items.leather, 'g', Items.gold_ingot));
        UpgradeRegistry.registerRecipe(new Recipe(lavaProtection, "oio", "ooo", "oio", 'o', Blocks.obsidian, 'i', "ingotIron"));
        UpgradeRegistry.registerRecipe(new Recipe(explosionProtection, "tit", "iti", "tit", 't', Blocks.tnt, 'i', "ingotIron"));
        UpgradeRegistry.registerRecipe(new Recipe(projectileProtection, "afa", "faf", "afa", 'f', Items.fire_charge, 'a', Items.arrow));
        UpgradeRegistry.registerRecipe(new Recipe(undeadProtection, "zzz", "zzz", "zzz", 'z', Items.rotten_flesh));
        UpgradeRegistry.registerRecipe(new Recipe(unblockableProtection, "d d", "ddd", "ddd", 'd', Items.diamond));
        UpgradeRegistry.registerRecipe(new Recipe(playerProtection, "cih", "iii", "iii", 'i', "ingotIron", 'c', Blocks.crafting_table, 'h', Blocks.chest));
        UpgradeRegistry.registerRecipe(new Recipe(witherProtection, "cic", "cwc", "cic", 'i', "ingotIron", 'w', Items.skull, 'c', Items.coal));
        
        TabletGui.registerPage(calfShields, 3, 3);

    }

    @Override
    public final void postInit() {

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

        registerAdminArmourPiece("modularHelmetUpgraded", Vanilla.stackHelmetModular.copy());
        registerAdminArmourPiece("modularChestplateUpgraded", Vanilla.stackChestplateModular.copy());
        registerAdminArmourPiece("modularLeggingsUpgraded", Vanilla.stackLeggingsModular.copy());
        registerAdminArmourPiece("modularBootsUpgraded", Vanilla.stackBootsModular.copy());

    }

    private final void registerAdminArmourPiece(String name, ItemStack armour) {
        
    	ImmutableArray<IUpgrade> upgrades = UpgradeRegistry.sortedRecipeList();
    	
    	for(IUpgrade upgrade : upgrades){
    		UpgradeHandler.addUpgradeChecked(armour, upgrade);
    	}

        armour.stackTagCompound.setInteger("Energy", EnergyUtil.getItem(armour).getMaxEnergyStored(armour));

        GameRegistry.registerCustomItemStack(name, ModularArmour.creativeTab.registerItemStack(armour));
    }

}
