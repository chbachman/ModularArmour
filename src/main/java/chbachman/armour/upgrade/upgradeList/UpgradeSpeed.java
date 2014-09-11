package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.api.IModularItem;
import chbachman.api.Upgrade;
import chbachman.armour.reference.ArmourSlot;

public class UpgradeSpeed extends Upgrade{

    public UpgradeSpeed() {
        super("speed");
    }

    @Override
    public boolean isCompatible(IModularItem item, ItemStack stack, int armourType) {
        return armourType == ArmourSlot.LEGS.id;
    }
    
    public void onArmourEquip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        player.capabilities.setPlayerWalkSpeed(0.2F);
        player.sendPlayerAbilities();
    }
    
    public void onArmourDequip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        player.capabilities.setPlayerWalkSpeed(0.1F);
        player.sendPlayerAbilities();
    }
    
    
    
}
