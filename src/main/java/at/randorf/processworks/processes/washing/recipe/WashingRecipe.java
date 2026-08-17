package at.randorf.processworks.processes.washing.recipe;

import at.randorf.processworks.registry.BasketRegister;
import at.randorf.processworks.registry.WashingRegister;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.List;

public class WashingRecipe implements Recipe<WashingRecipeInput> {
    public static final MapCodec<WashingRecipe> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    BuiltInRegistries.ITEM
                            .byNameCodec()
                            .fieldOf("state")
                            .forGetter(WashingRecipe::getInputState),
                    Codec.INT
                            .fieldOf("fall_time")
                            .forGetter(WashingRecipe::getTime),
                    Identifier.CODEC
                            .xmap(
                                    id -> ResourceKey.create(Registries.LOOT_TABLE, id),
                                    ResourceKey::identifier
                            )
                            .fieldOf("loot_table")
                            .forGetter(WashingRecipe::getLootTable)
            ).apply(instance, WashingRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, WashingRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.idMapper(BuiltInRegistries.ITEM),
                    WashingRecipe::getInputState,
                    ByteBufCodecs.VAR_INT,
                    WashingRecipe::getTime,
                    Identifier.STREAM_CODEC.map(
                            id -> ResourceKey.create(Registries.LOOT_TABLE, id),
                            ResourceKey::identifier
                    ),
                    WashingRecipe::getLootTable,
                    WashingRecipe::new
            );

    public Item getInputState() {
        return inputState;
    }

    public int getTime() {
        return time;
    }

    public ResourceKey<LootTable> getLootTable() {
        return lootTable;
    }

    private final Item inputState;
    private final int time;
    private final ResourceKey<LootTable> lootTable;

    public WashingRecipe(
            Item inputState,
            int time,
            ResourceKey<LootTable> lootTable
    ) {
        this.inputState = inputState;
        this.time = time;
        this.lootTable = lootTable;
    }

    @Override
    public boolean matches(WashingRecipeInput input, Level level) {
        return input.item().getDefaultInstance().is(inputState) && input.time() >= time;
    }
    @Override
    public ItemStack assemble(WashingRecipeInput washerRecipeInput) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public RecipeSerializer<? extends Recipe<WashingRecipeInput>> getSerializer() {
        return WashingRegister.WASHING_SERIALIZER.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public List<RecipeDisplay> display() {
        return Recipe.super.display();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    @Override
    public RecipeType<? extends Recipe<WashingRecipeInput>> getType() {
        return WashingRegister.WASHING_TYPE.get();
    }
}