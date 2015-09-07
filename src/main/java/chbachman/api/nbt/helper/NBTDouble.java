package chbachman.api.nbt.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTDouble {

    String key;
    double def;

    public NBTDouble() {
    }

    public NBTDouble(String key) {
        this.key = key;
    }

    public NBTDouble(String key, double defaul) {
        this.key = key;
        this.def = defaul;
    }

    public double set(ItemStack stack, double data) {
        double temp = this.get(stack);

        stack.stackTagCompound.setDouble(key, data);

        return temp;
    }

    public double set(NBTTagCompound stack, double data) {
        double temp = this.get(stack);

        stack.setDouble(key, data);

        return temp;
    }

    public double get(NBTTagCompound stack) {
        return stack == null ? def : stack.getInteger(key);
    }

    public double get(ItemStack stack) {
        return get(stack.stackTagCompound);
    }

    public void setDefault(double def) {
        this.def = def;
    }

    public double getDefault() {
        return this.def;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
