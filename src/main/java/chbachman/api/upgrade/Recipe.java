package chbachman.api.upgrade;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class Recipe {
    // Added in for future ease of change, but hard coded for now.
    private static final int MAX_CRAFT_GRID_WIDTH = 3;
    private static final int MAX_CRAFT_GRID_HEIGHT = 3;

    private IUpgrade output = null;

    /**
     * This can be consisted of: ItemStack, ArrayList<ItemStack>
     * 
     */
    private Object[] input = null;
    public int width = 0;
    public int height = 0;
    public boolean mirrored = true;

    /**
     * Construct the recipe with a contructor identical to
     * {@link ShapedOreRecipe}.
     * 
     * @param result
     * @param recipe
     */
    public Recipe(IUpgrade result, Object... recipe) {

        output = result;

        String shape = "";
        int idx = 0;

        if (recipe[idx] instanceof Boolean) {
            mirrored = (Boolean) recipe[idx];
            if (recipe[idx + 1] instanceof Object[]) {
                recipe = (Object[]) recipe[idx + 1];
            } else {
                idx = 1;
            }
        }

        if (recipe[idx] instanceof String[]) {
            String[] parts = ((String[]) recipe[idx++]);

            for (String s : parts) {
                width = s.length();
                shape += s;
            }

            height = parts.length;
        } else {
            while (recipe[idx] instanceof String) {
                String s = (String) recipe[idx++];
                shape += s;
                width = s.length();
                height++;
            }
        }

        if (width * height != shape.length()) {
            String ret = "Invalid shaped ore recipe: ";
            for (Object tmp : recipe) {
                ret += tmp + ", ";
            }
            ret += output;
            throw new RuntimeException(ret);
        }

        HashMap<Character, Object> itemMap = new HashMap<Character, Object>();

        for (; idx < recipe.length; idx += 2) {
            Character chr = (Character) recipe[idx];
            Object in = recipe[idx + 1];

            if (in instanceof ItemStack) {
                itemMap.put(chr, ((ItemStack) in).copy());
            } else if (in instanceof Item) {
                itemMap.put(chr, new ItemStack((Item) in));
            } else if (in instanceof Block) {
                itemMap.put(chr, new ItemStack((Block) in, 1, OreDictionary.WILDCARD_VALUE));
            } else if (in instanceof String) {
                itemMap.put(chr, in);
            } else {
                String ret = "Invalid shaped ore recipe: ";
                for (Object tmp : recipe) {
                    ret += tmp + ", ";
                }
                ret += output;
                throw new RuntimeException(ret);
            }
        }

        input = new Object[width * height];
        int x = 0;
        for (char chr : shape.toCharArray()) {
            input[x++] = itemMap.get(chr);
        }
    }

    /**
     * Constructs the Recipe with the array for the input and the result for the
     * output. The flag does not matter, it just differentaties it from the
     * Recipe(IUpgrade result, Object...) constructor.
     * 
     * @param result
     * @param array
     * @param flag
     */
    public Recipe(IUpgrade result, Object[] array, int width, int height) {
        this.input = array;
        this.output = result;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public IUpgrade getCraftingResult() {
        return output;
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize() {
        return input.length;
    }

    /**
     * Get the output of the recipe.
     * 
     * @return
     */
    public IUpgrade getRecipeOutput() {
        return output;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(IInventory inv) {
        for (int x = 0; x <= MAX_CRAFT_GRID_WIDTH - width; x++) {
            for (int y = 0; y <= MAX_CRAFT_GRID_HEIGHT - height; ++y) {
                if (checkMatch(inv, x, y, false)) {
                    return true;
                }

                if (mirrored && checkMatch(inv, x, y, true)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks whether the inventory matches the current recipe.
     * 
     * @param inv
     * @param startX
     * @param startY
     * @param mirror
     * @return
     */
    private boolean checkMatch(IInventory inv, int startX, int startY, boolean mirror) {
        for (int x = 0; x < MAX_CRAFT_GRID_WIDTH; x++) {
            for (int y = 0; y < MAX_CRAFT_GRID_HEIGHT; y++) {
                int subX = x - startX;
                int subY = y - startY;
                Object target = null;

                if (subX >= 0 && subY >= 0 && subX < width && subY < height) {
                    if (mirror) {
                        target = input[width - subX - 1 + subY * width];
                    } else {
                        target = input[subX + subY * width];
                    }
                }

                ItemStack slot = inv.getStackInSlot(x + y * 3);

                if (target instanceof ItemStack) {
                    if (!OreDictionary.itemMatches((ItemStack) target, slot, false)) {
                        return false;
                    }
                } else if (target instanceof String) {
                    boolean matched = false;

                    Iterator<ItemStack> itr = OreDictionary.getOres(((String) target)).iterator();
                    while (itr.hasNext() && !matched) {
                        matched = OreDictionary.itemMatches(itr.next(), slot, false);
                    }

                    if (!matched) {
                        return false;
                    }
                } else if (target == null && slot != null) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks the match with another recipe.
     * 
     * @param recipe
     * @return
     */
    public boolean matches(Recipe recipe) {
        for (int x = 0; x <= MAX_CRAFT_GRID_WIDTH - width; x++) {
            for (int y = 0; y <= MAX_CRAFT_GRID_HEIGHT - height; ++y) {
                if (checkMatch(recipe, x, y, false)) {
                    return true;
                }

                if (mirrored && checkMatch(recipe, x, y, true)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks the match with the recipe with the start and whether the mirror
     * works.
     * 
     * @param inv
     * @param startX
     * @param startY
     * @param mirror
     * @return
     */
    private boolean checkMatch(Recipe inv, int startX, int startY, boolean mirror) {
        for (int x = 0; x < MAX_CRAFT_GRID_WIDTH; x++) {
            for (int y = 0; y < MAX_CRAFT_GRID_HEIGHT; y++) {
                int subX = x - startX;
                int subY = y - startY;
                Object target = null;

                if (subX >= 0 && subY >= 0 && subX < width && subY < height) {
                    if (mirror) {
                        target = input[width - subX - 1 + subY * width];
                    } else {
                        target = input[subX + subY * width];
                    }
                }

                Object slot = inv.input[(x) * 3 + y];

                if (target instanceof ItemStack) {
                    if (!(slot instanceof ItemStack)) {
                        return false;
                    }

                    if (!OreDictionary.itemMatches((ItemStack) target, (ItemStack) slot, false)) {
                        return false;
                    }
                } else if (target instanceof String) {

                    if (!(slot instanceof String)) {
                        return false;
                    }

                    if (!slot.equals(target)) {
                        return false;
                    }
                } else if (target == null && slot != null) {
                    return false;
                }
            }
        }

        return true;
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

        if (!this.matches(other)) {
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
     * Returns the input for this recipe, any mod accessing this value should
     * never manipulate the values in this array as it will effect the recipe
     * itself.
     * 
     * @return The recipes input vales.
     */
    public Object[] getInput() {
        return this.input;
    }

    private ItemStack[][] itemStackInput;

    public ItemStack[][] getItemStackInput() {

        if (itemStackInput != null) {
            return itemStackInput;
        }

        ItemStack[][] output = new ItemStack[9][];

        Object[] list = this.getInput();

        for (int i = 0; i < list.length; i++) {

            Object object = list[i];

            if (object == null) {
                output[i] = new ItemStack[0];
            }

            if (object instanceof String) {
                output[i] = OreDictionary.getOres((String) object).toArray(new ItemStack[0]);
            }

            if (object instanceof ItemStack) {
                output[i] = new ItemStack[] { (ItemStack) object };
            }

        }

        itemStackInput = output;

        return output;

    }

    @Override
    public String toString() {
        return "Output: " + this.output.getName() + "Input: " + Arrays.toString(this.input);
    }

}