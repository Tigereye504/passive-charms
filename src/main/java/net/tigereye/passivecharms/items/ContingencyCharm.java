package net.tigereye.passivecharms.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.tigereye.passivecharms.items.contingency_reactors.ContingencyCharmReaction;
import net.tigereye.passivecharms.items.contingency_triggers.ContingencyCharmTrigger;

import java.util.List;


public class ContingencyCharm extends Item{
    
    public static final int DURABILITY = 120;
    private static final String TRIGGERED_KEY = "Triggered";
    private static final String TRIGGER_ITEM_KEY = "TriggerItem";
    private static final String REACTOR_ITEM_KEY = "ReactionItem";

	public ContingencyCharm() {
		super(new Item.Settings().maxCount(1).group(ItemGroup.TOOLS).maxDamage(DURABILITY));
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient()){
            ItemStack trigger = loadTriggerFromNBT(stack);
            ItemStack reaction = loadReactionFromNBT(stack);
            NbtCompound nbt = stack.getOrCreateNbt();
            if(trigger != null && reaction != null){
                if(((ContingencyCharmTrigger)trigger.getItem()).TriggerConditionMet(stack, world, entity, slot, selected, trigger)){
                    if(!nbt.contains(TRIGGERED_KEY)) {
                        if (((ContingencyCharmReaction) reaction.getItem()).React(stack, world, entity, slot, selected, reaction)) {
                            nbt.putBoolean(TRIGGERED_KEY, true);
                        }
                    }
                }
                else {
                    nbt.remove(TRIGGERED_KEY);
                }
            }
        }
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
        Text triggerName;
        Text reactionName;
        if(trigger != null){
            triggerName = trigger.getName();
        }
        else{
            triggerName = Text.translatable("item.passivecharms.contingency_charm.tooltip_empty");
        }
        if(reaction != null){
            reactionName = reaction.getName();
        }
        else{
            reactionName = Text.translatable("item.passivecharms.contingency_charm.tooltip_empty");
        }
        tooltip.add(triggerName);
        tooltip.add(reactionName);

    }

}