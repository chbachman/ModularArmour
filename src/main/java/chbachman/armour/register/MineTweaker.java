
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chbachman.armour.register;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import scala.actors.threadpool.Arrays;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import chbachman.api.IUpgrade;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.upgrade.UpgradeList;

/**
 *
 * @author Stan
 */
@ZenClass("modularArmour.recipe")
public class MineTweaker implements Module{
	 
	@ZenMethod
	public static void addRecipe(String output, IIngredient[] ingredients) {
		MineTweakerAPI.apply(new AddRecipeAction(output, ingredients));
	}
	
	@ZenMethod
	public static void remove(String output) {
		removeRecipe(output, null);
	}
	
	@ZenMethod
	public static void removeRecipe(String output, @Optional IIngredient[] ingredients) {
		
		IUpgrade upgrade = UpgradeList.INSTANCE.get(output);
		
		if(upgrade == null){
			MineTweakerAPI.logError("Not an valid Upgrade");
		}
		
		List<Recipe> toRemove = new ArrayList<Recipe>();
		
		Iterator<Recipe> iterator = Recipe.craftingList.iterator();
		
		while(iterator.hasNext()){
			Recipe recipe = iterator.next();
			
			if(!recipe.getRecipeOutput().equals(upgrade)){
				continue;
			}
			
			
			
			if(ingredients == null){
				iterator.remove();
			}else{
				
				Object[] mcIngredients = new Object[ingredients.length];
				
				for (int i = 0; i < ingredients.length; i++) {
					mcIngredients[i] = ingredients[i].getInternal();
				}
				
				if(Arrays.equals(recipe.getInput(), mcIngredients)){
					iterator.remove();
				}
			}
			
		}
		
		for (Recipe recipe : toRemove) {
			MineTweakerAPI.apply(new RemoveRecipeAction(recipe));
		}
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddRecipeAction implements IUndoableAction {
		private final Recipe recipe;
		
		public AddRecipeAction(String output, IIngredient[] ingredients) {
			
			Object[] mcIngredients = new Object[ingredients.length];
			
			for (int i = 0; i < ingredients.length; i++) {
				mcIngredients[i] = ingredients[i].getInternal();
			}
			
			IUpgrade upgrade = UpgradeList.INSTANCE.get(output);
			
			if(upgrade == null){
				MineTweakerAPI.logError("Not an valid Upgrade");
			}
			
			recipe = new Recipe(upgrade, mcIngredients, true);
		}

		@Override
		public void apply() {
			Recipe.addRecipe(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			Recipe.removeRecipe(recipe);
		}

		@Override
		public String describe() {
			return "Adding Modular Armour recipe for " + recipe.getRecipeOutput().getName();
		}

		@Override
		public String describeUndo() {
			return "Removing Modular Armour recipe for " + recipe.getRecipeOutput().getName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveRecipeAction implements IUndoableAction {
		private final Recipe recipe;
		
		public RemoveRecipeAction(Recipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			Recipe.removeRecipe(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			Recipe.addRecipe(recipe);
		}

		@Override
		public String describe() {
			return "Removing Modular Armour recipe for " + recipe.getRecipeOutput().getName();
		}

		@Override
		public String describeUndo() {
			return "Restoring Modular Armour recipe for " + recipe.getRecipeOutput().getName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

	//Module
	@Override
	public void preInit() {
		
	}

	@Override
	public void init() {
		
	}

	@Override
	public void postInit() {
		MineTweakerAPI.registerClass(this.getClass());
	}

	@Override
	public void registerUpgrades() {
		
	}

	@Override
	public void registerUpgradeRecipes() {
		
	}
}
