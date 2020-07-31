package net.tigereye.passivecharms.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.tigereye.passivecharms.PassiveCharms;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventory;

import java.util.Random;


public class MaintenanceCharm extends Item{
    
    private static final int REPAIRS_BETWEEN_RESETS = 1000;
    private static final int SECONDS_BETWEEN_REPAIR = 5;
    private static final int SECONDS_TO_RECOVER = 10;
    private static final int USES_PER_RECOVER = 1;

    int mendingTimer = 0;
    int lastRepair = 0;
	public MaintenanceCharm() {
		super(new Item.Settings().maxCount(1).group(ItemGroup.TOOLS).maxDamage(REPAIRS_BETWEEN_RESETS));
	}
    
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient()){
            mendingTimer++;
            if(mendingTimer >= 20*SECONDS_TO_RECOVER){
                System.out.println("Restoring the Charm");
                stack.setDamage(Math.max(stack.getDamage()-USES_PER_RECOVER,0));
                mendingTimer = 0;
            }
            else if(entity instanceof ServerPlayerEntity && mendingTimer >= 20*SECONDS_BETWEEN_REPAIR && stack.getDamage() < (stack.getMaxDamage()-1)){
                Inventory inv = ((ServerPlayerEntity)entity).inventory;
                ItemStack invItem;
                for(int i = 1; i <= inv.size(); i++){
                    invItem = inv.getStack((lastRepair + i) % inv.size());
                    if(invItem.getDamage() != 0 && invItem.getItem() != PassiveCharms.maintenanceCharm){
                        System.out.println("Repairing "+invItem.getItem().getName().asString()+" in Slot "+((lastRepair + i) % inv.size())+"\n");
                        invItem.setDamage(Math.max(invItem.getDamage()-1,0));
                        stack.damage(1, new Random(), ((ServerPlayerEntity)entity));
                        lastRepair = (lastRepair + i) % inv.size();
                        mendingTimer = 0;
                        return;
                    }
                }
            }
        }
    }
}