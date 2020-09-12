package net.tigereye.passivecharms;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.tigereye.passivecharms.models.ContingencyCharmModelProvider;
import net.tigereye.passivecharms.registration.PC_Items;
import net.tigereye.passivecharms.registration.PC_Recipes;

public class PassiveCharms implements ModInitializer, ClientModInitializer {
	public static final String MODID = "passivecharms";
	
	@Override
	public void onInitialize() {
		PC_Items.register();
		PC_Recipes.register();
	}

	@Override
	public void onInitializeClient() {
		//ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> new ContingencyCharmModelProvider());
	}
}
