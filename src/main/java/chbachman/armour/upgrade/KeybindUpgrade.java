package chbachman.armour.upgrade;

import repack.cofh.core.key.IKeyBinding;
import repack.cofh.lib.util.helpers.StringHelper;
import net.minecraft.entity.player.EntityPlayer;
import chbachman.api.upgrade.Upgrade;
import chbachman.armour.ModularArmour;

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
