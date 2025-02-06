package net.tigereye.passivecharms.items.contingency_triggers;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.tigereye.passivecharms.items.ContingencyCharm;
import net.tigereye.passivecharms.items.TooltipNester;

import java.util.List;

public class NotTrigger extends Item implements ContingencyCharmTrigger, TooltipNester {

    private static final String TRIGGER_COMPONENT_KEY = "TriggerComponent";

    public NotTrigger(){
        super(new Settings().maxCount(1).group(ItemGroup.MISC));
    }

    public boolean TriggerConditionMet(World world, Entity entity, ItemStack trigger)
    {
        ItemStack triggerComponent = loadTriggerFromNBT(trigger);
        if(triggerComponent != null && triggerComponent.getItem() instanceof ContingencyCharmTrigger ccTrigger) {
            return !ccTrigger.TriggerConditionMet(world, entity, triggerComponent);
        }
        return false;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if(stack != null) {
            ItemStack trigger = loadTriggerFromNBT(stack);
            if (trigger != null) {
                user.getInventory().insertStack(trigger);
            }
            user.getInventory().insertStack(new ItemStack(Items.REDSTONE_TORCH,1));
            stack.decrement(1);
            return TypedActionResult.consume(user.getStackInHand(hand));
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.passivecharms.contingency_charm_trigger_not.tooltip.description"));
        appendNestedTooltip(itemStack,world,tooltip,tooltipContext,1);
        tooltip.add(Text.translatable("item.passivecharms.contingency_charm.tooltip_instructions"));
    }

    @Override
    public void appendNestedTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext, int depth) {
        ItemStack triggerComponent = loadTriggerFromNBT(itemStack);
        ContingencyCharm.appendComponentNameAndNestedTooltip(triggerComponent,world,tooltip,tooltipContext,depth);
    }

    public static ItemStack loadTriggerFromNBT(ItemStack stack){
        NbtCompound triggerNbt = stack.getSubNbt(TRIGGER_COMPONENT_KEY);
        if(triggerNbt != null) {
            ItemStack temp = ItemStack.fromNbt(stack.getSubNbt(TRIGGER_COMPONENT_KEY));
            if (temp.getItem() instanceof ContingencyCharmTrigger) {
                return temp;
            }
        }
        return null;
    }

    public static void saveTriggerComponentToNBT(ItemStack stack, ItemStack triggerComponent){
        stack.setSubNbt(TRIGGER_COMPONENT_KEY, triggerComponent.writeNbt(new NbtCompound()));
    }
}
