package chbachman.armour.configurability;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chbachman.api.Configurable;
import chbachman.api.IUpgrade;
import chbachman.armour.util.VariableInt;

public class FieldList{

	public static Map<IUpgrade, VariableInt[]> fieldList = new HashMap<IUpgrade, VariableInt[]>();

	public static void register(IUpgrade upgrade){

		Class<?> upgradeClass = upgrade.getClass();

		List<VariableInt> storageList = new ArrayList<VariableInt>();

		for (Field field : upgradeClass.getFields()){

			if (!field.isAnnotationPresent(Configurable.class)){
				continue;
			}
			
			if(field.getType() != VariableInt.class){
				continue;
			}

			try{
				storageList.add((VariableInt) field.get(upgrade));
			}catch (Exception e){
				e.printStackTrace();
				continue;
			}
		}

		fieldList.put(upgrade, storageList.toArray(new VariableInt[0]));

	}
}
