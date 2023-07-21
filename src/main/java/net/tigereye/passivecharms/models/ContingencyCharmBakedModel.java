package net.tigereye.passivecharms.models;

import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.Resource;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;
import net.tigereye.passivecharms.PassiveCharms;
import net.tigereye.passivecharms.items.ContingencyCharm;
import net.tigereye.passivecharms.registration.PCItems;
import net.tigereye.passivecharms.util.ModelUtil;
import org.apache.commons.io.Charsets;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class ContingencyCharmBakedModel implements FabricBakedModel, BakedModel, UnbakedModel {

    private static final HashMap<String, FabricBakedModel> TRIGGER_MODELS = new HashMap<>();
    private static final HashMap<String, FabricBakedModel> REACTOR_MODELS = new HashMap<>();
    private final ModelIdentifier modelIdentifier;

    public ContingencyCharmBakedModel(ModelIdentifier modelIdentifier) {
        this.modelIdentifier = modelIdentifier;
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<net.minecraft.util.math.random.Random> randomSupplier, RenderContext context) {
        String triggerString;
        String reactorString;
        ItemStack trigger;
        ItemStack reactor;
        try {
            trigger = ContingencyCharm.loadTriggerFromNBT(stack);
            reactor = ContingencyCharm.loadReactionFromNBT(stack);
            triggerString = Registry.ITEM.getId(trigger.getItem()).toString();
            reactorString = Registry.ITEM.getId(reactor.getItem()).toString();
        }
        catch(NullPointerException e){
            trigger = PCItems.INJURY_TRIGGER.getDefaultStack();
            reactor = PCItems.RESTORATION_REACTOR.getDefaultStack();
            triggerString = Registry.ITEM.getId(PCItems.INJURY_TRIGGER).toString();
            reactorString = Registry.ITEM.getId(PCItems.RESTORATION_REACTOR).toString();
        }
        if(TRIGGER_MODELS.containsKey(triggerString)) {
            TRIGGER_MODELS.get(triggerString).emitItemQuads(trigger,null,context);
        }
        else {
            PassiveCharms.LOGGER.error("Failed to model trigger.");
        }
        if(REACTOR_MODELS.containsKey(reactorString)) {
            REACTOR_MODELS.get(reactorString).emitItemQuads(reactor,null,context);
        }
        else {
            PassiveCharms.LOGGER.error("Failed to model reactor.");
        }
    }

    public static ModelTransformation loadTransformFromJson(Identifier location) {
        try {
            return JsonUnbakedModel.deserialize(getReaderForResource(location)).getTransformations();
        } catch (IOException exception) {
            PassiveCharms.LOGGER.warn("Can't load resource " + location);
            exception.printStackTrace();
            return null;
        }
    }

    public static ModelTransformation loadTransformFromJsonString(String json) {
        return JsonUnbakedModel.deserialize(json).getTransformations();
    }

    public static Reader getReaderForResource(Identifier location) throws IOException {
        Identifier file = new Identifier(location.getNamespace(), location.getPath() + ".json");
        Resource resource = MinecraftClient.getInstance().getResourceManager().getResource(file).orElse(null);
        if(resource != null) {
            return new BufferedReader(new InputStreamReader(resource.getInputStream(), Charsets.UTF_8));
        }
        return null;
    }

    @Override
    public @Nullable
    BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        if (TRIGGER_MODELS.isEmpty()) {
            for (Identifier id : PCItems.CONTINGENCY_CHARM_TRIGGERS) {
                TRIGGER_MODELS.put(id.toString(), (FabricBakedModel) loader.bake(ModelUtil.inventoryModelID(id), ModelRotation.X0_Y0));
            }
        }
        if (REACTOR_MODELS.isEmpty()) {
            for (Identifier id : PCItems.CONTINGENCY_CHARM_REACTORS) {
                REACTOR_MODELS.put(id.toString(), (FabricBakedModel) loader.bake(ModelUtil.inventoryModelID(id), ModelRotation.X0_Y0));
            }
        }
        return this;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return Collections.emptyList();
    }

    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<com.mojang.datafixers.util.Pair<String, String>> unresolvedTextureReferences) {
        return Collections.emptyList();
    }


    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<net.minecraft.util.math.random.Random> randomSupplier, RenderContext context) {}


    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, net.minecraft.util.math.random.Random random) {
        return new ArrayList<>();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean hasDepth() {
        return false;
    }

    @Override
    public boolean isSideLit() {
        return false;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public Sprite getParticleSprite() {
        return MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(new Identifier("block/cobblestone"));
    }

    @Override
    public ModelTransformation getTransformation() {
        String model = modelIdentifier.getNamespace() + ":" + modelIdentifier.getPath();
        //if (ItemRegistry.MODELS.containsKey(model)) {
        //    String json = ItemRegistry.MODELS.get(model);
        //    return loadTransformFromJsonString(json);
        //}
        return loadTransformFromJson(new Identifier("minecraft:models/item/handheld"));
    }

    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }



}