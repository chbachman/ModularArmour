package chbachman.armour.upgrade.upgradeList;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.api.item.IModularItem;
import chbachman.api.upgrade.Upgrade;
import chbachman.api.util.ArmourSlot;
import chbachman.armour.util.ConfigHelper;
import chbachman.armour.util.EnergyUtil;

public class UpgradeSpeed extends Upgrade{

    public UpgradeSpeed() {
        super("speed");
    }

    private int cost;
    
    //private float speed;
    
    @Override
    public void registerConfigOptions(){
    	cost = ConfigHelper.get(ConfigHelper.ENERGY, this, "cost to walk faster, per tick", 100);
    }
    
    @Override
    public boolean isCompatible(IModularItem item, ItemStack stack, int armourType) {
        return armourType == ArmourSlot.LEGS.id;
    }
    
    @Override
    public int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot, int level){
    	
    	if(!EnergyUtil.isEmpty(stack) && (player.onGround || player.capabilities.isFlying) && player.moveForward > 0F && !player.isInsideOfMaterial(Material.water)){
			player.moveFlying(0F, 1F, player.capabilities.isFlying ? .15F : .15F * 2);
			return cost;
		}
    	
    	return 0;
    }
    
    
}
