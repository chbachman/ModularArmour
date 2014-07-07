package chbachman.armour.gui.client;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import chbachman.armour.gui.container.ArmourContainer;
import chbachman.armour.handler.UpgradeHandler;
import chbachman.armour.network.ArmourPacket;
import chbachman.armour.network.ArmourPacket.PacketTypes;
import chbachman.armour.reference.ResourceLocationHelper;
import chbachman.armour.upgrade.Upgrade;
import chbachman.armour.upgrade.UpgradeException;
import cofh.gui.GuiBaseAdv;
import cofh.gui.GuiProps;
import cofh.gui.GuiTextList;
import cofh.gui.element.ElementButton;
import cofh.network.PacketHandler;

public class ArmourGui extends GuiBaseAdv{

	private static final ResourceLocation TEXTURE = ResourceLocationHelper.getResourceLocation("/gui/armourGui.png");

	private ArmourContainer container;

	public ElementButton button;
	public GuiTextList list;
	public GuiTextList errorMessage;
	
	
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
		
		errorMessage = new GuiTextList(this.fontRendererObj, guiLeft + 110, guiTop + 90, 70, 6);
		errorMessage.textColor = Color.RED.getRGB();
		errorMessage.textLines = null;
		errorMessage.drawBackground = false;
		errorMessage.drawBorder = false;
		
		list.setEnabled(true);
		errorMessage.setEnabled(true);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {

		super.drawGuiContainerBackgroundLayer(f, x, y);
		
		list.textLines = getUpgradeNameList(this.container.stack);
		list.drawText();
		
		if(this.errorMessage.textLines != null){
			errorMessage.drawText();
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {

		super.drawGuiContainerForegroundLayer(x, y);
		
		if(container.upgrade != null){

			Upgrade upgrade = container.upgrade;

			this.drawString(this.getFontRenderer(), upgrade.getName(), 115, 61, -1);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void handleElementButtonClick(String buttonName, int mouseButton) {
		if(buttonName.equals("Confirm Choice")){
			
			PacketHandler.sendToServer(ArmourPacket.getPacket(PacketTypes.ARMOURCRAFTING));
			
			try {
				if (UpgradeHandler.addUpgrade(this.container.stack, this.container.upgrade)) {

					this.container.upgrade = null;
				}
			} catch (UpgradeException e) {
				errorMessage.textLines = this.getFontRenderer().listFormattedStringToWidth(e.getMessage(), 70);
			}
			list.textLines = getUpgradeNameList(this.container.stack);

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
