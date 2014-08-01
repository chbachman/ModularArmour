package chbachman.armour.proxy;

import net.minecraftforge.client.event.TextureStitchEvent;

public interface IProxy {
    
    public void registerKeyBinds();
    
    void registerIcons(TextureStitchEvent.Pre event);
    
}
