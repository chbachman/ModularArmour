package chbachman.armour.proxy;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;

public interface IProxy {
    
	void registerKeyBinds();
    
    void registerIcons(TextureStitchEvent.Pre event);

    EntityPlayer getClientPlayer();

	boolean isOp(String commandSenderName);

	boolean isClient();

	boolean isServer();

	float getSoundVolume(int category);
	
	List<EntityPlayer> getPlayerList();
	
	World getClientWorld();
	
	EntityPlayer findPlayer(String player);
	
	void registerPacketInformation();
    
}
