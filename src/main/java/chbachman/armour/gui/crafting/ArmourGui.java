package chbachman.armour.gui.crafting;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import chbachman.api.upgrade.IUpgrade;
import chbachman.armour.gui.element.ElementUpgradeListBox;
import chbachman.armour.gui.element.TabConfig;
import chbachman.armour.gui.element.TabCrafting;
import chbachman.armour.gui.element.TabError;
import chbachman.armour.gui.element.TabUpgradeRemoval;
import chbachman.armour.network.ArmourPacket;
import chbachman.armour.network.ArmourPacket.PacketTypes;
import chbachman.armour.reference.Reference;
import chbachman.armour.upgrade.UpgradeException;
import cofh.core.gui.GuiBaseAdv;
import cofh.core.network.PacketHandler;
import cofh.lib.util.helpers.StringHelper;

public class ArmourGui extends GuiBaseAdv {
    
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURE_LOCATION + "/gui/armourGui.png");
    
    public ArmourContainer container;
    
    public ElementUpgradeListBox list;
    
    public TabCrafting tabCrafting;
    public TabError scrolledText;
    public TabUpgradeRemoval removal;
    public TabConfig config;
    
    public IUpgrade selectedUpgrade;
    public ItemStack stack;
    
    public float gameTick;
    
    public ArmourGui(ArmourContainer container, InventoryPlayer inventory) {
        super(container, TEXTURE);
        
        this.container = container;
        this.stack = inventory.getCurrentItem();
        this.texture = TEXTURE;
        this.drawInventory = false;
        this.drawTitle = true;
        this.xSize = 176;
        this.ySize = 234;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        
        this.list = new ElementUpgradeListBox(this, 8, 5, 160, 142){
        	
        	public void onUpgradeSelected(IUpgrade upgrade, int index){
        		selectedUpgrade = upgrade;
        		config.onUpgradeSelected(upgrade);
        	}
        	
        };
        
        this.addElement(list);
        
        this.tabCrafting = new TabCrafting(this);
        this.addTab(this.tabCrafting);
        
        this.scrolledText = new TabError(this, StringHelper.localize("info.chbachman.tutorial"));
        this.addTab(this.scrolledText);
        
        this.removal = new TabUpgradeRemoval(this);
        this.addTab(this.removal);
        
        this.config = new TabConfig(this);
        this.addTab(this.config);
        
        this.list.setEnabled(true);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int x, int y) {
    	super.drawGuiContainerBackgroundLayer(partialTick, x, y); 	
    	this.list.loadStack(this.container.getContainerStack());
    }
    
    public void onButtonClick(String name) {
        
        try {
            
            if (name.equals("UpgradeAddition")) {
            	PacketHandler.sendToServer(ArmourPacket.getPacket(PacketTypes.BUTTON).addString(name));
            	
            } else if (name.equals("RemoveItems")) {
            	PacketHandler.sendToServer(ArmourPacket.getPacket(PacketTypes.BUTTON).addString(name));
            	
            } else if (name.equals("RemoveUpgrade")) {
            	//UpgradeUtil.removeUpgrade(this.container.getContainerStack(), this.selectedUpgrade);
                PacketHandler.sendToServer(ArmourPacket.getPacket(PacketTypes.BUTTON).addString(name).addString(this.selectedUpgrade.getBaseName()));
                
            }else if(name.equals("Recipe")){
            	PacketHandler.sendToServer(ArmourPacket.getPacket(PacketTypes.BUTTON).addString(name));
            	
            }else if(name.equals("ScrollDown")){
            	this.list.scrollDown();
            }else if(name.equals("ScrollUp")){
            	this.list.scrollUp();
            }
            
        } catch (UpgradeException e) {
            this.scrolledText.setString(e.getMessage());
            if (!this.scrolledText.isFullyOpened()) {
                this.scrolledText.setFullyOpen();
            }
            
        }
        
    }
    
}
