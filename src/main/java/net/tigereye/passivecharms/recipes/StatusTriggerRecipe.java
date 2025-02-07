package net.tigereye.passivecharms.recipes;

import net.minecraft.entity.effect.StatusEffect;
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
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.tigereye.passivecharms.registration.PCItems;
import net.tigereye.passivecharms.registration.PCRecipes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StatusTriggerRecipe extends SpecialCraftingRecipe {


    public StatusTriggerRecipe(Identifier id, CraftingRecipeCategory craftingRecipeCategory) {
        super(id, craftingRecipeCategory);
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
                        if (item != Items.POTION
                            && item != Items.SPLASH_POTION
                            && item != Items.LINGERING_POTION
                            && item != Items.GLASS_BOTTLE)
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
        ItemStack output = new ItemStack(PCItems.STATUS_TRIGGER);
        Set<StatusEffect> effects = new HashSet<>();
        addPotionEffectsToSet(inv.getStack(1),effects);
        addPotionEffectsToSet(inv.getStack(3),effects);
        addPotionEffectsToSet(inv.getStack(5),effects);
        addPotionEffectsToSet(inv.getStack(7),effects);
        NbtCompound tag = output.getOrCreateNbt();
        NbtList NbtList = new NbtList();
        for (StatusEffect effect : effects) {
            StatusEffectInstance effectCopy = new StatusEffectInstance(effect,1,0);
            NbtCompound nbtCompound = new NbtCompound();
            NbtList.add(effectCopy.writeNbt(nbtCompound));
        }
        tag.put("CustomPotionEffects",NbtList);
        return output;
    }

    private void addPotionEffectsToSet(ItemStack potion, Set<StatusEffect> effects){
        List<StatusEffectInstance> potionEffectList = PotionUtil.getPotionEffects(potion);
        for (StatusEffectInstance eInstance : potionEffectList) {
            effects.add(eInstance.getEffectType());
        }
    }

    public boolean fits(int width, int height) {
        return (width >= 3 && height >= 3);
    }

    public RecipeSerializer<?> getSerializer() {
        return PCRecipes.STATUS_TRIGGER;
    }
}
