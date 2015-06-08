package chbachman.api.nbt;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import com.google.gson.reflect.TypeToken;

public class TypeMap<E> extends HashMap<TypeToken<?>, E>{

	private static final long serialVersionUID = 8354438593891928627L;

	@Override
	public E get(Object key){
		TypeToken<?> type = (TypeToken<?>) key;

		Class<?> clazz = type.getRawType();

		if (super.containsKey(TypeToken.get(clazz))){
			return super.get(TypeToken.get(clazz));
		}

		Set<Class<?>> list = getInheritance(clazz);

		for (Class<?> sup : list){
			if (super.containsKey(TypeToken.get(sup))){
				return super.get(TypeToken.get(sup));
			}
		}

		return null;
	}

	@Override
	public boolean containsKey(Object key){
		TypeToken<?> type = (TypeToken<?>) key;

		Class<?> clazz = type.getRawType();

		if (super.containsKey(TypeToken.get(clazz))){
			return true;
		}

		Set<Class<?>> list = getInheritance(clazz);

		for (Class<?> sup : list){
			if (super.containsKey(TypeToken.get(sup))){
				return true;
			}
		}

		return false;

	}

	public static Set<Class<?>> getInheritance(Class<?> in){
		LinkedHashSet<Class<?>> result = new LinkedHashSet<Class<?>>();

		result.add(in);
		getInheritance(in, result);

		return result;
	}

	/**
	 * Get inheritance of type.
	 * 
	 * @param in
	 * @param result
	 */
	private static void getInheritance(Class<?> in, Set<Class<?>> result){
		Class<?> superclass = getSuperclass(in);

		if (superclass != null){
			result.add(superclass);
			getInheritance(superclass, result);
		}

		getInterfaceInheritance(in, result);
	}

	/**
	 * Get interfaces that the type inherits from.
	 * 
	 * @param in
	 * @param result
	 */
	private static void getInterfaceInheritance(Class<?> in, Set<Class<?>> result){
		for (Class<?> c : in.getInterfaces()){
			result.add(c);

			getInterfaceInheritance(c, result);
		}
	}

	/**
	 * Get superclass of class.
	 * 
	 * @param in
	 * @return
	 */
	private static Class<?> getSuperclass(Class<?> in){
		if (in == null){
			return null;
		}

		if (in.isArray() && in != Object[].class){
			Class<?> type = in.getComponentType();

			while (type.isArray()){
				type = type.getComponentType();
			}

			return type;
		}

		return in.getSuperclass();
	}

}
