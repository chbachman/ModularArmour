package chbachman.api.upgrade;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.api.item.IModularItem;
import chbachman.api.util.ArmourSlot;

/**
 * Interface for adding upgrades. Any armour that can hold these must call each
 * of these methods when appropriate.
 * 
 * @author chbachman
 *
 */
public interface IUpgrade {

    /**
     * Gets the UnLocalized information string
     * 
     * @return UnLocalized information string
     */
    String getInformation();

    /**
     * Get the UnLocalized Name of the Upgrade
     * 
     * @return UnLocalized Name of the Upgrade
     */
    String getName();

    /**
     * Get the base name of the upgrade. e.g. camelPack This name should be a
     * unique string that can be used for saving and load from NBT.
     * 
     * @return
     */
    String getBaseName();

    /**
     * Whether the upgrade is compatible with the given armour type.
     * 
     * @param armorType
     * @return
     */
    boolean isCompatible(IModularItem item, ItemStack stack, int armorType);

    /**
     * Gets the list of dependencies
     * 
     * @return list of Upgrade Dependencies.
     */
    IUpgrade[] getDependencies();

    /**
     * Called every tick, when Equipped from the Armour.
     * 
     * @param world
     * @param player
     * @param stack
     * @param armourSlot
     * @return energy to be subtracted from the armour, each tick.
     */
    int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot armourSlot);

    /**
     * Called when the Upgrade is added to the armour, usually used for editing
     * the VariableInt classes that the object contains.
     * 
     * @param armour
     * @param stack
     */
    void onUpgradeAddition(IModularItem armour, ItemStack stack);

    /**
     * Called on Armour Equip, also when Player joins world.
     * 
     * @param world
     * @param player
     * @param stack
     * @param armourSlot
     */
    void onEquip(World world, EntityPlayer player, ItemStack stack, ArmourSlot armourSlot);

    /**
     * Called when the armour is Dequipped.
     * 
     * @param world
     * @param player
     * @param stack
     * @param armourSlot
     */
    void onDequip(World world, EntityPlayer player, ItemStack stack, ArmourSlot armourSlot);

    /**
     * Register config options here. Called during init.
     */
    void registerConfigOptions();

}
