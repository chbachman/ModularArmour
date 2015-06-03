package chbachman.api.registry;

import java.util.HashMap;
import java.util.List;

import chbachman.api.upgrade.IUpgrade;
import chbachman.api.util.Array;

import com.google.common.collect.ImmutableList;

public final class UpgradeRegistry{

	private UpgradeRegistry(){}
	
	private static UpgradeRegistry INSTANCE = new UpgradeRegistry();
	
	private UpgradeList upgradeList = new UpgradeList();
	
	private Array<IUpgradeListener> listenerList = new Array<IUpgradeListener>();
	
	private HashMap<IUpgrade, IUpgradeListener[]> listenerMap = new HashMap<IUpgrade, IUpgradeListener[]>();
	
	@SuppressWarnings("unchecked")
	/**
	 * Gets the UpgradeListener for the given Upgrade, used to retrieve data from the listener.
	 * @param upgrade
	 * @param clazz
	 * @return
	 */
	public static <T extends IUpgradeListener> T getListenerForUpgrade(IUpgrade upgrade, Class<T> clazz){
		
		IUpgradeListener[] list = INSTANCE.listenerMap.get(upgrade);
		
		for(IUpgradeListener l : list){
			if(l.getClass() == clazz){
				return (T) l;
			}
		}
		
		return null;
		
	}
	
	/**
	 * Registers the UpgradeListener to be notified of all future additions of upgrades.
	 * @param l
	 * @return the listener, for chaining.
	 */
	public static IUpgradeListener registerListener(IUpgradeListener l){
		INSTANCE.listenerList.add(l);
		return l;
	}
	
	/**
	 * Registers the given upgrade, adds it to the global list, and calls all upgradeListeners.
	 * @param upgrade
	 * @return the upgrade, for chaining.
	 */
	public static IUpgrade registerUpgrade(IUpgrade upgrade){
		
		IUpgradeListener[] list = new IUpgradeListener[INSTANCE.listenerList.size];
		
		for(int i = 0; i < INSTANCE.listenerList.size; i++){
			list[i] = INSTANCE.listenerList.get(i).onUpgradeAdded(upgrade);
		}
		
		INSTANCE.listenerMap.put(upgrade, list);
		INSTANCE.upgradeList.put(upgrade);
		
		return upgrade;
	}
	
	/**
	 * Returns an immutable list of the currently registered Upgrades.
	 * @return
	 */
	public static List<IUpgrade> getUpgradeList(){
		return ImmutableList.copyOf(INSTANCE.upgradeList.values());
	}
	
	/**
	 * Get the upgrade with the specified name
	 * @param s
	 * @return
	 */
	public static IUpgrade getUpgrade(String s){
		return INSTANCE.upgradeList.get(s);
	}
	
}
