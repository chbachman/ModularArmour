package chbachman.armour.upgrade;

import net.minecraft.entity.player.EntityPlayer;
import cofh.CoFHCore;
import cofh.key.IKeyBinding;

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
        return "modularArmour." + this.getName();
    }
    
    @Override
    public boolean hasServerSide() {
        return true;
    }
    
    public abstract String getKeyName();
    
}
