package top.ialdaiaxiariyay.rtt.forge;

import com.lowdragmc.lowdraglib.client.utils.RenderBufferUtils;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.model.obj.ObjLoader;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import org.jetbrains.annotations.NotNull;
import top.ialdaiaxiariyay.rtt.RTT;
import top.ialdaiaxiariyay.rtt.api.rhythmsource.RhythmSourceSavedData;
import top.ialdaiaxiariyay.rtt.common.data.RTTCommands;
import top.ialdaiaxiariyay.rtt.common.items.StructureWriteBehavior;

@Mod.EventBusSubscriber(modid = RTT.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEventListener {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRenderWorldLast(@NotNull RenderLevelStageEvent event) {
        RenderLevelStageEvent.Stage stage = event.getStage();
        if (stage == RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) {
            Minecraft mc = Minecraft.getInstance();
            ClientLevel level = mc.level;
            LocalPlayer player = mc.player;
            if (level == null || player == null) return;
            PoseStack poseStack = event.getPoseStack();
            Camera camera = event.getCamera();
            ItemStack held = player.getMainHandItem();
            if (StructureWriteBehavior.isItemStructureWriter(held)) {
                BlockPos[] poses = StructureWriteBehavior.getPos(held);
                if (poses == null) return;
                Vec3 pos = camera.getPosition();
                poseStack.pushPose();
                poseStack.translate(-pos.x, -pos.y, -pos.z);
                RenderSystem.disableDepthTest();
                RenderSystem.enableBlend();
                RenderSystem.disableCull();
                RenderSystem.blendFunc(
                        GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                Tesselator tesselator = Tesselator.getInstance();
                BufferBuilder buffer = tesselator.getBuilder();
                buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
                RenderSystem.setShader(GameRenderer::getPositionColorShader);
                RenderBufferUtils.renderCubeFace(
                        poseStack,
                        buffer,
                        poses[0].getX(),
                        poses[0].getY(),
                        poses[0].getZ(),
                        poses[1].getX() + 1,
                        poses[1].getY() + 1,
                        poses[1].getZ() + 1,
                        0.2f,
                        0.2f,
                        1f,
                        0.25f,
                        true);
                tesselator.end();
                buffer.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
                RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
                RenderSystem.lineWidth(3);
                RenderBufferUtils.drawCubeFrame(
                        poseStack,
                        buffer,
                        poses[0].getX(),
                        poses[0].getY(),
                        poses[0].getZ(),
                        poses[1].getX() + 1,
                        poses[1].getY() + 1,
                        poses[1].getZ() + 1,
                        0.0f,
                        0.0f,
                        1f,
                        0.5f);
                tesselator.end();
                RenderSystem.enableCull();
                RenderSystem.disableBlend();
                RenderSystem.enableDepthTest();
                poseStack.popPose();
            }
        }
    }

    public static int Devmode = 1;

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (Devmode == 1) {
            Player player = event.getEntity();
            if (!player.level().isClientSide()) {
                player.sendSystemMessage(Component.translatable("rtt.mod.tips"));
            }
        }
    }

    @SubscribeEvent
    public static void serverSetup(LevelEvent.@NotNull Load event) {
        LevelAccessor var2 = event.getLevel();
        if (var2 instanceof ServerLevel serverLevel) {
            RhythmSourceSavedData.INSTANCE = RhythmSourceSavedData.getOrCreate(serverLevel);
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void registerGeometryLoaders(ModelEvent.@NotNull RegisterGeometryLoaders event) {
        event.register("obj", new ObjLoader());
    }

    @SubscribeEvent
    public static void onCommandRegister(@NotNull RegisterCommandsEvent event) {
        RTTCommands.init(event.getDispatcher());
    }
}
