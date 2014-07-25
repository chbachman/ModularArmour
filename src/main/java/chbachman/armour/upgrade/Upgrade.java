package chbachman.armour.upgrade;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.items.ItemModularArmour;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.upgrade.upgradeList.UpgradeCalfShields;
import chbachman.armour.upgrade.upgradeList.UpgradeHoverJetpack;
import chbachman.armour.upgrade.upgradeList.UpgradePotion;
import chbachman.armour.upgrade.upgradeList.potionEffect.PotionUpgrades;

public abstract class Upgrade {

	public static List<Upgrade> upgradeList = new ArrayList<Upgrade>();
	
	public static void preInit(){
		upgradeList.add(new UpgradeHoverJetpack());
		upgradeList.add(new UpgradeCalfShields());
		upgradeList.add(new UpgradePotion());
		
		PotionUpgrades.init();
	}

	public static void upgradeInit(){
		for(Upgrade upgrade: upgradeList){
			upgrade.init();
		}
	}
	
	protected final int id;
	protected String name;

	protected Recipe recipe;
	
	public Upgrade(String name) {
		this.id = getNextAvailableId();
		this.name = name;
		this.recipe = this.getRecipe();
		Recipe.addToList(recipe);
	}

	protected void init(){
		
	}
	
	private int getNextAvailableId() {
		return upgradeList.size();
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
			return upgradeList.get(nbt.getInteger("ID"));
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getId(){
		return this.id;
	}
	
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

	public void onArmourDequip(World world, EntityPlayer player, ItemStack stack, ArmourSlot armourSlot){

	}

}
