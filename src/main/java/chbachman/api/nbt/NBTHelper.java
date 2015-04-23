package chbachman.api.nbt;

import java.util.Collection;
import java.util.Iterator;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import chbachman.api.upgrade.IUpgrade;

public class NBTHelper {
    
    public static ItemStack createDefaultStackTag(ItemStack stack) {
        
        if (stack.stackTagCompound == null) {
            stack.stackTagCompound = createStackTagCompound();
        }
        return stack;
    }
    
    private static NBTTagCompound createStackTagCompound() {
        NBTTagCompound compound = new NBTTagCompound();
        
        NBTTagList nbtList = new NBTTagList();
        compound.setTag("UpgradeList", nbtList);
        
        compound.setInteger("Energy", 0);
        
        compound.setInteger("Capacity", 100);
        
        compound.setInteger("MaxTransfer", 100);
        
        return compound;
    }
    
    public static NBTList<IUpgrade> getNBTUpgradeList(ItemStack stack) {
        
        createDefaultStackTag(stack);
        
        return getNBTUpgradeList(stack.stackTagCompound);
        
    }
    
    public static NBTList<IUpgrade> getNBTUpgradeList(NBTTagCompound nbt) {
        
        if (nbt == null) {
            nbt = createStackTagCompound();
        }
        
        if (!nbt.hasKey("UpgradeList")) {
            nbt.setTag("UpgradeList", new NBTTagList());
        }
        
        NBTList<IUpgrade> tagList = new NBTList<IUpgrade>(nbt.getTagList("UpgradeList", Constants.NBT.TAG_COMPOUND));
        
        return tagList;
        
    }

    public static void save(NBTTagCompound nbt, String identifier, Collection<?> c){
    	NBTTagList list = new NBTTagList();
    	Iterator<?> iterator = c.iterator();
    	
    	while(iterator.hasNext()){
    		list.appendTag(NBTBuilder.save(iterator.next()));
    	}
    	
    	nbt.setTag(identifier, list);

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void load(NBTTagCompound nbt, String identifier, Collection toFill){
    	
    	NBTTagList list = nbt.getTagList(identifier, Constants.NBT.TAG_COMPOUND);
    	
    	for(int i = 0; i < list.tagCount(); i++){
    		toFill.add(NBTBuilder.load(list.getCompoundTagAt(i)));
    	}
    	
    }
}
