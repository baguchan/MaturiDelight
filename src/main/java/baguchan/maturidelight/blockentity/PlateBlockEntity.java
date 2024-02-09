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
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;

public class PlateBlockEntity extends BlockEntity implements HeatableBlockEntity, Clearable {
    private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    private int cookingProgress;
    public PlateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.PLATE.get(), pos, state);
    }
    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.items.clear();
        ContainerHelper.loadAllItems(compound, this.items);
        this.cookingProgress = compound.getInt("CookingTimes");
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        ContainerHelper.saveAllItems(compound, this.items, true);
        compound.putInt("CookingTimes", this.cookingProgress);
    }

    public NonNullList<ItemStack> getItems() {
        return this.items;
    }


    public boolean addDoughRaw() {
        boolean flag = false;
        for(int i= 0; i < this.items.size(); i++) {
            if (isEmpty(i)) {
                items.set(i, new ItemStack(ModItems.OKONOMIYAKI_RAW.get()));
                setChanged();
                flag = true;
            }
        }
        return flag;
    }

    public boolean addRaw(ItemStack stack) {
        boolean flag = false;
        if (isEmpty(0)) {
            items.set(0, stack.split(1));
            setChanged();
            flag = true;
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
            if (!isEmpty(i) && this.items.get(i).is(ModItems.OKONOMIYAKI_RAW.get()) && !item.is(ModItems.OKONOMIYAKI_RAW.get()) && !item.is(ModItems.OKONOMIYAKI.get()) && !item.is(ModItems.OKONOMIYAKI_SOYSAUCE.get())) {
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
            if (!isEmpty(i)) {
                if (this.items.get(i).is(ModItems.OKONOMIYAKI.get())) {
                    ItemStack original = this.items.get(i);
                    ItemStack item = new ItemStack(ModItems.OKONOMIYAKI_SOYSAUCE.get());
                    if (original.getTag() != null && original.getTag().contains(SuspiciousUtil.SUSPICIOUS_TAG)) {
                        item.getOrCreateTag().putString(SuspiciousUtil.SUSPICIOUS_TAG, original.getTag().getString(SuspiciousUtil.SUSPICIOUS_TAG));
                    }

                    this.items.set(i, item);
                    setChanged();
                    flag = true;

                }
                if (this.items.get(i).is(ModItems.YAKISOBA_RAW.get())) {
                    ItemStack original = this.items.get(i);
                    ItemStack item = new ItemStack(ModItems.YAKISOBA_SOYSAUCE_RAW.get());
                    if (original.getTag() != null && original.getTag().contains(SuspiciousUtil.SUSPICIOUS_TAG)) {
                        item.getOrCreateTag().putString(SuspiciousUtil.SUSPICIOUS_TAG, original.getTag().getString(SuspiciousUtil.SUSPICIOUS_TAG));
                    }

                    this.items.set(i, item);
                    setChanged();
                    flag = true;

                }
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

    public static void animationTick(Level level, BlockPos pos, BlockState state, PlateBlockEntity stove) {
        for (int i = 0; i < stove.items.size(); ++i) {
            if (!stove.items.get(i).isEmpty() && level.random.nextFloat() < 0.2F) {
                double x = ((double) pos.getX() + 0.5D);
                double y = (double) pos.getY() + 0.35D;
                double z = ((double) pos.getZ() + 0.5D);

                for (int k = 0; k < 3; ++k) {
                    level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 5.0E-4D, 0.0D);
                }

                if(level.random.nextFloat() < 0.005F){
                    level.playLocalSound(x, y, z, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.8F, 0.8F + level.random.nextFloat() * 0.4F, false);
                }
            }
        }
    }

    public static void cookTick(Level p_155307_, BlockPos p_155308_, BlockState p_155309_, PlateBlockEntity p_155310_) {
        boolean flag = false;

        if(p_155310_.isHeated(p_155307_, p_155308_)) {
                ItemStack itemstack = p_155310_.items.get(0);
                if (!itemstack.isEmpty() && itemstack.is(ModItems.OKONOMIYAKI_RAW.get())) {
                    flag = true;
                    p_155310_.cookingProgress++;
                    if (p_155310_.cookingProgress >= 400) {
                        if (itemstack.is(ModItems.OKONOMIYAKI_RAW.get())) {
                            ItemStack finshedStack = new ItemStack(ModItems.OKONOMIYAKI.get());
                            if (itemstack.getTag() != null && itemstack.getTag().contains(SuspiciousUtil.SUSPICIOUS_TAG)) {
                                finshedStack.getOrCreateTag().putString(SuspiciousUtil.SUSPICIOUS_TAG, itemstack.getTag().getString(SuspiciousUtil.SUSPICIOUS_TAG));
                            }
                            p_155310_.items.set(0, finshedStack);
                            p_155307_.sendBlockUpdated(p_155308_, p_155309_, p_155309_, 3);
                            p_155307_.gameEvent(GameEvent.BLOCK_CHANGE, p_155308_, GameEvent.Context.of(p_155309_));
                            p_155310_.cookingProgress = 0;
                        } else if (itemstack.is(vectorwing.farmersdelight.common.registry.ModItems.RAW_PASTA.get())) {
                            ItemStack finshedStack = new ItemStack(ModItems.YAKISOBA_RAW.get());
                            if (itemstack.getTag() != null && itemstack.getTag().contains(SuspiciousUtil.SUSPICIOUS_TAG)) {
                                finshedStack.getOrCreateTag().putString(SuspiciousUtil.SUSPICIOUS_TAG, itemstack.getTag().getString(SuspiciousUtil.SUSPICIOUS_TAG));
                            }
                            p_155310_.items.set(0, finshedStack);
                            p_155307_.sendBlockUpdated(p_155308_, p_155309_, p_155309_, 3);
                            p_155307_.gameEvent(GameEvent.BLOCK_CHANGE, p_155308_, GameEvent.Context.of(p_155309_));
                            p_155310_.cookingProgress = 0;

                        }
                    }
                }
        }

        if (flag) {
            setChanged(p_155307_, p_155308_, p_155309_);
        }

    }

    public static void cooldownTick(Level p_155314_, BlockPos p_155315_, BlockState p_155316_, PlateBlockEntity p_155317_) {
        boolean flag = false;

        for(int i = 0; i < p_155317_.items.size(); ++i) {
            if (p_155317_.cookingProgress > 0) {
                flag = true;
                p_155317_.cookingProgress = Mth.clamp(p_155317_.cookingProgress - 2, 0, 400);
            }
        }

        if (flag) {
            setChanged(p_155314_, p_155315_, p_155316_);
        }

    }

    public void clearContent() {
        this.items.clear();
    }
}