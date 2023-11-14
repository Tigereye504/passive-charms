package net.tigereye.passivecharms.registration;

import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tigereye.passivecharms.PassiveCharms;
import net.tigereye.passivecharms.recipes.*;

public class PCRecipes {

    public static SpecialRecipeSerializer<ContingencyCharmRecipe> CONTINGENCY_CHARM = Registry.register(Registries.RECIPE_SERIALIZER,
            new Identifier(PassiveCharms.MODID,"crafting_special_contingency_charm"),
            new SpecialRecipeSerializer<>(ContingencyCharmRecipe::new));
    public static SpecialRecipeSerializer<PotionReactorRecipe> POTION_REACTOR = Registry.register(Registries.RECIPE_SERIALIZER,
            new Identifier(PassiveCharms.MODID, "crafting_special_potion_reactor"),
            new SpecialRecipeSerializer<>(PotionReactorRecipe::new));
    public static SpecialRecipeSerializer<StatusTriggerRecipe> STATUS_TRIGGER = Registry.register(Registries.RECIPE_SERIALIZER,
            new Identifier(PassiveCharms.MODID, "crafting_special_status_trigger"),
            new SpecialRecipeSerializer<>(StatusTriggerRecipe::new));
    public static SpecialRecipeSerializer<IndustryCharmRecipe> INDUSTRY_CHARM = Registry.register(Registries.RECIPE_SERIALIZER,
            new Identifier(PassiveCharms.MODID, "crafting_special_industry_charm"),
            new SpecialRecipeSerializer<>(IndustryCharmRecipe::new));
    public static SpecialRecipeSerializer<IndustryCharmReloadRecipe> INDUSTRY_CHARM_RELOAD = Registry.register(Registries.RECIPE_SERIALIZER,
            new Identifier(PassiveCharms.MODID, "crafting_special_industry_charm_reload"),
            new SpecialRecipeSerializer<>(IndustryCharmReloadRecipe::new));

    public static void register(){

    }
    
}
