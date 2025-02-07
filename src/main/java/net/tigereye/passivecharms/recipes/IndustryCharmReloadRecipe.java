package net.tigereye.passivecharms.recipes;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.tigereye.passivecharms.items.IndustryCharm;
import net.tigereye.passivecharms.registration.PCItems;
import net.tigereye.passivecharms.registration.PCRecipes;

public class IndustryCharmReloadRecipe extends SpecialCraftingRecipe {

    public IndustryCharmReloadRecipe(Identifier id, CraftingRecipeCategory craftingRecipeCategory) {
        super(id, craftingRecipeCategory);
    }

    public boolean matches(CraftingInventory craftingInventory, World world) {
        if (craftingInventory.getWidth() >= 2 && craftingInventory.getHeight() >= 2) {
            boolean foundCharm = false;
            for(int i = 0; i < craftingInventory.getWidth(); ++i) {
                for(int j = 0; j < craftingInventory.getHeight(); ++j) {
                    ItemStack itemStack = craftingInventory.getStack(i + j * craftingInventory.getWidth());
                    Item item = itemStack.getItem();
                    if(item == PCItems.INDUSTRY_CHARM){
                        if(foundCharm){return false;}
                        foundCharm = true;
                    }
                    else if(item != Items.AIR){
                        try {
                            int fuel = FuelRegistry.INSTANCE.get(item);
                            if (fuel == 0) {
                                return false;
                                //found non-fuel
                            }
                        }
                        catch(NullPointerException e){
                            return false;
                            //found non-fuel
                        }
                    }
                }
            }
            return foundCharm;
        } else {
            return false;
        }
    }

    public ItemStack craft(CraftingInventory craftingInventory) {
        ItemStack charm = ItemStack.EMPTY;
        int fuelGain = 0;
        boolean foundCharm = false;
        if (craftingInventory.getWidth() >= 2 && craftingInventory.getHeight() >= 2) {
            for(int i = 0; i < craftingInventory.getWidth(); ++i) {
                for(int j = 0; j < craftingInventory.getHeight(); ++j) {
                    ItemStack itemStack = craftingInventory.getStack(i + j * craftingInventory.getWidth());
                    Item item = itemStack.getItem();
                    if(item == PCItems.INDUSTRY_CHARM){
                        if(foundCharm){return ItemStack.EMPTY;}
                        foundCharm = true;
                        charm = itemStack.copy();
                        fuelGain += charm.getOrCreateNbt().getInt(IndustryCharm.LEFTOVER_TICKS_KEY);
                    }
                    else if(item != Items.AIR){
                        try{
                            if(FuelRegistry.INSTANCE.get(item) != 0) {
                                fuelGain += FuelRegistry.INSTANCE.get(item);
                                //found fuel
                            }
                            else{
                                return ItemStack.EMPTY;
                                //non-fuel items are not fine
                            }
                        }
                        catch(NullPointerException e){
                            return ItemStack.EMPTY;
                        }
                    }

                }
            }
        } else {
            return ItemStack.EMPTY;
        }
        if(foundCharm){
            charm.setDamage(Math.max(0,charm.getDamage()-(fuelGain/IndustryCharm.FUEL_TICKS_PER_DURABILITY)));
            charm.getOrCreateNbt().putInt(IndustryCharm.LEFTOVER_TICKS_KEY,fuelGain % IndustryCharm.FUEL_TICKS_PER_DURABILITY);
            return charm;
        }
        return ItemStack.EMPTY;
    }

    public boolean fits(int width, int height) {
        return (width >= 2 && height >= 2);
    }

    public RecipeSerializer<?> getSerializer() {
        return PCRecipes.INDUSTRY_CHARM_RELOAD;
    }
    
}
