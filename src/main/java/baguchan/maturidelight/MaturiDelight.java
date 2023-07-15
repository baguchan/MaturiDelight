package baguchan.maturidelight;

import baguchan.maturidelight.client.ClientRegistrar;
import baguchan.maturidelight.register.ModBlockEntitys;
import baguchan.maturidelight.register.ModBlocks;
import baguchan.maturidelight.register.ModCreativeTabs;
import baguchan.maturidelight.register.ModItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MaturiDelight.MODID)
public class MaturiDelight
{
    public static final String MODID = "maturidelight";



    public MaturiDelight()
    {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.BLOCKS.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModBlockEntitys.BLOCK_ENTITIES.register(modBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modBus);
        // Register the commonSetup method for modloading
        modBus.addListener(this::commonSetup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientRegistrar::setup));
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }
}
