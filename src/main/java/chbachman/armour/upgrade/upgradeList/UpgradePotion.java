package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.upgrade.Upgrade;

public class UpgradePotion extends Upgrade{
	
	public UpgradePotion() {
		super("Potion");
		
		this.recipe = new Recipe(this, "iri", "gwg", "igi", 'i', Items.iron_ingot, 'g', Items.gold_ingot , 'r', Items.redstone, 'w', Items.water_bucket);
		
		Recipe.addToList(recipe);
	}

	@Override
	public boolean isCompatible(ArmourSlot slot) {
		return true;
	}
	
	public void onArmourTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot){

	}

}
