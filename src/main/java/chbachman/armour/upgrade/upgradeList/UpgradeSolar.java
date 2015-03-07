package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import chbachman.api.item.IModularItem;
import chbachman.api.upgrade.Upgrade;
import chbachman.api.util.ArmourSlot;
import cofh.lib.util.helpers.EnergyHelper;

public class UpgradeSolar extends Upgrade
{
    private int generation;

    public UpgradeSolar(String name, int generation)
    {
        super(name);
        this.generation = generation;
    }
    
    @Override
    public int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot, int level)
    {
        if (15 == world.getBlockLightValue(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ)))
        {
            return -generation;
        }
        
        return 0;
    }
    
    @Override
    public boolean isCompatible(IModularItem item, ItemStack stack, int armourType)
    {
    	return EnergyHelper.isEnergyContainerItem(stack) && armourType == ArmourSlot.HELMET.id;
    }
    
}
