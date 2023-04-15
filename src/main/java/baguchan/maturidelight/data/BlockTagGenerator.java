package baguchan.maturidelight.data;

import baguchan.maturidelight.MaturiDelight;
import baguchan.maturidelight.register.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockTagGenerator extends BlockTagsProvider {
    public BlockTagGenerator(DataGenerator generator, ExistingFileHelper exFileHelper) {
        super(generator, MaturiDelight.MODID, exFileHelper);
    }

    @Override
    protected void addTags() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.TAKOYAKI_MAKER.get(), ModBlocks.PLATE.get());
    }
}
