package chbachman.armour.gui;

import chbachman.armour.network.ArmourPacket;

public interface IInputHandler {
    
    public void onButtonClick(ArmourPacket packet, String name);
    
    public void onKeyTyped(ArmourPacket packet, char key, int keyCode);
    
}
