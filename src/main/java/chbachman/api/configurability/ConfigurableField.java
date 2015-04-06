package chbachman.api.configurability;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import chbachman.api.upgrade.IUpgrade;
import chbachman.api.util.VariableInt;

public class ConfigurableField extends VariableInt{

	public final String displayName;
	
	/**
	 * Constructed with the given upgrade-unique identifier and the default data of 0.
	 * @param u
	 * @param name
	 */
	public ConfigurableField(IUpgrade u, String name){
		this(u, name, 0);
	}
	
	/**
	 * Constructed with the given upgrade-unique identifier.
	 * @param u
	 * @param name
	 * @param defaultData
	 */
	public ConfigurableField(IUpgrade u, String name, int defaultData){
		this(u.getBaseName() + name, "upgrade.chbachman." + u.getBaseName() + "." + name, defaultData);
	}
	
	/**
	 * Constructed with the given key and displayName, with a default data of 0.
	 * @param key
	 * @param displayName
	 */
	public ConfigurableField(String key, String displayName){
		this(key, displayName, 0);
	}
	
	/**
	 * 
	 * @param key - For saving and loading from NBT data on the Armour Piece.
	 * @param displayName - For displaying to the user. Will be translated.
	 * @param defaultData - The data to default to if first created.
	 */
	public ConfigurableField(String key, String displayName, int defaultData) {
		super(key, defaultData);
		this.displayName = StatCollector.translateToLocal(displayName);
	}
	/**
	 * Get the user-defined percentage that the armour should work at.
	 * @param stack
	 * @return
	 */
	public float getPercentage(ItemStack stack){
		return  (float) super.get(stack) / 100F;
	}

}
