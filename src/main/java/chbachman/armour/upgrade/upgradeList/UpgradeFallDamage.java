package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.upgrade.Upgrade;
import chbachman.armour.util.EnergyUtil;

public class UpgradeFallDamage extends Upgrade {
    
    public UpgradeFallDamage() {
        super("fallDamage");
    }
    
    @Override
    public int onArmourTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        if (player.fallDistance > 2 && EnergyUtil.getItem(stack).getEnergyStored(stack) > 100) {
            player.fallDistance = 0;
            return 100;
        }
        
        return 0;
    }
    
    @Override
    public boolean isCompatible(ArmourSlot slot) {
        return slot == ArmourSlot.BOOTS;
    }
    
    @Override
    public Recipe getRecipe() {
        return new Recipe(this, "   ", "   ", "iwi", 'w', Item.getItemFromBlock(Blocks.wool), 'i', Items.iron_ingot);
    }
    
}
