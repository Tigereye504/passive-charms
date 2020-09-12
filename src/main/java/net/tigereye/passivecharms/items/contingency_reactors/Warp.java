package net.tigereye.passivecharms.items.contingency_reactors;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.Random;

public class Warp extends ContingencyCharmReaction{
    private static final int COST = 999;
    public Warp(){
        super(new Settings().maxCount(1).group(ItemGroup.MISC));
    }

    public void React(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack Reactant){
        int x = 0;
        int y = 64;
        int z = 0;
        RegistryKey<World> dim = World.OVERWORLD;
        if(stack.getMaxDamage()-stack.getDamage() > COST){
            if(entity instanceof LivingEntity){
                if(!Reactant.hasTag()){
                    Reactant.setTag(new CompoundTag());
                }
                stack.damage(COST, new Random(), entity instanceof ServerPlayerEntity ? (ServerPlayerEntity)entity : null);
                if(Reactant.getTag().contains("WarpX")){x = Reactant.getTag().getInt("WarpX");}
                if(Reactant.getTag().contains("WarpY")){y = Reactant.getTag().getInt("WarpY");}
                if(Reactant.getTag().contains("WarpZ")){z = Reactant.getTag().getInt("WarpZ");}
                if(Reactant.getTag().contains("WarpWorld")) {
                    dim = RegistryKey.of(Registry.DIMENSION,
                            new Identifier(Reactant.getTag().getString("WarpWorld")));
                }
                if(entity.getEntityWorld().getRegistryKey() != dim) {
                    entity.moveToWorld(entity.getServer().getWorld(dim));
                }
                entity.teleport(x,y,z);

            }
        }
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient()) {
            if(!stack.hasTag()){
                stack.setTag(new CompoundTag());
            }
            if(!stack.getTag().contains("WarpX")){
                stack.getTag().putInt("WarpX",entity.getBlockPos().getX());
            }
            if(!stack.getTag().contains("WarpY")){
                stack.getTag().putInt("WarpY",entity.getBlockPos().getY());
            }
            if(!stack.getTag().contains("WarpZ")){
                stack.getTag().putInt("WarpZ",entity.getBlockPos().getZ());
            }
            if(!stack.getTag().contains("WarpWorld")){
                stack.getTag().putString("WarpWorld",world.getRegistryKey().getValue().toString());
            }
        }
    }
}
