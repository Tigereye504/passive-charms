package net.tigereye.passivecharms.items.contingency_triggers;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class OblivionTrigger extends Item implements ContingencyCharmTrigger{
    public OblivionTrigger(){
        super(new Settings().maxCount(1));
    }

    public boolean TriggerConditionMet(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack trigger)
    {
        return (entity.getPos().y < -64);
    }
}
