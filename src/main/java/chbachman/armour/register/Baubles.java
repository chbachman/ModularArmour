package chbachman.armour.register;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import baubles.api.BaubleType;
import chbachman.api.item.IModularItem;
import chbachman.api.nbt.NBTHelper;
import chbachman.api.registry.ModularItemRegistry;
import chbachman.armour.items.bauble.RFBauble;
import chbachman.armour.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;

public class Baubles implements Module{

    public static Item itemRing;
    public static Item itemPendant;
    public static Item itemBelt;
    
    public static ItemStack stackItemRing;
    public static ItemStack stackItemPendant;
    public static ItemStack stackItemBelt;

	@Override
	public void preInit() {
        itemRing = new RFBauble().setBaubleType(BaubleType.RING).setUnlocalizedName("chbachman.armour.itemRing").setTextureName(Reference.ITEM_LOCATION + "ItemRing");
        itemPendant = new RFBauble().setBaubleType(BaubleType.AMULET).setUnlocalizedName("chbachman.armour.itemPendant").setTextureName(Reference.ITEM_LOCATION + "ItemPendant");
        itemBelt = new RFBauble().setBaubleType(BaubleType.BELT).setUnlocalizedName("chbachman.armour.itemBelt").setTextureName(Reference.ITEM_LOCATION + "ItemBelt");
        GameRegistry.registerItem(itemRing, "itemRing");
        GameRegistry.registerItem(itemPendant, "itemPendant");
        GameRegistry.registerItem(itemBelt, "itemBelt");
        
	}

	@Override
	public void init() {
        stackItemRing = NBTHelper.createDefaultStackTag(new ItemStack(itemRing));
        stackItemBelt = NBTHelper.createDefaultStackTag(new ItemStack(itemBelt));
        stackItemPendant = NBTHelper.createDefaultStackTag(new ItemStack(itemPendant));
        
        ModularItemRegistry.registerItem((IModularItem) itemPendant);
        ModularItemRegistry.registerItem((IModularItem) itemRing);
        ModularItemRegistry.registerItem((IModularItem) itemBelt);
	}

	@Override
	public void postInit() {
    	GameRegistry.addRecipe(new ShapedOreRecipe(stackItemRing, new Object[] {" I ", "I I", " I ", 'I', Vanilla.temperedElectrum}));
        GameRegistry.addRecipe(new ShapedOreRecipe(stackItemBelt, new Object[] {"III", "I I", "III", 'I', Vanilla.temperedElectrum}));
        GameRegistry.addRecipe(new ShapedOreRecipe(stackItemPendant, new Object[] {" ss", "IIs", "II ", 'I', Vanilla.temperedElectrum, 's', Items.string}));
	}

	@Override
	public void registerUpgrades() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerUpgradeRecipes() {
		// TODO Auto-generated method stub
		
	}

}
