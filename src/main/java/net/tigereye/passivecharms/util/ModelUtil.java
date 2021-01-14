package net.tigereye.passivecharms.util;

import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.tigereye.passivecharms.PassiveCharms;

public class ModelUtil {
    public static ModelIdentifier inventoryModelID(Identifier id) {
        return new ModelIdentifier(id, "inventory");
    }
}
