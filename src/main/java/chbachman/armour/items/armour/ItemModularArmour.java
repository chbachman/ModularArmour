package chbachman.armour.items.armour;

import ic2.api.item.IMetalArmor;

import java.util.List;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import thaumcraft.api.IGoggles;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.IRevealer;
import vazkii.botania.api.item.IPixieSpawner;
import chbachman.api.item.IModularItem;
import chbachman.armour.ModularArmour;
import chbachman.armour.items.armour.logic.UpgradeLogicAdv;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Interface;

@Optional.InterfaceList(value = {
        @Interface(iface = "thaumcraft.api.IGoggles", modid = "Thaumcraft"),
        @Interface(iface = "thaumcraft.api.IVisDiscountGear", modid = "Thaumcraft"),
        @Interface(iface = "thaumcraft.api.nodes.IRevealer", modid = "Thaumcraft"),
        @Interface(iface = "vazkii.botania.api.item.IPixieSpawner", modid = "Botania"),
        @Interface(iface = "ic2.api.item.IMetalArmor", modid = "IC2"),
})
public abstract class ItemModularArmour extends ItemArmor implements ISpecialArmor, IModularItem, IGoggles, IVisDiscountGear, IRevealer, IPixieSpawner, IMetalArmor {

    protected UpgradeLogicAdv holder;

    public ItemModularArmour(ArmorMaterial material, int type) {
        super(material, 0, type);
        this.setCreativeTab(ModularArmour.creativeTab);
    }

    // Item
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean check) {
        this.holder.addInformation(list, stack);
    }

    @Override
    public int getDisplayDamage(ItemStack stack) {
        return this.holder.getDisplayDamage(stack);
    }

    @Override
    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack) {
        return this.holder.getIsRepairable(itemToRepair, stack);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.holder.getMaxDamage(stack);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.uncommon;
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return this.holder.isDamaged(stack);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        this.holder.onArmourTick(world, player, stack);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        return this.holder.onItemRightClick(stack, world, player);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return holder.getArmourTexture(stack, entity, slot, type);
    }

    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack stack, int armourSlot) {
        return holder.getArmourModel(entityLiving, stack, armourSlot);
    }

    // ISpecialArmor
    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        this.holder.damageArmour(stack, damage);
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack stack, int slot) {
        return this.holder.getArmourDisplay(player, stack, slot);
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack stack, DamageSource source, double damage, int slot) {
        return this.holder.getProperties(player, stack, source, damage, slot);
    }

    // IModularItem
    @Override
    public int getSlot() {
        return this.armorType;
    }

    @Override
    public boolean isArmour() {
        return true;
    }

    @Override
    public void onArmourDequip(World world, EntityPlayer player, ItemStack stack) {
        this.holder.onArmourDequip(world, player, stack);
    }

    @Override
    public void onArmourEquip(World world, EntityPlayer player, ItemStack stack) {
        this.holder.onArmourEquip(world, player, stack);
    }

    @Override
    public void damageArmour(ItemStack stack, int damage) {
        this.holder.damageArmour(stack, damage);
    }

    @Override
    public void healArmour(ItemStack stack, int damage) {
        this.holder.healArmour(stack, damage);
    }

    @Override
    public UpgradeLogicAdv getLogic() {
        return this.holder;
    }

    @Override
    public Item getItem() {
        return this;
    }

    // IRevealer
    @Override
    @Optional.Method(modid = "Thaumcraft")
    public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {
        return this.holder.showNodes(itemstack, player);
    }

    // IVisDiscountGear
    @Override
    @Optional.Method(modid = "Thaumcraft")
    public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect) {
        return this.holder.getVisDiscount(stack, player, aspect);
    }

    // IGoggles
    @Override
    @Optional.Method(modid = "Thaumcraft")
    public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase player) {
        return this.holder.showIngamePopups(itemstack, player);
    }

    // IPixieSpawner
    @Override
    public float getPixieChance(ItemStack stack) {
        return this.holder.getPixieChance(stack);
    }

    // IMetalArmor
    @Override
    @Optional.Method(modid = "IC2")
    public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player) {
        return this.holder.isMetalArmor(itemstack, player);
    }

    @Override
    public int getSizeInventory(ItemStack container) {
        return 9;
    }

}
