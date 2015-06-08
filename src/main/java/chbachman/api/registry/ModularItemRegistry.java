package chbachman.api.registry;

import java.util.HashMap;

import chbachman.api.item.IModularItem;
import chbachman.api.util.Array;
import chbachman.api.util.ImmutableArray;

public final class ModularItemRegistry{

	private ModularItemRegistry() {}

	private static ModularItemRegistry INSTANCE = new ModularItemRegistry();

	private Array<IModularItem> itemList = new Array<IModularItem>();

	private Array<IItemListener> listenerList = new Array<IItemListener>();

	private HashMap<IModularItem, IItemListener[]> listenerMap = new HashMap<IModularItem, IItemListener[]>();

	@SuppressWarnings("unchecked")
	/**
	 * Gets the UpgradeListener for the given Upgrade, used to retrieve data from the listener.
	 * @param upgrade
	 * @param clazz
	 * @return
	 */
	public static <T extends IItemListener> T getListenerForUpgrade(IModularItem upgrade, Class<T> clazz){

		IItemListener[] list = INSTANCE.listenerMap.get(upgrade);

		for (IItemListener l : list){
			if (l.getClass() == clazz){
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
	public static IItemListener registerListener(IItemListener l){
		INSTANCE.listenerList.add(l);
		return l;
	}

	/**
	 * Registers the given upgrade, adds it to the global list, and calls all
	 * itemListeners.
	 * 
	 * @param upgrade
	 * @return the upgrade, for chaining.
	 */
	public static IModularItem registerItem(IModularItem upgrade){

		IItemListener[] list = new IItemListener[INSTANCE.listenerList.size];

		for (int i = 0; i < INSTANCE.listenerList.size; i++){
			list[i] = INSTANCE.listenerList.get(i).onItemAdded(upgrade);
		}

		INSTANCE.listenerMap.put(upgrade, list);
		INSTANCE.itemList.add(upgrade);

		return upgrade;
	}

	/**
	 * Returns an immutable list of the currently registered Upgrades.
	 * 
	 * @return
	 */
	public static ImmutableArray<IModularItem> getUpgradeList(){
		return new ImmutableArray(INSTANCE.itemList);
	}

	/**
	 * Get the upgrade with the specified name
	 * 
	 * @param s
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends IModularItem> T getUpgrade(Class<T> s){
		for (IModularItem item : INSTANCE.itemList){

			if (s == item.getClass()){
				return (T) item;
			}

		}

		return null;
	}

}
