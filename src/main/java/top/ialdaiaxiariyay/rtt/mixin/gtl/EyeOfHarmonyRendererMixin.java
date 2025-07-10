package top.ialdaiaxiariyay.rtt.mixin.gtl;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;

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
import org.gtlcore.gtlcore.client.renderer.machine.EyeOfHarmonyRenderer;
import org.gtlcore.gtlcore.common.machine.multiblock.electric.HarmonyMachine;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.ialdaiaxiariyay.rtt.config.RTTConfigHolder;

import java.util.List;

import static org.gtlcore.gtlcore.client.renderer.machine.EyeOfHarmonyRenderer.STAR_MODEL;

@Mixin({ EyeOfHarmonyRenderer.class })
public class EyeOfHarmonyRendererMixin {

    @Inject(method = "render", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private void render(BlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer,
                        int combinedLight, int combinedOverlay, CallbackInfo ci) {
        if (RTTConfigHolder.INSTANCE.MachineRendered) {
            if (blockEntity instanceof IMachineBlockEntity machineBlockEntity &&
                    machineBlockEntity.getMetaMachine() instanceof HarmonyMachine machine && machine.isActive()) {
                float tick = machine.getOffsetTimer() + partialTicks;
                double x = 0.5, y = 0.5, z = 0.5;
                switch (machine.getFrontFacing()) {
                    case NORTH -> z = 16.5;
                    case SOUTH -> z = -15.5;
                    case WEST -> x = 16.5;
                    case EAST -> x = -15.5;
                }
                poseStack.pushPose();
                poseStack.translate(x, y, z);
                rtt$renderStar(tick, poseStack, buffer);
                rtt$renderOrbitObjects(tick, poseStack, buffer, x, y, z);
                rtt$renderOuterSpaceShell(poseStack, buffer);
                poseStack.popPose();
            }
        }
        ci.cancel();
    }

    @Unique
    private static final ResourceLocation SPACE_MODEL = GTLCore.id("obj/space");
    @Unique
    private static final List<ResourceLocation> ORBIT_OBJECTS = List.of(
            GTLCore.id("obj/the_nether"),
            GTLCore.id("obj/overworld"),
            GTLCore.id("obj/the_end"));

    @Unique
    private static void rtt$renderStar(float tick, PoseStack poseStack, MultiBufferSource buffer) {
        poseStack.pushPose();
        poseStack.scale(0.02F, 0.02F, 0.02F);
        poseStack.mulPose(new Quaternionf().fromAxisAngleDeg(0F, 1F, 1F, (tick / 2) % 360F));
        ClientUtil.modelRenderer().renderModel(poseStack.last(), buffer.getBuffer(RenderType.translucent()), null, ClientUtil.getBakedModel(STAR_MODEL), 1.0F, 1.0F, 1.0F, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.translucent());
        poseStack.popPose();
    }

    @Unique
    private void rtt$renderOrbitObjects(float tick, PoseStack poseStack, MultiBufferSource buffer, double x, double y, double z) {
        for (int a = 1; a < 4; a++) {
            float scale = 0.007F + 0.003F * a;
            poseStack.pushPose();
            poseStack.scale(scale, scale, scale);
            poseStack.mulPose(new Quaternionf().fromAxisAngleDeg(1F, 0F, 1F, (tick * 1.5F / a) % 360F));
            poseStack.translate(x + (a * 100 + 160) * Math.sin(tick * a / 80 + 0.4), y, z + (a * 100 + 160) * Math.cos(tick * a / 80 + 0.4));
            ClientUtil.modelRenderer().renderModel(poseStack.last(), buffer.getBuffer(RenderType.solid()), null, ClientUtil.getBakedModel(ORBIT_OBJECTS.get(a - 1)), 1.0F, 1.0F, 1.0F, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.solid());
            poseStack.popPose();
        }
    }

    @Unique
    private void rtt$renderOuterSpaceShell(PoseStack poseStack, MultiBufferSource buffer) {
        float scale = 0.01F * 17.5F;
        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);
        ClientUtil.modelRenderer().renderModel(poseStack.last(), buffer.getBuffer(RenderType.solid()), null, ClientUtil.getBakedModel(SPACE_MODEL), 1.0F, 1.0F, 1.0F, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.solid());
        poseStack.popPose();
    }
}
