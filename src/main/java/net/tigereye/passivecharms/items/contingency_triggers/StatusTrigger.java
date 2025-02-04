package net.tigereye.passivecharms.items.contingency_triggers;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class StatusTrigger extends Item implements ContingencyCharmTrigger{
    public StatusTrigger(){
        super(new Settings().maxCount(1).group(ItemGroup.MISC));
    }

    @Override
    public Text getName(ItemStack stack) {
        List<StatusEffectInstance> statusEffects = getStatusEffects(stack);
        if(!statusEffects.isEmpty()){
            String adder = statusEffects.size() > 1 ? "+"+(statusEffects.size()-1)+" " : " ";
            return Text.literal(statusEffects.get(0).getEffectType().getName().getString() + adder + super.getName(stack).getString());
        }
        return Text.literal(Text.translatable("item.passivecharms.contingency_charm.tooltip_empty").getString()+" "+super.getName(stack).getString());
    }

    public boolean TriggerConditionMet(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack trigger) {
        List<StatusEffectInstance> triggeringEffectList = getStatusEffects(trigger);
        if (entity instanceof LivingEntity lEntity){
            for (StatusEffectInstance effectInstance : triggeringEffectList) {
                StatusEffect effect = effectInstance.getEffectType();
                if(lEntity.hasStatusEffect(effect)){return true;}
            }
        }
        return false;
    }

    public static List<StatusEffectInstance> getStatusEffects(ItemStack item){
        NbtCompound tag = item.getOrCreateNbt();
        NbtList NbtList;
        if (!tag.contains("CustomPotionEffects", 9)) {
            return new ArrayList<>();
        }
        else{
            NbtList = tag.getList("CustomPotionEffects",10);
            List<StatusEffectInstance> list = new ArrayList<>();
            for(int i = 0; i < NbtList.size(); ++i) {
                NbtCompound NbtCompound = NbtList.getCompound(i);
                StatusEffectInstance statusEffectInstance = StatusEffectInstance.fromNbt(NbtCompound);
                if (statusEffectInstance != null) {
                    list.add(statusEffectInstance);
                }
            }
            return list;
        }
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.passivecharms.contingency_charm_trigger_status.tooltip.description"));
        appendNestedTooltip(itemStack,world,tooltip,tooltipContext,1);
    }

    public void appendNestedTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext, int depth){
        List<StatusEffectInstance> statusEffects = getStatusEffects(itemStack);

        if(statusEffects.size() > 1) {
            for (StatusEffectInstance effect : statusEffects) {
                tooltip.add(Text.literal(" ".repeat(depth)).append(
                        effect.getEffectType().getName()));
            }
        }
    }

    public static int getColor(ItemStack stack, int tintIndex) {
        if(tintIndex == 0){
            return -1;
        }
        List<StatusEffectInstance> effectList = getStatusEffects(stack);
        if(!effectList.isEmpty()){
            int outColor = 0;
            for (StatusEffectInstance effect:
                    effectList) {
                outColor += effect.getEffectType().getColor();
            }
            return outColor / effectList.size();
        }
        return -1;
    }
}
