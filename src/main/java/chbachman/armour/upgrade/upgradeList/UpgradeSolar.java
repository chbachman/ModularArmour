package chbachman.armour.upgrade.upgradeList;

import modulararmour.cofh.lib.util.helpers.EnergyHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import chbachman.api.item.IModularItem;
import chbachman.api.upgrade.Upgrade;
import chbachman.api.util.ArmourSlot;

public class UpgradeSolar extends Upgrade
{
    private int generation;

    public UpgradeSolar(String name, int generation)
    {
        super(name);
        this.generation = generation;
    }
    
    @Override
    public int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot)
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
