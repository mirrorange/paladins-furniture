package com.unlikepaladin.pfm.mixin.fabric;

import com.google.common.base.Suppliers;
import com.unlikepaladin.pfm.client.PathPackRPWrapper;
import com.unlikepaladin.pfm.runtime.PFMRuntimeResources;
import net.minecraft.SharedConstants;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.PackResourceMetadata;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = ReloadableResourceManagerImpl.class)
public class PFMReloadableResourceManagerImplMixin {

    @ModifyVariable(at = @At(value = "HEAD"), method = "reload", argsOnly = true)
    private List<ResourcePack> createReload(List<ResourcePack> packs) {
        PFMRuntimeResources.RESOURCE_PACK_LIST = packs;
        List<ResourcePack> resourcePacks = new ArrayList<>(packs);
        PackResourceMetadata packResourceMetadata = new PackResourceMetadata(Text.literal("pfm-runtime-resources"), SharedConstants.getGameVersion().getResourceVersion(ResourceType.CLIENT_RESOURCES));
        resourcePacks.add(new PathPackRPWrapper(Suppliers.memoize(() -> {
            PFMRuntimeResources.prepareAndRunResourceGen(false); return PFMRuntimeResources.ASSETS_PACK;}), packResourceMetadata));
        return resourcePacks;
    }
}