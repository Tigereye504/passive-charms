package net.tigereye.passivecharms.recipes;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.impl.content.registry.FuelRegistryImpl;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.tigereye.passivecharms.items.IndustryCharm;
import net.tigereye.passivecharms.registration.PCItems;
import net.tigereye.passivecharms.registration.PCRecipes;

import java.util.List;

public class IndustryCharmReloadRecipe extends SpecialCraftingRecipe {

    public IndustryCharmReloadRecipe(Identifier id) {
        super(id);
    }

    public boolean matches(CraftingInventory craftingInventory, World world) {
        if (craftingInventory.getWidth() == 3 && craftingInventory.getHeight() == 3) {
            boolean foundCharm = false;
            for(int i = 0; i < craftingInventory.getWidth(); ++i) {
                for(int j = 0; j < craftingInventory.getHeight(); ++j) {
                    ItemStack itemStack = craftingInventory.getStack(i + j * craftingInventory.getWidth());
                    Item item = itemStack.getItem();
                    if(item == PCItems.INDUSTRY_CHARM){
                        if(foundCharm){return false;}
                        foundCharm = true;
                    }
                    else if(item == Items.AIR){
                        //empty space is fine
                    }
                    else {
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
            return true;
        } else {
            return false;
        }
    }

    public ItemStack craft(CraftingInventory craftingInventory) {
        ItemStack charm = ItemStack.EMPTY;
        int fuelGain = 0;
        boolean foundCharm = false;
        if (craftingInventory.getWidth() == 3 && craftingInventory.getHeight() == 3) {
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
                    else if(item == Items.AIR){
                        //empty space is fine
                    }
                    else try{
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
