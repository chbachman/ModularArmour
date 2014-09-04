package chbachman.api;

import chbachman.armour.objects.VariableInt;

public interface IModularItem{
	
	public VariableInt getInt(String name);
	
	public int getSlot();

}
