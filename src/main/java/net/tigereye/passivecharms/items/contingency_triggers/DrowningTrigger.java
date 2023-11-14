package net.tigereye.passivecharms.items.contingency_triggers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DrowningTrigger extends Item implements ContingencyCharmTrigger{

    public DrowningTrigger(){
        super(new Settings().maxCount(1));
    }

    public boolean TriggerConditionMet(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack trigger)
    {
        boolean canGetWaterBreathing = false;
        if(entity instanceof LivingEntity){
            canGetWaterBreathing = !((LivingEntity)entity).hasStatusEffect(StatusEffects.WATER_BREATHING);
        }
        return ((entity.getAir() <= 0) && canGetWaterBreathing);
    }
}
