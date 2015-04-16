package chbachman.api.registry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chbachman.api.configurability.Configurable;
import chbachman.api.configurability.ConfigurableField;
import chbachman.api.upgrade.IUpgrade;

public class FieldList{

	private static Map<IUpgrade, Field[]> fieldList = new HashMap<IUpgrade, Field[]>();

	public static void refreshFields(IUpgrade upgrade, ConfigurableField[] f){
		Field[] fields = fieldList.get(upgrade);

		for (int i = 0; i < fields.length; i++){
			try{
				fields[i].set(upgrade, f.length);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public static ConfigurableField[] getFieldList(IUpgrade upgrade){

		Field[] fields = fieldList.get(upgrade);
		ConfigurableField[] config = new ConfigurableField[fields.length];
		
		for (int i = 0; i < fields.length; i++){
			try{
				config[i] = (ConfigurableField) fields[i].get(upgrade);
			}catch (Exception e){
				e.printStackTrace();
			}

		}

		return config;
	}

	public static void register(IUpgrade upgrade){

		Class<?> upgradeClass = upgrade.getClass();

		List<Field> storageList = new ArrayList<Field>();

		for (Field field : upgradeClass.getFields()){

			if (!field.isAnnotationPresent(Configurable.class)){
				continue;
			}

			if (field.getType() != ConfigurableField.class){
				continue;
			}

			storageList.add(field);
		}

		fieldList.put(upgrade, storageList.toArray(new Field[0]));

	}
}
