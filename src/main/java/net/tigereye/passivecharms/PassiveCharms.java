package net.tigereye.passivecharms;

import net.fabricmc.api.ModInitializer;
import net.tigereye.passivecharms.registration.PCItems;
import net.tigereye.passivecharms.registration.PCRecipes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PassiveCharms implements ModInitializer{
	public static final String MODID = "passivecharms";
	public static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	public void onInitialize() {
		PCItems.register();
		PCRecipes.register();
	}
}
