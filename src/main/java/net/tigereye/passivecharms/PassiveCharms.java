package net.tigereye.passivecharms;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.impl.client.model.ModelLoadingRegistryImpl;
import net.tigereye.passivecharms.models.ItemModelProvider;
import net.tigereye.passivecharms.registration.PCItems;
import net.tigereye.passivecharms.registration.PCRecipes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PassiveCharms implements ModInitializer, ClientModInitializer {
	public static final String MODID = "passivecharms";
	public static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	public void onInitialize() {
		PCItems.register();
		PCRecipes.register();
	}

	@Override
	public void onInitializeClient() {
		ModelLoadingRegistry.INSTANCE.registerVariantProvider(rm -> new ItemModelProvider());
	}
}
