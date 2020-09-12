package net.tigereye.passivecharms.items.contingency_triggers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Injury extends ContingencyCharmTrigger{

    private static final float CRITICAL_HEALTH_THRESHOLD = 0.2f;
    public Injury(){
        super(new Settings().maxCount(1).group(ItemGroup.MISC));
    }

    public boolean TriggerConditionMet(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack trigger)
    {
        if(entity instanceof LivingEntity) {
            return (((LivingEntity)entity).getHealth() <= ((LivingEntity)entity).getMaxHealth()*CRITICAL_HEALTH_THRESHOLD);
        }
        return false;
    }
}
