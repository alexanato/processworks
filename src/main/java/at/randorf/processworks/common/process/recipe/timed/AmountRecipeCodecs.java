package at.randorf.processworks.common.process.recipe.timed;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Ingredient;

public class AmountRecipeCodecs {
    private AmountRecipeCodecs() {
    }

    public static <T extends AmountRecipe> MapCodec<T> codec(
            AmountRecipeFactory<T> factory
    ) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC
                        .fieldOf("ingredient")
                        .forGetter(AmountRecipe::getIngredient),

                Codec.INT
                        .fieldOf("time")
                        .forGetter(AmountRecipe::getTime),

                Identifier.CODEC
                        .xmap(
                                id -> ResourceKey.create(
                                        Registries.LOOT_TABLE,
                                        id
                                ),
                                ResourceKey::identifier
                        )
                        .fieldOf("loot_table")
                        .forGetter(AmountRecipe::getLootTable)

        ).apply(instance, factory::create));
    }

    public static <T extends AmountRecipe>
    StreamCodec<RegistryFriendlyByteBuf, T> streamCodec(
            AmountRecipeFactory<T> factory
    ) {
        return StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC,
                AmountRecipe::getIngredient,

                ByteBufCodecs.VAR_INT,
                AmountRecipe::getTime,

                Identifier.STREAM_CODEC.map(
                        id -> ResourceKey.create(
                                Registries.LOOT_TABLE,
                                id
                        ),
                        ResourceKey::identifier
                ),
                AmountRecipe::getLootTable,

                factory::create
        );
    }
}
