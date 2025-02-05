package net.tigereye.passivecharms.recipes;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.tigereye.passivecharms.registration.PCItems;
import net.tigereye.passivecharms.registration.PCRecipes;

public class DarknessTriggerRecipe extends SpecialCraftingRecipe {


    public DarknessTriggerRecipe(Identifier id) {
        super(id);
    }

    public boolean matches(CraftingInventory craftingInventory, World world) {
        if (craftingInventory.getWidth() == 3 && craftingInventory.getHeight() == 3) {
            for(int i = 0; i < craftingInventory.getWidth(); ++i) {
                for(int j = 0; j < craftingInventory.getHeight(); ++j) {
                    ItemStack itemStack = craftingInventory.getStack(i + j * craftingInventory.getWidth());
                    if((i == 0 && j == 0) || (i == 2 && j == 0) || (i == 0 && j == 2) || (i == 2 && j == 2)){
                        if (itemStack.isEmpty()) {
                            //System.out.println("Flameward Recipe Failed at ("+i+","+j+"); slot is empty");
                            return false;
                        }
                        if (itemStack.getItem() != Items.GOLD_INGOT) {
                            //System.out.println("Flameward Recipe Failed at ("+i+","+j+"); not Gold Ingot");
                            return false;
                        }
                    }
                    else if((i == 0 && j == 1) || (i == 2 && j == 1) || (i == 1 && j == 2) || i == 1 && j == 0){
                        if (itemStack.isEmpty()) {
                            //System.out.println("Flameward Recipe Failed at ("+i+","+j+"); slot is empty");
                            return false;
                        }
                        Item item = itemStack.getItem();
                        if (item != Items.TINTED_GLASS
                            && !itemStack.isIn(ConventionalItemTags.GLASS_BLOCKS))
                        //TODO: once in version 1.20, add dyed glass tag as +1 light threshold
                        {
                            //System.out.println("Flameward Recipe Failed at ("+i+","+j+"); not Potion");
                            return false;
                        }
                    }
                    else{
                        if (!itemStack.isEmpty()) {
                            //System.out.println("Flameward Recipe Failed at ("+i+","+j+"); slot is empty");
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

    public ItemStack craft(CraftingInventory inv) {
        ItemStack output = new ItemStack(PCItems.DARKNESS_TRIGGER);
        int lightPermitted = 0;
        lightPermitted += getLightPermissiveness(inv.getStack(1));
        lightPermitted += getLightPermissiveness(inv.getStack(3));
        lightPermitted += getLightPermissiveness(inv.getStack(5));
        lightPermitted += getLightPermissiveness(inv.getStack(7));
        NbtCompound tag = output.getOrCreateNbt();
        tag.putInt("lightLevel",lightPermitted);
        return output;
    }

    private int getLightPermissiveness(ItemStack block){
        if(block.getItem() == Items.TINTED_GLASS){
            return 0;
        }
        else{
            return 3;
        }
    }

    public boolean fits(int width, int height) {
        return (width >= 3 && height >= 3);
    }

    public RecipeSerializer<?> getSerializer() {
        return PCRecipes.DARKNESS_TRIGGER;
    }
}
