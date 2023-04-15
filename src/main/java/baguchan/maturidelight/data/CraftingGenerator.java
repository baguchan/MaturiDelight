package baguchan.maturidelight.data;

import baguchan.maturidelight.register.ModBlocks;
import baguchan.maturidelight.register.ModItems;
import baguchan.tofucraft.registry.TofuItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class CraftingGenerator  extends RecipeProvider {
    public CraftingGenerator(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(ModItems.DOUGH_RAW.get(), 1)
                .requires(Items.EGG)
                .requires(Items.EGG)
                .requires(TofuItems.BOTTLE_DASHI.get())
                .requires(Items.WHEAT)
                .unlockedBy("has_item", has(TofuItems.BOTTLE_DASHI.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.TAKOYAKI_MAKER.get())
                .pattern("I I")
                .define('I', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
                .unlockedBy("has_item", has(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.PLATE.get())
                .pattern("III")
                .define('I', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
                .unlockedBy("has_item", has(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE))
                .save(consumer);
    }
}
