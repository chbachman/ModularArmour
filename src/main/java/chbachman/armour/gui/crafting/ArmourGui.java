package chbachman.armour.gui.crafting;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import chbachman.api.nbt.helper.NBTHelper;
import chbachman.api.upgrade.IUpgrade;
import chbachman.armour.network.ArmourPacket;
import chbachman.armour.network.ArmourPacket.PacketTypes;
import chbachman.armour.reference.Reference;
import chbachman.armour.upgrade.UpgradeException;
import cofh.core.gui.GuiBaseAdv;
import cofh.core.network.PacketHandler;

public class ArmourGui extends GuiBaseAdv {
    
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURE_LOCATION + "/gui/armourGui.png");
    
    public ArmourContainer container;
    
    public UpgradeComponent list;
    
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
        
        this.list = new UpgradeComponent(this, 8, 5, 160, 14, stack);
        this.addElement(list);
        
        this.list.textLines = NBTHelper.getNBTUpgradeList(this.container.getContainerStack());
        
        this.list.highlightSelectedLine = true;
        
        this.tabCrafting = new TabCrafting(this);
        this.addTab(this.tabCrafting);
        
        this.scrolledText = new TabError(this, "This is used to upgrade your armour. Use the tab to the right to craft your upgrade.");
        this.addTab(this.scrolledText);
        
        this.removal = new TabUpgradeRemoval(this);
        this.addTab(this.removal);
        
        this.config = new TabConfig(this);
        this.addTab(this.config);
        
        this.list.setEnabled(true);
    }
    
    @Override
    protected void mouseClicked(int mX, int mY, int mouseButton) {
        super.mouseClicked(mX, mY, mouseButton);
        
        this.selectedUpgrade = this.list.getSelectedUpgrade();
        
        this.config.onUpgradeSelected(selectedUpgrade);
        
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int x, int y) {
    	super.drawGuiContainerBackgroundLayer(partialTick, x, y);
    	
    	this.list.textLines = NBTHelper.getNBTUpgradeList(this.container.getContainerStack());
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
