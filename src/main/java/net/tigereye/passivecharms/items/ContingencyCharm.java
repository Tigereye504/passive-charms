package net.tigereye.passivecharms.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.tigereye.passivecharms.items.contingency_reactors.ContingencyCharmReaction;
import net.tigereye.passivecharms.items.contingency_triggers.ContingencyCharmTrigger;

import java.util.List;


public class ContingencyCharm extends Item{
    
    public static final int DURABILITY = 120;
    private static final String TRIGGER_ITEM_KEY = "TriggerItem";
    private static final String REACTOR_ITEM_KEY = "ReactionItem";

	public ContingencyCharm() {
		super(new Item.Settings().maxCount(1).maxDamage(DURABILITY));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient()){
            ItemStack triggerStack = loadTriggerFromNBT(stack);
            ItemStack reactionStack = loadReactionFromNBT(stack);
            if(triggerStack != null && reactionStack != null){
                if(triggerStack.getItem() instanceof ContingencyCharmTrigger trigger
                && reactionStack.getItem() instanceof ContingencyCharmReaction reaction){
                    if(trigger.TriggerConditionMet(world, entity, triggerStack)){
                        reaction.React(stack, world, entity, slot, selected, reactionStack);
                    }
                }
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if(stack != null) {
            ItemStack trigger = loadTriggerFromNBT(stack);
            ItemStack reaction = loadReactionFromNBT(stack);
            if (trigger != null) {
                user.getInventory().insertStack(trigger);
            }
            if (reaction != null) {
                reaction.setDamage(stack.getDamage());
                user.getInventory().insertStack(reaction);
            }
            stack.decrement(1);
            return TypedActionResult.consume(user.getStackInHand(hand));
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    public static ItemStack loadReactionFromNBT(ItemStack stack){
        NbtCompound reactorNbt = stack.getSubNbt(REACTOR_ITEM_KEY);
        if(reactorNbt != null) {
            ItemStack temp = ItemStack.fromNbt(stack.getSubNbt(REACTOR_ITEM_KEY));
            if (temp.getItem() instanceof ContingencyCharmReaction) {
                return temp;
            }
        }
        return null;
    }

    public static ItemStack loadTriggerFromNBT(ItemStack stack){
        NbtCompound triggerNbt = stack.getSubNbt(TRIGGER_ITEM_KEY);
        if(triggerNbt != null) {
            ItemStack temp = ItemStack.fromNbt(stack.getSubNbt(TRIGGER_ITEM_KEY));
            if (temp.getItem() instanceof ContingencyCharmTrigger) {
                return temp;
            }
        }
        return null;
    }

    public static void saveContingencyToNBT(ItemStack stack, ItemStack trigger, ItemStack reactor){
        stack.setSubNbt(TRIGGER_ITEM_KEY, trigger.writeNbt(new NbtCompound()));
        stack.setSubNbt(REACTOR_ITEM_KEY, reactor.writeNbt(new NbtCompound()));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        ItemStack trigger = loadTriggerFromNBT(itemStack);
        ItemStack reaction = loadReactionFromNBT(itemStack);
        appendComponentNameAndNestedTooltip(trigger,world,tooltip,tooltipContext,0);
        appendComponentNameAndNestedTooltip(reaction,world,tooltip,tooltipContext,0);
        tooltip.add(Text.translatable("item.passivecharms.contingency_charm.tooltip_instructions"));
    }

    public static void appendComponentNameAndNestedTooltip (ItemStack component, World world, List<Text> tooltip, TooltipContext context, int depth){
        if(component != null){
            tooltip.add(Text.literal(" ".repeat(depth)).append(
                    component.getName()));
            Item triggerItem = component.getItem();
            if(triggerItem instanceof TooltipNester tooltipNester){
                tooltipNester.appendNestedTooltip(component,world,tooltip,context,depth+1);
            }
        }
        else{
            tooltip.add(Text.literal(" ".repeat(depth)).append(
                    Text.translatable("item.passivecharms.contingency_charm.tooltip_empty")));
        }
    }
}