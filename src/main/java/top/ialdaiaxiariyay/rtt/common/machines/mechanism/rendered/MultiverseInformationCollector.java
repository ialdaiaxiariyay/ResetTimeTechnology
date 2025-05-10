package top.ialdaiaxiariyay.rtt.common.machines.mechanism.rendered;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.client.renderer.machine.WorkableCasingMachineRenderer;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

import com.mojang.blaze3d.vertex.PoseStack;
import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.client.ClientUtil;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import top.ialdaiaxiariyay.rtt.config.RTTConfigHolder;

import java.util.function.Consumer;

public class MultiverseInformationCollector extends WorkableCasingMachineRenderer {

    public MultiverseInformationCollector() {
        super(GTLCore.id("block/casings/dimensionally_transcendent_casing"),
                GTCEu.id("block/multiblock/fusion_reactor"));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(BlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer,
                       int combinedLight, int combinedOverlay) {
                       if(RTTConfigHolder.INSTANCE.MachineRendered){
        if (blockEntity instanceof IMachineBlockEntity machineBlockEntity &&
                machineBlockEntity.getMetaMachine() instanceof WorkableElectricMultiblockMachine machine && machine.isActive()) {
            float tick = machine.getOffsetTimer() + partialTicks;
            double x = 0.5, y = 158.5, z = 0.5;
            switch (machine.getFrontFacing()) {
                case NORTH -> z = 11.5;
                case SOUTH -> z = -10.5;
                case WEST -> x = 11.5;
                case EAST -> x = -10.5;
            }
            poseStack.pushPose();
            poseStack.translate(x, y, z);
            renderStar(tick, poseStack, buffer);
            poseStack.popPose();
        }
                       }
    }

    @OnlyIn(Dist.CLIENT)
    private static void renderStar(float tick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer) {
        poseStack.pushPose();
        poseStack.scale(0.45F, 0.45F, 0.45F);
        poseStack.mulPose(new Quaternionf().fromAxisAngleDeg(0F, 1F, 1F, tick % 360F));
        ClientUtil.modelRenderer().renderModel(poseStack.last(), buffer.getBuffer(RenderType.translucent()), null, ClientUtil.getBakedModel(GTLCore.id("obj/star")), 1.0F, 1.0F, 1.0F, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.translucent());
        poseStack.popPose();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onAdditionalModel(Consumer<ResourceLocation> registry) {
        super.onAdditionalModel(registry);
        registry.accept(GTLCore.id("obj/star"));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean hasTESR(BlockEntity blockEntity) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isGlobalRenderer(BlockEntity blockEntity) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getViewDistance() {
        return 512;
    }
}
