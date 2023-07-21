package net.tigereye.passivecharms.items.contingency_reactors;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ContingencyCharmReaction{
    boolean React(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack Reactant);
}
