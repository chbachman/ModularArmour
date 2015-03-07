package chbachman.armour.configurability;

import java.lang.reflect.Field;

import chbachman.api.IUpgrade;

public class ConfigurableField{
	public Field field;
	public int min;
	public int max;

	public void check(IUpgrade upgrade){

		try{
			internalCheck(upgrade, (Integer) field.get(upgrade));

		}catch (Exception e){
			e.printStackTrace();
		}

	}

	public ConfigurableField set(IUpgrade upgrade, int value){
		try{
			field.set(upgrade, value);
		}catch (Exception e){
			e.printStackTrace();
		}

		this.internalCheck(upgrade, value);

		return this;
	}

	private void internalCheck(IUpgrade upgrade, int value){

		int newValue = value;
		if (value > max){
			newValue = max;
		}

		if (value < min){
			newValue = min;
		}

		try{
			if (newValue != value){
				field.set(upgrade, newValue);
			}

		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
