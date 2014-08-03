package chbachman.armour.upgrade;

import net.minecraft.entity.player.EntityPlayer;
import cofh.CoFHCore;
import cofh.key.IKeyBinding;
import cofh.util.StringHelper;

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
        return "modularArmour." + this.getUnlocalizedName();
    }
    
    @Override
    public boolean hasServerSide() {
        return true;
    }
    
    public String getKeyName(){
        return StringHelper.localize(this.getUnlocalizedName() + ".key");
    }
    
}
