package net.tigereye.passivecharms.items.contingency_reactors;

import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class ContingencyCharmReaction extends Item{

    public ContingencyCharmReaction(Settings arg0){
        super(arg0);
    }

    public abstract void React(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack Reactant);
}
