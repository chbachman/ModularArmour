package chbachman.armour.proxy;

import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.IUpgrade;
import chbachman.armour.network.ArmourPacket;
import chbachman.armour.upgrade.KeybindUpgrade;
import cofh.core.key.CoFHKeyHandler;
import cofh.core.network.PacketHandler;

public abstract class CommonProxy implements IProxy {

    @Override
    public void registerPacketInformation() {
        PacketHandler.instance.registerPacket(ArmourPacket.class);
    }

    @Override
    public void registerKeyBinds() {

        for (IUpgrade upgrade : UpgradeRegistry.getUpgradeList()) {
            if (upgrade instanceof KeybindUpgrade) {
                CoFHKeyHandler.addServerKeyBind((KeybindUpgrade) upgrade);
            }
        }

    }

}
