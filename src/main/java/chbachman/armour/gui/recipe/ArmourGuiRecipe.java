package chbachman.armour.gui.recipe;

import java.util.List;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;

import chbachman.api.IUpgrade;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.network.ArmourPacket;
import chbachman.armour.network.ArmourPacket.PacketTypes;
import chbachman.armour.reference.ResourceLocationHelper;
import cofh.core.gui.GuiBaseAdv;
import cofh.core.network.PacketHandler;
import cofh.lib.gui.element.ElementButton;
import cofh.lib.util.helpers.StringHelper;

public class ArmourGuiRecipe extends GuiBaseAdv {
    
    private static final ResourceLocation TEXTURE = ResourceLocationHelper.getResourceLocation("/gui/recipeGui.png");
    
    public ArmourContainerRecipe container;
    public ElementButton rightArrow;
    public ElementButton leftArrow;
    
    
    public ArmourGuiRecipe(ArmourContainerRecipe container, InventoryPlayer inventory) {
        super(container);
        
        this.container = container;
        
        this.texture = TEXTURE;
        this.drawTitle = false;
        this.drawInventory = false;
        this.xSize = 176;
        this.ySize = 152;
        
        
        leftArrow = new ElementButton(this, 5, 5, "Go Back", 182, 11, 182, 11, 182, 11, 7, 7, TEXTURE.toString());
        rightArrow = new ElementButton(this, 164, 5, "Next", 190, 11, 190, 11, 190, 11, 7, 7, TEXTURE.toString());
        
        rightArrow.setToolTip("Next");
        leftArrow.setToolTip("Back");
    }
    
    @Override
    public void initGui() {
        super.initGui();
        
        this.addElement(leftArrow);
        this.addElement(rightArrow);
    }
    
    
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        super.drawGuiContainerForegroundLayer(x, y);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        
        super.drawGuiContainerBackgroundLayer(f, x, y);
        
        if(this.container.recipe != null){
            
        	IUpgrade upgrade = this.container.recipe.getRecipeOutput();
        	
        	this.drawStringBounded(upgrade.getName(), 70, this.guiLeft + 100, this.guiTop + 18, 0xFFFFFF);
            
            this.drawStringBounded(upgrade.getInformation(), 159, this.guiLeft + 11, this.guiTop + 80, 0xFFFFFF);
            
            if(!upgrade.isCompatible(this.container.item, this.container.stack, this.container.item.getSlot())){
            	this.drawStringBounded(StringHelper.localize("info.chbachman.doesWork"), 70, this.guiLeft + 100, this.guiTop + 50, 0xFFFFFF);
            }else{
            	this.drawStringBounded(StringHelper.localize("info.chbachman.doesntWork"), 70, this.guiLeft + 100, this.guiTop + 50, 0xFFFFFF);
            }
        }
    }
    
    @Override
    public void handleElementButtonClick(String buttonName, int mouseButton){
        super.handleElementButtonClick(buttonName, mouseButton);
        
        if(buttonName.equals("Go Back")){
            this.container.index--;
            try{
                this.container.recipe = Recipe.craftingList.get(this.container.index);
            }catch(IndexOutOfBoundsException e){
                this.container.index = Recipe.craftingList.size() - 1;
                this.container.recipe = Recipe.craftingList.get(this.container.index);
            }
        }else if(buttonName.equals("Next")){
            this.container.index++;
            try{
                this.container.recipe = Recipe.craftingList.get(this.container.index);
            }catch(IndexOutOfBoundsException e){
                this.container.index = 0;
                this.container.recipe = Recipe.craftingList.get(this.container.index);
            }
            
        }
        
        PacketHandler.sendToServer(ArmourPacket.getPacket(PacketTypes.BUTTON).addString(buttonName).addInt(this.container.index));
    }
    
    @Override
    public void keyTyped(char characterTyped, int keyPressed){
        if (keyPressed == Keyboard.KEY_ESCAPE || characterTyped == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            PacketHandler.sendToServer(ArmourPacket.getPacket(ArmourPacket.PacketTypes.KEYTYPED).addShort((short) characterTyped).addInt(keyPressed));
            return;
        }
        super.keyTyped(characterTyped, keyPressed);
    }
    
    
    @SuppressWarnings("unchecked")
    public void drawStringBounded(String name, int width, int x, int y, int color){
        List<String> strings = this.getFontRenderer().listFormattedStringToWidth(name, width);
        
        for(int i = 0; i < strings.size(); i++){
            this.drawString(this.getFontRenderer(), strings.get(i), x, y + i * 10, color);
        }
    }
    
}
