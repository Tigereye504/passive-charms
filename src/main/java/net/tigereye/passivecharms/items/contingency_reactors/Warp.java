package net.tigereye.passivecharms.items.contingency_reactors;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;
import net.tigereye.passivecharms.PassiveCharms;
import net.tigereye.passivecharms.items.ContingencyCharm;
import net.tigereye.passivecharms.items.TooltipNester;
import net.tigereye.passivecharms.registration.PCItems;
import org.apache.logging.log4j.Level;

import java.util.List;

public class Warp extends Item implements ContingencyCharmReaction, TooltipNester {
    private static final int COST = ContingencyCharm.DURABILITY;
    public Warp(){
        super(PCItems.REACTOR_SETTINGS);
    }

    public boolean React(ItemStack stack, World world, Entity entity, int slot, boolean selected, ItemStack Reactant){
        if(world.isClient()){
            return false;
        }
        if(!entity.isAlive()){
            PassiveCharms.LOGGER.log(Level.DEBUG,"Player is dead! Aborting reaction.");
            return false;
        }
        RegistryKey<World> dim = World.OVERWORLD;
        if(stack.getMaxDamage()-stack.getDamage() >= COST
            && entity instanceof LivingEntity lEntity) {
            NbtCompound nbt = Reactant.getOrCreateNbt();
            stack.damage(COST, lEntity.getRandom(), entity instanceof ServerPlayerEntity sEntity ? sEntity : null);
            int x = nbt.getInt("WarpX");
            int y = nbt.getInt("WarpY");
            int z = nbt.getInt("WarpZ");
            String worldID = nbt.getString("WarpWorld");
            if (nbt.contains("WarpWorld")) {
                dim = RegistryKey.of(RegistryKeys.WORLD, new Identifier(worldID));
            }
            PassiveCharms.LOGGER.log(Level.DEBUG,"Teleporting from "+
                    entity.getBlockX() + ", "+
                    entity.getBlockY() + ", "+
                    entity.getBlockZ() + " to "+
                    x + ", "+
                    y + ", "+
                    z + " ");
            if(dim != entity.getEntityWorld().getRegistryKey()){
                PassiveCharms.LOGGER.log(Level.DEBUG,"This involves hopping dimensions");
            }
            if (lEntity instanceof ServerPlayerEntity spEntity) {
                PassiveCharms.LOGGER.log(Level.DEBUG,"Warping as player");
                spEntity.teleport(spEntity.server.getWorld(dim),x,y,z,spEntity.getYaw(),spEntity.getPitch());
            }
            else {
                PassiveCharms.LOGGER.log(Level.DEBUG,"Warping as non-player. Somehow.");
                if (dim != null && entity.getEntityWorld().getRegistryKey() != dim) {
                    MinecraftServer server = entity.getServer();
                    if (server != null) {
                        entity.moveToWorld(server.getWorld(dim));
                    }
                }
                entity.teleport(x, y, z);
            }
            return true;
        }
        return false;
    }

    /*public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient()) {
            NbtCompound nbt = stack.getOrCreateNbt();
            if(!nbt.contains("WarpX")){
                nbt.putInt("WarpX",entity.getBlockPos().getX());
                nbt.putInt("WarpY",entity.getBlockPos().getY());
                nbt.putInt("WarpZ",entity.getBlockPos().getZ());
                nbt.putString("WarpWorld",world.getRegistryKey().getValue().toString());
            }
        }
    }*/

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        NbtCompound nbt = user.getStackInHand(hand).getOrCreateNbt();
        nbt.putInt("WarpX",user.getBlockPos().getX());
        nbt.putInt("WarpY",user.getBlockPos().getY());
        nbt.putInt("WarpZ",user.getBlockPos().getZ());
        nbt.putString("WarpWorld",world.getRegistryKey().getValue().toString());
        return TypedActionResult.success(user.getStackInHand(hand),true);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        appendNestedTooltip(itemStack,world,tooltip,tooltipContext,0);
        tooltip.add(Text.translatable("item.passivecharms.contingency_charm_reaction_warp.tooltip.instructions"));
    }

    @Override
    public void appendNestedTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext, int depth){
        NbtCompound nbt = itemStack.getOrCreateNbt();
        if(nbt.contains("WarpX")){
            tooltip.add(Text.literal(" ".repeat(depth)).append(
                    Text.literal("X: "+nbt.getInt("WarpX"))));
            tooltip.add(Text.literal(" ".repeat(depth)).append(
                    Text.literal("Y: "+nbt.getInt("WarpY"))));
            tooltip.add(Text.literal(" ".repeat(depth)).append(
                    Text.literal("Z: "+nbt.getInt("WarpZ"))));
            RegistryKey<World> dim = RegistryKey.of(RegistryKeys.WORLD, new Identifier(nbt.getString("WarpWorld")));
            if(world.getRegistryKey() == dim){
                tooltip.add(Text.literal(" ".repeat(depth)).append(
                        Text.translatable("item.passivecharms.contingency_charm_reaction_warp.tooltip.dimension_match")));
            }
            else{
                tooltip.add(Text.literal(" ".repeat(depth)).append(
                        Text.translatable("item.passivecharms.contingency_charm_reaction_warp.tooltip.dimension_mismatch")));
            }
        }
        else{
            tooltip.add(Text.literal(" ".repeat(depth)).append(
                    Text.translatable("item.passivecharms.contingency_charm_reaction_warp.tooltip.unbound")));
        }
    }
}
