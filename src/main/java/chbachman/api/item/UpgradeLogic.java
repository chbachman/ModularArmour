package chbachman.api.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;

import org.lwjgl.input.Keyboard;

import chbachman.api.nbt.helper.NBTBoolean;
import chbachman.api.nbt.helper.NBTHelper;
import chbachman.api.upgrade.IArmourUpgrade;
import chbachman.api.upgrade.IUpgrade;
import chbachman.api.util.ArmourSlot;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class UpgradeLogic implements ArmourLogic {

    protected final IModularItem item;

    protected final NBTBoolean hasOpened;

    public UpgradeLogic(IModularItem item) {
        this.item = item;
        this.hasOpened = new NBTBoolean("hasOpened", false);
    }

    // Item

    /**
     * Adds the energy and upgrade lines to the tooltip.
     * 
     * @param list
     *            - The tooltip list
     * @param armour
     *            - The ItemStack of the armour;
     */
    @Override
    public void addInformation(List<String> list, ItemStack stack) {
        NBTHelper.createDefaultStackTag(stack);

        if (!hasOpened.get(stack)) {
            list.add(StatCollector.translateToLocal("info.chbachman.openme"));
        }

        if (!(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) && NBTHelper.getNBTUpgradeList(stack.stackTagCompound).size() != 0) {
            list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("info.cofh.hold") + " " + EnumChatFormatting.YELLOW + EnumChatFormatting.ITALIC + StatCollector.translateToLocal("info.chbachman.control") + " " + EnumChatFormatting.RESET + EnumChatFormatting.GRAY + StatCollector.translateToLocal("info.chbachman.upgradeList") + EnumChatFormatting.RESET);
        } else if (NBTHelper.getNBTUpgradeList(stack.stackTagCompound).size() != 0) {
            for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {

                list.add(StatCollector.translateToLocal(upgrade.getName()));

            }
        }
    }

    /**
     * Called on right click.
     * 
     * @param stack
     * @param world
     * @param player
     * @return stack passed in.
     */
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        this.hasOpened.set(stack, true);
        return stack;
    }

    /**
     * Get the display damage for the ItemStack.
     * 
     * @param stack
     * @return
     */
    @Override
    public int getDisplayDamage(ItemStack stack) {
        return 0;
    }

    /**
     * Get the max damage for the ItemStack
     * 
     * @param stack
     * @return
     */
    @Override
    public int getMaxDamage(ItemStack stack) {
        return Short.MAX_VALUE;
    }

    /**
     * Get whether the stack is damaged.
     * 
     * @param stack
     * @return
     */
    @Override
    public boolean isDamaged(ItemStack stack) {
        return false;
    }

    /**
     * Get whether the stack is repairable in an anvil.
     * 
     * @param itemToRepair
     * @param stack
     * @return
     */
    @Override
    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack) {
        return false;
    }

    /**
     * Get the Armour Texture for the upgrades.
     * 
     * @param stack
     * @param entity
     * @param slot
     * @param type
     * @return The location of the texture.
     */
    @Override
    public String getArmourTexture(ItemStack stack, Entity entity, int slot, String type) {
        String texture = "Modular";
        String color = "";

        for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {

            if (!(upgrade instanceof IArmourUpgrade)) {
                continue;
            }

            String textureLocation = ((IArmourUpgrade) upgrade).getArmourTexture(stack, ArmourSlot.getArmourSlot(slot));

            if (textureLocation != null) {
                texture = textureLocation;

                if (textureLocation.startsWith("modulararmour:textures")) {
                    return textureLocation;
                }
            }

            String newColor = ((IArmourUpgrade) upgrade).getArmourColor(stack, ArmourSlot.getArmourSlot(slot));

            if (newColor != null) {
                color = newColor;
            }
        }

        return "modulararmour:textures/armour/" + texture + (slot == 2 ? "_2" : "_1") + color + ".png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmourModel(EntityLivingBase entityLiving, ItemStack stack, int armourSlot) {

        ModelBiped finalModel = null;

        for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack)) {

            if (!(upgrade instanceof IArmourUpgrade)) {
                continue;
            }

            ModelBiped model = ((IArmourUpgrade) upgrade).getArmourModel(entityLiving, stack, armourSlot);

            if (model == null) {
                continue;
            }

            if (finalModel == null) {
                finalModel = new ModelBiped() {

                    @Override
                    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
                        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
                        this.bipedRightArm.render(f5);
                        this.bipedHead.render(f5);
                        this.bipedBody.render(f5);
                        this.bipedLeftArm.render(f5);
                        this.bipedRightLeg.render(f5);
                        this.bipedLeftLeg.render(f5);
                    }

                };
            }

            mergeModels(finalModel.bipedBody, model.bipedBody);
            mergeModels(finalModel.bipedHead, model.bipedHead);
            mergeModels(finalModel.bipedRightArm, model.bipedRightArm);
            mergeModels(finalModel.bipedLeftArm, model.bipedLeftArm);
            mergeModels(finalModel.bipedRightLeg, model.bipedRightLeg);
            mergeModels(finalModel.bipedLeftLeg, model.bipedLeftLeg);
            mergeModels(finalModel.bipedCloak, model.bipedCloak);
            mergeModels(finalModel.bipedEars, model.bipedEars);
            mergeModels(finalModel.bipedHeadwear, model.bipedHeadwear);

        }

        return finalModel;

    }

    private void mergeModels(ModelRenderer parent, ModelRenderer holder) {

        if (holder.childModels == null) {
            return;
        }

        for (ModelRenderer child : (ArrayList<ModelRenderer>) holder.childModels) {
            parent.addChild(child);
        }
    }

    /**
     * Called every tick.
     * 
     * @param world
     * @param player
     * @param stack
     */
    @Override
    public void onArmourTick(World world, EntityPlayer player, ItemStack stack) {
        int energy = 0;

        Iterator<IUpgrade> iterator = NBTHelper.getNBTUpgradeList(stack).iterator();
        while (iterator.hasNext()) {
            IUpgrade upgrade = iterator.next();

            if (upgrade == null) {
                iterator.remove();
                continue;
            }

            energy += upgrade.onTick(world, player, stack, ArmourSlot.getArmourSlot(this.item.getSlot()));
        }

        if (energy > 0) {
            this.damageArmour(stack, energy);
        } else {
            this.healArmour(stack, -energy);
        }
    }

    /**
     * Called on Armour Equip.
     * 
     * @param world
     * @param player
     * @param stack
     */
    @Override
    public void onArmourEquip(World world, EntityPlayer player, ItemStack stack) {
        Iterator<IUpgrade> iterator = NBTHelper.getNBTUpgradeList(stack).iterator();
        while (iterator.hasNext()) {
            IUpgrade upgrade = iterator.next();

            if (upgrade == null) {
                iterator.remove();
                continue;
            }

            upgrade.onEquip(world, player, stack, ArmourSlot.getArmourSlot(this.item.getSlot()));
        }
    }

    /**
     * Called on Armour Dequip.
     * 
     * @param world
     * @param player
     * @param stack
     */
    @Override
    public void onArmourDequip(World world, EntityPlayer player, ItemStack stack) {
        Iterator<IUpgrade> iterator = NBTHelper.getNBTUpgradeList(stack).iterator();
        while (iterator.hasNext()) {
            IUpgrade upgrade = iterator.next();

            if (upgrade == null) {
                iterator.remove();
                continue;
            }

            upgrade.onDequip(world, player, stack, ArmourSlot.getArmourSlot(this.item.getSlot()));
        }
    }

    /**
     * Get the properties for the armour.
     * 
     * @param player
     * @param stack
     * @param source
     * @param damage
     * @param slot
     * @return The properties detailing the reaction on hit.
     */
    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack stack, DamageSource source, double damage, int slot) {

        ArmorProperties output = new ArmorProperties(0, 0, 0);

        for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {
            ArmorProperties prop = null;

            if (upgrade instanceof IArmourUpgrade) {
                prop = ((IArmourUpgrade) upgrade).getProperties(player, stack, source, damage, ArmourSlot.getArmourSlot(slot));
            }

            if (prop == null) {
                continue;
            }

            if (prop.Priority > output.Priority) {
                output = prop;
            } else if (prop.Priority == output.Priority && prop.AbsorbRatio > output.AbsorbRatio) {
                output = prop;
            }

        }

        return new ArmorProperties(output.Priority, output.AbsorbRatio, Integer.MAX_VALUE);
    }

    /**
     * Get the Armour Display, in half chestplates for the given stack.
     * 
     * @param player
     * @param stack
     * @param slot
     * @return
     */
    @Override
    public int getArmourDisplay(EntityPlayer player, ItemStack stack, int slot) {
        int sum = 0;
        for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {
            sum += upgrade instanceof IArmourUpgrade ? ((IArmourUpgrade) upgrade).getArmourDisplay(player, stack, ArmourSlot.getArmourSlot(slot)) : 0;
        }
        return sum;
    }

}
