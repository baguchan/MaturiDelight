package baguchan.maturidelight.register;

import baguchan.maturidelight.MaturiDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.Stream;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MaturiDelight.MODID);


    public static final RegistryObject<CreativeModeTab> MATURI_DELIGHT = CREATIVE_MODE_TABS.register("tofus", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .title(Component.translatable("itemGroup." + MaturiDelight.MODID))
            .icon(() -> ModItems.TAKOYAKI.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.acceptAll(Stream.of(
                        ModItems.DOUGH_RAW,
                        ModItems.TAKOYAKI_RAW,
                        ModItems.TAKOYAKI,
                        ModItems.TAKOYAKI_SOYSAUCE,
                        ModItems.OKONOMIYAKI_RAW,
                        ModItems.OKONOMIYAKI,
                        ModItems.OKONOMIYAKI_SOYSAUCE,
                        ModItems.YAKISOBA,
                        ModItems.YAKISOBA_SOYSAUCE
                ).map(sup -> {
                    return sup.get().getDefaultInstance();
                }).toList());
                output.acceptAll(Stream.of(
                        ModBlocks.PLATE,
                        ModBlocks.TAKOYAKI_MAKER
                ).map(sup -> {
                    return sup.get().asItem().getDefaultInstance();
                }).toList());
            }).build());
}
