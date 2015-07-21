package chbachman.api.upgrade;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import chbachman.api.item.IModularItem;
import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.util.ArmourSlot;

/**
 * Default implementation of IUpgrade. Use this or make your own.
 * 
 * @author chbachman
 *
 */
public abstract class Upgrade implements IArmourUpgrade{

	/**
	 * The unlocalized, base name of the upgrade.
	 */
	protected final String name;

	/**
	 * Passes in the base name of the upgrade. The base name must be unique.
	 * This also registers the upgrade in the UpgradeList.
	 * 
	 * @param name
	 */
	public Upgrade(String name) {
		this.name = name;

		UpgradeRegistry.registerUpgrade(this);
	}

	@Override
	public String getInformation(){
		return this.getLocalizationString() + ".information";
	}

	@Override
	public String getName(){
		return this.getLocalizationString() + ".name";
	}

	@Override
	public String getBaseName(){
		return this.name;
	}

	/**
	 * Get the localization String for the upgrade. Append the ending to get the
	 * correct localization.
	 * 
	 * @return
	 */
	protected String getLocalizationString(){
		return "upgrade.chbachman." + this.name;
	}

	// Default Implementations
	@Override
	public boolean isCompatible(IModularItem item, ItemStack stack, int armorType){
		return true;
	}

	@Override
	public int getArmourDisplay(EntityPlayer player, ItemStack stack, ArmourSlot slot){
		return 0;
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase attacker, ItemStack armor, DamageSource source, double damage, ArmourSlot slot){
		return null;
	}

	@Override
	public IUpgrade[] getDependencies(){
		return null;
	}

	@Override
	public void onUpgradeAddition(IModularItem item, ItemStack stack){}

	@Override
	public void onEquip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot){}

	@Override
	public int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot){
		return 0;
	}

	@Override
	public void onDequip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot){}

	@Override
	public String getArmourTexture(ItemStack stack, ArmourSlot slot){
		return null;
	}

	@Override
	public void registerConfigOptions(){}

	@Override
	public String getArmourColor(ItemStack stack, ArmourSlot slot){
		return null;
	}
	
	@Override
	public ModelBiped getArmourModel(EntityLivingBase entityLiving, ItemStack itemStack, int armourSlot){
	    return null;
	}

}
