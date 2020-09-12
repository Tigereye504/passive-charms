package net.tigereye.passivecharms.items.contingency_triggers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LightInjury extends ContingencyCharmTrigger{

    private static final float LIGHT_INJURY_THRESHOLD = 0.8f;
    public LightInjury(){
        super(new Settings().maxCount(1).group(ItemGroup.MISC));
    }

    public boolean TriggerConditionMet(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack trigger)
    {
        if(entity instanceof LivingEntity) {
            return (((LivingEntity)entity).getHealth() <= ((LivingEntity)entity).getMaxHealth()*LIGHT_INJURY_THRESHOLD);
        }
        return false;
    }
}
