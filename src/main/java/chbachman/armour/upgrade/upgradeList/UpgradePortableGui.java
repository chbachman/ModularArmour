package chbachman.armour.upgrade.upgradeList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import org.lwjgl.input.Keyboard;

import chbachman.armour.crafting.Recipe;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.upgrade.KeybindUpgrade;
import chbachman.armour.upgrade.UpgradeList;

public class UpgradePortableGui extends KeybindUpgrade{
    
    private final GuiUpgrades upgrade;
    
    public UpgradePortableGui(GuiUpgrades upgrade) {
        super(upgrade.name);
        this.upgrade = upgrade;
    }
    
    @Override
    public void keyPressServer(EntityPlayer player) {
        
        if(upgrade == GuiUpgrades.CRAFTING){
            player.displayGUIWorkbench((int) player.posX, (int) player.posY, (int) player.posZ);
        }
        
    }

    @Override
    public int getKey() {
        return Keyboard.KEY_O;
    }
    
    @Override
    public boolean keyPress(EntityPlayer player) {
        return true;
    }

    @Override
    public boolean isCompatible(ArmourSlot slot) {
        return slot == ArmourSlot.CHESTPLATE;
    }

    @Override
    public Recipe getRecipe() {
        return new Recipe(this, "pip", "ici", "pip", 'p', Items.ender_pearl, 'i', Items.iron_ingot, 'c', this.upgrade.recipe);
    }
    
    @Override
    public boolean shouldRegisterRecipes(){
        return false;
    }
    
    public enum GuiUpgrades{
        CRAFTING("crafting", Blocks.crafting_table);
        
        public final Item recipe;
        public final String name;
        
        private GuiUpgrades(String name, Item item){
            this.recipe = item;
            this.name = name;
        }
        
        private GuiUpgrades(String name, Block item){
            this.recipe = Item.getItemFromBlock(item);
            this.name = name;
        }
        
        public static void init(){
            for(GuiUpgrades gui : GuiUpgrades.values()){
                UpgradeList.list.add(new UpgradePortableGui(gui));
            }
        }
    }
    
}
