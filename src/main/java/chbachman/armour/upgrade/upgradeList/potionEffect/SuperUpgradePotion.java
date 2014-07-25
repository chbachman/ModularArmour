package chbachman.armour.upgrade.upgradeList.potionEffect;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.items.ItemModularArmour;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.upgrade.Upgrade;
import chbachman.armour.util.UpgradeUtil;

public class SuperUpgradePotion extends Upgrade{

	Potion effect;
	int level;
	Upgrade dependency = null;
	Item item;
	
	public SuperUpgradePotion(String name, Potion potion, Item item, int level) {
		super(name);
		
		if(level != 0){
			super.name = "Strong " + super.name;
		}
		
		this.item = item;
		this.effect = potion;
		this.level = level;
		
	}
	
	public boolean isCompatible(ArmourSlot slot){
		return true;
	}


	@Override
	public Recipe getRecipe() {
		if(level == 0){
			return new Recipe(this, "iri", "gug", "igi", 'i', Items.iron_ingot, 'g', Item.getItemFromBlock(Blocks.glass) , 'r', Items.redstone, 'u', item);
		}else{
			return new Recipe(this, "iri", "gug", "igi", 'i', Items.iron_ingot, 'g', Item.getItemFromBlock(Blocks.glass) , 'r', Item.getItemFromBlock(Blocks.redstone_block), 'u', item);
		}
	}
	
	public void onArmourTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot){
		player.addPotionEffect(new PotionEffect(effect.id, 0, level));
	}
	
	
	@Override
	public List<String> getDependencies(){
		if(level != 0){
			return UpgradeUtil.getDependencyList(upgradeList.get(this.getId() - 1));
		}else{
			return UpgradeUtil.getDependencyList("Potion");
		}
	}
	
	public void onUpgradeAddition(ItemModularArmour armour, ItemStack stack){
		UpgradeUtil.removeUpgrade(stack, this);
	}

	
}
