package chbachman.armour.network;


public interface IInputHandler {
    
	/**
	 * Called when a button is clicked on the server side.
	 * @param packet
	 * @param name
	 */
    public void onButtonClick(ArmourPacket packet, String name);
    
    /**
     * Called when a key is typed on the server side;
     * @param packet
     * @param key
     * @param keyCode
     */
    public void onKeyTyped(ArmourPacket packet, char key, int keyCode);
    
}
