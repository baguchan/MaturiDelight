package baguchan.maturidelight.client;

import baguchan.maturidelight.blockentity.TakoyakiMakerBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TakoyakiMakerRender implements BlockEntityRenderer<TakoyakiMakerBlockEntity>
{
    public TakoyakiMakerRender(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TakoyakiMakerBlockEntity blockentity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
        NonNullList<ItemStack> inventory = blockentity.getItems();
        int posLong = (int) blockentity.getBlockPos().asLong();

        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.get(i);
            if (!stack.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate(0.5D, (5D / 16D) + 0.02F, 0.5D);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));

                // Neatly align items according to their index
                Vec2 itemOffset = blockentity.getItemOffset(i);
                poseStack.translate(itemOffset.x, itemOffset.y, 0.0D);

                // Resize the items
                poseStack.scale(0.375F, 0.375F, 0.375F);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));

                if (blockentity.getLevel() != null)
                    Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, LevelRenderer.getLightColor(blockentity.getLevel(), blockentity.getBlockPos().above()), combinedOverlayIn, poseStack, buffer, posLong + i);
                poseStack.popPose();
            }
        }
    }
}