package chbachman.armour.gui.client;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;

import chbachman.armour.ModularArmour;
import chbachman.armour.gui.GuiHandler;
import chbachman.armour.gui.container.ArmourContainerRecipe;
import chbachman.armour.reference.ResourceLocationHelper;
import cofh.gui.GuiBaseAdv;

public class ArmourRecipe extends GuiBaseAdv {
    
    private static final ResourceLocation TEXTURE = ResourceLocationHelper.getResourceLocation("/gui/armourGui.png");
    
    public ArmourContainerRecipe container;
    
    public ArmourRecipe(ArmourContainerRecipe container, InventoryPlayer inventory) {
        super(container);
        
        this.container = container;
        
        this.texture = TEXTURE;
        this.drawTitle = true;
        this.xSize = 176;
        this.ySize = 234;
        
    }
    
    @Override
    public void keyTyped(char characterTyped, int keyPressed){
        if (keyPressed == Keyboard.KEY_ESCAPE || characterTyped == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.container.player.openGui(ModularArmour.instance, GuiHandler.ARMOUR_ID, this.container.player.worldObj, 0, 0, 0);
            return;
        }
        
        super.keyTyped(characterTyped, keyPressed);
    }
    
}
