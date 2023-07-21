package net.tigereye.passivecharms.items.contingency_triggers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ImmolationTrigger extends Item implements ContingencyCharmTrigger{

    public ImmolationTrigger(){
        super(new Item.Settings().maxCount(1).group(ItemGroup.MISC));
    }

    public boolean TriggerConditionMet(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack trigger)
    {
        boolean canGetFireResistance = entity instanceof LivingEntity && (!((LivingEntity) entity).hasStatusEffect(StatusEffects.FIRE_RESISTANCE));
        return (entity.isOnFire() && canGetFireResistance);
    }
}
