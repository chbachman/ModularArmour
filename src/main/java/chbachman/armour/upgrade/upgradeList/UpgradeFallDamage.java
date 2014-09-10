package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.api.Upgrade;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.util.EnergyUtil;

public class UpgradeFallDamage extends Upgrade {
    
    public UpgradeFallDamage() {
        super("fallDamage");
    }
    
    @Override
    public int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        if (player.fallDistance > 2 && EnergyUtil.getEnergyStored(stack) > 100) {
            player.fallDistance = 0;
            return 100;
        }
        
        return 0;
    }
    
}
