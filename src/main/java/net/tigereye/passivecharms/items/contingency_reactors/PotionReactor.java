package net.tigereye.passivecharms.items.contingency_reactors;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.tigereye.passivecharms.items.ContingencyCharm;
import net.tigereye.passivecharms.registration.PCItems;

import java.util.ArrayList;
import java.util.List;

public class PotionReactor extends Item implements ContingencyCharmReaction{
    private static final int COST = ContingencyCharm.DURABILITY/4;

    public PotionReactor(){
        super(PCItems.REACTOR_SETTINGS);
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

    public boolean React(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack reactant){
        if(stack.getMaxDamage()-stack.getDamage() >= COST
            && entity instanceof LivingEntity lEntity)
        {
            List<StatusEffectInstance> effectList = getStatusEffects(reactant);
            if(!effectList.isEmpty()){
                boolean appliedEffect = false;
                for(StatusEffectInstance effect : effectList){
                    if(!lEntity.hasStatusEffect(effect.getEffectType())){
                        appliedEffect = true;
                        lEntity.addStatusEffect(effect);
                    }
                }
                if(appliedEffect) {
                    stack.damage(COST, lEntity.getRandom(), entity instanceof ServerPlayerEntity sEntity ? sEntity : null);
                    return true;
                }
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
        tooltip.add(Text.translatable("item.passivecharms.contingency_charm_reaction_potion.tooltip.description"));
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
