package chbachman.armour.handler;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/**
 * This class is used during development to detail the amount of damage dealt with each hit.
 * @author CBachman
 *
 */
public class DamageEventHandler {

    HashMap<EntityPlayer, Float> otherMap = new HashMap<EntityPlayer, Float>();
    
    @SubscribeEvent
    public void onEntityDamaged(LivingHurtEvent e){
    	if(e.entityLiving instanceof EntityPlayer){
    		EntityPlayer player = (EntityPlayer) e.entityLiving;
    		player.addChatMessage(new ChatComponentText(String.format("Damage is %s.", e.ammount)));
    		otherMap.put(player, e.ammount);
    	}
    }
    
    HashMap<EntityPlayer, Float> map = new HashMap<EntityPlayer, Float>();
    
    @SubscribeEvent
    public void onEntityUpdate(LivingUpdateEvent e){
    	if(e.entityLiving instanceof EntityPlayer){
    		EntityPlayer player = (EntityPlayer) e.entityLiving;
    		
    		if(player == null){
    			return;
    		}
    		
    		Float f = map.get(player);
    		
    		Float health = player.getHealth();
    		
    		if(f == null){
    			map.put(player, health);
    			return;
    		}
    		
    		if(f.floatValue() == health.floatValue()){
    			return;
    		}else{
    			player.addChatMessage(new ChatComponentText(String.format("Damage is %s", f - health)));
    			map.put(player, health);
    			
    			Float damage = otherMap.get(player);
    			
    			if(damage == null){
    				return;
    			}
    			player.addChatMessage(new ChatComponentText(String.format("Percentage is %s", (1 - ((f - health) / damage)) * 100)));
    			
    		}
    	}
    }
}
