package chbachman.armour.upgrade.upgradeList.potionEffect;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import chbachman.armour.upgrade.Upgrade;

public enum PotionUpgrades {
	
	DAMAGE("Strength Potion", Potion.damageBoost, Items.blaze_powder),
	SPEED("Speed Potion", Potion.moveSpeed, Items.sugar),
	HASTE("Haste Potion", Potion.digSpeed, Items.iron_pickaxe),
	JUMP("Jump Potion", Potion.jump, Items.feather),
	REGENERATION("Regen Potion", Potion.regeneration, Items.ghast_tear),
	RESISTANCE("Resistance Potion", Potion.resistance, Items.iron_chestplate),
	FIRERESISTANCE("Fire Resistance Potion", Potion.fireResistance, Items.magma_cream),
	WATERBREATHING("Water Breathing Potion", Potion.waterBreathing, Items.glass_bottle),
	NIGHTVISION("NightVision Potion", Potion.nightVision, Items.golden_carrot),
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
