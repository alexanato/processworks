package at.randorf.processworks;

import at.randorf.processworks.entitys.basket.BasketFallingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister.Entities ENTITIES =DeferredRegister.createEntities(Processworks.MOD_ID);

    public static final DeferredHolder<
                EntityType<?>,
                EntityType<BasketFallingEntity>
                > BASKET_FALLING = ENTITIES.registerEntityType(
            "basket_falling",
            BasketFallingEntity::new,
            MobCategory.MISC,
            builder -> builder.sized(0.98F, 0.98F)
    );
}
