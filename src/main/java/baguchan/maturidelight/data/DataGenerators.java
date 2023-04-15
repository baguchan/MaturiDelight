package baguchan.maturidelight.data;

import baguchan.maturidelight.MaturiDelight;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MaturiDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        BlockTagsProvider blocktags = new BlockTagGenerator(event.getGenerator(), event.getExistingFileHelper());
        event.getGenerator().addProvider(event.includeServer(), blocktags);event.getGenerator().addProvider(event.includeServer(), new LootGenerator(event.getGenerator()));
        event.getGenerator().addProvider(event.includeServer(), new CraftingGenerator(event.getGenerator()));
    }
}