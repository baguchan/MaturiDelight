package baguchan.maturidelight.item;

import baguchan.maturidelight.register.ModAdvancements;
import baguchan.maturidelight.util.SuspiciousUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.item.ConsumableItem;

import javax.annotation.Nullable;
import java.util.List;

public class SuspiciousBowlItem extends ConsumableItem {
    public SuspiciousBowlItem(Properties food) {
        super(food, true);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack p_41409_, Level p_41410_, LivingEntity p_41411_) {
        if (p_41409_.getTag() != null && p_41409_.getTag().contains(SuspiciousUtil.SUSPICIOUS_TAG)) {
            Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(p_41409_.getTag().getString(SuspiciousUtil.SUSPICIOUS_TAG)));
            if (item != null) {
                if (item.isEdible()) {
                    item.finishUsingItem(item.getDefaultInstance(), p_41410_, p_41411_);
                } else if (item instanceof BlockItem blockItem) {
                    if (blockItem.getBlock() instanceof FlowerBlock flowerBlock) {
                        if (flowerBlock.getSuspiciousEffect() == MobEffects.POISON || flowerBlock.getSuspiciousEffect() == MobEffects.WITHER) {
                            if (p_41411_ instanceof ServerPlayer serverPlayer) {
                                ModAdvancements.POISON.trigger(serverPlayer);
                            }
                        }
                        p_41411_.addEffect(new MobEffectInstance(flowerBlock.getSuspiciousEffect(), flowerBlock.getEffectDuration()));
                    } else {
                        if (p_41411_ instanceof ServerPlayer serverPlayer) {
                            ModAdvancements.POISON.trigger(serverPlayer);
                        }
                        p_41411_.hurt(p_41411_.damageSources().starve(), 5);
                        p_41411_.addEffect(new MobEffectInstance(MobEffects.POISON, 600));
                        p_41411_.addEffect(new MobEffectInstance(MobEffects.HUNGER, 600));
                        p_41411_.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 600));
                    }
                }
            }
        }

        return super.finishUsingItem(p_41409_, p_41410_, p_41411_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag p_41424_) {
        super.appendHoverText(stack, level, tooltip, p_41424_);
        ChatFormatting[] textformatting2 = new ChatFormatting[]{ChatFormatting.DARK_PURPLE};
        if (stack.getTag() != null && stack.getTag().contains(SuspiciousUtil.SUSPICIOUS_TAG)) {
            tooltip.add(Component.translatable("item.maturidelight.suspicious_item").withStyle(textformatting2));
        }
    }
}