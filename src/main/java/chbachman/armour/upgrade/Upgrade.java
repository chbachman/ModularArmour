package chbachman.armour.upgrade;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.upgrade.upgradeList.UpgradeIron;

public abstract class Upgrade {

	public static List<Upgrade> upgradeList = new ArrayList<Upgrade>();
	
	public static void init(){
		upgradeList.add(new UpgradeIron());
	}

	private int id;
	private String name;

	protected Recipe recipe;
	
	public Upgrade(String name) {
		this.id = getNextAvailableId();
		upgradeList.add(this);
		this.name = name;
	}

	private int getNextAvailableId() {
		return upgradeList.size();
	}
	
	
	
	public NBTTagCompound getNBT(){
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("ID", this.id);
		return nbt;
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt){
		NBTTagList nbtList = nbt.getTagList("UpgradeList", Constants.NBT.TAG_COMPOUND);

		if(nbtList == null){
			nbtList = new NBTTagList();
		}
		
		nbtList.appendTag(this.getNBT());
		
		nbt.setTag("UpgradeList", nbtList);
		
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
	
	public Color getColor(){
		return Color.BLACK;
	}
	
	public int getArmourDisplay(){
		return 0;
	}
	
	public void onArmourTick(World world, EntityPlayer player, ItemStack stack){
		
	}
	
	public void onArmourPutOn(){
		
	}
	
	public void onArmourTakeOff(){
		
	}

}
