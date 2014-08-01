package chbachman.armour.items;

import net.minecraft.item.ItemStack;
import chbachman.armour.objects.VariableInt;
import chbachman.armour.util.NBTHelper;
import cofh.api.energy.IEnergyContainerItem;
import cofh.item.ItemArmorAdv;
import cofh.util.EnergyHelper;

public abstract class ItemElectricArmour extends ItemArmorAdv implements IEnergyContainerItem {
    
    public VariableInt capacity = new VariableInt("Capacity", 100);
    public VariableInt maxTransfer = new VariableInt("MaxTransfer", 10);
    
    public VariableInt energyPerDamage = new VariableInt("energyPerDamage", 10);
    
    public ItemElectricArmour(ArmorMaterial material, int type) {
        super(material, type);
    }
    
    @Override
    public int getDisplayDamage(ItemStack stack) {
        NBTHelper.createDefaultStackTag(stack);
        return 1 + this.capacity.get(stack) - stack.stackTagCompound.getInteger("Energy");
    }
    
    @Override
    public int getMaxDamage(ItemStack stack) {
        
        return 1 + this.capacity.get(stack);
    }
    
    @Override
    public boolean isDamaged(ItemStack stack) {
        
        return stack.getItemDamage() != Short.MAX_VALUE;
    }
    
    // IEnergystackItem
    @Override
    public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate) {
        
        if (stack.stackTagCompound == null) {
            EnergyHelper.setDefaultEnergyTag(stack, 0);
        }
        int stored = stack.stackTagCompound.getInteger("Energy");
        int receive = Math.min(maxReceive, Math.min(this.capacity.get(stack) - stored, this.maxTransfer.get(stack)));
        
        if (!simulate) {
            stored += receive;
            stack.stackTagCompound.setInteger("Energy", stored);
        }
        return receive;
    }
    
    @Override
    public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate) {
        
        if (stack.stackTagCompound == null) {
            EnergyHelper.setDefaultEnergyTag(stack, 0);
        }
        int stored = stack.stackTagCompound.getInteger("Energy");
        int extract = Math.min(maxExtract, stored);
        
        if (!simulate) {
            stored -= extract;
            stack.stackTagCompound.setInteger("Energy", stored);
        }
        return extract;
    }
    
    @Override
    public int getEnergyStored(ItemStack stack) {
        
        if (stack.stackTagCompound == null) {
            EnergyHelper.setDefaultEnergyTag(stack, 0);
        }
        return stack.stackTagCompound.getInteger("Energy");
    }
    
    @Override
    public int getMaxEnergyStored(ItemStack stack) {
        return this.capacity.get(stack);
    }
    
}
