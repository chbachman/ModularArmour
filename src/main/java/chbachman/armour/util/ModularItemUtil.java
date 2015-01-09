package chbachman.armour.util;

import java.util.ArrayList;

import cpw.mods.fml.common.Loader;
import chbachman.armour.register.Baubles;
import chbachman.armour.register.Vanilla;
import net.minecraft.item.ItemStack;

public class ModularItemUtil {

	public static ItemStack[] getListOfItems(){
		
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		
		list.add(new ItemStack(Vanilla.helmetModular, 1, 0));
		list.add(new ItemStack(Vanilla.chestplateModular, 1, 0));
		list.add(new ItemStack(Vanilla.leggingsModular, 1, 0));
		list.add(new ItemStack(Vanilla.bootsModular, 1, 0));
		
		if(Loader.isModLoaded("Baubles")){
			list.add(new ItemStack(Baubles.itemBelt, 1, 0));
			list.add(new ItemStack(Baubles.itemPendant, 1, 0));
			list.add(new ItemStack(Baubles.itemRing, 1, 0));
		}
		
		return list.toArray(new ItemStack[0]);
		
	}
	
}
