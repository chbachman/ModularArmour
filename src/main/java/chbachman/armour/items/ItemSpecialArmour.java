package chbachman.armour.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import chbachman.armour.reference.ArmourSlot;

public abstract class ItemSpecialArmour extends ItemElectricArmour implements ISpecialArmor {
    
    protected static final ArmorProperties USELESS = new ArmorProperties(0, 0, 0);
    
    public ItemSpecialArmour(ArmorMaterial material, int type) {
        super(material, type);
    }
    
    @Override
    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack) {
        return false;
    }
    
    @Override
    public EnumRarity getRarity(ItemStack stack) {
        
        return EnumRarity.uncommon;
    }
    
    public ArmourSlot getArmourSlot() {
        return ArmourSlot.getArmourSlot(this.armorType);
    }
    
    // ISpecialArmor
    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        this.extractEnergy(stack, damage * this.energyPerDamage.get(stack), false);
    }
    
}
