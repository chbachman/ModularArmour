package chbachman.armour.gui.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import util.ItemStackHelper;
import chbachman.armour.gui.container.ArmourContainer;
import chbachman.armour.reference.ResourceLocationHelper;
import chbachman.armour.upgrade.Upgrade;
import cofh.gui.GuiBaseAdv;
import cofh.gui.GuiProps;
import cofh.gui.GuiTextList;
import cofh.gui.element.ElementButton;

public class ArmourGui extends GuiBaseAdv{

	private static final ResourceLocation TEXTURE = ResourceLocationHelper.getResourceLocation("/gui/armourGui.png");

	private ArmourContainer container;

	public ElementButton button;
	public GuiTextList list;
	
	
	public ArmourGui(ArmourContainer container, InventoryPlayer inventory) {
		super(container);

		this.container = container;

		this.texture = TEXTURE;
		this.drawInventory = false;
		this.drawTitle = true;
		this.xSize = 176;
		this.ySize = 234;


	}
	
	@Override
	public void initGui(){
		super.initGui();
		
		button = new ElementButton(this, 139, 70, "Confirm Choice", 208, 128, 208, 144, 208, 160, 16, 16, GuiProps.PATH_GUI + "FriendsList.png");
		addElement(button);
		
		button.setActive();
		
		list = new GuiTextList(this.fontRendererObj, guiLeft + 5, guiTop + 5, 40, 20);
		list.textLines = this.getUpgradeNameList(container.stack);
		list.drawBackground = false;
		list.drawBorder = false;
		
		list.setEnabled(true);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {

		super.drawGuiContainerBackgroundLayer(f, x, y);
		
		list.textLines = getUpgradeNameList(this.container.stack);
		
		list.drawText();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {

		super.drawGuiContainerForegroundLayer(x, y);
		
		if(container.upgrade != null){

			Upgrade upgrade = container.upgrade;

			this.drawString(this.getFontRenderer(), upgrade.getName(), 115, 61, -1);
		}
	}
	
	@Override
	public void handleElementButtonClick(String buttonName, int mouseButton) {
		if(buttonName.equals("Confirm Choice")){
			
			ItemStack stack = this.container.stack;
			
			if(stack.stackTagCompound == null){
				stack.stackTagCompound =  ItemStackHelper.createStackTagCompound();
			}
			
			if(this.container.upgrade != null){
				ItemStackHelper.getNBTTagList(stack.stackTagCompound).appendTag(this.container.upgrade.getNBT());
			}

		}
		
	}
	
	public List<String> getUpgradeNameList(ItemStack item){
		List<String> output = new ArrayList<String>();
		
		NBTTagList tagList = item.stackTagCompound.getTagList("UpgradeList", Constants.NBT.TAG_COMPOUND);
		
		for(int i = 0; i < tagList.tagCount(); i++){
			Upgrade upgrade  = Upgrade.getUpgradeFromNBT(tagList.getCompoundTagAt(i));
			output.add(upgrade.getName());
		}
		
		return output;
		
	}

}
