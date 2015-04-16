package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import chbachman.api.configurability.Configurable;
import chbachman.api.configurability.ConfigurableField;
import chbachman.api.item.IModularItem;
import chbachman.api.upgrade.Upgrade;
import chbachman.api.util.ArmourSlot;
import chbachman.api.util.VariableInt;
import chbachman.armour.util.ConfigHelper;

public class UpgradeAutoFeeder extends Upgrade{

	// Data Storage
	private VariableInt storedFood = new VariableInt("foodLevel", 0);

	// Configrability
	@Configurable
	public ConfigurableField foodAmount = new ConfigurableField(this, "foodAmount", 100);

	// Config Options
	private int absorbing;
	private int eating;
	private int amountToHold;

	public UpgradeAutoFeeder() {
		super("feeder");
	}

	@Override
	public void registerConfigOptions(){
		absorbing = ConfigHelper.get(ConfigHelper.ENERGY, this, "cost for absorbing food", 100);
		eating = ConfigHelper.get(ConfigHelper.ENERGY, this, "cost for eating food", 100);

		amountToHold = ConfigHelper.get(ConfigHelper.OTHER, this, "amount of food to hold", 20);
	}

	@Override
	public boolean isCompatible(IModularItem item, ItemStack stack, int armourType){
		return armourType == ArmourSlot.HELMET.id;
	}

	@Override
	public int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot){

		int modifiedAmount = amountToHold * foodAmount.get(stack);
		
		if (storedFood.get(stack) < modifiedAmount){ // Grab the food from the
													// player's inventory.
			for (int i = 0; i < player.inventory.mainInventory.length; i++){
				
				ItemStack playerStack = player.inventory.mainInventory[i];
				
				if (playerStack == null){
					continue;
				}

				if (playerStack.getItem() instanceof ItemFood){
					ItemFood food = (ItemFood) playerStack.getItem();
					
					int amountToStore = food.func_150905_g(playerStack);
					
					if(modifiedAmount - storedFood.get(stack) >= amountToStore){ //If we can store food
						storedFood.increment(stack, amountToStore);

						playerStack.stackSize--;

						if (playerStack.stackSize <= 0){
							player.inventory.mainInventory[i] = null;
						}

						return absorbing;
					}
				}
			}
		}

		FoodStats food = player.getFoodStats(); // Feed the player if necesary.

		if (food.needFood() && this.storedFood.get(stack) > 0){

			int foodNeeded = 20 - food.getFoodLevel();

			food.addStats(foodNeeded, 0);

			this.storedFood.set(stack, this.storedFood.get(stack) - foodNeeded);

			return eating;
		}

		return 0;

	}

}
