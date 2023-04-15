package baguchan.maturidelight.item;

import baguchan.maturidelight.util.SuspiciousUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
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

import javax.annotation.Nullable;
import java.util.List;

public class SuspiciousItem extends Item {
    public SuspiciousItem(Item.Properties food) {
        super(food);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack p_41409_, Level p_41410_, LivingEntity p_41411_) {
        if(p_41409_.getTag() != null && p_41409_.getTag().contains(SuspiciousUtil.SUSPICIOUS_TAG)){
            Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(p_41409_.getTag().getString(SuspiciousUtil.SUSPICIOUS_TAG)));
            if(item != null){
                if(p_41409_.isEdible()){
                    p_41411_.eat(p_41410_, p_41409_);
                }else if(item instanceof BlockItem blockItem){
                    if(blockItem.getBlock() instanceof FlowerBlock flowerBlock){
                        p_41411_.addEffect(new MobEffectInstance(flowerBlock.getSuspiciousStewEffect(), flowerBlock.getEffectDuration()));
                    }else {
                        p_41411_.hurt(DamageSource.STARVE, 5);
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
        if(stack.getTag() != null && stack.getTag().contains(SuspiciousUtil.SUSPICIOUS_TAG)){
            tooltip.add(Component.translatable("item.maturidelight.suspicious_item").withStyle(textformatting2));
        }
    }
}