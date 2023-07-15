package baguchan.maturidelight.data;

import baguchan.maturidelight.MaturiDelight;
import baguchan.maturidelight.register.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends BlockTagsProvider {
    public BlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper exFileHelper) {
        super(output, lookupProvider, MaturiDelight.MODID, exFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.TAKOYAKI_MAKER.get(), ModBlocks.PLATE.get());
    }
}
