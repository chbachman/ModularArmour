package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.upgrade.Upgrade;

public class UpgradeHoverJetpack extends Upgrade{

	public UpgradeHoverJetpack() {
		super("Jetpack");
	}

	@Override
	public boolean isCompatible(ArmourSlot slot) {
		return slot == ArmourSlot.CHESTPLATE;
	}

	@Override
	public Recipe getRecipe() {
		return new Recipe(this, "igi", "ini", "r r", 'i', Items.iron_ingot, 'g', Items.gold_ingot, 'r', Items.redstone, 'n', Items.nether_star);
	}
	
	@Override
	public void onArmourEquip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot){
		player.capabilities.allowFlying = true;
		player.sendPlayerAbilities();
	}
	
	@Override
	public void onArmourTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot){
	    
		/*if(!UpgradeUtil.doesPlayerHaveUpgrade(player, "CalfShields") && player.capabilities.isFlying){
			player.attackEntityFrom(DamageSource.onFire, 4F);
		}*/
	}
	
	@Override
	public void onArmourDequip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot){
		player.capabilities.allowFlying = false;
		player.capabilities.isFlying = false;
		player.sendPlayerAbilities();
	}
	
	public int getEnergyTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot){
		if(player.capabilities.isFlying){
			return 500;
		}else{
			return 0;
		}
	}
	
	public void onNoEnergy(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot){
		player.capabilities.allowFlying = false;
		player.capabilities.isFlying = false;
		player.sendPlayerAbilities();
	}
	
}
