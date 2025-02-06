package net.tigereye.passivecharms.items.contingency_triggers;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ContingencyCharmTrigger{
    boolean TriggerConditionMet(World world, Entity entity, ItemStack trigger);
}
