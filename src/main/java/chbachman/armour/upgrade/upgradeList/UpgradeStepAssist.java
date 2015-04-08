package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.api.item.IModularItem;
import chbachman.api.upgrade.Upgrade;
import chbachman.api.util.ArmourSlot;

public class UpgradeStepAssist extends Upgrade{

    public UpgradeStepAssist() {
        super("stepAssist");
    }

    @Override
    public boolean isCompatible(IModularItem item, ItemStack stack, int armourType) {
        return armourType == ArmourSlot.LEGS.id;
    }
    
    @Override
    public void onEquip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        player.stepHeight = 1;
    }
    
    @Override
    public void onDequip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        player.stepHeight = .5F;
    }
    
    
    
}
