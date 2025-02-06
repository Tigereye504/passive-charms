package net.tigereye.passivecharms.recipes;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.tigereye.passivecharms.items.contingency_triggers.ContingencyCharmTrigger;
import net.tigereye.passivecharms.items.contingency_triggers.NotTrigger;
import net.tigereye.passivecharms.registration.PCItems;
import net.tigereye.passivecharms.registration.PCRecipes;

public class NotTriggerRecipe extends SpecialCraftingRecipe {

    public NotTriggerRecipe(Identifier id) {
        super(id);
    }

    public boolean matches(CraftingInventory inv, World world) {
        boolean foundTrigger = false;
        boolean foundRedstoneTorch = false;
        for(int i = 0; i < inv.size(); ++i) {
            ItemStack tempItemStack = inv.getStack(i);
            if (!tempItemStack.isEmpty()) {
                if(tempItemStack.getItem() instanceof ContingencyCharmTrigger){
                    if(foundTrigger){
                        return false; //a second trigger was found
                    }
                    foundTrigger = true; //first trigger found
                }
                else if (tempItemStack.getItem() == Items.REDSTONE_TORCH){
                    if(foundRedstoneTorch){
                        return false; //a second torch was found
                    }
                    foundRedstoneTorch = true; //first torch found
                }
                else{
                    return false; //something neither torch nor trigger was found
                }
            }
        }
        return (foundRedstoneTorch&&foundTrigger);
    }

    public ItemStack craft(CraftingInventory inv) {
        ItemStack trigger = null;
        boolean foundRedstoneTorch = false;
        for(int i = 0; i < inv.size(); ++i) {
            ItemStack tempItemStack = inv.getStack(i);
            if (!tempItemStack.isEmpty()) {
                if(tempItemStack.getItem() instanceof ContingencyCharmTrigger){
                    if(trigger != null){
                        return  ItemStack.EMPTY; //a second trigger was found
                    }
                    trigger = tempItemStack; //first trigger found
                }
                else if (tempItemStack.getItem() == Items.REDSTONE_TORCH){
                    if(foundRedstoneTorch){
                        return  ItemStack.EMPTY; //a second torch was found
                    }
                    foundRedstoneTorch = true; //first torch found
                }
                else{
                    return ItemStack.EMPTY;//something neither reactor nor trigger was found
                }
            }
        }
        if(foundRedstoneTorch && trigger != null){
            ItemStack output = new ItemStack(PCItems.NOT_TRIGGER);
            NotTrigger.saveTriggerComponentToNBT(output, trigger);
            return output;
        }
        return ItemStack.EMPTY;
    }

    public boolean fits(int width, int height) {
        return (width * height >= 2);
    }

    public RecipeSerializer<?> getSerializer() {
        return PCRecipes.NOT_TRIGGER;
    }


    
}
