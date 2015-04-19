package chbachman.api.registry;

import chbachman.api.upgrade.IUpgrade;

/**
 * A Upgrade Listener is something that gets notified whenever an upgrade gets added, and usually stores extra data per upgrade.
 * @author Chbachman
 *
 */
public interface IUpgradeListener{
	
	/**
	 * Create and return the UpgradeListener that stores data for the upgrade
	 * @param upgrade
	 * @return
	 */
	IUpgradeListener onUpgradeAdded(IUpgrade upgrade);
	
}
