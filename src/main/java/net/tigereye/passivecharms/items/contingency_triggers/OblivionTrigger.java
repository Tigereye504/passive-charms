package net.tigereye.passivecharms.items.contingency_triggers;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class OblivionTrigger extends Item implements ContingencyCharmTrigger{
    public OblivionTrigger(){
        super(new Settings().maxCount(1).group(ItemGroup.MISC));
    }

    public boolean TriggerConditionMet(World world, Entity entity, ItemStack trigger)
    {
        return (entity.getPos().y < world.getBottomY());
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.passivecharms.contingency_charm_trigger_oblivion.tooltip.description"));
    }
}
