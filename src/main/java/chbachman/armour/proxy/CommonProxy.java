package chbachman.armour.proxy;

import repack.cofh.core.key.CoFHKeyHandler;
import repack.cofh.core.key.KeyPacket;
import repack.cofh.core.network.PacketHandler;
import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.IUpgrade;
import chbachman.armour.network.ArmourPacket;
import chbachman.armour.upgrade.KeybindUpgrade;

public abstract class CommonProxy implements IProxy {

	@Override
	public void registerPacketInformation() {
		PacketHandler.instance.registerPacket(ArmourPacket.class);
        PacketHandler.instance.registerPacket(KeyPacket.class);
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
