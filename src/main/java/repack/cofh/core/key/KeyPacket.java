package repack.cofh.core.key;

import org.apache.logging.log4j.Level;

import repack.cofh.core.network.PacketCoFHBase;
import repack.cofh.core.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.FMLLog;

public class KeyPacket extends PacketCoFHBase {

	public static void initialize() {

		PacketHandler.instance.registerPacket(KeyPacket.class);
	}

	@Override
	public void handlePacket(EntityPlayer player, boolean isServer) {

		String bindUUID = getString();
		if (CoFHKeyHandler.serverBinds.containsKey(bindUUID)) {
			CoFHKeyHandler.serverBinds.get(bindUUID).keyPressServer(player);
		} else {
			FMLLog.log(Level.ERROR, "Invalid Key Packet! Unregistered Server Key! UUID: " + bindUUID);
		}
	}

	public void sendKeyPacket(String uuid) {

		addString(uuid);
		PacketHandler.sendToServer(this);
	}
}
