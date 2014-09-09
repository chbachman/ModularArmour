package chbachman.armour.proxy;

import net.minecraft.init.Items;
import net.minecraftforge.client.event.TextureStitchEvent;
import chbachman.api.IUpgrade;
import chbachman.armour.register.Vanilla;
import chbachman.armour.upgrade.KeybindUpgrade;
import chbachman.armour.upgrade.UpgradeList;
import cofh.core.key.CoFHKey;
import cofh.core.render.IconRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {
    
    @Override
    public void registerKeyBinds() {
        super.registerKeyBinds();
        for (IUpgrade normalUpgrade : UpgradeList.list) {
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
            
            IconRegistry.addIcon("IconRecipe", Items.paper.getIconFromDamage(0));
            IconRegistry.addIcon("IconUpgrade", Vanilla.chestplateModular.getIconFromDamage(0));
        }
    }
    
}
