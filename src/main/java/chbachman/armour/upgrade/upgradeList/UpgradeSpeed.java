package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.api.IModularItem;
import chbachman.api.Upgrade;
import chbachman.armour.util.ArmourSlot;
import chbachman.armour.util.ConfigHelper;

public class UpgradeSpeed extends Upgrade{

    public UpgradeSpeed() {
        super("speed");
    }

    private int cost;
    
    @Override
    public void registerConfigOptions(){
    	cost = ConfigHelper.getEnergyCost(this, "cost to walk faster, per tick", 100);
    }
    
    @Override
    public boolean isCompatible(IModularItem item, ItemStack stack, int armourType) {
        return armourType == ArmourSlot.LEGS.id;
    }
    
    @Override
    public int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot, int level){
    	if(player.capabilities.getWalkSpeed() == .3F){
    		return cost;
    	}
    	
    	return 0;
    }
    
    @Override
    public void onEquip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot, int level) {
        player.capabilities.setPlayerWalkSpeed(0.3F);
        player.capabilities.setFlySpeed(0.15F);
        player.sendPlayerAbilities();
    }
    
    @Override
    public void onDequip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot, int level) {
        player.capabilities.setPlayerWalkSpeed(0.1F);
        player.capabilities.setFlySpeed(0.1F);
        player.sendPlayerAbilities();
    }
    
    
    
}
