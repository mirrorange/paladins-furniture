package com.unlikepaladin.pfm.compat.imm_ptl;

import com.unlikepaladin.pfm.compat.imm_ptl.entity.PFMMirrorEntity;
import com.unlikepaladin.pfm.registry.PaladinFurnitureModBlocksItems;
import com.unlikepaladin.pfm.registry.fabric.EntityRegistryFabric;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

public class PFMImmPtlRegistry {
    public static final EntityType<PFMMirrorEntity> MIRROR = EntityType.Builder.create(PFMMirrorEntity::new, SpawnGroup.MISC).setDimensions(0.0F, 0.0F).makeFireImmune().disableSummon().build("mirror_entity");
    public static void register() {
        PaladinFurnitureModBlocksItems.WHITE_MIRROR = new PFMMirrorBlockIP(AbstractBlock.Settings.of(Material.STONE, MapColor.WHITE).nonOpaque());
        PaladinFurnitureModBlocksItems.GRAY_MIRROR = new PFMMirrorBlockIP(AbstractBlock.Settings.of(Material.STONE, MapColor.GRAY).nonOpaque());
        EntityRegistryFabric.registerEntity("mirror_entity", MIRROR);
    }
}