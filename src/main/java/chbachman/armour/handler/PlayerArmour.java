package chbachman.armour.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants;

/**
 * This class stores the previous Armour that the person had worn. Used to call onArmourEquip and Deuip.
 * @author CBachman
 *
 */
public class PlayerArmour implements IExtendedEntityProperties{
	
	//The identifier that is used for saving and loading.
	public static final String IDENTIFIER = "ModularArmour:ArmourSaving";
	
	ItemStack[] stacks;
	
	/**
	 * Called every tick to update the player's worn armour.
	 * @param armourList
	 */
	public void update(ItemStack[] armourList){
		stacks = armourList.clone();
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagList list = new NBTTagList();
		for(ItemStack stack : stacks){
			
			if(stack == null){
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setBoolean("Empty", true);
				list.appendTag(nbt);
				return;
			}
			
			list.appendTag(stack.writeToNBT(new NBTTagCompound()));
		}
		compound.setTag("armourList", list);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagList list = compound.getTagList("armourList", Constants.NBT.TAG_COMPOUND);
		
		for(int i = 0; i < list.tagCount(); i++){
			NBTTagCompound nbt = list.getCompoundTagAt(i);
			
			if(nbt.hasKey("Empty")){
				stacks[i] = null;
				return;
			}
			
			stacks[i] = ItemStack.loadItemStackFromNBT(nbt);
		}

	}

	@Override
	public void init(Entity entity, World world) {
		stacks = new ItemStack[4];
	}
	
	/**
	 * Gets the storage instance for the given player.
	 * @param player
	 * @return
	 */
	public static PlayerArmour getFor(EntityPlayer player){
		return (PlayerArmour) player.getExtendedProperties(IDENTIFIER);
	}
	
	/**
	 * Registers the storage instance for the player.
	 * @param player
	 */
	public static void register(EntityPlayer player){
		player.registerExtendedProperties(IDENTIFIER, new PlayerArmour());
	}

}
