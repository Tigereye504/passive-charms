package net.tigereye.passivecharms.models;

import jdk.internal.jline.internal.Nullable;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelVariantProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.tigereye.passivecharms.PassiveCharms;

public class ItemModelProvider implements ModelVariantProvider {

    @Override
    public @Nullable UnbakedModel loadModelVariant(ModelIdentifier modelIdentifier, ModelProviderContext modelProviderContext) {
        if(modelIdentifier.getNamespace().equals(PassiveCharms.MODID)) {
            if (modelIdentifier.getPath().equals("contingency_charm")) {
                return new ContingencyCharmBakedModel(modelIdentifier);
            }
        }
        return null;
    }

}
