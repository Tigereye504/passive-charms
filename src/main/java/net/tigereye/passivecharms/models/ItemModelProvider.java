package net.tigereye.passivecharms.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelVariantProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.tigereye.passivecharms.PassiveCharms;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ItemModelProvider implements ModelVariantProvider {

    @Override
    public @Nullable UnbakedModel loadModelVariant(ModelIdentifier modelIdentifier, ModelProviderContext modelProviderContext) {
        if(modelIdentifier.getNamespace().equals(PassiveCharms.MODID)) {
            if (modelIdentifier.getPath().equals("contingency_charm")) {
                return new ContingencyCharmBakedModel();
            }
        }
        return null;
    }

}
