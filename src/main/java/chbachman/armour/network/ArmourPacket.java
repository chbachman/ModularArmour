package chbachman.armour.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import chbachman.api.item.IModularItem;
import chbachman.api.nbt.helper.NBTHelper;
import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.IUpgrade;
import chbachman.api.upgrade.Recipe;
import chbachman.api.util.ArmourSlot;
import chbachman.api.util.Array;
import chbachman.armour.util.MiscUtil;
import cofh.core.network.PacketCoFHBase;

public class ArmourPacket extends PacketCoFHBase {
    
    public enum PacketTypes {
        
        BUTTON(), KEYTYPED(), ENTITYJOINWORLD(), CONTAINERSYNC(), RECIPESYNC();
        
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
			case CONTAINERSYNC:
				this.handleContainerSync(player);
				break;
			case RECIPESYNC:
				this.handleRecipeSync(player);
				break;
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	/**
     * Get the packet for the given type.
     * @param type
     * @return
     */
    public static ArmourPacket getPacket(PacketTypes type) {
        
        return (ArmourPacket) new ArmourPacket().addByte(type.ordinal());
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
            if (stack != null && stack.getItem() instanceof IModularItem) {
                
            	IModularItem armour = (IModularItem) stack.getItem();
                
                for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {
                    upgrade.onEquip(player.worldObj, player, stack, ArmourSlot.getArmourSlot(armour.getSlot()));
                }
                
            }
        }
    }
    
    public void handleContainerSync(EntityPlayer player){
		
    	Container container = player.openContainer;
    	
    	if(container instanceof IContainerSyncable){
    		
    		IContainerSyncable sync = (IContainerSyncable) container;
    		
    		if(MiscUtil.isClient(player)){
    			sync.clientLoad(this);
    		}else{
    			sync.serverLoad(this);
    		}
    		
    	}
    	
	}
    
	public void handleRecipeSync(EntityPlayer player){
		
		Array<Recipe> recipeList = UpgradeRegistry.INSTANCE.recipeList;
		
		recipeList.clear();
		
		
		int size = this.getInt();
		for (int i = 0; i < size; i++){
			Object[] recipe = new Object[9];
			for(int g = 0; g < 9; g++){
				
				byte type = this.getByte();
				
				if(type == 1){
					recipe[g] = this.getString();
				}
				
				if(type == 2){
					recipe[g] = this.getItemStack();
				}
				
				if(type == 3){
					recipe[g] = null;
				}
				
			}
			
			UpgradeRegistry.registerRecipe(new Recipe(UpgradeRegistry.getUpgrade(this.getString()), recipe, this.getInt(), this.getInt()));
		}
		
		
	}
    
}
