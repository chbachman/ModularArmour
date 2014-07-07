package chbachman.armour.upgrade.upgradeList.potionEffect;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import chbachman.armour.upgrade.Upgrade;

public enum PotionUpgrades {
	
	DAMAGE("Strength Potion", Potion.damageBoost, Items.blaze_powder),
	SPEED("Speed Potion", Potion.moveSpeed, Items.sugar),
	
	
	
	
	;
	
	public final String name;
	public final Potion potion;
	public final Item recipe;
	
	private PotionUpgrades(String name, Potion potion, Item recipe){
		this.name = name;
		this.potion = potion;
		this.recipe = recipe;
	}
	
	public static void init(){
		
		for(PotionUpgrades upgrade: PotionUpgrades.values()){
			for (int i = 0; i < 2; i++) {
				Upgrade.upgradeList.add(new SuperUpgradePotion(upgrade.name, upgrade.potion, upgrade.recipe, i));
			}
		}

	}
	
	

}
