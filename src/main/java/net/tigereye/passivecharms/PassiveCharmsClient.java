package net.tigereye.passivecharms;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.tigereye.passivecharms.items.contingency_reactors.PotionReactor;
import net.tigereye.passivecharms.items.contingency_triggers.StatusTrigger;
import net.tigereye.passivecharms.models.ItemModelProvider;
import net.tigereye.passivecharms.registration.PCItems;

public class PassiveCharmsClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ModelLoadingRegistry.INSTANCE.registerVariantProvider(rm -> new ItemModelProvider());
		ColorProviderRegistry.ITEM.register(PotionReactor::getColor, PCItems.POTION_REACTOR);
		ColorProviderRegistry.ITEM.register(StatusTrigger::getColor, PCItems.STATUS_TRIGGER);
	}
}
