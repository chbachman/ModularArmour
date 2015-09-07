package chbachman.armour.items.armour.logic;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import chbachman.api.item.IModularItem;
import chbachman.armour.ModularArmour;

public class LPUpgradeLogic extends UpgradeLogicAdv {

    float cost;

    public LPUpgradeLogic(IModularItem item) {
        super(item);
        cost = ModularArmour.config.get("Conversions", "1LP = ?RF", 1);
    }

    @Override
    public void addInformation(List<String> list, ItemStack stack) {
        super.addInformation(list, stack);

        list.add("Current Owner: " + (SoulNetworkHandler.getOwnerName(stack) == null ? "None" : SoulNetworkHandler.getOwnerName(stack)));
    }

    @Override
    public void onArmourEquip(World world, EntityPlayer player, ItemStack stack) {
        SoulNetworkHandler.checkAndSetItemPlayer(stack, player);
        super.onArmourEquip(world, player, stack);
    }

    @Override
    public void healArmour(ItemStack stack, int toHeal) {
        String name = SoulNetworkHandler.getOwnerName(stack);
        EntityPlayer player = SoulNetworkHandler.getPlayerForUsername(name);

        if (player == null) {
            return;
        }

        if (!player.capabilities.isCreativeMode) {
            SoulNetworkHandler.addCurrentEssenceToMaximum(name, (int) (toHeal * cost), SoulNetworkHandler.getMaximumForOrbTier(SoulNetworkHandler.getCurrentMaxOrb(name)));
        }
    }

    @Override
    public void damageArmour(ItemStack stack, int damage) {

        EntityPlayer player = SoulNetworkHandler.getPlayerForUsername(SoulNetworkHandler.getOwnerName(stack));

        if (player == null) {
            return;
        }

        System.out.println("Draining: " + damage * cost);

        if (!player.capabilities.isCreativeMode) {
            SoulNetworkHandler.syphonAndDamageFromNetwork(stack, player, (int) (damage * cost));
        }
    }

}
