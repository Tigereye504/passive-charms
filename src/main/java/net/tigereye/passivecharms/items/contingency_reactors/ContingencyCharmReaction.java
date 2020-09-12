package net.tigereye.passivecharms.items.contingency_reactors;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ContingencyCharmReaction extends Item{

    public ContingencyCharmReaction(Settings arg0){
        super(arg0);
    }

    public abstract void React(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack Reactant);
}
