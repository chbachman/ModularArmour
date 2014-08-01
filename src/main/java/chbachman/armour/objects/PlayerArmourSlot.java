package chbachman.armour.objects;

import net.minecraft.entity.player.EntityPlayer;
import chbachman.armour.reference.ArmourSlot;

public class PlayerArmourSlot {
    
    private final EntityPlayer player;
    private final ArmourSlot slot;
    
    public PlayerArmourSlot(EntityPlayer player, ArmourSlot slot) {
        super();
        this.player = player;
        this.slot = slot;
    }
    
    public PlayerArmourSlot(EntityPlayer player, int slot) {
        super();
        this.player = player;
        this.slot = ArmourSlot.getArmourSlot(slot);
    }
    
    public EntityPlayer getPlayer() {
        return this.player;
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
