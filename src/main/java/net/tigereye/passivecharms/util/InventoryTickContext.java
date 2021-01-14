package net.tigereye.passivecharms.util;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class InventoryTickContext{
    public Inventory inventory;
    public ItemStack stack;
    public World world;
    public Entity entity;
    public int slot;
    public boolean selected;

    public InventoryTickContext(Inventory inventory, ItemStack stack, World world, Entity entity, int slot, boolean selected){
        this.inventory = inventory;
        this.stack = stack;
        this.world = world;
        this.entity = entity;
        this.slot = slot;
        this.selected = selected;
    }
}
