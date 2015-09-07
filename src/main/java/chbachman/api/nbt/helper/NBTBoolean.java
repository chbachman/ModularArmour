package chbachman.api.nbt.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTBoolean {

    String key;
    boolean def;

    public NBTBoolean() {
    }

    public NBTBoolean(String key) {
        this.key = key;
    }

    public NBTBoolean(String key, boolean defaul) {
        this.key = key;
        this.def = defaul;
    }

    public boolean set(ItemStack stack, boolean data) {
        boolean temp = this.get(stack);

        stack.stackTagCompound.setBoolean(key, data);

        return temp;
    }

    public boolean set(NBTTagCompound stack, boolean data) {
        boolean temp = this.get(stack);

        stack.setBoolean(key, data);

        return temp;
    }

    public boolean get(NBTTagCompound stack) {
        return stack == null ? def : stack.getBoolean(key);
    }

    public boolean get(ItemStack stack) {
        return get(stack.stackTagCompound);
    }

    public void setDefault(boolean def) {
        this.def = def;
    }

    public boolean getDefault() {
        return this.def;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
