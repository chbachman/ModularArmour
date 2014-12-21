package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import chbachman.api.IModularItem;
import chbachman.api.Upgrade;
import chbachman.armour.util.ArmourSlot;
import cofh.lib.util.helpers.EnergyHelper;

public class UpgradeSolar extends Upgrade
{
    private int generationA;

    public UpgradeSolar(String name, int generationB)
    {
        super(name);
        generationA = generationB;
    }
    
    @Override
    public int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot, int level)
    {
        if (15 == world.getBlockLightValue(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ)))
        {
            return -generationA;
        }
        
        return 0;
    }
    
    @Override
    public boolean isCompatible(IModularItem item, ItemStack stack, int armourType)
    {
    	return EnergyHelper.isEnergyContainerItem(stack) && armourType == ArmourSlot.HELMET.id;
    }
    
}
