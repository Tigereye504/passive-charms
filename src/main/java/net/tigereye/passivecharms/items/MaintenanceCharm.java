package net.tigereye.passivecharms.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.tigereye.passivecharms.PassiveCharms;
import net.tigereye.passivecharms.registration.PCItems;
import net.minecraft.entity.Entity;
import net.tigereye.passivecharms.util.InventoryTickContext;
import net.tigereye.passivecharms.util.InventoryUtil;


public class MaintenanceCharm extends Item{
    
    private static final int REPAIRS_BETWEEN_RESETS = 1000;
    private static final int TICKS_BETWEEN_REPAIR = 100;
    private static final int TICKS_TO_RECOVER = 200;
    private static final int USES_PER_RECOVER = 1;

	public MaintenanceCharm() {
		super(new Item.Settings().maxCount(1).group(ItemGroup.TOOLS).maxDamage(REPAIRS_BETWEEN_RESETS));
	}
    
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
	    super.inventoryTick(stack,world,entity,slot,selected);
        NbtCompound nbt = stack.getOrCreateNbt();
        long timeSinceLastAction = world.getTime() - nbt.getLong("mendingTimer");
        if(!world.isClient() && timeSinceLastAction >= TICKS_BETWEEN_REPAIR){
            if(entity instanceof ServerPlayerEntity sPLayer && stack.getDamage() < (stack.getMaxDamage()-1)){
                InventoryTickContext context = new InventoryTickContext(sPLayer.getInventory(),stack,world,entity,slot,selected);
                int lastRepair = nbt.getInt("lastRepair");
                int slotToRepair = InventoryUtil.findTargetableInventoryItemStack(context,this::repairableItemCondition,lastRepair+1);
                if(slotToRepair != -1){
                    repairItem(context,slotToRepair);
                }
            }
            else if(timeSinceLastAction >= TICKS_TO_RECOVER){
                stack.setDamage(Math.max(stack.getDamage()-USES_PER_RECOVER,0));
                nbt.putLong("mendingTimer", world.getTime());
            }
        }
    }

    protected boolean repairableItemCondition(InventoryTickContext context, Integer slot){
        ItemStack invItem = context.inventory.getStack(slot);
        return (invItem.getDamage() != 0 && invItem.getItem() != PCItems.MAINTENANCE_CHARM);
    }

    protected void repairItem(InventoryTickContext context, Integer slot){
        NbtCompound nbt = context.stack.getOrCreateNbt();
        if(context.entity instanceof LivingEntity lEntity) {
            ItemStack invItem = context.inventory.getStack(slot);
            PassiveCharms.LOGGER.debug("Repairing " + "in Slot " + slot + "\n");
            invItem.setDamage(Math.max(invItem.getDamage() - 1, 0));
            context.stack.damage(1, lEntity.getRandom(), ((ServerPlayerEntity) context.entity));
            nbt.putInt("lastRepair", slot);
            nbt.putLong("mendingTimer", context.world.getTime());
        }
    }
}