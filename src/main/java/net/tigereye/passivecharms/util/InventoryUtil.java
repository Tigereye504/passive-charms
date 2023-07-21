package net.tigereye.passivecharms.util;

import net.minecraft.item.ItemStack;

import java.util.function.BiFunction;

public class InventoryUtil {

    public static int findTargetableInventoryItemStack(InventoryTickContext context, BiFunction<InventoryTickContext,Integer,Boolean> condition){
        return findTargetableInventoryItemStack(context,condition,0);
    }

    public static int findTargetableInventoryItemStack(InventoryTickContext context, BiFunction<InventoryTickContext,Integer,Boolean> condition, int start) {
        for (int i = 0; i < context.inventory.size(); i++) {
            if(condition.apply(context,(start + i) % context.inventory.size())){
                return (start + i) % context.inventory.size();
            }
        }
        return -1;
    }
}
