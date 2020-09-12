package net.tigereye.passivecharms.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.tigereye.passivecharms.items.contingency_reactors.ContingencyCharmReaction;
import net.tigereye.passivecharms.items.contingency_triggers.ContingencyCharmTrigger;
import net.tigereye.passivecharms.registration.PC_Items;
import net.minecraft.entity.Entity;

import java.util.List;


public class ContingencyCharm extends Item{
    
    private static final int DURABILITY = 1000;
    //ContingencyCharmTrigger trigger = PC_Items.IMMOLATION_TRIGGER;
    //ContingencyCharmReaction reaction = PC_Items.FLAMEWARD_REACTOR;

	public ContingencyCharm() {
		super(new Item.Settings().maxCount(1).group(ItemGroup.TOOLS).maxDamage(DURABILITY));
    }
    /*
    public ContingencyCharm(ContingencyCharmTrigger trigger, ContingencyCharmReaction reaction) {
        super(new Item.Settings().maxCount(1).group(ItemGroup.TOOLS).maxDamage(DURABILITY));
        this.trigger = trigger;
        this.reaction = reaction;
	}
    */
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient()){
            ItemStack trigger = loadTriggerFromNBT(stack);
            ItemStack reaction = loadReactionFromNBT(stack);
            if(trigger != null && reaction != null){
                if(((ContingencyCharmTrigger)trigger.getItem()).TriggerConditionMet(stack, world, entity, slot, selected, trigger))
                {
                    ((ContingencyCharmReaction)reaction.getItem()).React(stack, world, entity, slot, selected, reaction);
                }
            }
        }
    }
    /*
    private boolean loadContingencyFromNBT(ItemStack stack){
        boolean ret = true;
        Item temp = ItemStack.fromTag(stack.getSubTag("TriggerItem")).getItem();
        if(temp instanceof ContingencyCharmTrigger){
            trigger = (ContingencyCharmTrigger)temp;
        }
        else{ret = false;}
        temp = ItemStack.fromTag(stack.getSubTag("ReactionItem")).getItem();
        if(temp instanceof ContingencyCharmReaction){
            reaction = (ContingencyCharmReaction)temp;
        }
        else{ret = false;}
        return ret;
    }
    */
    public static ItemStack loadReactionFromNBT(ItemStack stack){
        ItemStack temp = ItemStack.fromTag(stack.getSubTag("ReactionItem"));
        if(temp.getItem() instanceof ContingencyCharmReaction){
            return temp;
        }
        return null;
    }

    public static ItemStack loadTriggerFromNBT(ItemStack stack){
        ItemStack temp = ItemStack.fromTag(stack.getSubTag("TriggerItem"));
        if(temp.getItem() instanceof ContingencyCharmTrigger){
            return temp;
        }
        return null;
    }

    public static void saveContingencyToNBT(ItemStack stack, ItemStack trigger, ItemStack reactor){
        stack.putSubTag("TriggerItem", trigger.toTag(new CompoundTag()));
        stack.putSubTag("ReactionItem", reactor.toTag(new CompoundTag()));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        ItemStack trigger = loadTriggerFromNBT(itemStack);
        ItemStack reaction = loadReactionFromNBT(itemStack);
        TranslatableText triggerName = new TranslatableText("item.passivecharms.contingency_charm.tooltip_empty");
        TranslatableText reactionName = new TranslatableText("item.passivecharms.contingency_charm.tooltip_empty");
        if(trigger != null){
            triggerName = new TranslatableText(trigger.getTranslationKey());
        }
        if(reaction != null){
            reactionName = new TranslatableText(reaction.getTranslationKey());
        }
        tooltip.add(triggerName);
        tooltip.add(reactionName);

    }

}