package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.upgrade.Upgrade;
import chbachman.armour.util.UpgradeUtil;

public class UpgradeHoverJetpack extends Upgrade{

	public UpgradeHoverJetpack() {
		super("Jetpack");
		
		this.recipe = new Recipe(this, "igi", "ini", "r r", 'i', Items.iron_ingot, 'g', Items.gold_ingot, 'r', Items.redstone, 'n', Items.nether_star);
		
		Recipe.addToList(this.recipe);
	}

	@Override
	public boolean isCompatible(ArmourSlot slot) {
		return slot == ArmourSlot.CHESTPLATE;
	}

	@Override
	public void onArmourTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot){
		//if(WorldUtil.isServer(world)){
			player.capabilities.allowFlying = true;
			
			if(!UpgradeUtil.doesPlayerHaveUpgrade(player, "Calf Shields") && player.capabilities.isFlying){
				player.attackEntityFrom(DamageSource.onFire, 4F);
			}
		//}
	}



}
