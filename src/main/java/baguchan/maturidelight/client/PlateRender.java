package baguchan.maturidelight.client;

import baguchan.maturidelight.blockentity.PlateBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class PlateRender implements BlockEntityRenderer<PlateBlockEntity>
{
    public PlateRender(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(PlateBlockEntity blockentity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
        NonNullList<ItemStack> inventory = blockentity.getItems();
        int posLong = (int) blockentity.getBlockPos().asLong();

        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.get(i);
            if (!stack.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate(0.5D, (5D / 16D) + 0.15F, 0.5D);
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));

                // Resize the items
                poseStack.scale(0.85F, 0.85F, 0.85F);
                poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));

                if (blockentity.getLevel() != null)
                    Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(blockentity.getLevel(), blockentity.getBlockPos().above()), combinedOverlayIn, poseStack, buffer, blockentity.getLevel(), posLong + i);
                poseStack.popPose();
            }
        }
    }
}