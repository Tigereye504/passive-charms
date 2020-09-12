package net.tigereye.passivecharms.models;

import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;

public class ContingencyCharmModelProvider implements ModelResourceProvider {
    public static final Identifier CONTINGENCY_CHARM_MODEL = new Identifier("passivecharms:item/contingency_charm_model");
    @Override
    public UnbakedModel loadModelResource(Identifier identifier, ModelProviderContext modelProviderContext){// throws ModelProviderException {
        if(identifier.equals(CONTINGENCY_CHARM_MODEL)) {
            return new ContingencyCharmModel();
        } else {
            return null;
        }
    }
}
