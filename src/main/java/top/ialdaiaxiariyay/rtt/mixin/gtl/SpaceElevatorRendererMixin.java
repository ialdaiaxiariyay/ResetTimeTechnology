package top.ialdaiaxiariyay.rtt.mixin.gtl;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.client.renderer.GTRenderTypes;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.model.data.ModelData;

import com.mojang.blaze3d.vertex.PoseStack;
import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.client.ClientUtil;
import org.gtlcore.gtlcore.client.renderer.RenderBufferHelper;
import org.gtlcore.gtlcore.client.renderer.machine.SpaceElevatorRenderer;
import org.gtlcore.gtlcore.common.machine.multiblock.electric.SpaceElevatorMachine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.ialdaiaxiariyay.rtt.config.RTTConfigHolder;

@Mixin({ SpaceElevatorRenderer.class })
public class SpaceElevatorRendererMixin {

    @Inject(method = "render", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private void render(BlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer,
                        int combinedLight, int combinedOverlay, CallbackInfo ci) {
        if (RTTConfigHolder.INSTANCE.MachineRendered) {
            if (blockEntity instanceof IMachineBlockEntity machineBlockEntity &&
                    machineBlockEntity.getMetaMachine() instanceof SpaceElevatorMachine machine && machine.isFormed()) {
                float tick = machine.getOffsetTimer() + partialTicks;
                double x = 0.5, y = 1, z = 0.5;
                switch (machine.getFrontFacing()) {
                    case NORTH -> z = 3.5;
                    case SOUTH -> z = -2.5;
                    case WEST -> x = 3.5;
                    case EAST -> x = -2.5;
                }
                poseStack.pushPose();
                RenderBufferHelper.renderCylinder(poseStack, buffer.getBuffer(GTRenderTypes.getLightRing()), (float) x, (float) (y - 2), (float) z, 0.3F, 360, 10, 0, 0, 0, 255);
                poseStack.translate(x, y + 180 + (140 * Math.sin(tick / 160)), z);
                rtt$renderClimber(poseStack, buffer);
                poseStack.popPose();
            }
        }
        ci.cancel();
    }

    @Unique
    private static final ResourceLocation CLIMBER_MODEL = GTLCore.id("obj/climber");

    @Unique
    private void rtt$renderClimber(PoseStack poseStack, MultiBufferSource buffer) {
        float scale = 4F;
        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);
        ClientUtil.modelRenderer().renderModel(poseStack.last(), buffer.getBuffer(RenderType.solid()), null, ClientUtil.getBakedModel(CLIMBER_MODEL), 1.0F, 1.0F, 1.0F, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.solid());
        poseStack.popPose();
    }
}
