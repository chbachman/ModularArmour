package chbachman.armour.util;

import baubles.api.BaubleType;

public enum ArmourSlot {
    
    HELMET(0), CHESTPLATE(1), LEGS(2), BOOTS(3), BELT(4), RING(5), PENDANT(6), UNKNOWN(7);
    
    public int id;
    
    private ArmourSlot(int id) {
        this.id = id;
    }
    
    public static ArmourSlot getArmourSlot(int id) {
        for(ArmourSlot slot : ArmourSlot.values()){
        	if(slot.id == id){
        		return slot;
        	}
        }
        
        return ArmourSlot.UNKNOWN;
    }
    
    public static ArmourSlot getArmourSlot(BaubleType type){
    	switch(type){
    	
    	case BELT: return BELT;
    	case RING: return RING;
    	case AMULET: return PENDANT;
    	default: return UNKNOWN;
    	
    	}
    }
    
}
