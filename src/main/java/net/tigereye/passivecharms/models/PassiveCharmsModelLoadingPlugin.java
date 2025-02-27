package net.tigereye.passivecharms.models;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.util.Identifier;
import net.tigereye.passivecharms.PassiveCharms;

public class PassiveCharmsModelLoadingPlugin implements ModelLoadingPlugin {
    public static final Identifier CONTINGENCY_CHARM_MODEL = Identifier.of(PassiveCharms.MODID,"contingency_charm");
    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        pluginContext.modifyModelAfterBake().register((original, context) -> {
            if (CONTINGENCY_CHARM_MODEL.equals(context.id())) {
                return new ContingencyCharmBakedModel();
            }
            return original;
        });
    }
}
