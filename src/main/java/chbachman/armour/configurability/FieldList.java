package chbachman.armour.configurability;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chbachman.api.Configurable;
import chbachman.api.IUpgrade;

public class FieldList{

	public static Map<IUpgrade, ConfigurableField[]> fieldList = new HashMap<IUpgrade, ConfigurableField[]>();

	public static void register(IUpgrade upgrade){

		Class<?> upgradeClass = upgrade.getClass();

		List<ConfigurableField> storageList = new ArrayList<ConfigurableField>();

		for (Field field : upgradeClass.getFields()){

			if (!field.isAnnotationPresent(Configurable.class)){
				continue;
			}

			Configurable c = field.getAnnotation(Configurable.class);

			ConfigurableField s = new ConfigurableField();

			s.field = field;
			s.min = c.min();
			s.max = c.max();

			s.check(upgrade);

			storageList.add(s);
		}

		fieldList.put(upgrade, storageList.toArray(new ConfigurableField[0]));

	}
}
