package net.tigereye.passivecharms.recipes;

import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.World;
import net.tigereye.passivecharms.registration.PCItems;
import net.tigereye.passivecharms.registration.PCRecipes;

public class IndustryCharmRecipe extends SpecialCraftingRecipe {

    public IndustryCharmRecipe(CraftingRecipeCategory craftingRecipeCategory) {
        super(craftingRecipeCategory);
    }

    public boolean matches(RecipeInputInventory craftingInventory, World world) {
        if (craftingInventory.getWidth() == 3 && craftingInventory.getHeight() == 3) {
            for(int i = 0; i < craftingInventory.getWidth(); ++i) {
                for(int j = 0; j < craftingInventory.getHeight(); ++j) {
                    ItemStack itemStack = craftingInventory.getStack(i + j * craftingInventory.getWidth());
                    Item item = itemStack.getItem();
                    if(i == 1 && j == 1){
                        if (item != Items.FURNACE) {
                            return false;
                        }
                    }
                    else if((i == 0 && j == 1) || (i == 2 && j == 1) || (i == 1 && j == 2) || (i == 1 && j == 0)
                        || (i == 0 && j == 0) || (i == 0 && j == 2) || (i == 2 && j == 0) || (i == 2 && j == 2)){
                        if (item != Items.GOLD_INGOT) {
                            return false;
                        }
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public ItemStack craft(RecipeInputInventory inv, DynamicRegistryManager registryManager) {
        ItemStack output = new ItemStack(PCItems.INDUSTRY_CHARM);
        output.setDamage(output.getMaxDamage());
        return output;
    }

    public boolean fits(int width, int height) {
        return (width >= 3 && height >= 3);
    }

    public RecipeSerializer<?> getSerializer() {
        return PCRecipes.INDUSTRY_CHARM;
    }
}
