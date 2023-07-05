package net.tigereye.passivecharms;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.tigereye.passivecharms.models.ItemModelProvider;
import net.tigereye.passivecharms.registration.PCItems;
import net.tigereye.passivecharms.registration.PCRecipes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PassiveCharmsClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ModelLoadingRegistry.INSTANCE.registerVariantProvider(rm -> new ItemModelProvider());
	}
}
