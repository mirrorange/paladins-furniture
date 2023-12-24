package com.unlikepaladin.pfm.blocks.models.kitchenWallCounter.forge;

import com.unlikepaladin.pfm.blocks.models.kitchenDrawer.forge.ForgeKitchenDrawerModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.texture.Sprite;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UnbakedKitchenWallCounterModelImpl {
    static Map<ModelBakeSettings, BakedModel> modelMap = new ConcurrentHashMap<>();
    public static BakedModel getBakedModel(ModelBakeSettings settings, List<BakedModel> modelParts) {
        if (modelMap.containsKey(settings))
            return modelMap.get(settings);
        BakedModel model = new ForgeKitchenWallCounterModel(settings, modelParts);
        modelMap.put(settings, model);
        return model;
    }
}
