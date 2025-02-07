package net.tigereye.passivecharms.recipes;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.tigereye.passivecharms.items.contingency_triggers.ContingencyCharmTrigger;
import net.tigereye.passivecharms.items.contingency_triggers.OrTrigger;
import net.tigereye.passivecharms.registration.PCItems;
import net.tigereye.passivecharms.registration.PCRecipes;

public class OrTriggerRecipe extends SpecialCraftingRecipe {

    public OrTriggerRecipe(Identifier id, CraftingRecipeCategory craftingRecipeCategory) {
        super(id, craftingRecipeCategory);
    }

    public boolean matches(CraftingInventory inv, World world) {
        int foundTriggers = 0;
        boolean foundRedstone = false;
        for(int i = 0; i < inv.size(); ++i) {
            ItemStack tempItemStack = inv.getStack(i);
            if (!tempItemStack.isEmpty()) {
                if(tempItemStack.getItem() instanceof ContingencyCharmTrigger){
                    if(foundTriggers >= 2){
                        return false; //a third trigger was found
                    }
                    foundTriggers++; //increment triggers found
                }
                else if (tempItemStack.getItem() == Items.REDSTONE){
                    if(foundRedstone){
                        return false; //a second comparator was found
                    }
                    foundRedstone = true; //first comparator found
                }
                else{
                    return false; //something neither comparator nor trigger was found
                }
            }
        }
        return (foundRedstone && (foundTriggers == 2));
    }

    public ItemStack craft(CraftingInventory inv, DynamicRegistryManager registryManager) {
        ItemStack trigger1 = null;
        ItemStack trigger2 = null;
        boolean foundRedstone = false;
        for(int i = 0; i < inv.size(); ++i) {
            ItemStack tempItemStack = inv.getStack(i);
            if (!tempItemStack.isEmpty()) {
                if(tempItemStack.getItem() instanceof ContingencyCharmTrigger){
                    if(trigger1 == null){
                        trigger1 = tempItemStack;
                    }
                    else if(trigger2 == null){
                        trigger2 = tempItemStack;
                    }
                    else{
                        return ItemStack.EMPTY; //too many triggers
                    }
                }
                else if (tempItemStack.getItem() == Items.REDSTONE){
                    if(foundRedstone){
                        return  ItemStack.EMPTY; //a second was found
                    }
                    foundRedstone = true; //first found
                }
                else{
                    return ItemStack.EMPTY;//something neither reactor nor trigger was found
                }
            }
        }
        if(foundRedstone && trigger1 != null & trigger2 != null){
            ItemStack output = new ItemStack(PCItems.OR_TRIGGER);
            OrTrigger.saveTriggerComponentsToNBT(output,trigger1,trigger2);
            return output;
        }
        return ItemStack.EMPTY;
    }

    public boolean fits(int width, int height) {
        return (width * height >= 2);
    }

    public RecipeSerializer<?> getSerializer() {
        return PCRecipes.OR_TRIGGER;
    }


    
}
