package net.tigereye.passivecharms.registration;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
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

public class PCItems {

    public static final Item.Settings TRIGGER_SETTINGS = new Item.Settings().maxCount(1);
    public static final Item.Settings REACTOR_SETTINGS = new Item.Settings().maxCount(1).maxDamage(ContingencyCharm.DURABILITY);

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
    public static final Item NOT_TRIGGER = new NotTrigger();
    public static final Item AND_TRIGGER = new AndTrigger();
    public static final Item OR_TRIGGER = new OrTrigger();
    public static final Item OBLIVION_TRIGGER = new OblivionTrigger();
    public static final Item STATUS_TRIGGER = new StatusTrigger();

    public static void register(){
	 	registerItem("maintenance_charm", MAINTENANCE_CHARM, ItemGroups.TOOLS);
        registerItem("industry_charm", INDUSTRY_CHARM, ItemGroups.TOOLS);
        registerItem("contingency_charm", CONTINGENCY_CHARM, ItemGroups.TOOLS);
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
        registerContingencyTrigger("not", NOT_TRIGGER);
        registerContingencyTrigger("and", AND_TRIGGER);
        registerContingencyTrigger("or", OR_TRIGGER);
        registerContingencyTrigger("oblivion", OBLIVION_TRIGGER);
        registerContingencyTrigger("status", STATUS_TRIGGER);
    }

    public static void registerContingencyReactor(String name,Item reactor){
        Identifier id = new Identifier(PassiveCharms.MODID,"contingency_charm_reaction_"+name);
        registerItem(id,reactor,ItemGroups.TOOLS);
    }
    public static void registerContingencyTrigger(String name,Item trigger){
        Identifier id = new Identifier(PassiveCharms.MODID,"contingency_charm_trigger_"+name);
        registerItem(id,trigger,ItemGroups.TOOLS);
    }
    public static void registerItem(String name, Item item, RegistryKey<ItemGroup> itemGroup){
        registerItem(new Identifier(PassiveCharms.MODID,name),item,itemGroup);
    }
    public static void registerItem(Identifier id, Item item, RegistryKey<ItemGroup> itemGroup){
        Registry.register(Registries.ITEM, id, item);
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.add(item));
    }
}
