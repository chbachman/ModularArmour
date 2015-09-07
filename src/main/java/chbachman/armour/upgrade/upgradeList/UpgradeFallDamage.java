package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.api.configurability.Configurable;
import chbachman.api.configurability.ConfigurableField;
import chbachman.api.item.IModularItem;
import chbachman.api.upgrade.Upgrade;
import chbachman.api.util.ArmourSlot;
import chbachman.armour.util.ConfigHelper;
import chbachman.armour.util.EnergyUtil;

public class UpgradeFallDamage extends Upgrade {

    public UpgradeFallDamage() {
        super("fallDamage");
    }

    @Configurable
    public ConfigurableField f = new ConfigurableField(this, "fallDamage");

    private int cost;

    @Override
    public void registerConfigOptions() {
        cost = ConfigHelper.get(ConfigHelper.SPEED, this, "cost to fall for every 2 blocks", 100);
    }

    @Override
    public int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        if (player.fallDistance > (30 - 28 * f.get(stack).getPercentage()) && EnergyUtil.getEnergyStored(stack) > 100) {
            player.fallDistance = 0;
            return cost;
        }

        return 0;
    }

    @Override
    public boolean isCompatible(IModularItem item, ItemStack stack, int armorType) {
        return armorType == ArmourSlot.BOOTS.id;
    }

}
