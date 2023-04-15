package baguchan.maturidelight.client;

import baguchan.maturidelight.blockentity.PlateBlockEntity;
import baguchan.maturidelight.register.ModBlockEntitys;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientRegistrar {
    public static void setup(FMLCommonSetupEvent event) {
        BlockEntityRenderers.register(ModBlockEntitys.TAKOYAKI_MAKER.get(), TakoyakiMakerRender::new);
        BlockEntityRenderers.register(ModBlockEntitys.PLATE.get(), PlateRender::new);
    }
}
