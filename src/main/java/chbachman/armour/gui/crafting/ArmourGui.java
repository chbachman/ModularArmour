package chbachman.armour.gui.crafting;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import chbachman.api.IUpgrade;
import chbachman.armour.ModularArmour;
import chbachman.armour.gui.GuiHandler;
import chbachman.armour.handler.UpgradeHandler;
import chbachman.armour.network.ArmourPacket;
import chbachman.armour.network.ArmourPacket.PacketTypes;
import chbachman.armour.reference.ResourceLocationHelper;
import chbachman.armour.upgrade.UpgradeException;
import chbachman.armour.util.NBTHelper;
import chbachman.armour.util.UpgradeUtil;
import cofh.core.gui.GuiBaseAdv;
import cofh.core.network.PacketHandler;

public class ArmourGui extends GuiBaseAdv {
    
    private static final ResourceLocation TEXTURE = ResourceLocationHelper.getResourceLocation("/gui/armourGui.png");
    
    public ArmourContainer container;
    
    public UpgradeComponent list;
    
    public TabCrafting tabCrafting;
    public TabError scrolledText;
    public TabUpgradeRemoval removal;
    
    public IUpgrade selectedUpgrade;
    
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
    public void initGui() {
        super.initGui();
        
        this.list = new UpgradeComponent(this.fontRendererObj, this.guiLeft + 8, this.guiTop + 5, 160, 20);
        
        this.getUpgradeList();
        
        this.list.highlightSelectedLine = true;
        
        this.tabCrafting = new TabCrafting(this);
        this.addTab(this.tabCrafting);
        
        this.scrolledText = new TabError(this, "This is used to upgrade your armour. Use the tab to the right to craft your upgrade.");
        this.addTab(this.scrolledText);
        
        this.removal = new TabUpgradeRemoval(this);
        this.addTab(this.removal);
        
        this.list.setEnabled(true);
    }
    
    @Override
    protected void mouseClicked(int mX, int mY, int mouseButton) {
        super.mouseClicked(mX, mY, mouseButton);
        
        if (mX > 5 + this.guiLeft && mX < 165 + this.guiLeft && mY > 5) {
            this.selectedUpgrade = this.list.mouseClicked(mX, mY, mouseButton, this.guiTop + 5);
        }
        
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        
        super.drawGuiContainerBackgroundLayer(f, x, y);
        
        this.getUpgradeList();
        this.list.drawText();
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        
        super.drawGuiContainerForegroundLayer(x, y);
        
    }
    
    public void onButtonClick(String name) {
        
        try {
            
            if (name.equals("UpgradeAddition")) {
                
                if (UpgradeHandler.addUpgrade(this.container.getContainerStack(), this.container.upgrade)) {
                    
                    this.container.upgrade = UpgradeHandler.getResult(this.container.containerWrapper);
                    
                    if (this.scrolledText.hasError) {
                        
                        if (this.scrolledText.isFullyOpened()) {
                            this.scrolledText.toggleOpen();
                        }
                        
                        this.scrolledText.reset();
                    }
                    
                }
                
                this.getUpgradeList();
            } else if (name.equals("RemoveItems")) {
                this.container.onContainerClosed(this.container.player);
            } else if (name.equals("RemoveUpgrade")) {
                UpgradeUtil.removeUpgrade(this.container.stack, this.selectedUpgrade);
                PacketHandler.sendToServer(ArmourPacket.getPacket(PacketTypes.BUTTON).addString(name).addInt(this.selectedUpgrade.getId()));
                return;
            } else if(name.equals("Recipe")){
                if (this.container.player.worldObj.isRemote == false) {
                    this.container.player.openGui(ModularArmour.instance, GuiHandler.ARMOUR_ID, this.container.player.worldObj, 0, 0, 0);
                }
            }
            
            PacketHandler.sendToServer(ArmourPacket.getPacket(PacketTypes.BUTTON).addString(name));
            
        } catch (UpgradeException e) {
            this.scrolledText.setString(e.getMessage());
            if (!this.scrolledText.isFullyOpened()) {
                this.scrolledText.setFullyOpen();
            }
            
        }
        
    }
    
    private void getUpgradeList() {
        if (this.container.getContainerStack().stackTagCompound == null) {
            NBTHelper.createDefaultStackTag(this.container.getContainerStack());
        }
        
        this.list.textLines = NBTHelper.getNBTUpgradeList(this.container.getContainerStack().stackTagCompound);
    }
    
}
