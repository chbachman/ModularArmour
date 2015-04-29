package chbachman.api.nbt.serializers;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import chbachman.api.nbt.NBTBuilder;
import chbachman.api.nbt.NBTContext;
import chbachman.api.nbt.NBTSerializer;

@SuppressWarnings("rawtypes")
public abstract class CollectionNBT<E extends Collection> implements NBTSerializer<E>{

	public static void register(){
		NBTBuilder.registerNBT(ArrayList.class, new ArrayListNBT());
	}
	
	abstract E getCollection();
	
	@Override
	public void saveToNBT(E data, NBTTagCompound d, NBTContext context){
		NBTTagCollection nbt = new NBTTagCollection(this);
		nbt.setCollection(data);
		
		d.setTag("value", nbt);
	}
	
	@SuppressWarnings({"unchecked"})
	@Override
	public E loadFromNBT(NBTTagCompound d, NBTContext context){
		NBTTagCollection c = (NBTTagCollection) d.getTag("value");
		
		return (E) c.c;
	}
	
	static class ArrayListNBT extends CollectionNBT<ArrayList>{

		@Override
		ArrayList<?> getCollection(){
			return new ArrayList();
		}

		
		
	}
	
	static class LinkedListNBT extends CollectionNBT<LinkedList>{

		@Override
		LinkedList<?> getCollection(){
			return new LinkedList();
		}
		
	}
	
	static class HashSetNBT extends CollectionNBT<HashSet>{

		@Override
		HashSet<?> getCollection(){
			return new HashSet();
		}
		
	}
	
	static class TreeSetNBT extends CollectionNBT<TreeSet>{

		@Override
		TreeSet<?> getCollection(){
			return new TreeSet();
		}
		
	}
	
	static class LinkedHashSetNBT extends CollectionNBT<LinkedHashSet>{

		@Override
		LinkedHashSet<?> getCollection(){
			return new LinkedHashSet();
		}
		
	}
	
	static class ArrayDequeNBT extends CollectionNBT<ArrayDeque>{

		@Override
		ArrayDeque<?> getCollection(){
			return new ArrayDeque();
		}
		
	}

	private static class NBTTagCollection extends NBTBase{
	
		Collection c;
		CollectionNBT<?> nbt;
		
		public NBTTagCollection(CollectionNBT nbt){
			this.nbt = nbt;
		}
		
		public void setCollection(Collection<?> c){
			this.c = c;
		}
		
		@Override
		public void write(DataOutput out) throws IOException{
			
			out.writeInt(c.size());
			
			for(Object obj : c){
				NBTBuilder.save(obj).write(out);
			}
			
		}
	
		@SuppressWarnings("unchecked")
		@Override
		public void func_152446_a(DataInput input, int complexity, NBTSizeTracker tracker) throws IOException{
			if (complexity > 512)
	        {
	            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
	        }
			
			int size = input.readInt();
			c = nbt.getCollection();
			
			for(int i = 0; i < size; i++){
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.func_152446_a(input, complexity, tracker);
				
				c.add(NBTBuilder.load(nbt));
			}
		}
	
		@Override
		public String toString(){
			String s = "[";
	        int i = 0;
	
	        for (Iterator iterator = this.c.iterator(); iterator.hasNext(); ++i)
	        {
	            Object obj = iterator.next();
	            s = s + "" + i + ':' + obj + ',';
	        }
	
	        return s + "]";
		}
	
		@Override
		public byte getId(){
			return 12;
		}
	
		@SuppressWarnings({"unchecked" })
		@Override
		public NBTBase copy(){
			NBTTagCollection collection = new NBTTagCollection(nbt);
			Iterator iterator = this.c.iterator();
	
	        while (iterator.hasNext())
	        {
	            Object obj = iterator.next();
	            Object clone = NBTBuilder.load(NBTBuilder.save(obj));
	            collection.c.add(clone);
	        }
	
	        return collection;
		}
		
		
		
	}

}
