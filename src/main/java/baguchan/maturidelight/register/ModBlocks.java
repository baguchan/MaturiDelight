package baguchan.maturidelight.register;

import baguchan.maturidelight.MaturiDelight;
import baguchan.maturidelight.block.PlateBlock;
import baguchan.maturidelight.block.TakoyakiMakerBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MaturiDelight.MODID);
    public static final RegistryObject<Block> TAKOYAKI_MAKER = register("takoyaki_maker", () -> new TakoyakiMakerBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().noOcclusion().strength(5.0F, 6.0F).sound(SoundType.METAL)));
    public static final RegistryObject<Block> PLATE = register("plate", () -> new PlateBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().noOcclusion().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    private static <T extends Block> RegistryObject<T> baseRegister(String name, Supplier<? extends T> block, Function<RegistryObject<T>, Supplier<? extends Item>> item) {
        RegistryObject<T> register = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, item.apply(register));
        return register;
    }

    private static <T extends Block> RegistryObject<T> noItemRegister(String name, Supplier<? extends T> block) {
        RegistryObject<T> register = BLOCKS.register(name, block);
        return register;
    }

    private static <B extends Block> RegistryObject<B> register(String name, Supplier<? extends Block> block) {
        return (RegistryObject<B>) baseRegister(name, block, (object) -> ModBlocks.registerBlockItem(object));
    }

    private static <T extends Block> Supplier<BlockItem> registerBlockItem(final RegistryObject<T> block) {
        return () -> {
            return new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties());
        };
    }
}
