package chbachman.armour.upgrade;

import chbachman.api.Upgrade;
import net.minecraft.entity.player.EntityPlayer;
import cofh.CoFHCore;
import cofh.core.key.IKeyBinding;
import cofh.lib.util.helpers.StringHelper;

public abstract class KeybindUpgrade extends Upgrade implements IKeyBinding {
    
    public KeybindUpgrade(String name) {
        super(name);
    }
    
    @Override
    public boolean keyPress() {
        return this.keyPress(CoFHCore.proxy.getClientPlayer());
    }
    
    public abstract boolean keyPress(EntityPlayer player);
    
    @Override
    public String getUUID() {
        return this.getLocalizationString();
    }
    
    @Override
    public boolean hasServerSide() {
        return true;
    }
    
    public String getKeyName(){
        return StringHelper.localize(this.getLocalizationString() + ".key");
    }
    
}
