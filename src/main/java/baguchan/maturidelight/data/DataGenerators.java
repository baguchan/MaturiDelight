package baguchan.maturidelight.data;

import baguchan.maturidelight.MaturiDelight;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MaturiDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        BlockTagGenerator blocktags = new BlockTagGenerator(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper());
        event.getGenerator().addProvider(event.includeServer(), blocktags);
        event.getGenerator().addProvider(event.includeServer(), LootGenerator.create(event.getGenerator().getPackOutput()));
        event.getGenerator().addProvider(event.includeServer(), new CraftingGenerator(event.getGenerator().getPackOutput()));
    }
}