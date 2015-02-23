package chbachman.armour.configurability;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chbachman.api.Configurable;
import chbachman.api.IUpgrade;

public class FieldList{
	
	public static Map<IUpgrade, Storage[]> fieldList = new HashMap<IUpgrade, Storage[]>();
	
	public static void register(IUpgrade upgrade){
		
		Class<?> upgradeClass = upgrade.getClass();
		
		List<Storage> storageList = new ArrayList<Storage>();
		
		for(Field field : upgradeClass.getFields()){
			
			if(!field.isAnnotationPresent(Configurable.class)){
				continue;
			}
			
			Configurable c = field.getAnnotation(Configurable.class);
			
			Storage s = new Storage();
			
			s.field = field;
			s.min = c.min();
			s.max = c.max();
			
			s.check(upgrade);
			
			storageList.add(s);	
		}
		
		fieldList.put(upgrade, storageList.toArray(new Storage[0]));
	}
	
	public static class Storage{
		
		Field field;
		int min;
		int max;
		
		public void check(IUpgrade upgrade){
			
			try{
				int f = (Integer) field.get(upgrade);
				
				if(f > max){
					f = max;
				}
				
				if(f < min){
					f = min;
				}
				
				field.set(upgrade, f);
				
			}catch (Exception e){
				
			}
				
			
		}
	}

}
