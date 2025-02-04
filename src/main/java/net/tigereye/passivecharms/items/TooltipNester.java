package net.tigereye.passivecharms.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public interface TooltipNester {
    void appendNestedTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext, int depth);
}
