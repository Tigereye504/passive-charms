package net.tigereye.passivecharms.items.contingency_triggers;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.tigereye.passivecharms.items.TooltipNester;
import net.tigereye.passivecharms.registration.PCItems;

import java.util.List;

public class DarknessTrigger extends Item implements ContingencyCharmTrigger, TooltipNester {

    public DarknessTrigger(){
        super(PCItems.TRIGGER_SETTINGS);    }

    public boolean TriggerConditionMet(World world, Entity entity, ItemStack trigger)
    {
        return (world.getLightLevel(LightType.BLOCK,entity.getBlockPos()) <= getMaximumLightLevel(trigger));
    }

    public int getMaximumLightLevel(ItemStack stack){
        NbtCompound nbt = stack.getOrCreateNbt();
        return nbt.getInt("lightLevel");
    }
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.passivecharms.contingency_charm_trigger_darkness.tooltip.description"));
        appendNestedTooltip(itemStack,world,tooltip,tooltipContext,0);
    }

    public void appendNestedTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext, int depth){
        tooltip.add(Text.literal(" ".repeat(depth)).append(
                Text.translatable("item.passivecharms.contingency_charm_trigger_darkness.tooltip.lightLevel",getMaximumLightLevel(itemStack))));
    }
}
