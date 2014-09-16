package chbachman.api;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import chbachman.armour.upgrade.UpgradeList;
import chbachman.armour.util.ArmourSlot;
import cofh.lib.util.helpers.StringHelper;
/**
 * Default implementation of IUpgrade. Use this or make your own. 
 * @author chbachman
 *
 */
public abstract class Upgrade implements IArmourUpgrade{
    
    protected final int id;
    protected String name;
    protected boolean isDisabled;
    
    public Upgrade(String name) {
        this.id = this.getNextAvailableId();
        
        this.name = name;
        
        UpgradeList.list.add(this);
    }
    
    private int getNextAvailableId() {
        return UpgradeList.list.size();
    }
    
    public String getInformation(){
    	return StringHelper.localize(this.getLocalizationString() + ".information");
    }
    
    public String getName() {
    	return StringHelper.localize(this.getLocalizationString() + ".name");
    }
    
    protected String getLocalizationString() {
        return "upgrade.chbachman." + StringHelper.camelCase(name).replace(" ", "");
    }
    
    public int getId() {
        return this.id;
    }
    
    public boolean isDisabled(){
    	return isDisabled;
    }
    
    public IUpgrade setDisabled(boolean value){
    	this.isDisabled = value;
    	return this;
    }
    
    @Override
    public int compareTo(IUpgrade upgrade) {
        return this.getName().compareTo(upgrade.getName());
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Upgrade))
			return false;
		Upgrade other = (Upgrade) obj;
		if (id != other.id)
			return false;
		return true;
	}

	// Api for Upgrades here
    public boolean isCompatible(IModularItem item, ItemStack stack, int armorType) {
        return true;
    }
    
    public int getArmourDisplay() {
        return 0;
    }
    
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot) {
        return null;
    }
    
    public IUpgrade[] getDependencies() {
        return null;
    }
    
    public void onUpgradeAddition(IModularItem item, ItemStack stack) {
        
    }
    
    public void onEquip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        
    }
    
    public int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        return 0;
    }
    
    public void onDequip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        
    }
    
}
