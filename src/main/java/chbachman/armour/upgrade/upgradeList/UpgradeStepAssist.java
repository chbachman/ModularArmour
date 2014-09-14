package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.api.IModularItem;
import chbachman.api.Upgrade;
import chbachman.armour.util.ArmourSlot;

public class UpgradeStepAssist extends Upgrade{

    public UpgradeStepAssist() {
        super("stepAssist");
    }

    @Override
    public boolean isCompatible(IModularItem item, ItemStack stack, int armourType) {
        return armourType == ArmourSlot.LEGS.id;
    }
    
    public void onArmourEquip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        player.stepHeight = 1;
    }
    
    public void onArmourDequip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        player.stepHeight = .5F;
    }
    
    
    
}
