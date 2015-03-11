package chbachman.armour.items.armour;

import chbachman.armour.items.holder.LPHolder;

public class LPModularArmour extends ItemModularArmour{

	public LPModularArmour(ArmorMaterial material, int type) {
		super(material, type);
		this.holder = new LPHolder(this);
	}
	
}
