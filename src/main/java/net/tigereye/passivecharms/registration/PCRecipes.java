package net.tigereye.passivecharms.registration;

import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.registry.Registry;
import net.tigereye.passivecharms.items.IndustryCharm;
import net.tigereye.passivecharms.items.contingency_reactors.Featherfall;
import net.tigereye.passivecharms.items.contingency_triggers.Drowning;
import net.tigereye.passivecharms.items.contingency_triggers.Freefall;
import net.tigereye.passivecharms.items.contingency_triggers.LightInjury;
import net.tigereye.passivecharms.items.contingency_triggers.Oblivion;
import net.tigereye.passivecharms.recipes.*;

import java.util.function.Function;

public class PCRecipes {
    public static SpecialRecipeSerializer<ContingencyCharmRecipe> CONTINGENCY_CHARM;
    public static SpecialRecipeSerializer<FeatherfallReactorRecipe> FEATHERFALL_REACTOR;
    public static SpecialRecipeSerializer<FlamewardReactorRecipe> FLAMEWARD_REACTOR;
    public static SpecialRecipeSerializer<GillsReactorRecipe> GILLS_REACTOR;
    public static SpecialRecipeSerializer<RegenerationReactorRecipe> REGENERATION_REACTOR;
    public static SpecialRecipeSerializer<RestorationReactorRecipe> RESTORATION_REACTOR;
    public static SpecialRecipeSerializer<DrowningTriggerRecipe> DROWNING_TRIGGER;
    public static SpecialRecipeSerializer<FreefallTriggerRecipe> FREEFALL_TRIGGER;
    public static SpecialRecipeSerializer<ImmolationTriggerRecipe> IMMOLATION_TRIGGER;
    public static SpecialRecipeSerializer<InjuryTriggerRecipe> INJURY_TRIGGER;
    public static SpecialRecipeSerializer<LightInjuryTriggerRecipe> LIGHT_INJURY_TRIGGER;
    public static SpecialRecipeSerializer<IndustryCharmRecipe> INDUSTRY_CHARM;
    public static SpecialRecipeSerializer<IndustryCharmReloadRecipe> INDUSTRY_CHARM_RELOAD;

    public static void register(){
        CONTINGENCY_CHARM = (SpecialRecipeSerializer<ContingencyCharmRecipe>) Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_contingency_charm", new SpecialRecipeSerializer<ContingencyCharmRecipe>(ContingencyCharmRecipe::new));
        FEATHERFALL_REACTOR = (SpecialRecipeSerializer<FeatherfallReactorRecipe>) Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_featherfall_reactor", new SpecialRecipeSerializer<FeatherfallReactorRecipe>(FeatherfallReactorRecipe::new));
        FLAMEWARD_REACTOR = (SpecialRecipeSerializer<FlamewardReactorRecipe>) Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_flameward_reactor", new SpecialRecipeSerializer<FlamewardReactorRecipe>(FlamewardReactorRecipe::new));
        GILLS_REACTOR = (SpecialRecipeSerializer<GillsReactorRecipe>) Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_gills_reactor", new SpecialRecipeSerializer<GillsReactorRecipe>(GillsReactorRecipe::new));
        REGENERATION_REACTOR = (SpecialRecipeSerializer<RegenerationReactorRecipe>) Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_regeneration_reactor", new SpecialRecipeSerializer<RegenerationReactorRecipe>(RegenerationReactorRecipe::new));
        RESTORATION_REACTOR = (SpecialRecipeSerializer<RestorationReactorRecipe>) Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_restoration_reactor", new SpecialRecipeSerializer<RestorationReactorRecipe>(RestorationReactorRecipe::new));
        DROWNING_TRIGGER = (SpecialRecipeSerializer<DrowningTriggerRecipe>) Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_drowning_trigger", new SpecialRecipeSerializer<DrowningTriggerRecipe>(DrowningTriggerRecipe::new));
        FREEFALL_TRIGGER = (SpecialRecipeSerializer<FreefallTriggerRecipe>) Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_freefall_trigger", new SpecialRecipeSerializer<FreefallTriggerRecipe>(FreefallTriggerRecipe::new));
        IMMOLATION_TRIGGER = (SpecialRecipeSerializer<ImmolationTriggerRecipe>) Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_immolation_trigger", new SpecialRecipeSerializer<ImmolationTriggerRecipe>(ImmolationTriggerRecipe::new));
        INJURY_TRIGGER = (SpecialRecipeSerializer<InjuryTriggerRecipe>) Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_injury_trigger", new SpecialRecipeSerializer<InjuryTriggerRecipe>(InjuryTriggerRecipe::new));
        LIGHT_INJURY_TRIGGER = (SpecialRecipeSerializer<LightInjuryTriggerRecipe>) Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_light_injury_trigger", new SpecialRecipeSerializer<LightInjuryTriggerRecipe>(LightInjuryTriggerRecipe::new));
        INDUSTRY_CHARM = (SpecialRecipeSerializer<IndustryCharmRecipe>) Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_industry_charm", new SpecialRecipeSerializer<IndustryCharmRecipe>(IndustryCharmRecipe::new));
        INDUSTRY_CHARM_RELOAD = (SpecialRecipeSerializer<IndustryCharmReloadRecipe>) Registry.register(Registry.RECIPE_SERIALIZER, "crafting_special_industry_charm_reload", new SpecialRecipeSerializer<IndustryCharmReloadRecipe>(IndustryCharmReloadRecipe::new));

    }
    
}
