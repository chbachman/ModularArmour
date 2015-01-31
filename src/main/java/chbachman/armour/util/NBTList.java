package chbachman.armour.util;

import java.util.AbstractList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class NBTList<E> extends AbstractList<E>{

    public final NBTTagList list;
    public final NBTAble<E> loader;
    
    public NBTList(NBTAble<E> tag){
        this(tag, new NBTTagList());
    }
    
    public NBTList(NBTAble<E> loader, NBTTagList list){
        this.list = list;
        this.loader = loader;
    }
    
    public NBTList(NBTAble<E> loader, NBTTagCompound nbt){
        this(loader, nbt.getTagList("UpgradeList", Constants.NBT.TAG_COMPOUND));
    }
    
    public NBTTagCompound addNBTTagCompound(NBTTagCompound nbt){
        nbt.setTag("UpgradeList", list);
        
        return nbt;
    }
    
    @Override
    public E get(int index){
        NBTTagCompound nbt = list.getCompoundTagAt(index);
        
        if (nbt == null) {
            return null;
        }
        
        E data =  loader.loadFromNBT(nbt);
        
        return data;
        
    }
    
    @Override
    public int size(){
        return list.tagCount();
    }
    
    @Override
    public void add(int index, E element) {
    	
    	if(element == null){
    		return;
    	}
    	
    	this.list.appendTag(new NBTTagCompound());
    	
    	for(int i = list.tagCount() - 2; i >= index; i++){
    		list.func_150304_a(i + 1, list.getCompoundTagAt(i));
    	}
    	
    	NBTTagCompound nbt =  new NBTTagCompound();
    	
    	loader.saveToNBT(element, nbt);
    	
    	this.list.func_150304_a(index, nbt);
    }
    
    @Override
    public E set(int index, E element) {
        
    	E data = this.get(index);
    	
    	if(element == null){
    		return data;
    	}
    	
    	NBTTagCompound nbt = new NBTTagCompound();
    	
    	loader.saveToNBT(element, nbt);
    	
        this.list.func_150304_a(index, nbt);
        
        return data;
    }
    
    public E remove(int index){
        
        E data = this.get(index);
        
        list.removeTag(index);
        
        return data;
    }
}
