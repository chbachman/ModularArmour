package chbachman.armour.upgrade.upgradeList;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import chbachman.api.item.IModularItem;
import chbachman.api.upgrade.Upgrade;
import chbachman.api.util.ArmourSlot;
import chbachman.armour.util.ConfigHelper;
import chbachman.armour.util.EnergyUtil;
import chbachman.armour.util.UpgradeUtil;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.input.Keyboard;

public class UpgradeJumpBoost extends Upgrade {

	public UpgradeJumpBoost() {
		super("jumpBoost");
		MinecraftForge.EVENT_BUS.register(this);

	}

	private int cost;

	@Override
	public void registerConfigOptions(){
		cost = ConfigHelper.get(ConfigHelper.SPEED,this, "cost to jump high", 1000);
	}

	@SubscribeEvent
	public void onPlayerJump(LivingJumpEvent event) {
		if(event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			
			List<ItemStack> list = UpgradeUtil.getPlayerUpgrades(player, this);
			
			int energyCost = cost;
			
			for(ItemStack stack : list){
				// You might not always want to jump 10 blocks high :P
				if(stack != null && EnergyUtil.getEnergyStored(stack) > energyCost && !Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
					IModularItem modularItem = (IModularItem) stack.getItem();
					int level = modularItem.getLevel(stack);
					
					(modularItem).extractEnergy(stack, energyCost * level + 1, false);
					player.motionY += .3 * level + 1;
					
				}
			}
		}
	}
	
	@Override
	public boolean isCompatible(IModularItem item, ItemStack stack, int armorType) {
		return armorType == ArmourSlot.LEGS.id;
	}

}
