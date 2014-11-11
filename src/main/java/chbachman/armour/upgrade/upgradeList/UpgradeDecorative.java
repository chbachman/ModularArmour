package chbachman.armour.upgrade.upgradeList;

import net.minecraft.item.ItemStack;

public class UpgradeDecorative extends UpgradeBasic{

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
