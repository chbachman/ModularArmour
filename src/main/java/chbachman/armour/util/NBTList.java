package chbachman.armour.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class NBTList<E extends INBTAble> implements List<E> {

	public final NBTTagList list;

	public NBTList() {
		this.list = new NBTTagList();
	}

	public NBTList(NBTTagList list) {
		this.list = list;
	}

	public NBTList(NBTTagCompound nbt) {
		this.list = nbt.getTagList("List", Constants.NBT.TAG_COMPOUND);
	}

	public NBTTagCompound addNBTTagCompound(NBTTagCompound nbt) {
		nbt.setTag("List", this.list);

		return nbt;
	}

	@Override
	public int size() {
		return this.list.tagCount();
	}

	private E getUpgrade(NBTTagCompound nbt) {
		try {

			// E upgrade = //TODO: add getFromNBTMethodHERE

			// return upgrade;
			
			return null;

		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	@Override
	public E get(int index) {
		NBTTagCompound nbt = this.list.getCompoundTagAt(index);

		if (nbt == null) {
			return null;
		}

		E upgrade = this.getUpgrade(nbt);

		if (upgrade == null) {
			this.list.removeTag(index);
			return this.get(index);
		}

		return upgrade;

	}

	@Override
	public boolean add(E upgrade) {

		this.list.appendTag(upgrade.getNBT());

		return true;
	}

	@Override
	public E set(int index, E element) {

		this.list.func_150304_a(index, element.getNBT());

		return element;
	}

	@Override
	public E remove(int index) {

		E upgrade = this.get(index);

		this.list.removeTag(index);

		return upgrade;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		for (E upgrade : c) {
			this.add(upgrade);
		}
		return true;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.size(); i++) {
			this.remove(i);
		}
	}

	@Override
	public boolean contains(Object o) {

		for (E upgrade : this) {
			if (upgrade.equals(o)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : this) {
			if (this.contains(o)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int indexOf(Object o) {
		for (int i = 0; i < this.size(); i++) {
			E upgrade = this.get(i);
			if (upgrade.equals(o)) {
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
		for (int i = this.size() - 1; i >= 0; i--) {
			E upgrade = this.get(i);
			if (upgrade.equals(o)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public boolean remove(Object o) {
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i).equals(o)) {
				this.remove(i);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		for (Object o : c) {
			if (!this.remove(o)) {
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
	public List<E> subList(int fromIndex, int toIndex) {
		List<E> list = new ArrayList<E>();

		for (int i = fromIndex; i < toIndex; i++) {
			list.add(this.get(i));
		}

		return list;
	}

	@Override
	public Object[] toArray() {
		Object[] objects = new Object[this.size()];

		for (int i = 0; i < this.size(); i++) {
			objects[i] = this.get(i);
		}

		return objects;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {

		if (a.length < this.size()) {
			a = (T[]) this.toArray();
		} else {
			System.arraycopy(this.toArray(), 0, a, 0, this.size());
		}

		if (a.length > this.size()) {
			a[this.size()] = null;
		}

		return a;
	}

	public class IteratorUpgradeList implements ListIterator<E> {

		NBTList<E> list;
		int index = 0;

		public IteratorUpgradeList(NBTList<E> list) {
			this.list = list;
		}

		@Override
		public boolean hasNext() {
			return this.index != this.list.size();
		}

		@Override
		public E next() {
			return this.list.get(this.index++);
		}

		@Override
		public boolean hasPrevious() {
			return this.index != 0;
		}

		@Override
		public E previous() {
			return this.list.get(--this.index);
		}

		@Override
		public int nextIndex() {
			return this.index + 1;
		}

		@Override
		public int previousIndex() {
			return this.index - 1;
		}

		@Override
		public void remove() {
			this.list.remove(this.index);
		}

		@Override
		public void set(E e) {
			this.list.set(this.index, e);
		}

		@Override
		public void add(E e) {
			this.list.add(this.index, e);
		}
	}

	@Override
	public void add(int index, E element) {
		this.add(element);

	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		for (E upgrade : c) {
			this.add(index, upgrade);
		}

		return true;
	}

	@Override
	public ListIterator<E> listIterator() {
		return new IteratorUpgradeList(this);
	}

	@Override
	public Iterator<E> iterator() {
		return new IteratorUpgradeList(this);
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new IteratorUpgradeList(this);
	}

}
