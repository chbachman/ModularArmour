package chbachman.armour.handler;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import chbachman.api.IModularItem;

public class ModularArmourHandler {
	
	private List<Storage> list;
	
	public ModularArmourHandler(){
		list = new ArrayList<Storage>();
	}
	
	public void register(IModularItem item, ItemStack defaultStack){
		list.add(new Storage(item, defaultStack));
	}
	
	public class Storage{
		
		ItemStack stack;
		IModularItem item;
		
		public Storage(IModularItem item, ItemStack stack){
			this.stack = stack;
			this.item = item;
			
		}
		
		
	}
	
	public ItemStack[] getListOfItems(){
		
		ItemStack[] stacks = new ItemStack[this.getNumberOfItems()];
		
		for(int i = 0; i < list.size(); i++){
			stacks[i] = list.get(i).stack;
		}
		
		return stacks;
		
	}
	
	public int getNumberOfItems(){
		return list.size();
	}
	
}
