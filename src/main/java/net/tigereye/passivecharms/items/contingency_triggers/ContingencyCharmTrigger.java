package net.tigereye.passivecharms.items.contingency_triggers;

import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class ContingencyCharmTrigger extends Item{

    public ContingencyCharmTrigger(Settings arg0){
        super(arg0);
    }
    public abstract boolean TriggerConditionMet(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack trigger);
}
