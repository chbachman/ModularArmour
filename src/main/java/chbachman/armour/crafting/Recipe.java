package chbachman.armour.crafting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import chbachman.api.IUpgrade;
import chbachman.armour.gui.ArmourContainerWrapper;

public class Recipe
{
    //Added in for future ease of change, but hard coded for now.
    private static final int MAX_CRAFT_GRID_WIDTH = 3;
    private static final int MAX_CRAFT_GRID_HEIGHT = 3;

    private IUpgrade output = null;
    private Object[] input = null;
    private int width = 0;
    private int height = 0;
    private boolean mirrored = true;
    
    public Recipe(IUpgrade result, Object... recipe)
    {
    	
        output = result;

        String shape = "";
        int idx = 0;

        if (recipe[idx] instanceof Boolean)
        {
            mirrored = (Boolean)recipe[idx];
            if (recipe[idx+1] instanceof Object[])
            {
                recipe = (Object[])recipe[idx+1];
            }
            else
            {
                idx = 1;
            }
        }

        if (recipe[idx] instanceof String[])
        {
            String[] parts = ((String[])recipe[idx++]);

            for (String s : parts)
            {
                width = s.length();
                shape += s;
            }

            height = parts.length;
        }
        else
        {
            while (recipe[idx] instanceof String)
            {
                String s = (String)recipe[idx++];
                shape += s;
                width = s.length();
                height++;
            }
        }

        if (width * height != shape.length())
        {
            String ret = "Invalid shaped ore recipe: ";
            for (Object tmp :  recipe)
            {
                ret += tmp + ", ";
            }
            ret += output;
            throw new RuntimeException(ret);
        }

        HashMap<Character, Object> itemMap = new HashMap<Character, Object>();

        for (; idx < recipe.length; idx += 2)
        {
            Character chr = (Character)recipe[idx];
            Object in = recipe[idx + 1];

            if (in instanceof ItemStack)
            {
                itemMap.put(chr, ((ItemStack)in).copy());
            }
            else if (in instanceof Item)
            {
                itemMap.put(chr, new ItemStack((Item)in));
            }
            else if (in instanceof Block)
            {
                itemMap.put(chr, new ItemStack((Block)in, 1, OreDictionary.WILDCARD_VALUE));
            }
            else if (in instanceof String)
            {
                itemMap.put(chr, OreDictionary.getOres((String)in));
            }
            else
            {
                String ret = "Invalid shaped ore recipe: ";
                for (Object tmp :  recipe)
                {
                    ret += tmp + ", ";
                }
                ret += output;
                throw new RuntimeException(ret);
            }
        }

        input = new Object[width * height];
        int x = 0;
        for (char chr : shape.toCharArray())
        {
            input[x++] = itemMap.get(chr);
        }
    }

    public Recipe(IUpgrade result, Object[] array, boolean flag){
    	this.input = array;
    	this.output = result;
    }
    
    /**
     * Returns an Item that is the result of this recipe
     */
    public IUpgrade getCraftingResult(ArmourContainerWrapper var1){ return output;}

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize(){ return input.length; }

    public IUpgrade getRecipeOutput(){ return output; }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(ArmourContainerWrapper inv)
    {
        for (int x = 0; x <= MAX_CRAFT_GRID_WIDTH - width; x++)
        {
            for (int y = 0; y <= MAX_CRAFT_GRID_HEIGHT - height; ++y)
            {
                if (checkMatch(inv, x, y, false))
                {
                    return true;
                }

                if (mirrored && checkMatch(inv, x, y, true))
                {
                    return true;
                }
            }
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private boolean checkMatch(ArmourContainerWrapper inv, int startX, int startY, boolean mirror)
    {
        for (int x = 0; x < MAX_CRAFT_GRID_WIDTH; x++)
        {
            for (int y = 0; y < MAX_CRAFT_GRID_HEIGHT; y++)
            {
                int subX = x - startX;
                int subY = y - startY;
                Object target = null;

                if (subX >= 0 && subY >= 0 && subX < width && subY < height)
                {
                    if (mirror)
                    {
                        target = input[width - subX - 1 + subY * width];
                    }
                    else
                    {
                        target = input[subX + subY * width];
                    }
                }

                ItemStack slot = inv.getStackInRowAndColumn(x, y);

                if (target instanceof ItemStack)
                {
                    if (!OreDictionary.itemMatches((ItemStack)target, slot, false))
                    {
                        return false;
                    }
                }
                else if (target instanceof ArrayList)
                {
                    boolean matched = false;

                    Iterator<ItemStack> itr = ((ArrayList<ItemStack>)target).iterator();
                    while (itr.hasNext() && !matched)
                    {
                        matched = OreDictionary.itemMatches(itr.next(), slot, false);
                    }

                    if (!matched)
                    {
                        return false;
                    }
                }
                else if (target == null && slot != null)
                {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean matches(Recipe inv)
    {
        for (int x = 0; x <= MAX_CRAFT_GRID_WIDTH - width; x++)
        {
            for (int y = 0; y <= MAX_CRAFT_GRID_HEIGHT - height; ++y)
            {
                if (checkMatch(inv, x, y, false))
                {
                    return true;
                }

                if (mirrored && checkMatch(inv, x, y, true))
                {
                    return true;
                }
            }
        }

        return false;
    }
    
    @SuppressWarnings("unchecked")
    private boolean checkMatch(Recipe inv, int startX, int startY, boolean mirror)
    {
        for (int x = 0; x < MAX_CRAFT_GRID_WIDTH; x++)
        {
            for (int y = 0; y < MAX_CRAFT_GRID_HEIGHT; y++)
            {
                int subX = x - startX;
                int subY = y - startY;
                Object target = null;

                if (subX >= 0 && subY >= 0 && subX < width && subY < height)
                {
                    if (mirror)
                    {
                        target = input[width - subX - 1 + subY * width];
                    }
                    else
                    {
                        target = input[subX + subY * width];
                    }
                }
                
                Object slot = inv.input[(x - 1) * 3 + y];

                if (target instanceof ItemStack)
                {
                	if(!(slot instanceof ItemStack)){
                		return false;
                	}
                	
                    if (!OreDictionary.itemMatches((ItemStack)target, (ItemStack)slot, false))
                    {
                        return false;
                    }
                }
                else if (target instanceof ArrayList)
                {
                	
                	if(!(slot instanceof ArrayList)){
                		return false;
                	}
                	
                    if(!slot.equals(target)){
                    	return false;
                    }
                }
                else if (target == null && slot != null)
                {
                    return false;
                }
            }
        }

        return true;
    }
    
    public Recipe setMirrored(boolean mirror)
    {
        mirrored = mirror;
        return this;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(input);
		result = prime * result + ((output == null) ? 0 : output.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Recipe))
			return false;
		Recipe other = (Recipe) obj;
		
		if(!this.matches(other)){
			return false;
		}
		if (output == null) {
			if (other.output != null)
				return false;
		} else if (!output.equals(other.output))
			return false;
		return true;
	}

	/**
     * Returns the input for this recipe, any mod accessing this value should never
     * manipulate the values in this array as it will effect the recipe itself.
     * @return The recipes input vales.
     */
    public Object[] getInput()
    {
        return this.input;
    }
    
    
    public static List<Recipe> craftingList = new ArrayList<Recipe>();
    
    public static IUpgrade getResult(ArmourContainerWrapper crafting){
    	for(Recipe recipe : craftingList){
    		if(recipe.matches(crafting)){
    			return recipe.getCraftingResult(crafting);
    		}
    	}
    	
    	return null;
    }
    
    public static void addRecipe(Recipe recipe){
    	craftingList.add(recipe);
    }
    
    public static void removeRecipe(IUpgrade upgrade){
    	Iterator<Recipe> iterator = craftingList.iterator();
    	
    	while(iterator.hasNext()){
    		if(iterator.next().output.equals(upgrade)){
    			iterator.remove();
    		}
    	}
    }
    
    public static void removeRecipe(Recipe upgrade){
    	Iterator<Recipe> iterator = craftingList.iterator();
    	
    	while(iterator.hasNext()){
    		if(iterator.next().equals(upgrade)){
    			iterator.remove();
    		}
    	}
    }
    
    public String toString(){
    	return "Output: " + this.output + "Input: " + Arrays.toString(this.input);
    }
    
}