package net.tigereye.passivecharms.items.contingency_reactors.deprecated;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.tigereye.passivecharms.items.ContingencyCharm;
import net.tigereye.passivecharms.items.contingency_reactors.ContingencyCharmReaction;

public class RestorationReactor extends Item implements ContingencyCharmReaction {
    private static final int COST = ContingencyCharm.DURABILITY/4;
    private static final float HEALING_AMOUNT = 8.0f;
    public RestorationReactor(){
        super(new Settings().maxCount(1).group(ItemGroup.MISC));
    }

    public boolean React(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack Reactant){
        if(stack.getMaxDamage()-stack.getDamage() >= COST
                && entity instanceof LivingEntity lEntity
                && lEntity.getMaxHealth() > lEntity.getHealth())
        {
            stack.damage(COST, lEntity.getRandom(), entity instanceof ServerPlayerEntity sEntity ? sEntity : null);
            lEntity.heal(8.0f);
            return true;
        }
        return false;
    }
}
