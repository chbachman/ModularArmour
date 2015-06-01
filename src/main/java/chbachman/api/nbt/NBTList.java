package chbachman.api.nbt;

import java.util.AbstractList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import chbachman.api.upgrade.IUpgrade;

import com.google.gson.reflect.TypeToken;

public class NBTList<E> extends AbstractList<E>{

	public final NBTTagList list;

	public NBTList() {
		this(new NBTTagList());
	}

	public NBTList(NBTTagList list) {
		this.list = list;
	}

	public NBTList(NBTTagCompound nbt) {
		this(nbt.getTagList("UpgradeList", Constants.NBT.TAG_COMPOUND));
	}

	public NBTTagCompound addNBTTagCompound(NBTTagCompound nbt){
		nbt.setTag("UpgradeList", list);

		return nbt;
	}

	@Override
	public E get(int index){
		NBTTagCompound nbt = list.getCompoundTagAt(index);

		if (nbt == null){
			return null;
		}

		E data = (E) NBTBuilder.load(nbt);

		return data;

	}

	@Override
	public int size(){
		return list.tagCount();
	}

	@Override
	public void add(int index, E element){

		if (element == null){
			return;
		}

		this.list.appendTag(new NBTTagCompound());

		for (int i = list.tagCount() - 2; i >= index; i++){
			list.func_150304_a(i + 1, list.getCompoundTagAt(i));
		}

		this.list.func_150304_a(index, NBTBuilder.save(element));
	}

	@Override
	public E set(int index, E element){

		E data = this.get(index);

		if (element == null){
			return data;
		}

		this.list.func_150304_a(index, NBTBuilder.save(element));

		return data;
	}

	@Override
	public E remove(int index){

		E data = this.get(index);

		list.removeTag(index);

		return data;
	}
}
