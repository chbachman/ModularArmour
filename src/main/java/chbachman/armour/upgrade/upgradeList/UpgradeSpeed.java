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

public class UpgradeSpeed extends Upgrade{

    public UpgradeSpeed() {
        super("speed");
    }

    @Override
    public boolean isCompatible(ArmourSlot slot) {
        return slot == ArmourSlot.LEGS;
    }

    @Override
    public Recipe getRecipe() {
        return new Recipe(this, "pip", "i i", "i i", 'i', Items.iron_ingot, 'p', Item.getItemFromBlock(Blocks.piston));
    }
    
    public void onArmourEquip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        player.capabilities.setPlayerWalkSpeed(0.2F);
        player.sendPlayerAbilities();
    }
    
    public void onArmourDequip(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        player.capabilities.setPlayerWalkSpeed(0.1F);
        player.sendPlayerAbilities();
    }
    
    
    
}
