package net.tigereye.passivecharms.registration;

import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import net.tigereye.passivecharms.PassiveCharms;
import net.tigereye.passivecharms.items.ContingencyCharm;
import net.tigereye.passivecharms.items.MaintenanceCharm;
import net.tigereye.passivecharms.items.contingency_reactors.*;
import net.tigereye.passivecharms.items.contingency_triggers.*;

public class PC_Items {
    public static final Item MAINTENANCE_CHARM = new MaintenanceCharm();
    public static final Item CONTINGENCY_CHARM = new ContingencyCharm();
    public static final ContingencyCharmReaction FEATHERFALL_REACTOR = new Featherfall();
    public static final ContingencyCharmReaction FLAMEWARD_REACTOR = new FlameWard();
    public static final ContingencyCharmReaction GILLS_REACTOR = new Gills();
    public static final ContingencyCharmReaction REGENERATION_REACTOR = new Regeneration();
    public static final ContingencyCharmReaction RESTORATION_REACTOR = new Restoration();
    public static final ContingencyCharmReaction WARP_REACTOR = new Warp();
    public static final ContingencyCharmTrigger DROWNING_TRIGGER = new Drowning();
    public static final ContingencyCharmTrigger FREEFALL_TRIGGER = new Freefall();
    public static final ContingencyCharmTrigger IMMOLATION_TRIGGER = new Immolation();
    public static final ContingencyCharmTrigger INJURY_TRIGGER = new Injury();
    public static final ContingencyCharmTrigger LIGHT_INJURY_TRIGGER = new LightInjury();
    public static final ContingencyCharmTrigger OBLIVION_TRIGGER = new Oblivion();
    
    public static void register(){
		Registry.register(Registry.ITEM, PassiveCharms.MODID + ":" + "maintenance_charm", MAINTENANCE_CHARM);
		Registry.register(Registry.ITEM, PassiveCharms.MODID + ":" + "contingency_charm", CONTINGENCY_CHARM);
        Registry.register(Registry.ITEM, PassiveCharms.MODID + ":" + "contingency_charm_reaction_featherfall", FEATHERFALL_REACTOR);
        Registry.register(Registry.ITEM, PassiveCharms.MODID + ":" + "contingency_charm_reaction_flameward", FLAMEWARD_REACTOR);
        Registry.register(Registry.ITEM, PassiveCharms.MODID + ":" + "contingency_charm_reaction_gills", GILLS_REACTOR);
        Registry.register(Registry.ITEM, PassiveCharms.MODID + ":" + "contingency_charm_reaction_regeneration", REGENERATION_REACTOR);
        Registry.register(Registry.ITEM, PassiveCharms.MODID + ":" + "contingency_charm_reaction_restoration", RESTORATION_REACTOR);
        Registry.register(Registry.ITEM, PassiveCharms.MODID + ":" + "contingency_charm_reaction_warp", WARP_REACTOR);
        Registry.register(Registry.ITEM, PassiveCharms.MODID + ":" + "contingency_charm_trigger_drowning", DROWNING_TRIGGER);
        Registry.register(Registry.ITEM, PassiveCharms.MODID + ":" + "contingency_charm_trigger_freefall", FREEFALL_TRIGGER);
        Registry.register(Registry.ITEM, PassiveCharms.MODID + ":" + "contingency_charm_trigger_immolation", IMMOLATION_TRIGGER);
        Registry.register(Registry.ITEM, PassiveCharms.MODID + ":" + "contingency_charm_trigger_injury", INJURY_TRIGGER);
        Registry.register(Registry.ITEM, PassiveCharms.MODID + ":" + "contingency_charm_trigger_light_injury", LIGHT_INJURY_TRIGGER);
        Registry.register(Registry.ITEM, PassiveCharms.MODID + ":" + "contingency_charm_trigger_oblivion", OBLIVION_TRIGGER);

    }
}
