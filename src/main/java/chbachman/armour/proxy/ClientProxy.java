package chbachman.armour.proxy;

import net.minecraftforge.client.event.TextureStitchEvent;
import chbachman.armour.items.ItemRegister;
import chbachman.armour.upgrade.KeybindUpgrade;
import chbachman.armour.upgrade.Upgrade;
import chbachman.armour.upgrade.UpgradeList;
import cofh.key.CoFHKey;
import cofh.render.IconRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {
    
    @Override
    public void registerKeyBinds() {
        super.registerKeyBinds();
        for (Upgrade normalUpgrade : UpgradeList.list) {
            if (normalUpgrade instanceof KeybindUpgrade) {
                
                KeybindUpgrade upgrade = (KeybindUpgrade) normalUpgrade;
                
                CoFHKey.addKeyBind(upgrade);
                // ClientRegistry.registerKeyBinding(new
                // KeyBinding(upgrade.getKeyName(), upgrade.getKey(),
                // "Modular Armour"));
            }
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerIcons(TextureStitchEvent.Pre event) {
        
        if (event.map.getTextureType() == 0) {
            
        } else if (event.map.getTextureType() == 1) {
            
            IconRegistry.addIcon("IconPlus", "cofh:icons/Icon_Access_Friends", event.map);
            IconRegistry.addIcon("IconUpgrade", ItemRegister.chestplateModular.getIconFromDamage(0));
        }
    }
    
}
