package chbachman.armour.upgrade.upgradeList;

import chbachman.api.item.IModularItem;
import chbachman.api.upgrade.IUpgrade;
import chbachman.api.upgrade.Upgrade;
import chbachman.api.util.ArmourSlot;
import cofh.core.render.IconRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class UpgradeBasic extends Upgrade {

    private ArmourSlot slot = null;
    private IUpgrade dependency = null;
    private String icon = null;

    public UpgradeBasic(String name) {
        super(name);
    }

    public UpgradeBasic setArmourSlot(ArmourSlot slot) {
        this.slot = slot;
        return this;
    }

    public UpgradeBasic setDependencies(IUpgrade upgrade) {
        this.dependency = upgrade;
        return this;
    }

    public UpgradeBasic setIcon(String icon){
    	this.icon = icon;
    	return this;
    }
    
    @Override
    public boolean isCompatible(IModularItem item, ItemStack stack, int armorType) {
        if (this.slot == null) {
            return true;
        }

        return armorType == slot.id;
    }

    @Override
    public IUpgrade[] getDependencies() {
        if (dependency == null) {
            return null;
        }

        return new IUpgrade[] { this.dependency };
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(){
    	return IconRegistry.getIcon(icon);
    }


}
