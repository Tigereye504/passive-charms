package net.tigereye.passivecharms.items.contingency_triggers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Drowning extends ContingencyCharmTrigger{

    public Drowning(){
        super(new Settings().maxCount(1).group(ItemGroup.MISC));
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
