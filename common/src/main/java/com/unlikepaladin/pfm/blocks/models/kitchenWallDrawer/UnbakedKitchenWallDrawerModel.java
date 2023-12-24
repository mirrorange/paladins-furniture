package com.unlikepaladin.pfm.blocks.models.kitchenWallDrawer;

import com.unlikepaladin.pfm.PaladinFurnitureMod;
import com.unlikepaladin.pfm.data.materials.*;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class UnbakedKitchenWallDrawerModel implements UnbakedModel {
    public static final Identifier[] COUNTER_MODEL_PARTS_BASE = new Identifier[] {
            new Identifier(PaladinFurnitureMod.MOD_ID, "block/kitchen_drawer/kitchen_drawer_middle"),
            new Identifier(PaladinFurnitureMod.MOD_ID, "block/kitchen_drawer/kitchen_drawer_middle_inner_corner_left"),
            new Identifier(PaladinFurnitureMod.MOD_ID, "block/kitchen_drawer/kitchen_drawer_middle_inner_corner_right"),
            new Identifier(PaladinFurnitureMod.MOD_ID, "block/kitchen_drawer/kitchen_drawer_middle_outer_corner_left"),
            new Identifier(PaladinFurnitureMod.MOD_ID, "block/kitchen_drawer/kitchen_drawer_middle_outer_corner_right"),
            new Identifier(PaladinFurnitureMod.MOD_ID, "block/kitchen_drawer/kitchen_drawer_middle_open"),
            new Identifier(PaladinFurnitureMod.MOD_ID, "block/kitchen_drawer/kitchen_drawer_middle_inner_corner_left"),
            new Identifier(PaladinFurnitureMod.MOD_ID, "block/kitchen_drawer/kitchen_drawer_middle_inner_corner_right"),
            new Identifier(PaladinFurnitureMod.MOD_ID, "block/kitchen_drawer/kitchen_drawer_middle_outer_corner_open_left"),
            new Identifier(PaladinFurnitureMod.MOD_ID, "block/kitchen_drawer/kitchen_drawer_middle_outer_corner_open_right")
    };


    private static final Identifier PARENT = new Identifier("block/block");
    public static final Identifier DRAWER_MODEL_ID = new Identifier(PaladinFurnitureMod.MOD_ID, "block/kitchen_wall_drawer");
    public static final List<Identifier> DRAWER_MODEL_IDS = new ArrayList<>() {
        {
            for(WoodVariant variant : WoodVariantRegistry.getVariants()){
                add(new Identifier(PaladinFurnitureMod.MOD_ID, "item/" + variant.asString() + "_kitchen_wall_drawer"));
                if (variant.hasStripped())
                    add(new Identifier(PaladinFurnitureMod.MOD_ID, "item/stripped_" + variant.asString() + "_kitchen_wall_drawer"));
            }
            for(StoneVariant variant : StoneVariant.values()){
                if (variant.equals(StoneVariant.QUARTZ))
                    continue;
                add(new Identifier(PaladinFurnitureMod.MOD_ID, "item/" + variant.asString() + "_kitchen_wall_drawer"));
            }
            for(ExtraCounterVariant variant : ExtraCounterVariant.values()){
                add(new Identifier(PaladinFurnitureMod.MOD_ID, "item/" + variant.asString() + "_kitchen_wall_drawer"));
            }
            add(DRAWER_MODEL_ID);
        }
    };

    @Override
    public Collection<Identifier> getModelDependencies() {
        return List.of(PARENT);
    }

    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<com.mojang.datafixers.util.Pair<String, String>> unresolvedTextureReferences) {
        return Collections.emptyList();
    }

    public static final Map<ModelBakeSettings, List<BakedModel>> CACHED_MODELS = new ConcurrentHashMap<>();
    @Nullable
    @Override
    public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        if (CACHED_MODELS.containsKey(rotationContainer))
            return getBakedModel(rotationContainer, CACHED_MODELS.get(rotationContainer));

        List<BakedModel> bakedModelList = new ArrayList<>();
        for (Identifier modelPart : COUNTER_MODEL_PARTS_BASE) {
            bakedModelList.add(loader.bake(modelPart, rotationContainer));
        }
        CACHED_MODELS.put(rotationContainer, bakedModelList);
        return getBakedModel(rotationContainer, bakedModelList);
    }

    @ExpectPlatform
    public static BakedModel getBakedModel(ModelBakeSettings settings, List<BakedModel> modelParts) {
        throw new RuntimeException("Method wasn't replaced correctly");
    }
}