package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import chbachman.api.IModularItem;
import chbachman.api.Upgrade;
import chbachman.armour.util.ArmourSlot;
import chbachman.armour.util.ConfigHelper;
import chbachman.armour.util.VariableInt;

public class UpgradeAutoFeeder extends Upgrade{
    
	private VariableInt storedFood = new VariableInt("foodLevel", 0);

	public UpgradeAutoFeeder() {
		super("feeder");
	}

	private int absorbing;
	private int eating;

	@Override
	public void registerConfigOptions(){
		absorbing = ConfigHelper.get(ConfigHelper.ENERGY, this, "cost for absorbing food", 100);
		eating = ConfigHelper.get(ConfigHelper.ENERGY, this, "cost for eating food", 100);
	}
    
    @Override
    public boolean isCompatible(IModularItem item, ItemStack stack, int armourType) {
        return armourType == ArmourSlot.HELMET.id;
    }
    
    @Override
    public int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot, int level) {
        
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
                    return absorbing;
                }
            }
        }
        
        FoodStats food = player.getFoodStats();
        
        if(food.needFood() && this.storedFood.get(stack) > 0){
            
            int foodNeeded = 20 - food.getFoodLevel();
            
            food.addStats(foodNeeded, 0);
            
            this.storedFood.set(stack, this.storedFood.get(stack) - foodNeeded);
            
            return eating;
        }
        
        return 0;
        
    }
    
}
