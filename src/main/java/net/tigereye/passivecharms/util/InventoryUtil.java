package net.tigereye.passivecharms.util;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.tigereye.passivecharms.registration.PCItems;

import java.util.Random;
import java.util.function.BiFunction;

public class InventoryUtil {

    public static int findTargetableInventoryItemStack(InventoryTickContext context, BiFunction<InventoryTickContext,Integer,Boolean> condition){
        return findTargetableInventoryItemStack(context,condition,0);
    }

    public static int findTargetableInventoryItemStack(InventoryTickContext context, BiFunction<InventoryTickContext,Integer,Boolean> condition, int start) {
        ItemStack invItem;
        for (int i = 0; i < context.inventory.size(); i++) {
            if(condition.apply(context,(start + i) % context.inventory.size())){
                return (start + i) % context.inventory.size();
            }
        }
        return -1;
    }
}
