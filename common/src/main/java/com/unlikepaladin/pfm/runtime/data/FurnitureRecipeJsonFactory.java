package com.unlikepaladin.pfm.runtime.data;


import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.unlikepaladin.pfm.recipes.FurnitureRecipe;
import com.unlikepaladin.pfm.registry.RecipeTypes;
import net.minecraft.advancement.*;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FurnitureRecipeJsonFactory implements CraftingRecipeJsonBuilder {
    private final Item output;
    private final int outputCount;
    private final DefaultedList<Ingredient> inputs = DefaultedList.of();
    private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<>();
    private boolean showNotification = true;
    @NotNull
    private NbtCompound nbtElement;
    @Nullable
    private String group;

    public FurnitureRecipeJsonFactory(ItemConvertible output, int outputCount) {
        this.output = output.asItem();
        this.outputCount = outputCount;
        this.nbtElement = new NbtCompound();
    }

    public FurnitureRecipeJsonFactory(ItemConvertible output, int outputCount, @NotNull NbtCompound nbtElement) {
        this.output = output.asItem();
        this.outputCount = outputCount;
        this.nbtElement = nbtElement;
    }

    public static FurnitureRecipeJsonFactory create(ItemConvertible output, int count, NbtCompound nbtElement) {
        return new FurnitureRecipeJsonFactory(output, count, nbtElement);
    }

    public static FurnitureRecipeJsonFactory create(ItemConvertible output, NbtCompound nbtElement) {
        return new FurnitureRecipeJsonFactory(output, 1, nbtElement);
    }

    public static FurnitureRecipeJsonFactory create(ItemConvertible output) {
        return new FurnitureRecipeJsonFactory(output, 1);
    }
    public static FurnitureRecipeJsonFactory create(ItemConvertible output, int count) {
        return new FurnitureRecipeJsonFactory(output, count);
    }
    public FurnitureRecipeJsonFactory input(TagKey<Item> tag) {
        return this.input(Ingredient.fromTag(tag));
    }

    public FurnitureRecipeJsonFactory input(ItemConvertible itemProvider) {
        return this.input(itemProvider, 1);
    }

    public FurnitureRecipeJsonFactory input(ItemConvertible itemProvider, int size) {
        for (int i = 0; i < size; ++i) {
            this.input(Ingredient.ofItems(itemProvider));
        }
        return this;
    }

    public FurnitureRecipeJsonFactory input(Ingredient ingredient) {
        return this.input(ingredient, 1);
    }

    public FurnitureRecipeJsonFactory input(Ingredient ingredient, int size) {
        for (int i = 0; i < size; ++i) {
            this.inputs.add(ingredient);
        }
        return this;
    }

    @Override
    public FurnitureRecipeJsonFactory criterion(String name, AdvancementCriterion<?> criterionConditions) {
        this.criteria.put(name, criterionConditions);
        return this;
    }

    @Override
    public FurnitureRecipeJsonFactory group(@Nullable String string) {
        this.group = string;
        return this;
    }

    public FurnitureRecipeJsonFactory showNotification(boolean showNotification) {
        this.showNotification = showNotification;
        return this;
    }

    @Override
    public Item getOutputItem() {
        return this.output;
    }

    @Override
    public void offerTo(RecipeExporter exporter, Identifier recipeId) {
        Advancement.Builder advancement$builder = exporter.getAdvancementBuilder().criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        this.criteria.forEach(advancement$builder::criterion);
        ItemStack stack = new ItemStack(this.output, this.outputCount);
        stack.setNbt(nbtElement);
        exporter.accept(recipeId, new FurnitureRecipe(this.group == null || this.group.isBlank() ? "" : this.group, stack, this.inputs), advancement$builder.build(recipeId.withPrefixedPath("recipes/furniture/")));
    }

    private void validate(Identifier recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        }
    }
}


