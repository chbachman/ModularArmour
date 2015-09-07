package chbachman.armour.upgrade.upgradeList;

import net.minecraft.item.ItemStack;
import chbachman.api.item.IModularItem;
import chbachman.api.util.ArmourSlot;

public class UpgradeDecorative extends UpgradeBasic {

    String textureName;
    String textureColor;

    public UpgradeDecorative(String name) {
        super(name);
    }

    public UpgradeDecorative setTextureName(String name) {
        textureName = name;

        return this;
    }

    public UpgradeDecorative setTextureColor(String color) {
        textureColor = color;

        return this;
    }

    @Override
    public boolean isCompatible(IModularItem item, ItemStack stack, int armorType) {
        return super.isCompatible(item, stack, armorType) && item.isArmour();
    }

    @Override
    public String getArmourTexture(ItemStack stack, ArmourSlot slot) {
        return textureName;
    }

    @Override
    public String getArmourColor(ItemStack stack, ArmourSlot slot) {
        return textureColor;
    }
}
