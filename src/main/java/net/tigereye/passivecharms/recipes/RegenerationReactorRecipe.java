package net.tigereye.passivecharms.recipes;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.tigereye.passivecharms.registration.PC_Items;
import net.tigereye.passivecharms.registration.PC_Recipes;

import java.util.List;

public class RegenerationReactorRecipe extends SpecialCraftingRecipe {

    public RegenerationReactorRecipe(Identifier id) {
        super(id);
    }

    public boolean matches(CraftingInventory craftingInventory, World world) {
        if (craftingInventory.getWidth() == 3 && craftingInventory.getHeight() == 3) {
            for(int i = 0; i < craftingInventory.getWidth(); ++i) {
                for(int j = 0; j < craftingInventory.getHeight(); ++j) {
                    ItemStack itemStack = craftingInventory.getStack(i + j * craftingInventory.getWidth());
                    if((i == 0 && j == 0) || (i == 2 && j == 0) || (i == 0 && j == 2) || (i == 2 && j == 2)){
                        if (!itemStack.isEmpty()) {
                            //System.out.println("Flameward Recipe Failed at ("+i+","+j+"); slot should be empty");
                            return false;
                        }
                    }
                    else if((i == 0 && j == 1) || (i == 2 && j == 1) || (i == 1 && j == 2) || (i == 1 && j == 0)){
                        if (itemStack.isEmpty()) {
                            //System.out.println("Flameward Recipe Failed at ("+i+","+j+"); slot is empty");
                            return false;
                        }
                        if (itemStack.getItem() != Items.GOLD_INGOT) {
                            //System.out.println("Flameward Recipe Failed at ("+i+","+j+"); not Gold Ingot");
                            return false;
                        }
                    }
                    else {
                        if (itemStack.isEmpty()) {
                            //System.out.println("Flameward Recipe Failed at ("+i+","+j+"); slot is empty");
                            return false;
                        }
                        Item item = itemStack.getItem();
                        if (item != Items.LINGERING_POTION) {
                            //System.out.println("Flameward Recipe Failed at ("+i+","+j+"); not Lingering Potion");
                            return false;
                        }
                        if (!containsPotionEffect(itemStack,StatusEffects.REGENERATION)){
                            //System.out.println("Flameward Recipe Failed at ("+i+","+j+"); not Fire Resistance");
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
        return new ItemStack(PC_Items.REGENERATION_REACTOR);
    }

    public boolean fits(int width, int height) {
        return (width >= 3 && height >= 3);
    }

    public RecipeSerializer<?> getSerializer() {
        return PC_Recipes.REGENERATION_REACTOR;
    }

    private boolean containsPotionEffect(ItemStack itemStack, StatusEffect statusEffect){
        List<StatusEffectInstance> effects = PotionUtil.getPotionEffects(itemStack);
        for (StatusEffectInstance i: effects) {
            if(i.getEffectType() == statusEffect){
                return true;
            }
        }
        return false;
    }
    
}
