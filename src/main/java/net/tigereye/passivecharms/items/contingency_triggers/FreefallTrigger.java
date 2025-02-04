package net.tigereye.passivecharms.items.contingency_triggers;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class FreefallTrigger extends Item implements ContingencyCharmTrigger{
    public FreefallTrigger(){
        super(new Settings().maxCount(1).group(ItemGroup.MISC));
    }

    //The freefall trigger actives if the entity has fallen so far that impact would be lethal to it
    public boolean TriggerConditionMet(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack trigger)
    {
        float expectedFallDamage;
        float lethalFallDamage;
        //if the entity isn't alive, it can't die.
        if (entity instanceof LivingEntity) {
            expectedFallDamage = Math.max(entity.fallDistance - entity.getSafeFallDistance(), 0);
            lethalFallDamage = ((LivingEntity)entity).getAbsorptionAmount() + ((LivingEntity)entity).getHealth();
            //only bother applying defenses if the fall would be lethal without them
            if (expectedFallDamage >= lethalFallDamage) {
                expectedFallDamage = ApplyDefenses((LivingEntity) entity, expectedFallDamage);
            }
            //if the fall is still lethal, trigger the contingency
            return (expectedFallDamage >= lethalFallDamage);
        }
        return false;
    }

    private float ApplyDefenses(LivingEntity entity, float dmg){
        float factor;
        if(entity.hasStatusEffect(StatusEffects.RESISTANCE)){
            factor = Math.max((5-(entity.getStatusEffect(StatusEffects.RESISTANCE).getAmplifier() + 1))/5,0);
            dmg *= factor;
        }
        factor = EnchantmentHelper.getProtectionAmount(entity.getArmorItems(), DamageSource.FALL);
        if (factor > 0) {
            dmg = DamageUtil.getInflictedDamage(dmg, factor);
        }
        return dmg;
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.passivecharms.contingency_charm_trigger_freefall.tooltip.description"));
    }
}
