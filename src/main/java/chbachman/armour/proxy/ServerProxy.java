package chbachman.armour.proxy;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import cpw.mods.fml.common.FMLCommonHandler;

public class ServerProxy extends CommonProxy {

    @Override
    public void registerIcons(TextureStitchEvent.Pre event) {

    }

    /* SERVER UTILS */
    @Override
    public boolean isOp(String playerName) {

        MinecraftServer theServer = FMLCommonHandler.instance().getMinecraftServerInstance();
        playerName = playerName.trim();
        for (String a : theServer.getConfigurationManager().func_152606_n()) {
            if (playerName.equalsIgnoreCase(a)) {
                return true; // TODO: this is completely horrible. needs
                             // improvement. will probably still be horrible.
            }
        }
        return false;
    }

    @Override
    public boolean isClient() {

        return false;
    }

    @Override
    public boolean isServer() {

        return true;
    }

    @Override
    public World getClientWorld() {

        return null;
    }

    /* PLAYER UTILS */
    @Override
    public EntityPlayer findPlayer(String player) {

        return null;
    }

    @Override
    public EntityPlayer getClientPlayer() {

        return null;
    }

    @Override
    public List<EntityPlayer> getPlayerList() {

        List<EntityPlayer> result = new LinkedList<EntityPlayer>();
        for (int i = 0; i < FMLCommonHandler.instance().getMinecraftServerInstance().worldServers.length; i++) {
            if (FMLCommonHandler.instance().getMinecraftServerInstance().worldServers[i] != null) {
                result.addAll(FMLCommonHandler.instance().getMinecraftServerInstance().worldServers[i].playerEntities);
            }
        }
        return result;
    }

    @Override
    public float getSoundVolume(int category) {

        return 0;
    }

}
