package net.tigereye.passivecharms.models;

import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import net.tigereye.passivecharms.PassiveCharms;
import net.tigereye.passivecharms.items.ContingencyCharm;
import net.tigereye.passivecharms.registration.PCItems;
import org.jetbrains.annotations.Nullable;
import java.util.*;
import java.util.function.Supplier;

public class ContingencyCharmBakedModel implements FabricBakedModel, BakedModel {

    private static final SpriteIdentifier DEFAULT_SPRITE_ID = new SpriteIdentifier(PlayerScreenHandler.EMPTY_OFFHAND_ARMOR_SLOT,Identifier.of(PassiveCharms.MODID,"item/contingency_charm"));
    public ContingencyCharmBakedModel() {

    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<net.minecraft.util.math.random.Random> randomSupplier, RenderContext context) {
        ItemStack trigger;
        ItemStack reactor;
        try {
            trigger = ContingencyCharm.loadTriggerFromNBT(stack);
            reactor = ContingencyCharm.loadReactionFromNBT(stack);
            if(trigger == null){
                trigger = PCItems.INJURY_TRIGGER.getDefaultStack();
            }
            if(reactor == null){
                reactor = PCItems.RESTORATION_REACTOR.getDefaultStack();
            }
        }
        catch(NullPointerException e){
            trigger = PCItems.INJURY_TRIGGER.getDefaultStack();
            reactor = PCItems.RESTORATION_REACTOR.getDefaultStack();
        }
        try {
            MinecraftClient.getInstance().getItemRenderer().getModels().getModel(trigger).emitItemQuads(trigger, randomSupplier, context);
        }
        catch(NullPointerException e){
            PassiveCharms.LOGGER.error("Failed to model trigger.");
        }
        try {
            MinecraftClient.getInstance().getItemRenderer().getModels().getModel(reactor).emitItemQuads(reactor,randomSupplier,context);
        }
        catch(NullPointerException e){
            PassiveCharms.LOGGER.error("Failed to model reactor.");
        }
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<net.minecraft.util.math.random.Random> randomSupplier, RenderContext context) {}

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }


    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return List.of();
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
        return DEFAULT_SPRITE_ID.getSprite();
    }

    @Override
    public ModelTransformation getTransformation() {
        return ModelTransformation.NONE;
    }

    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }
}