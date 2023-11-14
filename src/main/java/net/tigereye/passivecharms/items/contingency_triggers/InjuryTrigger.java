package net.tigereye.passivecharms.items.contingency_triggers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class InjuryTrigger extends Item implements ContingencyCharmTrigger{

    private static final float CRITICAL_HEALTH_THRESHOLD = 0.2f;
    public InjuryTrigger(){
        super(new Settings().maxCount(1));
    }

    public boolean TriggerConditionMet(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack trigger)
    {
        if(entity instanceof LivingEntity) {
            return (((LivingEntity)entity).getHealth() <= ((LivingEntity)entity).getMaxHealth()*CRITICAL_HEALTH_THRESHOLD);
        }
        return false;
    }
}
