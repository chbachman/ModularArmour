package chbachman.armour.upgrade.upgradeList;

import chbachman.api.item.IModularItem;
import net.minecraft.item.ItemStack;

public class UpgradeDecorative extends UpgradeBasic{

	@Override
	public boolean isCompatible(IModularItem item, ItemStack stack, int armorType) {
		return super.isCompatible(item, stack, armorType) && item.isArmour();
	}

	String textureName;
	
	public UpgradeDecorative(String name) {
		super(name);
	}
	
	public UpgradeDecorative setTextureName(String name){
		
		textureName = name;
		
		return this;
	}
	
	@Override
	public String getArmourTexture(ItemStack stack, int slot){
    	return textureName;
    }

}
