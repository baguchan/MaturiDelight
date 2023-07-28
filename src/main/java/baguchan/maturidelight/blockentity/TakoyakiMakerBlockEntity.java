package baguchan.maturidelight.blockentity;

import baguchan.maturidelight.register.ModBlockEntitys;
import baguchan.maturidelight.register.ModItems;
import baguchan.maturidelight.util.SuspiciousUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Clearable;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;

public class TakoyakiMakerBlockEntity extends BlockEntity implements HeatableBlockEntity, Clearable {
    private final NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);
    private final int[] cookingProgress = new int[9];
    public TakoyakiMakerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.TAKOYAKI_MAKER.get(), pos, state);
    }
    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.items.clear();
        ContainerHelper.loadAllItems(compound, this.items);
        if (compound.contains("CookingTimes", 11)) {
            int[] aint = compound.getIntArray("CookingTimes");
            System.arraycopy(aint, 0, this.cookingProgress, 0, Math.min(400, aint.length));
        }

    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        ContainerHelper.saveAllItems(compound, this.items, true);
        compound.putIntArray("CookingTimes", this.cookingProgress);
    }

    public NonNullList<ItemStack> getItems() {
        return this.items;
    }


    public boolean addDoughRaw() {
        boolean flag = false;
        for(int i= 0; i < this.items.size(); i++) {
            if (isEmpty(i)) {
                items.set(i, new ItemStack(ModItems.TAKOYAKI_RAW.get()));
                setChanged();
                flag = true;
            }
        }
        return flag;
    }

    public boolean addItem(ItemStack itemStack) {
        for(int i= 0; i < this.items.size(); i++) {
            if (isEmpty(i) && !itemStack.isEmpty()) {
                items.set(i, itemStack.split(1));
                setChanged();
                return true;
            }
        }
        return false;
    }
    public ItemStack removeItem() {
        for(int i= 0; i < this.items.size(); i++) {
            if (!isEmpty(i)) {
                ItemStack item = getStoredItem(i).split(1);
                setChanged();
                return item;
            }
        }
        return ItemStack.EMPTY;
    }

    public boolean putSuspiciousItem(ItemStack item) {
        for(int i= 0; i < this.items.size(); i++) {
            if (!isEmpty(i) && this.items.get(i).is(ModItems.TAKOYAKI_RAW.get()) && !item.is(ModItems.TAKOYAKI_RAW.get()) && !item.is(ModItems.TAKOYAKI.get()) && !item.is(ModItems.TAKOYAKI_SOYSAUCE.get())) {
                ItemStack original = this.items.get(i);
                if(original.getTag() != null && original.getTag().contains(SuspiciousUtil.SUSPICIOUS_TAG)){
                    continue;
                }
                original.getOrCreateTag().putString(SuspiciousUtil.SUSPICIOUS_TAG, ForgeRegistries.ITEMS.getKey(item.getItem()).toString());

                this.items.set(i, original.copy());
                setChanged();
                item.shrink(1);
                return true;
            }
        }
        return false;
    }

    public boolean putSoySauceItem() {
        boolean flag = false;
        for(int i= 0; i < this.items.size(); i++) {
            if (!isEmpty(i) && this.items.get(i).is(ModItems.TAKOYAKI.get())) {
                ItemStack original = this.items.get(i);
                ItemStack item = new ItemStack(ModItems.TAKOYAKI_SOYSAUCE.get());
                if (original.getTag() != null && original.getTag().contains(SuspiciousUtil.SUSPICIOUS_TAG)) {
                    item.getOrCreateTag().putString(SuspiciousUtil.SUSPICIOUS_TAG, original.getTag().getString(SuspiciousUtil.SUSPICIOUS_TAG));
                }

                this.items.set(i, item);
                setChanged();
                flag = true;
            }
        }
        return flag;
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag() {
        CompoundTag compoundtag = new CompoundTag();
        this.saveAdditional(compoundtag);
        return compoundtag;
    }

    public ItemStack getStoredItem(int slot) {
        return items.get(slot);
    }

    public boolean isEmpty(int slot) {
        return items.get(slot).isEmpty();
    }

    public static void animationTick(Level level, BlockPos pos, BlockState state, TakoyakiMakerBlockEntity stove) {
        for (int i = 0; i < stove.items.size(); ++i) {
            if (!stove.items.get(i).isEmpty() && level.random.nextFloat() < 0.2F) {
                Vec2 stoveItemVector = stove.getItemOffset(i);
                Vec2 offset = stoveItemVector;

                double x = ((double) pos.getX() + 0.5D) + (offset.x);
                double y = (double) pos.getY() + 0.35D;
                double z = ((double) pos.getZ() + 0.5D) + (offset.y);

                for (int k = 0; k < 3; ++k) {
                    level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 5.0E-4D, 0.0D);
                }
                if(level.random.nextFloat() < 0.005F){
                    level.playLocalSound(x, y, z, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.8F, 0.8F + level.random.nextFloat() * 0.4F, false);
                }
            }
        }


    }

    public static void cookTick(Level p_155307_, BlockPos p_155308_, BlockState p_155309_, TakoyakiMakerBlockEntity p_155310_) {
        boolean flag = false;

        if(p_155310_.isHeated(p_155307_, p_155308_)) {
            for (int i = 0; i < p_155310_.items.size(); ++i) {
                ItemStack itemstack = p_155310_.items.get(i);
                if (!itemstack.isEmpty() && itemstack.is(ModItems.TAKOYAKI_RAW.get())) {
                    flag = true;
                    int j = p_155310_.cookingProgress[i]++;
                    if (p_155310_.cookingProgress[i] >= 400) {
                        if (itemstack.is(ModItems.TAKOYAKI_RAW.get())) {
                            ItemStack finshedStack = new ItemStack(ModItems.TAKOYAKI.get());
                            if (itemstack.getTag() != null && itemstack.getTag().contains(SuspiciousUtil.SUSPICIOUS_TAG)) {
                                finshedStack.getOrCreateTag().putString(SuspiciousUtil.SUSPICIOUS_TAG, itemstack.getTag().getString(SuspiciousUtil.SUSPICIOUS_TAG));
                            }
                            p_155310_.items.set(i, finshedStack);
                            p_155307_.sendBlockUpdated(p_155308_, p_155309_, p_155309_, 3);
                            p_155307_.gameEvent(GameEvent.BLOCK_CHANGE, p_155308_, GameEvent.Context.of(p_155309_));
                            p_155310_.cookingProgress[i] = 0;
                        }
                    }
                }
            }
        }

        if (flag) {
            setChanged(p_155307_, p_155308_, p_155309_);
        }

    }

    public static void cooldownTick(Level p_155314_, BlockPos p_155315_, BlockState p_155316_, TakoyakiMakerBlockEntity p_155317_) {
        boolean flag = false;

        for(int i = 0; i < p_155317_.items.size(); ++i) {
            if (p_155317_.cookingProgress[i] > 0) {
                flag = true;
                p_155317_.cookingProgress[i] = Mth.clamp(p_155317_.cookingProgress[i] - 2, 0, 400);
            }
        }

        if (flag) {
            setChanged(p_155314_, p_155315_, p_155316_);
        }

    }

    public void clearContent() {
        this.items.clear();
    }

    public Vec2 getItemOffset(int index) {
        final float X_OFFSET = 0.3F;
        final float Y_OFFSET = 0.3F;
        final Vec2[] OFFSETS = {
                new Vec2(X_OFFSET, Y_OFFSET),
                new Vec2(0.0F, Y_OFFSET),
                new Vec2(-X_OFFSET, Y_OFFSET),
                new Vec2(X_OFFSET, 0.0F),
                new Vec2(0.0F, 0.0F),
                new Vec2(-X_OFFSET, 0.0F),
                new Vec2(X_OFFSET, -Y_OFFSET),
                new Vec2(0.0F, -Y_OFFSET),
                new Vec2(-X_OFFSET, -Y_OFFSET),
        };
        return OFFSETS[index];
    }
}