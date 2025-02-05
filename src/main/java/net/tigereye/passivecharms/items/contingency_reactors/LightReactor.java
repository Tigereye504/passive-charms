package net.tigereye.passivecharms.items.contingency_reactors;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.tigereye.passivecharms.items.ContingencyCharm;

import java.util.List;

public class LightReactor extends Item implements ContingencyCharmReaction{
    private static final int COST = 1;
    public LightReactor(){
        super(new Settings().maxCount(1).group(ItemGroup.MISC).maxDamage(ContingencyCharm.DURABILITY));
    }

    public boolean React(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack Reactant){
        if(stack.getMaxDamage()-stack.getDamage() >= COST
            && entity instanceof LivingEntity lEntity)
        {
            BlockState state = world.getBlockState(entity.getBlockPos());
            if(state.isAir()){
                if(Blocks.TORCH.getDefaultState().canPlaceAt(world,entity.getBlockPos())){
                    world.setBlockState(entity.getBlockPos(),Blocks.TORCH.getDefaultState());
                    stack.damage(COST, lEntity.getRandom(), entity instanceof ServerPlayerEntity sEntity ? sEntity : null);
                    return true;
                }
            }
        }
        return false;
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.passivecharms.contingency_charm_reaction_light.tooltip.description"));
    }
}
