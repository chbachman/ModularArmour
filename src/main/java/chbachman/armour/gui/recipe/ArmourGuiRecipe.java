package chbachman.armour.gui.recipe;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;

import repack.cofh.core.gui.GuiBaseAdv;
import repack.cofh.core.network.PacketHandler;
import repack.cofh.lib.gui.GuiBase;
import repack.cofh.lib.gui.element.ElementButton;
import repack.cofh.lib.gui.element.ElementTextField;
import chbachman.api.upgrade.IUpgrade;
import chbachman.api.util.ImmutableArray;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.gui.GuiHelper;
import chbachman.armour.network.ArmourPacket;
import chbachman.armour.network.ArmourPacket.PacketTypes;
import chbachman.armour.reference.Reference;

public class ArmourGuiRecipe extends GuiBaseAdv{

	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURE_LOCATION + "/gui/recipeGui.png");

	public ArmourContainerRecipe container;
	public ElementButton rightArrow;
	public ElementButton leftArrow;
	public ElementButton upgrade;
	public RecipeTextField field;
	public TabCompatible compatible;
	public TabRecipeList list;

	public ArmourGuiRecipe(ArmourContainerRecipe container, InventoryPlayer inventory) {
		super(container, TEXTURE);

		this.container = container;

		this.texture = TEXTURE;
		this.drawTitle = false;
		this.drawInventory = false;
		this.xSize = 176;
		this.ySize = 172;

		this.leftArrow = new ElementButton(this, 5, 25, "Go Back", 227, 12, 227, 12, 227, 12, 7, 7, TEXTURE.toString());
		this.rightArrow = new ElementButton(this, 164, 25, "Next", 235, 12, 235, 12, 235, 12, 7, 7, TEXTURE.toString());
		
		this.upgrade = new ElementButton(this, 71, 38, "Upgrade", 71, 38, 71, 38, 16, 16, TEXTURE.toString());
		this.upgrade.setToolTip("Add Upgrade?");
		
		this.field = new RecipeTextField(this, 7, 6, 162, 11);
		field.setBackgroundColor(0xFF000000, 0xFF000000, 0xFF000000);
		
		this.list = new TabRecipeList(this, new ImmutableArray(Recipe.recipeList));
		
		this.compatible = new TabCompatible(this);

		this.rightArrow.setToolTip("Next");
		this.leftArrow.setToolTip("Back");
	}

	@Override
	public void initGui(){
		super.initGui();

		this.addTab(this.compatible);
		this.addTab(this.list);
		this.addElement(this.leftArrow);
		this.addElement(this.rightArrow);
		this.addElement(this.upgrade);
		this.addElement(this.field);

		for (int i = 9; i < 9 + ArmourContainerRecipe.modularItems.size(); i++){
			((Slot) this.container.inventorySlots.get(i)).xDisplayPosition = -this.guiLeft - 16;
			((Slot) this.container.inventorySlots.get(i)).yDisplayPosition = -this.guiTop - 16;
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y){
		super.drawGuiContainerForegroundLayer(x, y);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int x, int y){
		super.drawGuiContainerBackgroundLayer(partialTick, x, y);

		if (this.container.recipe != null){

			IUpgrade upgrade = this.container.recipe.getRecipeOutput();

			GuiHelper.drawStringBounded(this, upgrade.getName(), 70, this.guiLeft + 100, this.guiTop + 38, 0xFFFFFF);

			GuiHelper.drawStringBounded(this, upgrade.getInformation(), 159, this.guiLeft + 11, this.guiTop + 100, 0xFFFFFF);
		}
	}
	
	public void handleTyping(String currentString){
		PacketHandler.sendToServer(ArmourPacket.getPacket(PacketTypes.BUTTON).addString("TextField").addString(currentString));
	}
	
	@Override
	public void handleElementButtonClick(String buttonName, int mouseButton){
		super.handleElementButtonClick(buttonName, mouseButton);

		if (buttonName.equals("Go Back")){
			this.container.index--;
			wrapIndex();
		}else if (buttonName.equals("Next")){
			this.container.index++;
			wrapIndex();
		}else if (buttonName.equals("Upgrade")){
			
		}

		PacketHandler.sendToServer(ArmourPacket.getPacket(PacketTypes.BUTTON).addString(buttonName).addInt(this.container.index));
	}
	
	public void wrapIndex(){
		int min = 0;
		int max = Recipe.recipeList.size;
		
		if(this.container.index >= max){
			this.container.index = max - 1;
		}
		
		if(this.container.index < min){
			this.container.index = min;
		}
		
		this.container.recipe = Recipe.recipeList.get(this.container.index);
	}
	
	@Override
	public void keyTyped(char characterTyped, int keyPressed){
		if (keyPressed == Keyboard.KEY_ESCAPE || characterTyped == this.mc.gameSettings.keyBindInventory.getKeyCode()){
			PacketHandler.sendToServer(ArmourPacket.getPacket(ArmourPacket.PacketTypes.KEYTYPED).addShort((short) characterTyped).addInt(keyPressed));
			return;
		}
		super.keyTyped(characterTyped, keyPressed);
	}
	
	private class RecipeTextField extends ElementTextField{

		public RecipeTextField(GuiBase gui, int posX, int posY, int width, int height) {
			super(gui, posX, posY, width, height);
		}

		@Override
		protected void onCharacterEntered(boolean success){
			if(success){
				handleTyping(this.getText());
			}
		}
		
		
		
	}

}
