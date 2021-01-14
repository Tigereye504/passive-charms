package net.tigereye.passivecharms.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.tigereye.passivecharms.PassiveCharms;
import net.tigereye.passivecharms.registration.PCItems;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventory;
import net.tigereye.passivecharms.util.InventoryTickContext;
import net.tigereye.passivecharms.util.InventoryUtil;

import java.util.Random;


public class MaintenanceCharm extends Item{
    
    private static final int REPAIRS_BETWEEN_RESETS = 1000;
    private static final int SECONDS_BETWEEN_REPAIR = 5;
    private static final int SECONDS_TO_RECOVER = 10;
    private static final int USES_PER_RECOVER = 1;

	public MaintenanceCharm() {
		super(new Item.Settings().maxCount(1).group(ItemGroup.TOOLS).maxDamage(REPAIRS_BETWEEN_RESETS));
	}
    
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
	    super.inventoryTick(stack,world,entity,slot,selected);
        if(!world.isClient() && world.getTime() % 20 == 0){
            int mendingTimer = stack.getOrCreateTag().getInt("mendingTimer"); //load mendingTimer
            mendingTimer++;
            if(mendingTimer >= SECONDS_TO_RECOVER){
                stack.setDamage(Math.max(stack.getDamage()-USES_PER_RECOVER,0));
                stack.getTag().putInt("mendingTimer", 0);
            }
            else if(entity instanceof ServerPlayerEntity && mendingTimer >= SECONDS_BETWEEN_REPAIR && stack.getDamage() < (stack.getMaxDamage()-1)){
                InventoryTickContext context = new InventoryTickContext(((ServerPlayerEntity)entity).inventory,stack,world,entity,slot,selected);
                int lastRepair = stack.getTag().getInt("lastRepair");
                int slotToRepair = InventoryUtil.findTargetableInventoryItemStack(context,this::repairableItemCondition,lastRepair+1);
                if(slotToRepair != -1){
                    repairItem(context,slotToRepair);
                }
            }
            else {
                stack.getTag().putInt("mendingTimer", mendingTimer);
            }
        }
    }

    protected boolean repairableItemCondition(InventoryTickContext context, Integer slot){
        ItemStack invItem = context.inventory.getStack(slot);
        return (invItem.getDamage() != 0 && invItem.getItem() != PCItems.MAINTENANCE_CHARM);
    }

    protected void repairItem(InventoryTickContext context, Integer slot){
        ItemStack invItem = context.inventory.getStack(slot);
        int lastRepair = context.stack.getOrCreateTag().getInt("lastRepair");
        PassiveCharms.LOGGER.info("Repairing " + "in Slot " + slot + "\n");
        invItem.setDamage(Math.max(invItem.getDamage() - 1, 0));
        context.stack.damage(1, new Random(), ((ServerPlayerEntity) context.entity));
        context.stack.getTag().putInt("lastRepair", slot);
        context.stack.getTag().putInt("mendingTimer", 0);
    }
}