package chbachman.api.configurability;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import chbachman.api.nbt.NBTStorage;
import chbachman.api.upgrade.IUpgrade;

public class ConfigurableField extends NBTStorage<Percentage>{

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
		this(u.getBaseName() + name, "upgrade.chbachman." + u.getBaseName() + "." + name, new Percentage(defaultData));
	}
	
	/**
	 * Constructed with the given key and displayName, with a default data of 0.
	 * @param key
	 * @param displayName
	 */
	public ConfigurableField(String key, String displayName){
		this(key, displayName, null);
	}
	
	/**
	 * 
	 * @param key - For saving and loading from NBT data on the Armour Piece.
	 * @param displayName - For displaying to the user. Will be translated.
	 * @param defaultData - The data to default to if first created.
	 */
	public ConfigurableField(String key, String displayName, Percentage defaultData) {
		super(key, defaultData);
		this.displayName = StatCollector.translateToLocal(displayName);
	}
	
	public ConfigurableField(String key, String displayName, int defaultData) {
		this(key, displayName, new Percentage(defaultData));
	}

	public void set(ItemStack stack, int amount){
		this.get(stack).setPercentage(amount);
	}

}
