package chbachman.armour.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants;

public class PlayerArmour implements IExtendedEntityProperties{
	
	public static final String IDENTIFIER = "ModularArmour:ArmourSaving";
	
	ItemStack[] stacks;
	
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
	
	public static PlayerArmour getFor(EntityPlayer player){
		return (PlayerArmour) player.getExtendedProperties(IDENTIFIER);
	}
	
	public static void register(EntityPlayer player){
		player.registerExtendedProperties(IDENTIFIER, new PlayerArmour());
	}

}
