package baguchan.maturidelight.data;

import baguchan.maturidelight.register.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashSet;
import java.util.Set;

public class BlockLootTables extends net.minecraft.data.loot.BlockLoot {
    private final Set<Block> knownBlocks = new HashSet<>();
    // [VanillaCopy] super
    private static final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    private static final float[] RARE_SAPLING_DROP_RATES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};

    @Override
    protected void add(Block block, LootTable.Builder builder) {
        super.add(block, builder);
        knownBlocks.add(block);
    }

    @Override
    protected void addTables() {
        this.dropSelf(ModBlocks.TAKOYAKI_MAKER.get());
        this.dropSelf(ModBlocks.PLATE.get());
    }

    @Override
    public Set<Block> getKnownBlocks() {
        return knownBlocks;
    }
}
