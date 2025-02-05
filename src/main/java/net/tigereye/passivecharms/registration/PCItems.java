package net.tigereye.passivecharms.registration;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.tigereye.passivecharms.PassiveCharms;
import net.tigereye.passivecharms.items.ContingencyCharm;
import net.tigereye.passivecharms.items.IndustryCharm;
import net.tigereye.passivecharms.items.MaintenanceCharm;
import net.tigereye.passivecharms.items.contingency_reactors.LightReactor;
import net.tigereye.passivecharms.items.contingency_reactors.PotionReactor;
import net.tigereye.passivecharms.items.contingency_reactors.PurityReactor;
import net.tigereye.passivecharms.items.contingency_reactors.Warp;
import net.tigereye.passivecharms.items.contingency_reactors.deprecated.*;
import net.tigereye.passivecharms.items.contingency_triggers.*;

import java.util.ArrayList;
import java.util.List;

public class PCItems {
    public static List<Identifier> CONTINGENCY_CHARM_REACTORS = new ArrayList<>();
    public static List<Identifier> CONTINGENCY_CHARM_TRIGGERS = new ArrayList<>();
    public static final Item INDUSTRY_CHARM = new IndustryCharm();
    public static final Item MAINTENANCE_CHARM = new MaintenanceCharm();
    public static final Item CONTINGENCY_CHARM = new ContingencyCharm();
    public static final Item POTION_REACTOR = new PotionReactor();
    public static final Item FEATHERFALL_REACTOR = new FeatherfallReactor();
    public static final Item FLAMEWARD_REACTOR = new FlameWardReactor();
    public static final Item GILLS_REACTOR = new GillsReactor();
    public static final Item LIGHT_REACTOR = new LightReactor();
    public static final Item PURITY_REACTOR = new PurityReactor();
    public static final Item REGENERATION_REACTOR = new RegenerationReactor();
    public static final Item RESTORATION_REACTOR = new RestorationReactor();
    public static final Item WARP_REACTOR = new Warp();
    public static final Item DARKNESS_TRIGGER = new DarknessTrigger();
    public static final Item DROWNING_TRIGGER = new DrowningTrigger();
    public static final Item FREEFALL_TRIGGER = new FreefallTrigger();
    public static final Item IMMOLATION_TRIGGER = new ImmolationTrigger();
    public static final Item INJURY_TRIGGER = new InjuryTrigger();
    public static final Item LIGHT_INJURY_TRIGGER = new LightInjuryTrigger();
    public static final Item OBLIVION_TRIGGER = new OblivionTrigger();
    public static final Item STATUS_TRIGGER = new StatusTrigger();
    
    public static void register(){
		Registry.register(Registry.ITEM, new Identifier(PassiveCharms.MODID,"maintenance_charm"), MAINTENANCE_CHARM);
        Registry.register(Registry.ITEM, new Identifier(PassiveCharms.MODID + ":" + "industry_charm"), INDUSTRY_CHARM);
		Registry.register(Registry.ITEM, new Identifier(PassiveCharms.MODID + ":" + "contingency_charm"), CONTINGENCY_CHARM);
        registerContingencyReactor("potion", POTION_REACTOR);
        registerContingencyReactor("featherfall", FEATHERFALL_REACTOR);     //deprecated, but needs left in at least for now
        registerContingencyReactor("flameward", FLAMEWARD_REACTOR);         //deprecated, but needs left in at least for now
        registerContingencyReactor("gills", GILLS_REACTOR);                 //deprecated, but needs left in at least for now
        registerContingencyReactor("regeneration", REGENERATION_REACTOR);   //deprecated, but needs left in at least for now
        registerContingencyReactor("restoration", RESTORATION_REACTOR);     //deprecated, but needs left in at least for now
        registerContingencyReactor("light", LIGHT_REACTOR);
        registerContingencyReactor("purity", PURITY_REACTOR);
        registerContingencyReactor("warp", WARP_REACTOR);
        registerContingencyTrigger("darkness", DARKNESS_TRIGGER);
        registerContingencyTrigger("drowning", DROWNING_TRIGGER);
        registerContingencyTrigger("freefall", FREEFALL_TRIGGER);
        registerContingencyTrigger("immolation", IMMOLATION_TRIGGER);
        registerContingencyTrigger("injury", INJURY_TRIGGER);
        registerContingencyTrigger("light_injury", LIGHT_INJURY_TRIGGER);
        registerContingencyTrigger("oblivion", OBLIVION_TRIGGER);
        registerContingencyTrigger("status", STATUS_TRIGGER);

    }

    public static void registerContingencyReactor(String name,Item reactor){
        Identifier id = new Identifier(PassiveCharms.MODID,"contingency_charm_reaction_"+name);
        CONTINGENCY_CHARM_REACTORS.add(id);
        Registry.register(Registry.ITEM, id, reactor);
    }
    public static void registerContingencyTrigger(String name,Item trigger){
        Identifier id = new Identifier(PassiveCharms.MODID,"contingency_charm_trigger_"+name);
        CONTINGENCY_CHARM_TRIGGERS.add(id);
        Registry.register(Registry.ITEM, id, trigger);
    }
}
