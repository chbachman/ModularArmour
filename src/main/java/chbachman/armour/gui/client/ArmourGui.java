package chbachman.armour.gui.client;

import java.awt.Color;
import java.util.Collections;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import chbachman.armour.gui.container.ArmourContainer;
import chbachman.armour.gui.container.TabCrafting;
import chbachman.armour.handler.UpgradeHandler;
import chbachman.armour.network.ArmourPacket;
import chbachman.armour.network.ArmourPacket.PacketTypes;
import chbachman.armour.reference.ResourceLocationHelper;
import chbachman.armour.upgrade.Upgrade;
import chbachman.armour.upgrade.UpgradeException;
import chbachman.armour.util.NBTHelper;
import cofh.gui.GuiBaseAdv;
import cofh.gui.GuiProps;
import cofh.gui.GuiTextList;
import cofh.gui.element.ElementButton;
import cofh.network.PacketHandler;

public class ArmourGui extends GuiBaseAdv{

	private static final ResourceLocation TEXTURE = ResourceLocationHelper.getResourceLocation("/gui/armourGui.png");

	private ArmourContainer container;

	public ElementButton button;
	public UpgradeComponent list;
	public GuiTextList errorMessage;
	public TabCrafting tab;
	
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
		
		button = new ElementButton(this, 183, 210, "Confirm Choice", 208, 128, 208, 144, 208, 160, 16, 16, GuiProps.PATH_GUI + "FriendsList.png");
		addElement(button);
		
		button.setActive();
		
		list = new UpgradeComponent(this.fontRendererObj, guiLeft + 5, guiTop + 5, 160 , 20);
		
		this.getUpgradeList();
		list.drawBackground = false;
		list.drawBorder = false;
		
		errorMessage = new GuiTextList(this.fontRendererObj, guiLeft + 110, guiTop + 90, 70, 6);
		errorMessage.textColor = Color.RED.getRGB();
		errorMessage.textLines = null;
		errorMessage.drawBackground = false;
		errorMessage.drawBorder = false;
		
		tab = new TabCrafting(this, this.container);
		this.addTab(tab);
		
		list.setEnabled(true);
		errorMessage.setEnabled(true);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {

		super.drawGuiContainerBackgroundLayer(f, x, y);
		
		this.getUpgradeList();
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
				if (UpgradeHandler.addUpgrade(this.container.getContainerStack(), this.container.upgrade)) {

					this.container.upgrade = null;
				}
			} catch (UpgradeException e) {
				errorMessage.textLines = this.getFontRenderer().listFormattedStringToWidth(e.getMessage(), 70);
			}
			
			this.getUpgradeList();

		}
		
	}
	
	private void getUpgradeList(){
	    if(this.container.getContainerStack().stackTagCompound == null){
            NBTHelper.createDefaultStackTag(this.container.getContainerStack());
        }
        
        list.textLines = NBTHelper.getUpgradeListFromNBT(this.container.getContainerStack().stackTagCompound);
        Collections.sort(list.textLines);
	}

}
