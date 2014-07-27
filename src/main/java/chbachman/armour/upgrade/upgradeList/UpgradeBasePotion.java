package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.upgrade.Upgrade;

public class UpgradeBasePotion extends Upgrade{
	
	public UpgradeBasePotion() {
		super("Potion");
	}


	@Override
	public Recipe getRecipe() {
		return new Recipe(this, "iri", "gwg", "igi", 'i', Items.iron_ingot, 'g', Item.getItemFromBlock(Blocks.glass) , 'r', Items.redstone, 'w', Items.water_bucket);
	}
	
	@Override
	public boolean isCompatible(ArmourSlot slot) {
		return true;
	}
	
	public void onArmourTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot){

	}

}
