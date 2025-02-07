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

public class LightInjuryTrigger extends Item implements ContingencyCharmTrigger{

    private static final float LIGHT_INJURY_THRESHOLD = 0.8f;
    public LightInjuryTrigger(){
        super(PCItems.TRIGGER_SETTINGS);    }

    public boolean TriggerConditionMet(World world, Entity entity, ItemStack trigger)
    {
        if(entity instanceof LivingEntity) {
            return (((LivingEntity)entity).getHealth() <= ((LivingEntity)entity).getMaxHealth()*LIGHT_INJURY_THRESHOLD);
        }
        return false;
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.passivecharms.contingency_charm_trigger_light_injury.tooltip.description"));
    }
}
