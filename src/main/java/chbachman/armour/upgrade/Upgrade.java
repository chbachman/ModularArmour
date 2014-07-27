package chbachman.armour.upgrade;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.items.ItemModularArmour;
import chbachman.armour.reference.ArmourSlot;
import cofh.util.StringHelper;

public abstract class Upgrade implements Comparable<Upgrade>{
	
	protected final int id;
	protected String name;

	protected Recipe recipe;
	
	public Upgrade(String name) {
		this.id = getNextAvailableId();
		
		this.name = name;
		
		if(this.shouldRegisterRecipes()){
			this.recipe = this.getRecipe();
			Recipe.addToList(recipe);
		}
	}

	protected void init(){
		
	}
	
	private int getNextAvailableId() {
		return UpgradeList.list.size();
	}
	
	public NBTTagCompound getNBT(){
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("ID", this.id);
		return nbt;
	}

	public static Upgrade getUpgradeFromNBT(NBTTagCompound nbt){

		if(nbt == null){
			return null;
		}
		
		try {
			return UpgradeList.list.get(nbt.getInteger("ID"));
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public String getName(){
		return StringHelper.localize(this.getUnlocalizedName());
	}
	
	public String getUnlocalizedName(){
	    return this.getLocalizationString(name);
	}
	
	protected String getLocalizationString(String name){
        return "upgrade.chbachman." + StringHelper.camelCase(name).replace(" ", "") +  ".name";
    }
	
	public int getId(){
		return this.id;
	}
	
	public boolean shouldRegisterRecipes(){
		return true;
	}
	
	public int compareTo(Upgrade upgrade){
	    return this.getName().compareTo(upgrade.getName());
	}
	
	//Api for Upgrades here
	public boolean isCompatible(int slot){
		return isCompatible(ArmourSlot.getArmourSlot(slot));
	}
	
	public abstract boolean isCompatible(ArmourSlot slot);
	
	public abstract Recipe getRecipe();
	
	public int getArmourDisplay(){
		return 0;
	}
	
	public double getDefenceRatio(int slot){
		return 0;
	}
	
	public List<String> getDependencies(){
		return null;
	}
	
	public void onUpgradeAddition(ItemModularArmour armour, ItemStack stack){
		
	}
	
	public void onArmourEquip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot){

	}

	public void onArmourTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot){

	}

	public void onArmourDequip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot){

	}
	
	public int getEnergyTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot){
		return 0;
	}
	
	public void onNoEnergy(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot){
		
	}

}
