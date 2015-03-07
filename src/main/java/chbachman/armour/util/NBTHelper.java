package chbachman.armour.util;

import java.util.Collection;
import java.util.Iterator;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import chbachman.api.nbt.NBTAble;
import chbachman.api.nbt.UpgradeNBT;
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
        
        NBTList<IUpgrade> tagList = new NBTList<IUpgrade>(UpgradeNBT.INSTANCE, nbt.getTagList("UpgradeList", Constants.NBT.TAG_COMPOUND));
        
        return tagList;
        
    }

    public static <E> void save(NBTTagCompound nbt, String identifier, Collection<E> c, NBTAble<E> n){
    	NBTTagList list = new NBTTagList();
    	Iterator<E> iterator = c.iterator();
    	
    	while(iterator.hasNext()){
    		NBTTagCompound local = new NBTTagCompound();
    		n.saveToNBT(iterator.next(), local);
    		
    		list.appendTag(local);
    	}
    	
    	nbt.setTag(identifier, list);

    }
    
    public static <E> void save(NBTTagCompound nbt, Collection<E> c, NBTAble<E> n){
    	save(nbt, "list", c, n);
    	
    }

    public static <E> void load(NBTTagCompound nbt, String identifier, Collection<E> toFill, NBTAble<E> n){
    	
    	NBTTagList list = nbt.getTagList(identifier, Constants.NBT.TAG_COMPOUND);
    	
    	for(int i = 0; i < list.tagCount(); i++){
    		toFill.add(n.loadFromNBT(list.getCompoundTagAt(i)));
    	}
    	
    }

    public static <E> void load(NBTTagCompound nbt, Collection<E> toFill, NBTAble<E> n){
    	
    	load(nbt, "list",toFill, n);
    	
    }
}
