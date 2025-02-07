package net.tigereye.passivecharms.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.tigereye.passivecharms.PassiveCharms;

import java.util.Iterator;
import java.util.List;

public class IndustryCharm extends Item {

    private static final int MAXIMUM_SMELTS = 1000;
    public static final int FUEL_TICKS_PER_DURABILITY = 200;
    private static final int SECONDS_BETWEEN_SMELTS = 10;
    private static final Inventory dummyInventory = new SimpleInventory(1);
    public static final String LEFTOVER_TICKS_KEY = "leftoverTicks";

    public IndustryCharm() {
        super(new Item.Settings().maxCount(1).maxDamage(MAXIMUM_SMELTS));
        //FuelRegistry.INSTANCE.get(Items.COAL);
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack,world,entity,slot,selected);
        if(!world.isClient() && stack.getDamage() < (stack.getMaxDamage()-1)){
            if(entity instanceof ServerPlayerEntity && world.getTime() % (20*SECONDS_BETWEEN_SMELTS) == 0){
                PlayerInventory inventory = ((ServerPlayerEntity)entity).getInventory();
                int lastSmelt = stack.getOrCreateNbt().getInt("lastSmelt");

                ItemStack invItem;
                int checkSlot;
                for (int i = 0; i < inventory.size(); i++) {
                    checkSlot = ((lastSmelt + i) % (inventory.size()));
                    invItem = inventory.getStack(checkSlot);
                    //TODO: move these 'safe target' checks to their own function
                    if(invItem != ItemStack.EMPTY && !invItem.hasCustomName() && !invItem.hasEnchantments() && checkSlot >= 9) {
                        Iterator<ItemStack> equipment = entity.getItemsEquipped().iterator();
                        boolean skip = false;
                        while(equipment.hasNext()){
                            if(equipment.next() == invItem){skip = true;}
                        }
                        if(!skip) {
                            dummyInventory.setStack(0, invItem);
                            List<SmeltingRecipe> recipes = entity.getWorld().getRecipeManager().listAllOfType(RecipeType.SMELTING);
                            for (SmeltingRecipe recipe :
                                    recipes) {
                                if (recipe.matches(dummyInventory, world)) {
                                    int cost = (recipe.getCookTime() + FUEL_TICKS_PER_DURABILITY - 1) / FUEL_TICKS_PER_DURABILITY; //ceiling-divide formula for integers
                                    if (stack.getDamage() < (stack.getMaxDamage() - cost)) {
                                        ItemStack output = recipe.getOutput(world.getRegistryManager()).copy();
                                        if (inventory.insertStack(output)) {
                                            PassiveCharms.LOGGER.debug("Smelting in Slot " + slot + "\n");
                                            invItem.decrement(1);
                                            stack.damage(cost, ((ServerPlayerEntity) entity).getRandom(), ((ServerPlayerEntity) entity));
                                            stack.getOrCreateNbt().putInt("lastSmelt", checkSlot);
                                            inventory.markDirty();
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /*
    protected boolean smeltableItemCondition(InventoryTickContext context, Integer slot){
        ItemStack invItem = context.inventory.getStack(slot);
        dummyInventory.setStack(0,invItem);
        List<SmeltingRecipe> recipes = context.entity.world.getRecipeManager().listAllOfType(RecipeType.SMELTING);
        for (SmeltingRecipe recipe:
             recipes) {
            if(recipe.matches(dummyInventory,context.world) && ){

            }
        }
        //SmeltingRecipe recipe = (Recipe)context.entity.world.getRecipeManager().getFirstMatch(RecipeType.SMELTING, this, context.entity.world).orElse((Object)null);

        //return (invItem.getDamage() != 0 && invItem.getItem() != PCItems.MAINTENANCE_CHARM);
    }
    */
}
