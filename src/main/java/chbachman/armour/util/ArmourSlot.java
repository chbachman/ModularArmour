package chbachman.armour.util;


public enum ArmourSlot {
    
    HELMET(0), CHESTPLATE(1), LEGS(2), BOOTS(3), BELT(4), RING(5), PENDANT(6), UNKNOWN(7);
    
    public int id;
    
    private ArmourSlot(int id) {
        this.id = id;
    }
    
    
    /**
     * Get the ArmourSlot for the given id;
     * @param id
     * @return
     */
    public static ArmourSlot getArmourSlot(int id) {
        for(ArmourSlot slot : ArmourSlot.values()){
        	if(slot.id == id){
        		return slot;
        	}
        }
        
        return ArmourSlot.UNKNOWN;
    }
    
}
