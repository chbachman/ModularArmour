package chbachman.armour.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.badlogic.gdx.utils.ObjectFloatMap;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/**
 * This class is used during development to detail the amount of damage dealt with each hit.
 * @author CBachman
 *
 */
public class DamageEventHandler {

	ObjectFloatMap<EntityPlayer> otherMap = new ObjectFloatMap<EntityPlayer>();
    
    @SubscribeEvent
    public void onEntityDamaged(LivingHurtEvent e){
    	if(e.entityLiving instanceof EntityPlayer){
    		EntityPlayer player = (EntityPlayer) e.entityLiving;
    		player.addChatMessage(new ChatComponentText(String.format("Damage is %s.", e.ammount)));
    		otherMap.put(player, e.ammount);
    	}
    }
    
    ObjectFloatMap<EntityPlayer> map = new ObjectFloatMap<EntityPlayer>();
    
    @SubscribeEvent
    public void onEntityUpdate(LivingUpdateEvent e){
    	if(e.entityLiving instanceof EntityPlayer){
    		EntityPlayer player = (EntityPlayer) e.entityLiving;
    		
    		if(player == null){
    			return;
    		}
    		
    		float health = player.getHealth();
    		
    		if(map.containsKey(player)){
    			map.put(player, health);
    		}
    		
    		float f = map.get(player, 0);
    		
    		if(f == health){
    			return;
    		}else{
    			player.addChatMessage(new ChatComponentText(String.format("Damage is %s", f - health)));
    			map.put(player, health);
    			
    			if(!otherMap.containsKey(player)){
    				return;
    			}

    			player.addChatMessage(new ChatComponentText(String.format("Percentage is %s", (1 - ((f - health) / otherMap.get(player, 0))) * 100)));
    			
    		}
    	}
    }
}
