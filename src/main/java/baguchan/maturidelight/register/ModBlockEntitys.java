package baguchan.maturidelight.register;

import baguchan.maturidelight.MaturiDelight;
import baguchan.maturidelight.blockentity.PlateBlockEntity;
import baguchan.maturidelight.blockentity.TakoyakiMakerBlockEntity;
import com.mojang.datafixers.types.Type;
import net.minecraft.Util;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntitys {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MaturiDelight.MODID);

    public static final RegistryObject<BlockEntityType<TakoyakiMakerBlockEntity>> TAKOYAKI_MAKER = BLOCK_ENTITIES.register("takoyaki_maker", () -> register(MaturiDelight.MODID+":takoyaki_maker", BlockEntityType.Builder.of(TakoyakiMakerBlockEntity::new, ModBlocks.TAKOYAKI_MAKER.get())));
    public static final RegistryObject<BlockEntityType<PlateBlockEntity>> PLATE = BLOCK_ENTITIES.register("plate", () -> register(MaturiDelight.MODID+":plate", BlockEntityType.Builder.of(PlateBlockEntity::new, ModBlocks.PLATE.get())));
    private static <T extends BlockEntity> BlockEntityType<T> register(String p_200966_0_, BlockEntityType.Builder<T> p_200966_1_) {
        Type<?> type = Util.fetchChoiceType(References.BLOCK_ENTITY, p_200966_0_);
        return p_200966_1_.build(type);
    }
}