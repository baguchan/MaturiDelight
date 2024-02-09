package baguchan.maturidelight.block;

import baguchan.maturidelight.blockentity.PlateBlockEntity;
import baguchan.maturidelight.register.ModAdvancements;
import baguchan.maturidelight.register.ModBlockEntitys;
import baguchan.maturidelight.register.ModItems;
import baguchan.maturidelight.register.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class PlateBlock extends BaseEntityBlock
{

    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D);

    public PlateBlock(Properties builder) {
        super(builder);
   }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldStack = player.getItemInHand(hand);
        Item heldItem = heldStack.getItem();
        BlockEntity tileEntity = level.getBlockEntity(pos);
        if (tileEntity instanceof PlateBlockEntity blockentity) {
            if (heldStack.is(ModTags.Items.SOYSAUCE)) {
                if (blockentity.putSoySauceItem()) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        ModAdvancements.EXTRA_TOPPING.trigger(serverPlayer);
                    }
                    player.playSound(SoundEvents.ITEM_PICKUP);
                    ItemStack stack = heldStack.getCraftingRemainingItem();
                    if (!player.addItem(stack)) {
                        player.drop(stack, false);
                        if (!player.getAbilities().instabuild) {
                            heldStack.shrink(1);
                        }
                    } else {
                        if (!player.getAbilities().instabuild) {
                            heldStack.shrink(1);
                        }
                    }
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.CONSUME;

            }else if(heldStack.is(ModItems.DOUGH_RAW.get())) {

                if (blockentity.addDoughRaw()) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        ModAdvancements.MAKING_FOOD.trigger(serverPlayer);
                    }
                    player.playSound(SoundEvents.ITEM_PICKUP);
                    ItemStack stack = new ItemStack(Items.BOWL);
                    if (!player.addItem(stack)) {
                        player.drop(stack, false);
                        if (!player.getAbilities().instabuild) {
                            heldStack.shrink(1);
                        }
                    } else {
                        if (!player.getAbilities().instabuild) {
                            heldStack.shrink(1);
                        }
                    }
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.CONSUME;

            } else if (heldStack.is(vectorwing.farmersdelight.common.registry.ModItems.RAW_PASTA.get())) {

                if (blockentity.addRaw(heldStack)) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        ModAdvancements.MAKING_FOOD.trigger(serverPlayer);
                    }
                    player.playSound(SoundEvents.ITEM_PICKUP);
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.CONSUME;

            }else {
                if (heldStack.isEmpty()) {
                    player.playSound(SoundEvents.ITEM_PICKUP);
                    if (!blockentity.getItems().get(0).is(ModItems.YAKISOBA_RAW.get()) || !blockentity.getItems().get(0).is(ModItems.YAKISOBA_SOYSAUCE_RAW.get())) {
                        ItemStack stack = blockentity.removeItem();
                        if (!player.addItem(stack)) {
                            player.drop(stack, false);
                        }
                    } else if (heldStack.is(Items.BOWL)) {
                        ItemStack original = new ItemStack(ModItems.YAKISOBA.get());
                        if (blockentity.getItems().get(0).is(ModItems.YAKISOBA_SOYSAUCE_RAW.get())) {
                            original = new ItemStack(ModItems.YAKISOBA_SOYSAUCE.get());
                        }

                        ItemStack stack = blockentity.removeItem();

                        original.setTag(stack.getTag());
                        if (!player.addItem(original)) {
                            player.drop(original, false);
                        }
                    }
                    return InteractionResult.SUCCESS;
                } else if ((heldStack.isEdible() || heldStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof FlowerBlock) && blockentity.putSuspiciousItem(heldStack)) {
                    player.playSound(SoundEvents.ITEM_PICKUP);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.PASS;
    }

    public void onRemove(BlockState p_51281_, Level p_51282_, BlockPos p_51283_, BlockState p_51284_, boolean p_51285_) {
        if (!p_51281_.is(p_51284_.getBlock())) {
            BlockEntity blockentity = p_51282_.getBlockEntity(p_51283_);
            if (blockentity instanceof PlateBlockEntity) {
                Containers.dropContents(p_51282_, p_51283_, ((PlateBlockEntity)blockentity).getItems());
            }

            super.onRemove(p_51281_, p_51282_, p_51283_, p_51284_, p_51285_);
        }
    }

    public VoxelShape getShape(BlockState p_51309_, BlockGetter p_51310_, BlockPos p_51311_, CollisionContext p_51312_) {
        return SHAPE;
    }


    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152755_, BlockState p_152756_, BlockEntityType<T> p_152757_) {
        return p_152755_.isClientSide() ? createTickerHelper(p_152757_, ModBlockEntitys.PLATE.get(), PlateBlockEntity::animationTick) : createTickerHelper(p_152757_, ModBlockEntitys.PLATE.get(), PlateBlockEntity::cookTick);
    }

    public BlockEntity newBlockEntity(BlockPos p_152759_, BlockState p_152760_) {
        return new PlateBlockEntity(p_152759_, p_152760_);
    }
}