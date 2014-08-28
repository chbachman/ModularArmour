package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import chbachman.api.Upgrade;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.objects.VariableInt;
import chbachman.armour.reference.ArmourSlot;

public class UpgradeAutoFeeder extends Upgrade{
    
    private VariableInt storedFood = new VariableInt("foodLevel", 0);
    
    public UpgradeAutoFeeder() {
        super("feeder");
    }

    @Override
    public boolean isCompatible(ArmourSlot slot) {
        return slot == ArmourSlot.HELMET;
    }

    @Override
    public Recipe getRecipe() {
        return new Recipe(this, "igi", "igi", "iii", 'i', Items.iron_ingot, 'g', Items.golden_apple);
    }
    
    @Override
    public int onArmourTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
        
        if(storedFood.get(stack) < 20){
            for(ItemStack playerStack : player.inventory.mainInventory){
                
                if(playerStack == null){
                    continue;
                }
                
                if(playerStack.getItem() instanceof ItemFood){
                    ItemFood food = (ItemFood) playerStack.getItem();
                    
                    if((20 - storedFood.get(stack)) < food.func_150905_g(playerStack)){
                        storedFood.set(stack, food.func_150905_g(playerStack) + storedFood.get(stack));
                        
                        playerStack.stackSize--;
                        
                        if(playerStack.stackSize <= 0){
                            playerStack = null;
                        }
                    }
                    return 100;
                }
            }
        }
        
        FoodStats food = player.getFoodStats();
        
        if(food.needFood() && this.storedFood.get(stack) > 0){
            
            int foodNeeded = 20 - food.getFoodLevel();
            
            food.addStats(foodNeeded, 0);
            
            this.storedFood.set(stack, this.storedFood.get(stack) - foodNeeded);
            
            return 100;
        }
        
        return 0;
        
    }
    
}
