package chbachman.armour.util.json;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.minecraft.item.ItemStack;

import org.apache.commons.io.filefilter.FileFilterUtils;

import chbachman.api.upgrade.IUpgrade;
import chbachman.armour.ModularArmour;
import chbachman.armour.crafting.Recipe;

import com.google.gson.GsonBuilder;

public class JsonRegister{
	
	public static void registerCustomSerializers(GsonBuilder gsonBuilder){
		gsonBuilder.registerTypeAdapter(ItemStack.class, new CustomItemStackJson());
		gsonBuilder.registerTypeAdapter(IUpgrade.class, new CustomIUpgradeJson());
		gsonBuilder.registerTypeAdapter(Recipe.class, new CustomRecipeJson());
	}
	
	public static void createJsonRecipes(GsonBuilder gsonBuilder){
		File folder = new File(ModularArmour.getConfigDirectory(), "recipes");

		folder.mkdir();
		
		if(folder.listFiles((FileFilter) FileFilterUtils.suffixFileFilter(".json")).length == 0){
			for(Recipe recipe : Recipe.recipeList){
				writeRecipeToFile(gsonBuilder, recipe, folder);
			}
		}
		
	}
	
	public static void registerJsonRecipes(GsonBuilder gsonBuilder){
		File folder = new File(ModularArmour.getConfigDirectory(), "modularArmourRecipes");
		
        folder.mkdir();
        
        File[] files = folder.listFiles((FileFilter) FileFilterUtils.suffixFileFilter(".json"));
        
        Recipe.recipeList.clear();
        
        for (File file : files){
        	Recipe.recipeList.add(createRecipeFromJson(gsonBuilder, file));
        }
	}
	
	public static void writeRecipeToFile(GsonBuilder gsonBuilder, Recipe recipe, File directory){
		
		String baseName = recipe.getRecipeOutput().getBaseName();
		
		File destination = new File(directory, baseName + ".json");
		
		int counter = 0;
		while(destination.exists()){
			destination = new File(directory, baseName + counter + ".json");
			counter++;
		}
		
		try {
			FileWriter writer = new FileWriter(destination);
			writer.write(createJsonFromRecipe(gsonBuilder, recipe));
			writer.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static String createJsonFromRecipe(GsonBuilder gsonBuilder, Recipe recipe){
		return gsonBuilder.setPrettyPrinting().create().toJson(recipe, Recipe.class);
	}
	
	public static Recipe createRecipeFromJson(GsonBuilder gsonBuilder, File file) {
        try {
            return gsonBuilder.setPrettyPrinting().create().fromJson(new FileReader(file), Recipe.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
