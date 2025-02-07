package net.tigereye.passivecharms.items.contingency_triggers;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.tigereye.passivecharms.registration.PCItems;

import java.util.List;

public class ImmolationTrigger extends Item implements ContingencyCharmTrigger{

    public ImmolationTrigger(){
        super(PCItems.TRIGGER_SETTINGS);    }

    public boolean TriggerConditionMet(World world, Entity entity, ItemStack trigger)
    {
        boolean canGetFireResistance = entity instanceof LivingEntity && (!((LivingEntity) entity).hasStatusEffect(StatusEffects.FIRE_RESISTANCE));
        return (entity.isOnFire() && canGetFireResistance);
    }


    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.passivecharms.contingency_charm_trigger_immolation.tooltip.description"));
    }
}
