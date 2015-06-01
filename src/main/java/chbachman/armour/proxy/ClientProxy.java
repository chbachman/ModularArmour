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
import cofh.core.CoFHProps;
import cofh.core.key.CoFHKeyHandler;
import cofh.core.render.IconRegistry;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {
    
    @Override
    public void registerKeyBinds() {
        super.registerKeyBinds();
        
        FMLCommonHandler.instance().bus().register(CoFHKeyHandler.instance);
        
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

			IconRegistry.addIcon("IconAccessFriends", CoFHProps.PATH_ICON + "Icon_Access_Friends", event.map);
			IconRegistry.addIcon("IconAccessGuild", CoFHProps.PATH_ICON + "Icon_Access_Guild", event.map);
			IconRegistry.addIcon("IconAccessPrivate", CoFHProps.PATH_ICON + "Icon_Access_Private", event.map);
			IconRegistry.addIcon("IconAccessPublic", CoFHProps.PATH_ICON + "Icon_Access_Public", event.map);
			IconRegistry.addIcon("IconAccept", CoFHProps.PATH_ICON + "Icon_Accept", event.map);
			IconRegistry.addIcon("IconAcceptInactive", CoFHProps.PATH_ICON + "Icon_Accept_Inactive", event.map);
			IconRegistry.addIcon("IconAugment", CoFHProps.PATH_ICON + "Icon_Augment", event.map);
			IconRegistry.addIcon("IconButton", CoFHProps.PATH_ICON + "Icon_Button", event.map);
			IconRegistry.addIcon("IconButtonHighlight", CoFHProps.PATH_ICON + "Icon_Button_Highlight", event.map);
			IconRegistry.addIcon("IconButtonInactive", CoFHProps.PATH_ICON + "Icon_Button_Inactive", event.map);
			IconRegistry.addIcon("IconCancel", CoFHProps.PATH_ICON + "Icon_Cancel", event.map);
			IconRegistry.addIcon("IconCancelInactive", CoFHProps.PATH_ICON + "Icon_Cancel_Inactive", event.map);
			IconRegistry.addIcon("IconConfig", CoFHProps.PATH_ICON + "Icon_Config", event.map);
			IconRegistry.addIcon("IconEnergy", CoFHProps.PATH_ICON + "Icon_Energy", event.map);
			IconRegistry.addIcon("IconNope", CoFHProps.PATH_ICON + "Icon_Nope", event.map);
			IconRegistry.addIcon("IconInformation", CoFHProps.PATH_ICON + "Icon_Information", event.map);
			IconRegistry.addIcon("IconTutorial", CoFHProps.PATH_ICON + "Icon_Tutorial", event.map);

			IconRegistry.addIcon("IconGunpowder", Items.gunpowder.getIconFromDamage(0));
			IconRegistry.addIcon("IconRedstone", Items.redstone.getIconFromDamage(0));
			IconRegistry.addIcon("IconRSTorchOff", CoFHProps.PATH_ICON + "Icon_RSTorchOff", event.map);
			IconRegistry.addIcon("IconRSTorchOn", CoFHProps.PATH_ICON + "Icon_RSTorchOn", event.map);

			IconRegistry.addIcon("IconArrowDown0", CoFHProps.PATH_ICON + "Icon_ArrowDown_Inactive", event.map);
			IconRegistry.addIcon("IconArrowDown1", CoFHProps.PATH_ICON + "Icon_ArrowDown", event.map);
			IconRegistry.addIcon("IconArrowUp0", CoFHProps.PATH_ICON + "Icon_ArrowUp_Inactive", event.map);
			IconRegistry.addIcon("IconArrowUp1", CoFHProps.PATH_ICON + "Icon_ArrowUp", event.map);
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
