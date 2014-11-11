package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.api.IModularItem;
import chbachman.api.Upgrade;
import chbachman.armour.util.ArmourSlot;

public class UpgradeSpeed extends Upgrade{

    public UpgradeSpeed() {
        super("speed");
    }

    @Override
    public boolean isCompatible(IModularItem item, ItemStack stack, int armourType) {
        return armourType == ArmourSlot.LEGS.id;
    }
    
    @Override
    public void onEquip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot, int level) {
        player.capabilities.setPlayerWalkSpeed(0.2F);
        player.sendPlayerAbilities();
    }
    
    @Override
    public void onDequip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot, int level) {
        player.capabilities.setPlayerWalkSpeed(0.1F);
        player.sendPlayerAbilities();
    }
    
    
    
}
