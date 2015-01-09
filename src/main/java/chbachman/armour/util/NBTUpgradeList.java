package chbachman.armour.util;

import java.util.AbstractList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import chbachman.api.IUpgrade;
import chbachman.armour.upgrade.UpgradeList;

public class NBTUpgradeList extends AbstractList<IUpgrade>{

    public final NBTTagList list;
    
    public NBTUpgradeList(){
        this.list = new NBTTagList();
    }
    
    public NBTUpgradeList(NBTTagList list){
        this.list = list;
    }
    
    public NBTUpgradeList(NBTTagCompound nbt){
        this.list = nbt.getTagList("UpgradeList", Constants.NBT.TAG_COMPOUND);
    }
    
    public NBTTagCompound addNBTTagCompound(NBTTagCompound nbt){
        nbt.setTag("UpgradeList", list);
        
        return nbt;
    }
    
    public static IUpgrade getUpgrade(NBTTagCompound nbt){
    	try {

    		IUpgrade upgrade = UpgradeList.INSTANCE.get(nbt.getInteger("ID")).setDisabled(nbt.getBoolean("disabled"));
    		
			return upgrade;

    	} catch (IndexOutOfBoundsException e) {
    		return null;
    	}
    }
    
    public static NBTTagCompound getNBTTagCompound(IUpgrade upgrade){
    	NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("ID", upgrade.getId());
        nbt.setBoolean("disabled", upgrade.isDisabled());
        return nbt;
    }
    
    @Override
    public IUpgrade get(int index){
        NBTTagCompound nbt = list.getCompoundTagAt(index);
        
        if (nbt == null) {
            return null;
        }
        
        IUpgrade upgrade =  getUpgrade(nbt);
        
        return upgrade;
        
    }
    
    @Override
    public int size(){
        return list.tagCount();
    }
    
    @Override
    public void add(int index, IUpgrade element) {
    	
    	if(element == null){
    		return;
    	}
    	
    	this.list.appendTag(new NBTTagCompound());
    	
    	for(int i = list.tagCount() - 2; i >= index; i++){
    		list.func_150304_a(i + 1, list.getCompoundTagAt(i));
    	}
    	
    	this.list.func_150304_a(index, getNBTTagCompound(element));
    }
    
    @Override
    public IUpgrade set(int index, IUpgrade element) {
        
    	IUpgrade upgrade = this.get(index);
    	
    	if(element == null){
    		return upgrade;
    	}
    	
        this.list.func_150304_a(index, getNBTTagCompound(element));
        
        return upgrade;
    }
    
    public IUpgrade remove(int index){
        
        IUpgrade upgrade = this.get(index);
        
        list.removeTag(index);
        
        return upgrade;
    }
}
