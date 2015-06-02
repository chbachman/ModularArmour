package chbachman.armour.crafting;

import java.util.Iterator;

import net.minecraft.inventory.IInventory;
import chbachman.api.upgrade.IUpgrade;

import com.badlogic.gdx.utils.Array;

public class RecipeList extends Array<Recipe>{
    
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
