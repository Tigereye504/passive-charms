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
import net.tigereye.passivecharms.registration.PCItems;
import net.tigereye.passivecharms.registration.PCRecipes;

import java.util.List;

public class DrowningTriggerRecipe extends SpecialCraftingRecipe {

    public DrowningTriggerRecipe(Identifier id) {
        super(id);
    }

    public boolean matches(CraftingInventory craftingInventory, World world) {
        if (craftingInventory.getWidth() == 3 && craftingInventory.getHeight() == 3) {
            for(int i = 0; i < craftingInventory.getWidth(); ++i) {
                for(int j = 0; j < craftingInventory.getHeight(); ++j) {
                    ItemStack itemStack = craftingInventory.getStack(i + j * craftingInventory.getWidth());
                    if(i == 1 && j == 1){
                        if (!itemStack.isEmpty()) {
                            //System.out.println("Immolation Recipe Failed at ("+i+","+j+"); slot should be empty");
                            return false;
                        }
                    }
                    else if((i == 0 && j == 1) || (i == 2 && j == 1) || (i == 1 && j == 2) || (i == 1 && j == 0)){
                        if (itemStack.isEmpty()) {
                            //System.out.println("Immolation Recipe Failed at ("+i+","+j+"); slot is empty");
                            return false;
                        }
                        Item item = itemStack.getItem();
                        if (item != Items.LINGERING_POTION) {
                            //System.out.println("Immolation Recipe Failed at ("+i+","+j+"); not Lingering Potion");
                            return false;
                        }
                        if (!containsPotionEffect(itemStack,StatusEffects.WATER_BREATHING)){
                            //System.out.println("Immolation Recipe Failed at ("+i+","+j+"); not Fire Resistance");
                            return false;
                        }
                    }
                    else if((i == 0 && j == 0) || (i == 0 && j == 2) || (i == 2 && j == 0) || (i == 2 && j == 2)){
                        if (itemStack.isEmpty()) {
                            //System.out.println("Immolation Recipe Failed at ("+i+","+j+"); slot is empty");
                            return false;
                        }
                        if (itemStack.getItem() != Items.GOLD_INGOT) {
                            //System.out.println("Immolation Recipe Failed at ("+i+","+j+"); not Gold Ingot");
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
        return new ItemStack(PCItems.DROWNING_TRIGGER);
    }

    public boolean fits(int width, int height) {
        return (width >= 3 && height >= 3);
    }

    public RecipeSerializer<?> getSerializer() {
        return PCRecipes.DROWNING_TRIGGER;
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
