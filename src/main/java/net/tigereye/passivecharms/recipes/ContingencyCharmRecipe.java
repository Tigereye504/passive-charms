package net.tigereye.passivecharms.recipes;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.tigereye.passivecharms.items.ContingencyCharm;
import net.tigereye.passivecharms.items.contingency_reactors.ContingencyCharmReaction;
import net.tigereye.passivecharms.items.contingency_triggers.ContingencyCharmTrigger;
import net.tigereye.passivecharms.registration.PCItems;
import net.tigereye.passivecharms.registration.PCRecipes;

public class ContingencyCharmRecipe extends SpecialCraftingRecipe {

    public ContingencyCharmRecipe(Identifier id, CraftingRecipeCategory craftingRecipeCategory) {
        super(id, craftingRecipeCategory);
    }

    public boolean matches(CraftingInventory inv, World world) {
        boolean foundTrigger = false;
        boolean foundReactor = false;
        for(int i = 0; i < inv.size(); ++i) {
            ItemStack tempItemStack = inv.getStack(i);
            if (!tempItemStack.isEmpty()) {
                if(tempItemStack.getItem() instanceof ContingencyCharmTrigger){
                    if(foundTrigger){
                        return false; //a second trigger was found
                    }
                    foundTrigger = true; //first trigger found
                }
                else if (tempItemStack.getItem() instanceof ContingencyCharmReaction){
                    if(foundReactor){
                        return false; //a second reactor was found
                    }
                    foundReactor = true; //first reactor found
                }
                else{
                    return false; //something neither reactor nor trigger was found
                }
            }
        }
        return (foundReactor&&foundTrigger);
    }

    public ItemStack craft(CraftingInventory inv) {
        ItemStack trigger = null;
        ItemStack reactor = null;
        for(int i = 0; i < inv.size(); ++i) {
            ItemStack tempItemStack = inv.getStack(i);
            if (!tempItemStack.isEmpty()) {
                if(tempItemStack.getItem() instanceof ContingencyCharmTrigger){
                    if(trigger != null){
                        return  ItemStack.EMPTY; //a second trigger was found
                    }
                    trigger = tempItemStack; //first trigger found
                }
                else if (tempItemStack.getItem() instanceof ContingencyCharmReaction){
                    if(reactor != null){
                        return  ItemStack.EMPTY; //a second reactor was found
                    }
                    reactor = tempItemStack; //first reactor found
                }
                else{
                    return ItemStack.EMPTY;//something neither reactor nor trigger was found
                }
            }
        }
        if(reactor != null && trigger != null){
            ItemStack output = new ItemStack(PCItems.CONTINGENCY_CHARM);
            output.setDamage(reactor.getDamage());
            ContingencyCharm.saveContingencyToNBT(output, trigger, reactor);
            return output;
        }
        return ItemStack.EMPTY;
    }

    public boolean fits(int width, int height) {
        return (width * height >= 2);
    }

    public RecipeSerializer<?> getSerializer() {
        return PCRecipes.CONTINGENCY_CHARM;
    }


    
}
