package chbachman.armour.upgrade;

import net.minecraft.entity.player.EntityPlayer;
import chbachman.api.upgrade.Upgrade;
import chbachman.armour.ModularArmour;
import cofh.core.key.IKeyBinding;
import cofh.lib.util.helpers.StringHelper;

public abstract class KeybindUpgrade extends Upgrade implements IKeyBinding {
    
    public KeybindUpgrade(String name) {
        super(name);
    }
    
    @Override
    public boolean keyPress() {
        return this.keyPress(ModularArmour.proxy.getClientPlayer());
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
