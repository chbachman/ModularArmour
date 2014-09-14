package chbachman.armour.handler;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import chbachman.api.IModularItem;
import chbachman.api.IUpgrade;
import chbachman.armour.util.ArmourSlot;
import chbachman.armour.util.NBTHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class GenericEventHandler {
    
    private Map<PlayerArmourSlot, ItemStack> playerList = new HashMap<PlayerArmourSlot, ItemStack>();
    
    @SubscribeEvent
    public void onEntityLivingUpdate(LivingUpdateEvent e) {
        
        if (e.entity instanceof EntityPlayer && e.entity.worldObj.isRemote == false) {
            EntityPlayer player = (EntityPlayer) e.entity;
            
            ItemStack[] stacks = player.inventory.armorInventory;
            
            for (int i = 0; i < stacks.length; i++) {
                
                // I have no idea why i - 1 needs to happen, or even works, but
                // it does.
                PlayerArmourSlot playerSlot = new PlayerArmourSlot(player, i - 1);
                
                ItemStack stack = stacks[i];
                
                if (stack != null && stack.getItem() instanceof IModularItem) {
                    
                    if (!this.playerList.containsKey(playerSlot)) {
                        this.playerList.put(playerSlot, stack);
                    }
                    
                } else {
                    
                    if (this.playerList.containsKey(playerSlot)) {
                        
                        ItemStack stack2 = this.playerList.get(playerSlot);
                        
                        IModularItem armour = (IModularItem) stack2.getItem();
                        
                        if (playerSlot.getSlot().id == armour.getSlot()) {
                            armour.onArmorDequip(player.worldObj, player, stack2);
                            
                            this.playerList.remove(playerSlot);
                        }
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerJoinWorld(PlayerEvent.PlayerLoggedInEvent e) {
        for (ItemStack stack : e.player.inventory.armorInventory) {
            if (stack != null && stack.getItem() instanceof IModularItem) {
                
            	NBTHelper.createDefaultStackTag(stack);
            	
                IModularItem armour = (IModularItem) stack.getItem();
                
                for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {
                	if(upgrade == null){
                		continue;
                	}
                	
                    upgrade.onEquip(e.player.worldObj, e.player, stack, ArmourSlot.getArmourSlot(armour.getSlot()));
                }
                
            }
        }
    }

    private class PlayerArmourSlot {
        
        private final EntityPlayer player;
        private final ArmourSlot slot;
        
        public PlayerArmourSlot(EntityPlayer player, int slot) {
            super();
            this.player = player;
            this.slot = ArmourSlot.getArmourSlot(slot);
        }
        
        public ArmourSlot getSlot() {
            return this.slot;
        }
        
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (this.player == null ? 0 : this.player.hashCode());
            result = prime * result + (this.slot == null ? 0 : this.slot.hashCode());
            return result;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            PlayerArmourSlot other = (PlayerArmourSlot) obj;
            if (this.player == null) {
                if (other.player != null) {
                    return false;
                }
            } else if (!this.player.equals(other.player)) {
                return false;
            }
            if (this.slot != other.slot) {
                return false;
            }
            return true;
        }
        
        @Override
        public String toString() {
            return "PlayerArmourSlot [player=" + this.player + ", slot=" + this.slot + "]";
        }
        
    }

}
