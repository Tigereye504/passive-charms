package net.tigereye.passivecharms.items.contingency_triggers;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class ImmolationTrigger extends Item implements ContingencyCharmTrigger{

    public ImmolationTrigger(){
        super(new Item.Settings().maxCount(1).group(ItemGroup.MISC));
    }

    public boolean TriggerConditionMet(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack trigger)
    {
        boolean canGetFireResistance = entity instanceof LivingEntity && (!((LivingEntity) entity).hasStatusEffect(StatusEffects.FIRE_RESISTANCE));
        return (entity.isOnFire() && canGetFireResistance);
    }


    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.passivecharms.contingency_charm_trigger_immolation.tooltip.description"));
    }
}
