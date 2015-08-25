package chbachman.api.nbt.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import chbachman.api.nbt.serializers.UpgradeNBT;
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
    
    static final UpgradeNBT serializer = new UpgradeNBT();
    
    public static NBTList<IUpgrade> getNBTUpgradeList(NBTTagCompound nbt) {
        
        if (nbt == null) {
            nbt = createStackTagCompound();
        }
        
        if (!nbt.hasKey("UpgradeList")) {
            nbt.setTag("UpgradeList", new NBTTagList());
        }
        
        NBTList<IUpgrade> tagList = new NBTList<IUpgrade>(nbt.getTagList("UpgradeList", Constants.NBT.TAG_COMPOUND), serializer);
        
        return tagList;
        
    }
}
