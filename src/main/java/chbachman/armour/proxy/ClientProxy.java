package chbachman.armour.proxy;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.IUpgrade;
import chbachman.armour.register.Vanilla;
import chbachman.armour.upgrade.KeybindUpgrade;
import cofh.core.key.CoFHKeyHandler;
import cofh.core.render.IconRegistry;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerKeyBinds() {
        super.registerKeyBinds();

        for (IUpgrade normalUpgrade : UpgradeRegistry.getUpgradeList()) {
            if (normalUpgrade instanceof KeybindUpgrade) {

                KeybindUpgrade upgrade = (KeybindUpgrade) normalUpgrade;

                CoFHKeyHandler.addKeyBind(upgrade);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerIcons(TextureStitchEvent.Pre event) {

        if (event.map.getTextureType() == 0) {

        } else if (event.map.getTextureType() == 1) {

            IconRegistry.addIcon("IconRecipe", Items.paper.getIconFromDamage(0));
            IconRegistry.addIcon("IconUpgrade", Vanilla.chestplateModular.getIconFromDamage(0));
        }
    }

    /* SERVER UTILS */
    @Override
    public boolean isOp(String playerName) {

        return true;
    }

    @Override
    public boolean isClient() {

        return true;
    }

    @Override
    public boolean isServer() {

        return false;
    }

    @Override
    public World getClientWorld() {

        return Minecraft.getMinecraft().theWorld;
    }

    /* PLAYER UTILS */
    @Override
    public EntityPlayer findPlayer(String playerName) {

        for (Object a : FMLClientHandler.instance().getClient().theWorld.playerEntities) {
            EntityPlayer player = (EntityPlayer) a;
            if (player.getCommandSenderName().toLowerCase().equals(playerName.toLowerCase())) {
                return player;
            }
        }
        return null;
    }

    @Override
    public EntityPlayer getClientPlayer() {

        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public List<EntityPlayer> getPlayerList() {

        return new LinkedList<EntityPlayer>();
    }

    /* SOUND UTILS */
    @Override
    public float getSoundVolume(int category) {

        if (category > SoundCategory.values().length) {
            return 0;
        }
        return FMLClientHandler.instance().getClient().gameSettings.getSoundLevel(SoundCategory.values()[category]);
    }

}
