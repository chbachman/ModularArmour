package chbachman.armour.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import chbachman.armour.gui.IInputHandler;
import chbachman.armour.items.ItemModularArmour;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.upgrade.Upgrade;
import chbachman.armour.util.NBTHelper;
import cofh.core.network.PacketCoFHBase;
import cofh.core.network.PacketHandler;

public class ArmourPacket extends PacketCoFHBase {
    
    public static void initialize() {
        
        PacketHandler.instance.registerPacket(ArmourPacket.class);
    }
    
    public enum PacketTypes {
        
        BUTTON(), KEYTYPED(), ENTITYJOINWORLD();
        
    }
    
    @Override
    public void handlePacket(EntityPlayer player, boolean isServer) {
        
        try {
            int type = this.getByte();
            
            switch (PacketTypes.values()[type]) {
            
            case BUTTON:
                this.handleButtonPressed(player);
                break;
            case KEYTYPED:
                this.handleKeyTyped(player);
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
    
    public void handleKeyTyped(EntityPlayer player){
        
        Container container = player.openContainer;
        
        if (container instanceof IInputHandler) {
            IInputHandler inputHandler = (IInputHandler) container;
            
            inputHandler.onKeyTyped(this, (char) this.getShort(), this.getInt());
        }
    }
    
    public void handleButtonPressed(EntityPlayer player) {
        
        Container container = player.openContainer;
        
        if (container instanceof IInputHandler) {
            IInputHandler inputHandler = (IInputHandler) container;
            
            inputHandler.onButtonClick(this, this.getString());
        }
    }
    
    public void handleEntityJoinWorld(EntityPlayer player) {
        
        for (ItemStack stack : player.inventory.armorInventory) {
            if (stack != null && stack.getItem() instanceof ItemModularArmour) {
                
                ItemModularArmour armour = (ItemModularArmour) stack.getItem();
                
                for (Upgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {
                    upgrade.onArmourEquip(player.worldObj, player, stack, ArmourSlot.getArmourSlot(armour.armorType));
                }
                
            }
        }
    }
    
}
