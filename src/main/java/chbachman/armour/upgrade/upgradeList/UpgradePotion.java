package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import chbachman.api.upgrade.IUpgrade;
import chbachman.api.upgrade.Upgrade;
import chbachman.api.util.ArmourSlot;
import chbachman.armour.register.Vanilla;
import chbachman.armour.util.EnergyUtil;

public class UpgradePotion extends Upgrade {

	public final PotionEffect effect;
	public final int energyCost;
	
	public UpgradePotion(String name, Potion effect, int duration, int energyCost) {
		super(name);
		this.effect = new PotionEffect(effect.id, duration, 0, false);
		this.energyCost = energyCost;
	}
	
	@Override
	public int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot){
		
		if(EnergyUtil.isEmpty(stack)){
			return 0;
		}
		
		player.addPotionEffect(new PotionEffect(effect.getPotionID(), 1, effect.getDuration()));
		return energyCost;
    }
	
	@Override
	public IUpgrade[] getDependencies() {
		
        return new IUpgrade[]{Vanilla.basePotion};
    }

}
