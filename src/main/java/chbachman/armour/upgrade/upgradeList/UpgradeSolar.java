package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import chbachman.api.Upgrade;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.util.EnergyUtil;

public class UpgradeSolar extends Upgrade {
    
    public UpgradeSolar() {
        super("solar");
    }
    
    @Override
    public int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
       
        if (15 == player.worldObj.getBlockLightValue(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ)))
        {
            int amount = this.amount;
            int i = 0;
            ItemStack[] stacks = player.inventory.armorInventory;
            while (amount > 0 && i < 3) {
                
                ItemStack armour = stacks[i];
                
                if (armour != null) {
                    amount = EnergyUtil.getItem(armour).receiveEnergy(armour, amount, false);
                }
                
                i++;
            }
        }
        
        return 0;
    }
    
    @Override
    public boolean isCompatible(ArmourSlot slot) {
        return slot == ArmourSlot.HELMET;
    }
    
    @Override
    public Recipe getRecipe() {
        return new Recipe(this, "ggg", "ici", "iii", 'g', Item.getItemFromBlock(Blocks.glass), 'i', Items.iron_ingot, 'c', Items.coal);
    }
    
    @Override
    public boolean isRepeatable() {
        return true;
    }
    
}
