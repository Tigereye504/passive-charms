package net.tigereye.passivecharms.models;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
//import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
//import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import net.tigereye.passivecharms.registration.PC_Items;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class ContingencyCharmModel implements UnbakedModel, BakedModel, FabricBakedModel {

    //private static final SpriteIdentifier[] SPRITE_IDS = new SpriteIdentifier[]{
    //};
    //private Sprite[] SPRITES = new Sprite[0];
    private Mesh mesh;
    private static final Identifier DEFAULT_ITEM_MODEL = new Identifier("minecraft:item/item");
    private ModelTransformation transformation;

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext renderContext)
    {}//its not a block, so I don't think I need anything here?

    @Override
    public void emitItemQuads(ItemStack itemStack, Supplier<Random> supplier, RenderContext renderContext) {
        Item Reaction = ItemStack.fromTag(itemStack.getSubTag("ReactionItem")).getItem();
        Item Trigger = ItemStack.fromTag(itemStack.getSubTag("TriggerItem")).getItem();
        if(Reaction != null && Trigger != null){
            renderContext.fallbackConsumer().accept(MinecraftClient.getInstance().getItemRenderer().getModels().getModel(Trigger));
            renderContext.fallbackConsumer().accept(MinecraftClient.getInstance().getItemRenderer().getModels().getModel(Reaction));
        }
        else{
            //this should make the defective charm look about as useful as waterbreathing in the void.
            renderContext.fallbackConsumer().accept(MinecraftClient.getInstance().getItemRenderer().getModels().getModel(PC_Items.OBLIVION_TRIGGER));
            renderContext.fallbackConsumer().accept(MinecraftClient.getInstance().getItemRenderer().getModels().getModel(PC_Items.GILLS_REACTOR));
        }
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction face, Random random) {
        return null;
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
    public Sprite getSprite() {
        return null;
    }

    @Override
    public ModelTransformation getTransformation() {
        return transformation;
    }

    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return Arrays.asList(DEFAULT_ITEM_MODEL);
    }

    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
        return Collections.emptyList();
    }

    @Override
    public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        // Load the default block model
        JsonUnbakedModel defaultItemModel = (JsonUnbakedModel) loader.getOrLoadModel(DEFAULT_ITEM_MODEL);
        // Get its ModelTransformation
        transformation = defaultItemModel.getTransformations();

        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        MeshBuilder builder = renderer.meshBuilder();
        //QuadEmitter emitter = builder.getEmitter();
        //for(Direction direction : Direction.values()) {
        //    int spriteIdx = direction == Direction.UP || direction == Direction.DOWN ? 1 : 0;
        //    // Add a new face to the mesh
        //    emitter.square(direction, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f);
        //    // Set the sprite of the face, must be called after .square()
        //    // We haven't specified any UV coordinates, so we want to use the whole texture. BAKE_LOCK_UV does exactly that.
        //    emitter.spriteBake(0, SPRITES[spriteIdx], MutableQuadView.BAKE_LOCK_UV);
        //    // Enable texture usage
        //    emitter.spriteColor(0, -1, -1, -1, -1);
        //    // Add the quad to the mesh
        //    emitter.emit();
        //}
        mesh = builder.build();
        return this;
    }
}
