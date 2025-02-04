package net.tigereye.passivecharms.items.contingency_triggers;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class InjuryTrigger extends Item implements ContingencyCharmTrigger{

    private static final float CRITICAL_HEALTH_THRESHOLD = 0.2f;
    public InjuryTrigger(){
        super(new Settings().maxCount(1).group(ItemGroup.MISC));
    }

    public boolean TriggerConditionMet(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack trigger)
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
