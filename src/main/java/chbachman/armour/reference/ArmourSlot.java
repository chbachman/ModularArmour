package chbachman.armour.reference;

public enum ArmourSlot {
	
	HELMET(0),
	CHESTPLATE(1),
	LEGS(2),
	BOOTS(3),
	UNKNOWN(4);
	
	public int id;
	
	private ArmourSlot(int id){
		this.id = id;
	}
	
	public static ArmourSlot getArmourSlot(int id){
		switch(id){
		
		case 0: return HELMET;
		case 1: return CHESTPLATE;
		case 2: return LEGS;
		case 3: return BOOTS;
		default: return UNKNOWN;
		
		}
	}

}
