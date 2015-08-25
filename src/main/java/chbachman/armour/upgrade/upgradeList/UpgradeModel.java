package chbachman.armour.upgrade.upgradeList;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import chbachman.api.item.IModularItem;
import chbachman.api.upgrade.Upgrade;
import chbachman.api.util.ArmourSlot;
import chbachman.armour.items.armour.renderer.AdvancedArmourModel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class UpgradeModel extends Upgrade{
    
    String temp = "modulararmour:textures/armour/" + "Advanced_Armour.png";
    
    public UpgradeModel() {
        super("advancedModel");
    }

    @Override
    public String getArmourTexture(ItemStack stack, ArmourSlot slot) {
        return "modulararmour:textures/armour/Advanced_Armour.png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmourModel(EntityLivingBase entityLiving, ItemStack itemStack, int armourSlot) {
        ArmourSlot slot = ArmourSlot.getArmourSlot(armourSlot);
        
        switch(slot){
        case HELMET: return new AdvancedArmourModel(true, false, false, false, false);
        case CHESTPLATE: return new AdvancedArmourModel(false, true, true, false, false);
        case LEGS: return new AdvancedArmourModel(false, false, false, true, false);
        case BOOTS: return new AdvancedArmourModel(false, false, false, false, true);
        default:
            break;
        }
        
        return null;
        
    }

    @Override
    public boolean isCompatible(IModularItem item, ItemStack stack, int armorType) {
        return item.isArmour();
    }
    
    
    
    

}
