package com.unlikepaladin.pfm.blocks.models.modernCoffeeTable.forge;

import com.unlikepaladin.pfm.blocks.models.modernDinnerTable.forge.ForgeModernDinnerTableModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UnbakedModernCoffeeTableModelImpl {
    static Map<ModelBakeSettings, BakedModel> modelMap = new ConcurrentHashMap<>();
    public static BakedModel getBakedModel(ModelBakeSettings settings, List<BakedModel> modelParts) {
        if (modelMap.containsKey(settings))
            return modelMap.get(settings);
        BakedModel model = new ForgeModernCoffeeTableModel(settings, modelParts);
        modelMap.put(settings, model);
        return model;
    }
}
