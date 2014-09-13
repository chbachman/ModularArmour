package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import chbachman.api.Upgrade;
import chbachman.armour.util.UpgradeUtil;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class UpgradeJumpBoost extends Upgrade {

	public UpgradeJumpBoost() {
		super("jumpBoost");
		MinecraftForge.EVENT_BUS.register(this);
		
	}
	
	@SubscribeEvent
	public void onPlayerJump(LivingJumpEvent event) {
		if(event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			
			if(UpgradeUtil.doesPlayerHaveUpgrade(player, this)){
				player.motionY += .3;
				
			}
		}
	}

}
