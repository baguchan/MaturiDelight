package baguchan.maturidelight.block;

import baguchan.maturidelight.blockentity.TakoyakiMakerBlockEntity;
import baguchan.maturidelight.register.ModBlockEntitys;
import baguchan.maturidelight.register.ModItems;
import baguchan.tofucraft.registry.TofuTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class TakoyakiMakerBlock extends BaseEntityBlock
{

    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D);

    public TakoyakiMakerBlock(BlockBehaviour.Properties builder) {
        super(builder);
   }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldStack = player.getItemInHand(hand);
        Item heldItem = heldStack.getItem();
        BlockEntity tileEntity = level.getBlockEntity(pos);
        if (tileEntity instanceof TakoyakiMakerBlockEntity blockentity) {
            if(heldStack.is(TofuTags.Items.SOYSAUCE)) {
                if (blockentity.putSoySauceItem()) {
                    player.playSound(SoundEvents.ITEM_PICKUP);
                    ItemStack stack = heldStack.getCraftingRemainingItem();
                    if (!player.addItem(stack)) {
                        player.drop(stack, false);
                        if(!player.getAbilities().instabuild) {
                            heldStack.shrink(1);
                        }
                    }else {
                        if(!player.getAbilities().instabuild) {
                            heldStack.shrink(1);
                        }
                    }
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.CONSUME;

            }else if(heldStack.is(ModItems.DOUGH_RAW.get())) {

                if (blockentity.addDoughRaw()) {
                    player.playSound(SoundEvents.ITEM_PICKUP);
                    ItemStack stack = new ItemStack(Items.BOWL);
                    if (!player.addItem(stack)) {
                        player.drop(stack, false);
                        if(!player.getAbilities().instabuild) {
                            heldStack.shrink(1);
                        }
                    }else {
                        if(!player.getAbilities().instabuild) {
                            heldStack.shrink(1);
                        }
                    }
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.CONSUME;

            }else {
                if(heldStack.isEmpty()) {
                    ItemStack stack = blockentity.removeItem();
                    if (!player.addItem(stack)) {
                        player.drop(stack, false);
                    }
                    return InteractionResult.SUCCESS;
                }else if(heldStack.isEdible() && blockentity.putSuspiciousItem(heldStack)){
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
            if (blockentity instanceof TakoyakiMakerBlockEntity) {
                Containers.dropContents(p_51282_, p_51283_, ((TakoyakiMakerBlockEntity)blockentity).getItems());
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
        return p_152755_.isClientSide() ? createTickerHelper(p_152757_, ModBlockEntitys.TAKOYAKI_MAKER.get(), TakoyakiMakerBlockEntity::animationTick) : createTickerHelper(p_152757_, ModBlockEntitys.TAKOYAKI_MAKER.get(), TakoyakiMakerBlockEntity::cookTick);
    }

    public BlockEntity newBlockEntity(BlockPos p_152759_, BlockState p_152760_) {
        return new TakoyakiMakerBlockEntity(p_152759_, p_152760_);
    }
}