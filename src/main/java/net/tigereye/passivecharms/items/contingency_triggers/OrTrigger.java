package net.tigereye.passivecharms.items.contingency_triggers;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.tigereye.passivecharms.items.ContingencyCharm;
import net.tigereye.passivecharms.items.TooltipNester;
import net.tigereye.passivecharms.registration.PCItems;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OrTrigger extends Item implements ContingencyCharmTrigger, TooltipNester {

    private static final String TRIGGER_COMPONENT_KEY = "TriggerComponent";
    private static final String TRIGGER_COMPONENT2_KEY = "TriggerComponent2";

    public OrTrigger(){
        super(PCItems.TRIGGER_SETTINGS);    }

    public boolean TriggerConditionMet(World world, Entity entity, ItemStack trigger)
    {
        ItemStack triggerComponent = loadTriggerFromNBT(trigger, TRIGGER_COMPONENT_KEY);
        ItemStack triggerComponent2 = loadTriggerFromNBT(trigger, TRIGGER_COMPONENT2_KEY);
        if(triggerComponent != null && triggerComponent.getItem() instanceof ContingencyCharmTrigger ccTrigger
        && triggerComponent2 != null && triggerComponent2.getItem() instanceof ContingencyCharmTrigger ccTrigger2) {
            return ccTrigger.TriggerConditionMet(world, entity, triggerComponent)
                    || ccTrigger2.TriggerConditionMet(world, entity, triggerComponent2);
        }
        return false;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if(stack != null) {
            ItemStack triggerComp1 = loadTriggerFromNBT(stack,TRIGGER_COMPONENT_KEY);
            if (triggerComp1 != null) {
                user.getInventory().insertStack(triggerComp1);
            }
            ItemStack triggerComp2 = loadTriggerFromNBT(stack,TRIGGER_COMPONENT2_KEY);
            if (triggerComp1 != null) {
                user.getInventory().insertStack(triggerComp2);
            }
            user.getInventory().insertStack(new ItemStack(Items.REDSTONE,1));
            stack.decrement(1);
            return TypedActionResult.consume(user.getStackInHand(hand));
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.passivecharms.contingency_charm_trigger_or.tooltip.description"));
        appendNestedTooltip(itemStack,world,tooltip,tooltipContext,1);
        tooltip.add(Text.translatable("item.passivecharms.contingency_charm.tooltip_instructions"));
    }

    @Override
    public void appendNestedTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext, int depth) {
        ItemStack triggerComponent1 = loadTriggerFromNBT(itemStack,TRIGGER_COMPONENT_KEY);
        ItemStack triggerComponent2 = loadTriggerFromNBT(itemStack,TRIGGER_COMPONENT2_KEY);
        ContingencyCharm.appendComponentNameAndNestedTooltip(triggerComponent1,world,tooltip,tooltipContext,depth);
        ContingencyCharm.appendComponentNameAndNestedTooltip(triggerComponent2,world,tooltip,tooltipContext,depth);
    }

    public static ItemStack loadTriggerFromNBT(ItemStack stack, String key){
        NbtCompound triggerNbt = stack.getSubNbt(key);
        if(triggerNbt != null) {
            ItemStack temp = ItemStack.fromNbt(stack.getSubNbt(key));
            if (temp.getItem() instanceof ContingencyCharmTrigger) {
                return temp;
            }
        }
        return null;
    }

    public static void saveTriggerComponentsToNBT(ItemStack stack, @NotNull ItemStack triggerComponent, @NotNull ItemStack triggerComponent2){
        stack.setSubNbt(TRIGGER_COMPONENT_KEY, triggerComponent.writeNbt(new NbtCompound()));
        stack.setSubNbt(TRIGGER_COMPONENT2_KEY, triggerComponent2.writeNbt(new NbtCompound()));
    }
}
