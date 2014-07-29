package chbachman.armour.gui.client;

import java.util.Collections;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import chbachman.armour.gui.container.ArmourContainer;
import chbachman.armour.handler.UpgradeHandler;
import chbachman.armour.network.ArmourPacket;
import chbachman.armour.network.ArmourPacket.PacketTypes;
import chbachman.armour.reference.ResourceLocationHelper;
import chbachman.armour.upgrade.Upgrade;
import chbachman.armour.upgrade.UpgradeException;
import chbachman.armour.util.NBTHelper;
import chbachman.armour.util.UpgradeUtil;
import cofh.gui.GuiBaseAdv;
import cofh.network.PacketHandler;

public class ArmourGui extends GuiBaseAdv{

	private static final ResourceLocation TEXTURE = ResourceLocationHelper.getResourceLocation("/gui/armourGui.png");

	public ArmourContainer container;
	
	public UpgradeComponent list;
	public TabCrafting tabCrafting;
	public TabError scrolledText;
	public TabUpgradeRemoval removal;
	
	public Upgrade selectedUpgrade;
	
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
		
		list = new UpgradeComponent(this.fontRendererObj, guiLeft + 8, guiTop + 5, 160 , 20);
		
		this.getUpgradeList();
		
		list.highlightSelectedLine = true;
		
		//list.drawBackground = false;
		//list.drawBorder = false;
		
		tabCrafting = new TabCrafting(this);
		this.addTab(tabCrafting);
		
		scrolledText = new TabError(this, "This is used to upgrade your armour. Use the tab to the right to craft your upgrade.");
		this.addTab(scrolledText);
		
		removal = new TabUpgradeRemoval(this);
		this.addTab(removal);
		
		list.setEnabled(true);
	}

	@Override
	protected void mouseClicked(int mX, int mY, int mouseButton){
	    super.mouseClicked(mX, mY, mouseButton);
	    
	    if(mX > 5 + this.guiLeft && mX < 165 + this.guiLeft && mY > 5){
	         this.selectedUpgrade = list.mouseClicked(mX, mY, mouseButton, 5 + this.guiTop);
	    }
	    
	    
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {

		super.drawGuiContainerBackgroundLayer(f, x, y);
		
		this.getUpgradeList();
		list.drawText();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {

	    super.drawGuiContainerForegroundLayer(x, y);
	    
	}
	
	public void onButtonClick(String name) {
	    
	    try {
	        
	        if (name.equals("UpgradeAddition")) {
	            
	            if (UpgradeHandler.addUpgrade(this.container.getContainerStack(), this.container.upgrade)) {
	                
	                this.container.upgrade = null;
	                
	                if (this.scrolledText.hasError) {
	                    
	                    if (this.scrolledText.isFullyOpened()) {
	                        this.scrolledText.toggleOpen();
	                    }
	                    
	                    this.scrolledText.reset();
	                }
	                
	            }
	            
	            this.getUpgradeList();
	        }else if(name.equals("RemoveItems")){
	            this.container.onContainerClosed(this.container.player);
	        }else if(name.equals("RemoveUpgrade")){
	            UpgradeUtil.removeUpgrade(this.container.stack, this.selectedUpgrade);
	            PacketHandler.sendToServer(ArmourPacket.getPacket(PacketTypes.ARMOURCRAFTING).addString(name).addInt(this.selectedUpgrade.getId()));
	            return;
	        }
	        
	        PacketHandler.sendToServer(ArmourPacket.getPacket(PacketTypes.ARMOURCRAFTING).addString(name));
	        
	    } catch (UpgradeException e) {
	        this.scrolledText.setString(e.getMessage());
	        if (!this.scrolledText.isFullyOpened()) {
	            this.scrolledText.setFullyOpen();
	        }
	        
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
