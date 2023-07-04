package net.tigereye.passivecharms.items.contingency_reactors;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class Warp extends ContingencyCharmReaction{
    private static final int COST = 999;
    public Warp(){
        super(new Settings().maxCount(1).group(ItemGroup.MISC));
    }

    public void React(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack Reactant){
        int x;
        int y = 64;
        int z;
        RegistryKey<World> dim = World.OVERWORLD;
        if(stack.getMaxDamage()-stack.getDamage() > COST){
            if(entity instanceof LivingEntity lEntity){
                NbtCompound nbt = Reactant.getOrCreateNbt();
                stack.damage(COST, lEntity.getRandom(), entity instanceof ServerPlayerEntity sEntity ? sEntity : null);
                x = nbt.getInt("WarpX");
                if(nbt.contains("WarpY")){y = nbt.getInt("WarpY");}
                z = nbt.getInt("WarpZ");
                if(nbt.contains("WarpWorld")) {
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
            NbtCompound nbt = stack.getOrCreateNbt();
            if(!nbt.contains("WarpX")){
                nbt.putInt("WarpX",entity.getBlockPos().getX());
                nbt.putInt("WarpY",entity.getBlockPos().getY());
                nbt.putInt("WarpZ",entity.getBlockPos().getZ());
                nbt.putString("WarpWorld",world.getRegistryKey().getValue().toString());
            }
        }
    }
}
