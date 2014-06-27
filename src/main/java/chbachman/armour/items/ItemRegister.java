package chbachman.armour.items;

import chbachman.armour.reference.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemRegister {
	
	public static ArmorMaterial materialModular;
	
	public static Item helmetModular;
	public static Item chestplateModular;
	public static Item leggingsModular;
	public static Item bootsModular;
	
	public static final String[] TEXTURE_MODULAR = {Reference.ARMOUR_LOATION + "Modular_1.png", Reference.ARMOUR_LOATION + "Modular_2.png" };
	
	public static void init(){
        materialModular = EnumHelper.addArmorMaterial("", 25, new int[] { 3, 7, 5, 3 }, 10);
        helmetModular = new ItemModularArmour(materialModular, 0).setArmorTextures(TEXTURE_MODULAR).setUnlocalizedName("chbachman.armour.helmetModular").setTextureName(Reference.ITEM_LOCATION + "ModularHelmet");
        chestplateModular = new ItemModularArmour(materialModular, 1).setArmorTextures(TEXTURE_MODULAR).setUnlocalizedName("chbachman.armour.chestplateModular").setTextureName(Reference.ITEM_LOCATION + "ModularChestplate");
        leggingsModular = new ItemModularArmour( materialModular, 2).setArmorTextures(TEXTURE_MODULAR).setUnlocalizedName("chbachman.armour.leggingsModular").setTextureName(Reference.ITEM_LOCATION + "ModularLegs");
        bootsModular = new ItemModularArmour(materialModular, 3).setArmorTextures(TEXTURE_MODULAR).setUnlocalizedName("chbachman.armour.bootsModular").setTextureName(Reference.ITEM_LOCATION + "ModularBoots");
        GameRegistry.registerItem(helmetModular, "helmetModular");
        GameRegistry.registerItem(chestplateModular, "chestplateModular");
        GameRegistry.registerItem(leggingsModular, "leggingsModular");
        GameRegistry.registerItem(bootsModular, "bootsModular");
	}

}
