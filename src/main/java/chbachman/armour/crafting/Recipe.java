package chbachman.armour.crafting;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import chbachman.api.IUpgrade;

public class Recipe {
    
    public static ArrayList<Recipe> craftinglist = new ArrayList<Recipe>();
    
    private ItemStack[] recipe;
    private IUpgrade result;
    
    public Recipe(IUpgrade output, Object... ingredients) {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;
        
        if (ingredients[i] instanceof String[]) {
            String[] astring = (String[]) ingredients[i++];
            
            for (int l = 0; l < astring.length; ++l) {
                String s1 = astring[l];
                ++k;
                j = s1.length();
                s = s + s1;
            }
        } else {
            while (ingredients[i] instanceof String) {
                String s2 = (String) ingredients[i++];
                ++k;
                j = s2.length();
                s = s + s2;
            }
        }
        
        HashMap<Character, ItemStack> hashmap;
        
        for (hashmap = new HashMap<Character, ItemStack>(); i < ingredients.length; i += 2) {
            Character character = (Character) ingredients[i];
            ItemStack itemstack1 = null;
            
            if (ingredients[i + 1] instanceof Item) {
                itemstack1 = new ItemStack((Item) ingredients[i + 1]);
            } else if (ingredients[i + 1] instanceof Block) {
                itemstack1 = new ItemStack(Item.getItemFromBlock((Block) ingredients[i + 1]), 1, 32767);
            } else if (ingredients[i + 1] instanceof ItemStack) {
                itemstack1 = (ItemStack) ingredients[i + 1];
            }
            
            hashmap.put(character, itemstack1);
        }
        
        ItemStack[] aitemstack = new ItemStack[j * k];
        
        for (int i1 = 0; i1 < j * k; ++i1) {
            char c0 = s.charAt(i1);
            
            if (hashmap.containsKey(Character.valueOf(c0))) {
                aitemstack[i1] = hashmap.get(Character.valueOf(c0)).copy();
            } else {
                aitemstack[i1] = null;
            }
        }
        
        this.result = output;
        this.recipe = aitemstack;
        Recipe.addToList(this);
    }
    
    private static void addToList(Recipe recipe) {
        craftinglist.add(recipe);
    }
    
    public boolean matches(IInventory containerWrapper) {
        
        if (containerWrapper == null) {
            return false;
        }
        
        for (int i = 0; i < 9; i++) {
            
            ItemStack stack = containerWrapper.getStackInSlot(i);
            
            if (stack == null) {
                if (this.recipe[i] != null) {
                    return false;
                }
            } else {
                
                ItemStack copy = stack.copy();
                
                copy.stackSize = 1;
                
                if (!ItemStack.areItemStacksEqual(copy, this.recipe[i])) {
                    return false;
                }
                
            }
            
        }
        return true;
    }
    
    public IUpgrade getResult() {
        return this.result;
    }
    
    public ItemStack[] getRecipe(){
        return this.recipe;
    }
    
    @Override
    public String toString() {
        return this.result.toString();
    }
    
    public static IUpgrade getResult(IInventory containerWrapper) {
        
        for (int i = 0; i < craftinglist.size(); i++) {
            if (craftinglist.get(i).matches(containerWrapper)) {
                return craftinglist.get(i).getResult();
            }
        }
        
        return null;
    }
    
}