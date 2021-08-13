package net.tigereye.passivecharms.items.contingency_reactors;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
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
                if(!Reactant.hasNbt()){
                    Reactant.setNbt(new NbtCompound());
                }
                stack.damage(COST, new Random(), entity instanceof ServerPlayerEntity ? (ServerPlayerEntity)entity : null);
                if(Reactant.getNbt().contains("WarpX")){x = Reactant.getNbt().getInt("WarpX");}
                if(Reactant.getNbt().contains("WarpY")){y = Reactant.getNbt().getInt("WarpY");}
                if(Reactant.getNbt().contains("WarpZ")){z = Reactant.getNbt().getInt("WarpZ");}
                if(Reactant.getNbt().contains("WarpWorld")) {
                    dim = RegistryKey.of(Registry.WORLD_KEY, //TODO: if warping acts badly, this may be the wrong registry. It was .DIMENSION
                            new Identifier(Reactant.getNbt().getString("WarpWorld")));
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
            if(!stack.hasNbt()){
                stack.setNbt(new NbtCompound());
            }
            if(!stack.getNbt().contains("WarpX")){
                stack.getNbt().putInt("WarpX",entity.getBlockPos().getX());
            }
            if(!stack.getNbt().contains("WarpY")){
                stack.getNbt().putInt("WarpY",entity.getBlockPos().getY());
            }
            if(!stack.getNbt().contains("WarpZ")){
                stack.getNbt().putInt("WarpZ",entity.getBlockPos().getZ());
            }
            if(!stack.getNbt().contains("WarpWorld")){
                stack.getNbt().putString("WarpWorld",world.getRegistryKey().getValue().toString());
            }
        }
    }
}
