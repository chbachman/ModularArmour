package chbachman.armour.handler;

import chbachman.api.item.IModularItem;
import chbachman.api.nbt.helper.NBTHelper;
import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.IUpgrade;
import chbachman.api.upgrade.Recipe;
import chbachman.api.util.ArmourSlot;
import chbachman.armour.network.ArmourPacket;
import chbachman.armour.network.ArmourPacket.PacketTypes;
import chbachman.armour.register.Vanilla;
import chbachman.armour.util.InventoryUtil;
import chbachman.armour.util.MiscUtil;
import cofh.core.network.PacketHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

/**
 * Generic event handler, used for onEquip and onDequip methods.
 * 
 * @author Chbachman
 *
 */
public class GenericEventHandler {

    public static void register() {
        new GenericEventHandler();
    }

    public GenericEventHandler() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event) {
        if (event.entity instanceof EntityPlayer && PlayerArmour.getFor((EntityPlayer) event.entity) == null) {

            EntityPlayer player = (EntityPlayer) event.entity;

            PlayerArmour.register(player);

        }
    }

    @SubscribeEvent
    public void onCraft(ItemCraftedEvent e) {
        PlayerArmour storage = PlayerArmour.getFor(e.player);

        if (!storage.hasCraftedArmour) {

            if (e.crafting.getItem() instanceof IModularItem) {
                storage.hasCraftedArmour = true;
                InventoryUtil.givePlayerItem(e.player, Vanilla.defaultTablet);
            }

        }

    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent e) {
        EntityPlayer player = e.player;

        PlayerArmour storage = PlayerArmour.getFor(player);

        ItemStack[] armourList = player.inventory.armorInventory;

        for (int i = 0; i < armourList.length; i++) {

            ItemStack playerStack = armourList[i];
            ItemStack storedStack = storage.stacks[i];

            if (!InventoryUtil.itemMatches(playerStack, storedStack, false)) {

                if (playerStack != null && playerStack.getItem() instanceof IModularItem) {
                    ((IModularItem) playerStack.getItem()).onArmourEquip(player.worldObj, player, playerStack);
                }

                if (storedStack != null && storedStack.getItem() instanceof IModularItem) {
                    ((IModularItem) storedStack.getItem()).onArmourDequip(player.worldObj, player, storedStack);
                }

            }

        }

        storage.update(armourList);
    }

    @SubscribeEvent
    public void onPlayerJoinWorld(PlayerEvent.PlayerLoggedInEvent e) {
        for (ItemStack stack : e.player.inventory.armorInventory) {
            if (stack != null && stack.getItem() instanceof IModularItem) {

                NBTHelper.createDefaultStackTag(stack);

                IModularItem armour = (IModularItem) stack.getItem();

                for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {
                    if (upgrade == null) {
                        continue;
                    }

                    upgrade.onEquip(e.player.worldObj, e.player, stack, ArmourSlot.getArmourSlot(armour.getSlot()));
                }

            }
        }

        if (MiscUtil.isServer(e.player)) {

            ArmourPacket packet = ArmourPacket.getPacket(PacketTypes.RECIPESYNC);

            packet.addInt(UpgradeRegistry.getRecipeList().size());

            for (Recipe recipe : UpgradeRegistry.getRecipeList()) {

                for (Object obj : recipe.getInput()) {
                    if (obj instanceof String) {
                        packet.addByte(1);
                        packet.addString((String) obj);
                    } else if (obj instanceof ItemStack) {
                        packet.addByte(2);
                        packet.addItemStack((ItemStack) obj);
                    } else if (obj == null) {
                        packet.addByte(3);
                    }
                }

                packet.addString(recipe.getRecipeOutput().getBaseName());
                packet.addInt(recipe.width);
                packet.addInt(recipe.height);

            }

            PacketHandler.sendTo(packet, e.player);
        }
    }

}
