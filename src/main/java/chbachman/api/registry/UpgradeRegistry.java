package chbachman.api.registry;

import java.util.ArrayList;
import java.util.Iterator;

import chbachman.api.upgrade.IUpgrade;
import chbachman.api.upgrade.Recipe;
import chbachman.api.util.Array;
import chbachman.api.util.ImmutableArray;
import chbachman.api.util.ObjectMap;
import chbachman.armour.handler.UpgradeHandler;
import cpw.mods.fml.common.Loader;

public final class UpgradeRegistry {

    public UpgradeRegistry() {
    }

    /**
     * This is not for public access, only for instances in which modifying the
     * registry is necesary, such as in syncing.
     */
    public static UpgradeRegistry INSTANCE = new UpgradeRegistry();

    public UpgradeList upgradeList = new UpgradeList();

    public Array<IUpgradeListener> listenerList = new Array<IUpgradeListener>();

    public ObjectMap<IUpgrade, IUpgradeListener[]> listenerMap = new ObjectMap<IUpgrade, IUpgradeListener[]>();

    public Array<Recipe> recipeList = new Array<Recipe>();

    @SuppressWarnings("unchecked")
    /**
     * Gets the UpgradeListener for the given Upgrade, used to retrieve data
     * from the listener.
     * 
     * @param upgrade
     * @param clazz
     * @return
     */
    public static <T extends IUpgradeListener> T getListenerForUpgrade(IUpgrade upgrade, Class<T> clazz) {

        IUpgradeListener[] list = INSTANCE.listenerMap.get(upgrade);

        for (IUpgradeListener l : list) {
        	
            if (l.getClass() == clazz) {
                return (T) l;
            }
        }

        return null;

    }

    /**
     * Registers the UpgradeListener to be notified of all future additions of
     * upgrades.
     * 
     * @param l
     * @return the listener, for chaining.
     */
    public static IUpgradeListener registerListener(IUpgradeListener l) {

        if (l == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }

        INSTANCE.listenerList.add(l);
        return l;
    }

    /**
     * Registers the given upgrade, adds it to the global list, and calls all
     * upgradeListeners.
     * 
     * @param upgrade
     * @return the upgrade, for chaining.
     */
    public static IUpgrade registerUpgrade(IUpgrade upgrade) {

        if (upgrade == null) {
            throw new IllegalArgumentException("Upgrade cannot be null");
        }

        Array<IUpgradeListener> list = new Array<IUpgradeListener>(IUpgradeListener.class);

        for (int i = 0; i < INSTANCE.listenerList.size; i++) {
        	
        	IUpgradeListener listener = INSTANCE.listenerList.get(i).onUpgradeAdded(upgrade);
        	
        	if(listener == null){
        		continue;
        	}
        	
            list.add(listener);
        }

        INSTANCE.listenerMap.put(upgrade, list.toArray());
        INSTANCE.upgradeList.put(upgrade);
        
        return upgrade;
    }

    public static Recipe registerRecipe(Recipe recipe) {

        if (recipe == null) {
            throw new IllegalArgumentException("Recipe cannot be null");
        }

        if (recipe.getRecipeOutput() == null) {
            throw new IllegalArgumentException(String.format("Recipe cannot create a null upgrade. Please fix %s recipe.", recipe.toString()));
        }
        
        if (recipe.modid != null && !Loader.isModLoaded(recipe.modid)) {
        	return recipe;
        }

        INSTANCE.recipeList.add(recipe);

        return recipe;
    }

    /**
     * Removes the given recipes.
     * 
     * @param recipe
     * @return success of removing.
     */
    public static boolean removeRecipe(Recipe recipe) {
        return INSTANCE.recipeList.removeValue(recipe, false);
    }

    /**
     * Removes all the recipes for the given Upgrade.
     * 
     * @param upgrade
     * @return sucess of removing.
     */
    public static boolean removeRecipe(IUpgrade upgrade) {

        boolean found = false;

        for (int i = 0; i < INSTANCE.recipeList.size; i++) {
            Recipe recipe = INSTANCE.recipeList.get(i);

            if (recipe.getRecipeOutput().equals(upgrade)) {
                INSTANCE.recipeList.removeIndex(i);
                found = true;
            }
        }

        return found;
    }

    /**
     * Returns an immutable list of the currently registered Upgrades.
     * 
     * @return
     */
    public static ImmutableArray<IUpgrade> getUpgradeList() {
        return new ImmutableArray(INSTANCE.upgradeList.values().toArray());
    }

    public static ImmutableArray<Recipe> getRecipeList() {
        return new ImmutableArray(INSTANCE.recipeList);
    }
    
    private static Array<IUpgrade> sortedList;
    
    /**
     * Gets a topologically sorted list of upgrades.
     * @return
     */
    public static ImmutableArray<IUpgrade> sortedRecipeList(){
    	
    	if(sortedList == null || sortedList.size != INSTANCE.upgradeList.size){ //Either the list is empty, or we need to update it.
    		sortedList = new Array<IUpgrade>();
    		
    		ArrayList<IUpgrade> toAdd = new ArrayList<IUpgrade>();

            for (IUpgrade upgrade : UpgradeRegistry.getUpgradeList()) { // Populate the inital list, add all first level upgrades.
                if (upgrade.getDependencies() == null || upgrade.getDependencies().length == 0) {
                	sortedList.add(upgrade);
                }else{
                	toAdd.add(upgrade);
                }
            }

            int prevSize = 0;
            int currSize = toAdd.size();

            while (prevSize != currSize && currSize != 0) {
            	
                Iterator<IUpgrade> iterator = toAdd.iterator();

                while (iterator.hasNext()) {
                    IUpgrade next = iterator.next();
                    
                    if (UpgradeHandler.checkDependencies(next, sortedList)) {
                        iterator.remove();
                        sortedList.add(next);
                    }
                }

                prevSize = currSize;
                currSize = toAdd.size();
            }
    	}
    	
    	return new ImmutableArray(sortedList);
    	
    }

    /**
     * Get the upgrade with the specified name
     * 
     * @param s
     * @return
     */
    public static IUpgrade getUpgrade(String s) {
        return INSTANCE.upgradeList.get(s);
    }

}
