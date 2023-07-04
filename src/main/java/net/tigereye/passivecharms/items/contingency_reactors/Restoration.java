package net.tigereye.passivecharms.items.contingency_reactors;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class Restoration extends ContingencyCharmReaction{
    private static final int COST = 333;
    public Restoration(){
        super(new Settings().maxCount(1).group(ItemGroup.MISC));
    }

    public void React(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack Reactant){
        if(stack.getMaxDamage()-stack.getDamage() > COST){
            if(entity instanceof LivingEntity lEntity){
                stack.damage(COST, lEntity.getRandom(), entity instanceof ServerPlayerEntity sEntity ? sEntity : null);
                lEntity.heal(8.0f);
            }
        }
    }
}
