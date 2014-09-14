package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import chbachman.api.IModularItem;
import chbachman.api.Upgrade;
import chbachman.armour.register.Vanilla;
import chbachman.armour.util.ArmourSlot;
import chbachman.armour.util.EnergyUtil;
import chbachman.armour.util.UpgradeUtil;

public class UpgradeHoverJetpack extends Upgrade {
    
    public UpgradeHoverJetpack() {
        super("Jetpack");
    }
    
    @Override
    public boolean isCompatible(IModularItem item, ItemStack stack, int armourType) {
        return armourType == ArmourSlot.CHESTPLATE.id;
    }
    
    @Override
    public int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        
    	if(EnergyUtil.getEnergyStored(stack) != 0){
    		setFlying(player, true);
    	}
    	
    	if(EnergyUtil.getEnergyStored(stack) == 0){
    		setFlying(player, false);
    	}
    	
        if (!UpgradeUtil.doesPlayerHaveUpgrade(player, Vanilla.calfShields) && player.capabilities.isFlying) {
            player.attackEntityFrom(DamageSource.onFire, 4F);
        }
        
        if (player.capabilities.isFlying) {
            return 500;
        } else {
            return 0;
        }
    }
    
    @Override
    public void onDequip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        setFlying(player, false);
    }
    
    private void setFlying(EntityPlayer player, boolean bool){
    	if(bool){
    		player.capabilities.allowFlying = true;
            player.sendPlayerAbilities();
    	}else{
    		player.capabilities.allowFlying = false;
            player.capabilities.isFlying = false;
            player.sendPlayerAbilities();
    	}
    }
    
}
