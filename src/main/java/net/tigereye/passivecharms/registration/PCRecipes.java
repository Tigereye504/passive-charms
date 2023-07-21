package net.tigereye.passivecharms.registration;

import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.registry.Registry;
import net.tigereye.passivecharms.recipes.*;

public class PCRecipes {
    
    /* deprecated recipes
    public static SpecialRecipeSerializer<FeatherfallReactorRecipe> FEATHERFALL_REACTOR;
    public static SpecialRecipeSerializer<FlamewardReactorRecipe> FLAMEWARD_REACTOR;
    public static SpecialRecipeSerializer<GillsReactorRecipe> GILLS_REACTOR;
    public static SpecialRecipeSerializer<RegenerationReactorRecipe> REGENERATION_REACTOR;
    public static SpecialRecipeSerializer<RestorationReactorRecipe> RESTORATION_REACTOR;
    */
    public static SpecialRecipeSerializer<ContingencyCharmRecipe> CONTINGENCY_CHARM;
    public static SpecialRecipeSerializer<PotionReactorRecipe> POTION_REACTOR;
    public static SpecialRecipeSerializer<DrowningTriggerRecipe> DROWNING_TRIGGER;
    public static SpecialRecipeSerializer<FreefallTriggerRecipe> FREEFALL_TRIGGER;
    public static SpecialRecipeSerializer<ImmolationTriggerRecipe> IMMOLATION_TRIGGER;
    public static SpecialRecipeSerializer<InjuryTriggerRecipe> INJURY_TRIGGER;
    public static SpecialRecipeSerializer<LightInjuryTriggerRecipe> LIGHT_INJURY_TRIGGER;
    public static SpecialRecipeSerializer<StatusTriggerRecipe> STATUS_TRIGGER;
    public static SpecialRecipeSerializer<IndustryCharmRecipe> INDUSTRY_CHARM;
    public static SpecialRecipeSerializer<IndustryCharmReloadRecipe> INDUSTRY_CHARM_RELOAD;

    public static void register(){
        /* deprecated recipes
        FEATHERFALL_REACTOR = (SpecialRecipeSerializer<FeatherfallReactorRecipe>) Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_featherfall_reactor", new SpecialRecipeSerializer<FeatherfallReactorRecipe>(FeatherfallReactorRecipe::new));
        FLAMEWARD_REACTOR = (SpecialRecipeSerializer<FlamewardReactorRecipe>) Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_flameward_reactor", new SpecialRecipeSerializer<FlamewardReactorRecipe>(FlamewardReactorRecipe::new));
        GILLS_REACTOR = (SpecialRecipeSerializer<GillsReactorRecipe>) Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_gills_reactor", new SpecialRecipeSerializer<GillsReactorRecipe>(GillsReactorRecipe::new));
        REGENERATION_REACTOR = (SpecialRecipeSerializer<RegenerationReactorRecipe>) Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_regeneration_reactor", new SpecialRecipeSerializer<RegenerationReactorRecipe>(RegenerationReactorRecipe::new));
        RESTORATION_REACTOR = (SpecialRecipeSerializer<RestorationReactorRecipe>) Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_restoration_reactor", new SpecialRecipeSerializer<RestorationReactorRecipe>(RestorationReactorRecipe::new));
        */
        
        CONTINGENCY_CHARM = Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_contingency_charm", new SpecialRecipeSerializer<>(ContingencyCharmRecipe::new));
        POTION_REACTOR = Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_potion_reactor", new SpecialRecipeSerializer<>(PotionReactorRecipe::new));
        DROWNING_TRIGGER = Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_drowning_trigger", new SpecialRecipeSerializer<>(DrowningTriggerRecipe::new));
        FREEFALL_TRIGGER = Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_freefall_trigger", new SpecialRecipeSerializer<>(FreefallTriggerRecipe::new));
        IMMOLATION_TRIGGER = Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_immolation_trigger", new SpecialRecipeSerializer<>(ImmolationTriggerRecipe::new));
        INJURY_TRIGGER = Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_injury_trigger", new SpecialRecipeSerializer<>(InjuryTriggerRecipe::new));
        LIGHT_INJURY_TRIGGER = Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_light_injury_trigger", new SpecialRecipeSerializer<>(LightInjuryTriggerRecipe::new));
        STATUS_TRIGGER = Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_status_trigger", new SpecialRecipeSerializer<>(StatusTriggerRecipe::new));
        INDUSTRY_CHARM = Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_industry_charm", new SpecialRecipeSerializer<>(IndustryCharmRecipe::new));
        INDUSTRY_CHARM_RELOAD = Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_industry_charm_reload", new SpecialRecipeSerializer<>(IndustryCharmReloadRecipe::new));

    }
    
}
