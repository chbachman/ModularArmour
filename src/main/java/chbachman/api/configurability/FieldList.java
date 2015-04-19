package chbachman.api.configurability;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import chbachman.api.registry.IUpgradeListener;
import chbachman.api.upgrade.IUpgrade;

public class FieldList implements IUpgradeListener{

	Field[] fields;
	
	public void refreshFields(IUpgrade upgrade, ConfigurableField[] f){
		for (int i = 0; i < fields.length; i++){
			try{
				fields[i].set(upgrade, f.length);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public ConfigurableField[] getFieldList(IUpgrade upgrade){
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

	@Override
	public IUpgradeListener onUpgradeAdded(IUpgrade upgrade){
		
		FieldList list = new FieldList();
		
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

		list.fields = storageList.toArray(new Field[0]);
		
		return list;

	}
}
