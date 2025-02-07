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
import net.minecraft.world.BlockRenderView;
import net.tigereye.passivecharms.PassiveCharms;
import net.tigereye.passivecharms.items.ContingencyCharm;
import net.tigereye.passivecharms.registration.PCItems;
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


    public ContingencyCharmBakedModel() {

    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<net.minecraft.util.math.random.Random> randomSupplier, RenderContext context) {
        ItemStack trigger;
        ItemStack reactor;
        try {
            trigger = ContingencyCharm.loadTriggerFromNBT(stack);
            reactor = ContingencyCharm.loadReactionFromNBT(stack);
        }
        catch(NullPointerException e){
            trigger = PCItems.INJURY_TRIGGER.getDefaultStack();
            reactor = PCItems.RESTORATION_REACTOR.getDefaultStack();
        }
        try {
            ((FabricBakedModel) (MinecraftClient.getInstance().getItemRenderer().getModels().getModel(trigger))).emitItemQuads(trigger, randomSupplier, context);
        }
        catch(NullPointerException e){
            PassiveCharms.LOGGER.error("Failed to model trigger.");
        }
        try {
            ((FabricBakedModel)(MinecraftClient.getInstance().getItemRenderer().getModels().getModel(reactor))).emitItemQuads(reactor,randomSupplier,context);
        }
        catch(NullPointerException e){
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

    public static Reader getReaderForResource(Identifier location) throws IOException {
        Identifier file = new Identifier(location.getNamespace(), location.getPath() + ".json");
        Resource resource = MinecraftClient.getInstance().getResourceManager().getResource(file).orElse(null);
        if(resource != null) {
            return new BufferedReader(new InputStreamReader(resource.getInputStream(), Charsets.UTF_8));
        }
        return null;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return Collections.emptyList();
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {

    }

    @Override
    public @Nullable BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        return this;
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