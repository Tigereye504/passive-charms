package net.tigereye.passivecharms.items.contingency_triggers;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.tigereye.passivecharms.registration.PCItems;

import java.util.List;

public class InjuryTrigger extends Item implements ContingencyCharmTrigger{

    private static final float CRITICAL_HEALTH_THRESHOLD = 0.2f;
    public InjuryTrigger(){
        super(PCItems.TRIGGER_SETTINGS);    }

    public boolean TriggerConditionMet(World world, Entity entity, ItemStack trigger)
    {
        if(entity instanceof LivingEntity) {
            return (((LivingEntity)entity).getHealth() <= ((LivingEntity)entity).getMaxHealth()*CRITICAL_HEALTH_THRESHOLD);
        }
        return false;
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.passivecharms.contingency_charm_trigger_injury.tooltip.description"));
    }
}
