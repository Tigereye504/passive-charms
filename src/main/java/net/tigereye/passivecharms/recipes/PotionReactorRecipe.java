package net.tigereye.passivecharms.recipes;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.tigereye.passivecharms.registration.PCItems;
import net.tigereye.passivecharms.registration.PCRecipes;

import java.util.ArrayList;
import java.util.List;

public class PotionReactorRecipe extends SpecialCraftingRecipe {

    private static final int DURATION_DIVISOR = 4;
    public PotionReactorRecipe(Identifier id, CraftingRecipeCategory craftingRecipeCategory) {
        super(id, craftingRecipeCategory);
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
                        if (item != Items.POTION
                            && item != Items.SPLASH_POTION
                            && item != Items.LINGERING_POTION)
                        {
                            //System.out.println("Flameward Recipe Failed at ("+i+","+j+"); not Potion");
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

    public ItemStack craft(CraftingInventory inv, DynamicRegistryManager registryManager) {
        ItemStack output = new ItemStack(PCItems.POTION_REACTOR);
        ItemStack potion = inv.getStack(4);
        List<StatusEffectInstance> potionList = PotionUtil.getPotionEffects(potion);
        List<StatusEffectInstance> list = new ArrayList<>();
        for (StatusEffectInstance effect : potionList) {
            StatusEffectInstance effectCopy = new StatusEffectInstance(effect.getEffectType(),Math.max(2,effect.getDuration()/DURATION_DIVISOR), effect.getAmplifier());
            list.add(effectCopy);
        }
        NbtCompound tag = output.getOrCreateNbt();
        NbtList NbtList = new NbtList();

        for (StatusEffectInstance effect : list) {
            if (effect != null) {
                NbtCompound NbtCompound = new NbtCompound();
                NbtList.add(effect.writeNbt(NbtCompound));
            }
        }
        tag.put("CustomPotionEffects",NbtList);
        return output;
    }

    public boolean fits(int width, int height) {
        return (width >= 3 && height >= 3);
    }

    public RecipeSerializer<?> getSerializer() {
        return PCRecipes.POTION_REACTOR;
    }
}
