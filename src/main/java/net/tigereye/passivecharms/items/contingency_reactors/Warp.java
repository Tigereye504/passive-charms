package net.tigereye.passivecharms.items.contingency_reactors;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.tigereye.passivecharms.items.ContingencyCharm;

import java.util.List;

public class Warp extends Item implements ContingencyCharmReaction{
    private static final int COST = ContingencyCharm.DURABILITY;
    public Warp(){
        super(new Settings().maxCount(1).group(ItemGroup.MISC));
    }

    public boolean React(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack Reactant){
        if(world.isClient()){
            return false;
        }
        RegistryKey<World> dim = World.OVERWORLD;
        if(stack.getMaxDamage()-stack.getDamage() >= COST
            && entity instanceof LivingEntity lEntity)
        {
            NbtCompound nbt = Reactant.getOrCreateNbt();
            stack.damage(COST, lEntity.getRandom(), entity instanceof ServerPlayerEntity sEntity ? sEntity : null);
            int x = nbt.getInt("WarpX");
            int y = nbt.getInt("WarpY");
            int z = nbt.getInt("WarpZ");
            if(nbt.contains("WarpWorld")) {
                dim = RegistryKey.of(Registry.WORLD_KEY,new Identifier(nbt.getString("WarpWorld")));
            }
            if(dim != null && entity.getEntityWorld().getRegistryKey() != dim) {
                MinecraftServer server = entity.getServer();
                if(server != null) {
                    entity.moveToWorld(server.getWorld(dim));
                }
            }
            entity.teleport(x,y,z);
            return true;
        }
        return false;
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

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        NbtCompound nbt = itemStack.getOrCreateNbt();
        if(nbt.contains("WarpX")){
            tooltip.add(Text.literal("X: "+nbt.getInt("WarpX")));
            tooltip.add(Text.literal("Y: "+nbt.getInt("WarpY")));
            tooltip.add(Text.literal("Z: "+nbt.getInt("WarpZ")));
            RegistryKey<World> dim = RegistryKey.of(Registry.WORLD_KEY, new Identifier(nbt.getString("WarpWorld")));
            if(world.getRegistryKey() == dim){
                tooltip.add(Text.translatable("item.passivecharms.contingency_charm_reaction_warp_tooltip.dimension_match"));
            }
            else{
                tooltip.add(Text.translatable("item.passivecharms.contingency_charm_reaction_warp_tooltip.dimension_mismatch"));
            }
        }
        else{
            tooltip.add(Text.translatable("item.passivecharms.contingency_charm_reaction_warp_tooltip.unbound"));
        }

    }
}
