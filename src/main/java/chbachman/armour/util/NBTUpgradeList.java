package chbachman.armour.util;

import java.util.*;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import chbachman.armour.upgrade.Upgrade;
import chbachman.armour.upgrade.UpgradeList;

public class NBTUpgradeList implements List<Upgrade>{

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
    
    public Upgrade get(int index){
        NBTTagCompound nbt = list.getCompoundTagAt(index);
        
        if (nbt == null) {
            return null;
        }
        
        try {
            Upgrade upgrade = UpgradeList.list.get(nbt.getInteger("ID"));
            
            return upgrade;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    
    public boolean add(Upgrade upgrade){
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("ID", upgrade.getId());
        
        list.appendTag(nbt);
        
        return true;
    }
    
    public Upgrade remove(int index){
        
        Upgrade upgrade = this.get(index);
        
        list.removeTag(index);
        
        return upgrade;
    }

    @Override
    public boolean addAll(Collection<? extends Upgrade> c) {
        for(Upgrade upgrade : c){
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
        
        for(Upgrade upgrade : this){
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
            Upgrade upgrade = this.get(i);
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
            Upgrade upgrade = this.get(i);
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
    public Upgrade set(int index, Upgrade element) {
        
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("ID", element.getId());
        
        list.appendTag(nbt);
        
        this.list.func_150304_a(index, nbt);
        
        return element;
    }

    @Override
    public List<Upgrade> subList(int fromIndex, int toIndex) {
        List<Upgrade> list = new ArrayList<Upgrade>();
        
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

    public class IteratorUpgradeList implements ListIterator<Upgrade>{
        
        NBTUpgradeList list;
        int index = -1;
        
        public IteratorUpgradeList(NBTUpgradeList list){
            this.list = list;
        }
        
        public boolean hasNext() {
            return index != list.size();
        }

        public Upgrade next() {
            this.index++;
            return list.get(index);
        }

        public boolean hasPrevious() {
            return index != 0;
        }

        public Upgrade previous() {
            this.index--;
            return list.get(index);
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

        public void set(Upgrade e) {
            list.set(index, e);
        }

        public void add(Upgrade e) {
            list.add(index, e);
        }
    }

    @Override
    public void add(int index, Upgrade element) {
        this.add(element);
        
    }

    @Override
    public boolean addAll(int index, Collection<? extends Upgrade> c) {
        for(Upgrade upgrade : c){
            this.add(index, upgrade);
        }
        
        return true;
    }

    @Override
    public ListIterator<Upgrade> listIterator() {
        return new IteratorUpgradeList(this);
    }

    @Override
    public Iterator<Upgrade> iterator() {
        return new IteratorUpgradeList(this);
    }

    @Override
    public ListIterator<Upgrade> listIterator(int index) {
        return new IteratorUpgradeList(this);
    }

    

    
    
}
