package top.ialdaiaxiariyay.rtt.mixin.gtl;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.model.data.ModelData;
import org.gtlcore.gtlcore.client.ClientUtil;
import org.gtlcore.gtlcore.client.renderer.machine.AnnihilateGeneratorRenderer;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.ialdaiaxiariyay.rtt.config.RTTConfigHolder;

import static org.gtlcore.gtlcore.client.renderer.machine.EyeOfHarmonyRenderer.STAR_MODEL;

@Mixin({AnnihilateGeneratorRenderer.class})
public class AnnihilateGeneratorRendererMixin {
    @Inject(method = "render", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private void render(BlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer,
                        int combinedLight, int combinedOverlay, CallbackInfo ci) {
                        if(RTTConfigHolder.INSTANCE.MachineRendered){
                            if (blockEntity instanceof IMachineBlockEntity machineBlockEntity &&
                                    machineBlockEntity.getMetaMachine() instanceof WorkableElectricMultiblockMachine machine && machine.isActive()) {
                                float tick = machine.getOffsetTimer() + partialTicks;
                                double x = 0.5, y = 36.5, z = 0.5;
                                switch (machine.getFrontFacing()) {
                                    case NORTH -> z = 39.5;
                                    case SOUTH -> z = -38.5;
                                    case WEST -> x = 39.5;
                                    case EAST -> x = -38.5;
                                }
                                poseStack.pushPose();
                                poseStack.translate(x, y, z);
                                rtt$renderStar(tick, poseStack, buffer);
                                poseStack.popPose();
                            }
                        }
        ci.cancel();
    }

    @Unique
    private static void rtt$renderStar(float tick, PoseStack poseStack, MultiBufferSource buffer) {
        poseStack.pushPose();
        poseStack.scale(0.45F, 0.45F, 0.45F);
        poseStack.mulPose(new Quaternionf().fromAxisAngleDeg(0F, 1F, 1F, tick % 360F));
        ClientUtil.modelRenderer().renderModel(poseStack.last(), buffer.getBuffer(RenderType.translucent()), null, ClientUtil.getBakedModel(STAR_MODEL), 1.0F, 1.0F, 1.0F, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.translucent());
        poseStack.popPose();
    }
}
