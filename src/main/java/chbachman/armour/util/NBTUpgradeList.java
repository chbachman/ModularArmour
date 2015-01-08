package chbachman.armour.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import chbachman.api.IUpgrade;
import chbachman.armour.upgrade.UpgradeList;

public class NBTUpgradeList implements List<IUpgrade>{

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
    
    public int size(){
        return list.tagCount();
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
    
    public IUpgrade get(int index){
        NBTTagCompound nbt = list.getCompoundTagAt(index);
        
        if (nbt == null) {
            return null;
        }
        
        IUpgrade upgrade =  getUpgrade(nbt);
        
        if(upgrade == null){
			list.removeTag(index);
			return this.get(index);
		}
        
        return upgrade;
        
    }
    
    public boolean add(IUpgrade upgrade){
        
        list.appendTag(getNBTTagCompound(upgrade));
        
        return true;
    }
    
    @Override
    public IUpgrade set(int index, IUpgrade element) {
        
        this.list.func_150304_a(index, getNBTTagCompound(element));
        
        return element;
    }
    
    public IUpgrade remove(int index){
        
        IUpgrade upgrade = this.get(index);
        
        list.removeTag(index);
        
        return upgrade;
    }

    @Override
    public boolean addAll(Collection<? extends IUpgrade> c) {
        for(IUpgrade upgrade : c){
            this.add(upgrade);
        }
        return true;
    }

    @Override
    public void clear() {
        for(int i = 0; i < this.size(); i++){
            this.remove(i);
        }
    }

    @Override
    public boolean contains(Object o) {
        
        for(IUpgrade upgrade : this){
            if(upgrade.equals(o)){
                return true;
            }
        }
        
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for(Object o : this){
            if(this.contains(o)){
                return true;
            }
        }
        
        return false;
    }

    @Override
    public int indexOf(Object o) {
        for(int i = 0; i < this.size(); i++){
            IUpgrade upgrade = this.get(i);
            if(upgrade.equals(o)){
               return i;
            }
        }
        
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        for(int i = this.size() - 1; i >= 0; i--){
            IUpgrade upgrade = this.get(i);
            if(upgrade.equals(o)){
               return i;
            }
        }
        
        return -1;
    }

    @Override
    public boolean remove(Object o) {
        for(int i = 0; i < this.size(); i++){
            if(this.get(i).equals(o)){
                this.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for(Object o : c){
            if(!this.remove(o)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public List<IUpgrade> subList(int fromIndex, int toIndex) {
        List<IUpgrade> list = new ArrayList<IUpgrade>();
        
        for(int i = fromIndex; i < toIndex; i++){
            list.add(this.get(i));
        }
        
        return list;
    }

    @Override
    public Object[] toArray() {
        Object[] objects = new Object[this.size()];
        
        for(int i = 0; i < this.size(); i++){
            objects[i] = this.get(i);
        }
        
        return objects;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        
        if(a.length < this.size()){
            a = (T[]) this.toArray();
        }else{
            System.arraycopy(this.toArray(), 0, a, 0, this.size());
        }
        
        if (a.length > this.size())
            a[this.size()] = null;
        
        return a;
    }

    public class IteratorUpgradeList implements ListIterator<IUpgrade>{
        
        NBTUpgradeList list;
        int index = 0;
        
        public IteratorUpgradeList(NBTUpgradeList list){
            this.list = list;
        }
        
        public boolean hasNext() {
            return index <= list.size();
        }

        public IUpgrade next() {
            return list.get(index++);
        }

        public boolean hasPrevious() {
            return index != 0;
        }

        public IUpgrade previous() {
            return list.get(--index);
        }

        public int nextIndex() {
            return this.index + 1;
        }

        public int previousIndex() {
            return this.index - 1;
        }

        public void remove() {
            list.remove(index);
        }

        public void set(IUpgrade e) {
            list.set(index, e);
        }

        public void add(IUpgrade e) {
            list.add(index, e);
        }
    }

    @Override
    public void add(int index, IUpgrade element) {
        this.add(element);
        
    }

    @Override
    public boolean addAll(int index, Collection<? extends IUpgrade> c) {
        for(IUpgrade upgrade : c){
            this.add(index, upgrade);
        }
        
        return true;
    }

    @Override
    public ListIterator<IUpgrade> listIterator() {
        return new IteratorUpgradeList(this);
    }

    @Override
    public Iterator<IUpgrade> iterator() {
        return new IteratorUpgradeList(this);
    }

    @Override
    public ListIterator<IUpgrade> listIterator(int index) {
    	IteratorUpgradeList list = new IteratorUpgradeList(this);
    	list.index = index;
    	return list;
    }

    

    
    
}
