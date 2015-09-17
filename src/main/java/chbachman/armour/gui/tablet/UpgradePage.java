package chbachman.armour.gui.tablet;

import chbachman.api.registry.IUpgradeListener;
import chbachman.api.upgrade.IUpgrade;
import chbachman.armour.ModularArmour;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.util.IIcon;

public class UpgradePage implements IUpgradeListener{
	
	IIcon icon;
	String iconName;
	
	IUpgrade upgrade;
	
	@Override
	public IUpgradeListener onUpgradeAdded(IUpgrade upgrade) {
		
		UpgradePage page = new UpgradePage();
		page.upgrade = upgrade;
		
		Class upgradeClass = upgrade.getClass();
		
		Object icon;
		
		try {
			icon = upgradeClass.getField("icon").get(upgrade);
			
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			ModularArmour.log.warn("The icon field on an upgrade needs to be public", e);
			return null; //We don't want to crash here, so just ignore it.
		} catch (NoSuchFieldException e) {
			ModularArmour.log.warn("The Upgrade " + upgrade.getBaseName() + " does not have a icon field. This should happen.");
			return null; //The field doesn't exist, we don't need to include it.
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
		
		if(icon instanceof String){
			page.iconName = (String) icon;
		}else if(icon instanceof IIcon){
			page.icon = (IIcon) icon;
		}else{
			ModularArmour.log.warn("The icon field on the %s upgrade must be either a IIcon or a String", upgrade);
			return null;
		}
		
		return page;
		
		
	}
	
	

}
