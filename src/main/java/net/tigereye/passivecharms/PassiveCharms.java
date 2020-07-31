package net.tigereye.passivecharms;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import net.tigereye.passivecharms.items.MaintenanceCharm;

public class PassiveCharms implements ModInitializer {
	public static final String MODID = "passivecharms";
	public static final Item maintenanceCharm = new MaintenanceCharm();
	
	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, PassiveCharms.MODID + ":" + "maintenance_charm", maintenanceCharm);
	}
}
