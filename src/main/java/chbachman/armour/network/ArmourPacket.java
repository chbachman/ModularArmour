package chbachman.armour.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import chbachman.armour.gui.container.ArmourContainer;
import chbachman.armour.items.ItemModularArmour;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.upgrade.Upgrade;
import chbachman.armour.util.NBTHelper;
import cofh.network.PacketCoFHBase;
import cofh.network.PacketHandler;

public class ArmourPacket extends PacketCoFHBase {
    
    public static void initialize() {
        
        PacketHandler.instance.registerPacket(ArmourPacket.class);
    }
    
    public enum PacketTypes {
        
        ARMOURCRAFTING(), ENTITYJOINWORLD();
        
    }
    
    @Override
    public void handlePacket(EntityPlayer player, boolean isServer) {
        
        try {
            int type = this.getByte();
            
            switch (PacketTypes.values()[type]) {
            
            case ARMOURCRAFTING:
                this.handleCraftingPacket(player);
                break;
            case ENTITYJOINWORLD:
                this.handleEntityJoinWorld(player);
                break;
            
            default:
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static PacketCoFHBase getPacket(PacketTypes type) {
        
        return new ArmourPacket().addByte(type.ordinal());
    }
    
    public void handleCraftingPacket(EntityPlayer player) {
        
        Container container = player.openContainer;
        
        if (container instanceof ArmourContainer) {
            ArmourContainer armourContainer = (ArmourContainer) container;
            
            armourContainer.onButtonClick(this, this.getString());
        }
    }
    
    public void handleEntityJoinWorld(EntityPlayer player) {
        
        for (ItemStack stack : player.inventory.armorInventory) {
            if (stack != null && stack.getItem() instanceof ItemModularArmour) {
                
                ItemModularArmour armour = (ItemModularArmour) stack.getItem();
                
                for (Upgrade upgrade : NBTHelper.getUpgradeListFromNBT(stack.stackTagCompound)) {
                    upgrade.onArmourEquip(player.worldObj, player, stack, ArmourSlot.getArmourSlot(armour.armorType));
                }
                
            }
        }
    }
    
}
