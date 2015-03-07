package chbachman.armour.crafting;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.inventory.IInventory;
import chbachman.api.IUpgrade;
import chbachman.armour.ModularArmour;

public class RecipeList extends ArrayList<Recipe>{

	private static final long serialVersionUID = -5631130444681024796L;

	@Override
	public boolean add(Recipe e){
		if(ModularArmour.config.get("Command Enabling", e.getCraftingResult().getName(), true)){
			return super.add(e);
		}
		
		return true;
	}

	@Override
	public void add(int index, Recipe element){
		if(ModularArmour.config.get("Command Enabling", element.getCraftingResult().getName(), true)){
			super.add(index, element);
		}
	}
    
    public IUpgrade getResult(IInventory crafting){
    	for(Recipe recipe : this){
    		if(recipe.matches(crafting)){
    			return recipe.getCraftingResult();
    		}
    	}
    	
    	return null;
    }
    
    public void remove(IUpgrade upgrade){
    	Iterator<Recipe> iterator = this.iterator();

    	while(iterator.hasNext()){
    		if(iterator.next().getCraftingResult().equals(upgrade)){
    			iterator.remove();
    		}
    	}
    }
}
