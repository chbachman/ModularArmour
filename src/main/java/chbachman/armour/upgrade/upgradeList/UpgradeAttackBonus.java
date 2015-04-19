package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import chbachman.armour.util.UpgradeUtil;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class UpgradeAttackBonus extends UpgradeBasic{
	
	public UpgradeAttackBonus(String name) {
		super(name);
	}

	@SubscribeEvent
	public void onPlayerAttack(LivingAttackEvent e){
		
		if(e.source.getEntity() instanceof EntityPlayer){
			
			UpgradeUtil.doesPlayerHaveUpgrade((EntityPlayer) e.source.getEntity(), this);
			
		}
		
	}

}
