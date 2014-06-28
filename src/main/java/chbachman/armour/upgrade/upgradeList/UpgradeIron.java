package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.upgrade.Upgrade;

public class UpgradeIron extends Upgrade{
	
	
	
	public UpgradeIron(){
		super("Iron");
		
		this.recipe = new Recipe(this, "III", "I I", "III", 'I', Items.iron_ingot);
		
		Recipe.craftinglist.add(recipe);
	}
	
	@Override
	public void onArmourTick(World world, EntityPlayer player, ItemStack stack){
		
		PotionEffect effect = new PotionEffect(Potion.digSpeed.id, 200, 1, false);
		
		player.addPotionEffect(effect);
	}
	
	@Override
	public int getArmourDisplay(){
		return 5;
	}

}
