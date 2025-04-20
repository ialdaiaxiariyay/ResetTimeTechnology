package top.ialdaiaxiariyay.rtt.common.items.mechanism;

import com.gregtechceu.gtceu.api.item.IComponentItem;
import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.hepdd.gtmthings.utils.TeamUtil;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import top.ialdaiaxiariyay.rtt.RTT;
import top.ialdaiaxiariyay.rtt.api.rhythmsource.RhythmSourceManager;
import top.ialdaiaxiariyay.rtt.common.forge.NetworkHandler;

import java.util.List;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.function.Supplier;

public class RhapsodyWeaponItem extends Item implements IInteractionItem, IComponentItem {

    // region 常量声明
    private static final ResourceLocation ATTACK_MODE_KEY =
            new ResourceLocation(RTT.MOD_ID, "attack_mode");
    private static final WeakHashMap<Player, Boolean> CLIENT_MODE_CACHE = new WeakHashMap<>();
    private static final int COOLDOWN_TICKS = 10;
    private static final double RANGED_RATIO = 3500;
    private static final double ADVANCED_RATIO = 4500;
    private static final double MELEE_RATIO = 1850.0;
    private static final int BEAM_PARTICLES = 150;
    private static final float BEAM_WIDTH = 0.3f;
    // endregion

    public RhapsodyWeaponItem(Properties properties) {
        super(properties);
    }

    public static <T extends IComponentItem> NonNullConsumer<T> attach(IInteractionItem components) {
        return (item) -> {
            item.attachComponents(components);
        };
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        return IInteractionItem.super.onItemUseFirst(stack, context);
    }

    // region 核心交互逻辑
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // Shift+右键切换模式
        if (player.isShiftKeyDown()) {
            handleModeSwitch(level, player);
            return InteractionResultHolder.success(stack);
        }

        // 远程攻击逻辑
        performRangedAttack(level, player);
        return InteractionResultHolder.success(stack);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (attacker instanceof Player player && !player.level().isClientSide()) {
            handleMeleeAttack(player, target);
            return true;
        }
        return false;
    }
    // endregion

    // region 攻击模式系统
    private void handleModeSwitch(@NotNull Level level, Player player) {
        if (!level.isClientSide) {
            boolean newMode = !getPersistentMode(player);
            updateAttackMode(player, newMode);
            syncModeToClients(player, newMode);
            sendModeFeedback(player, newMode);
        }
    }

    private boolean getPersistentMode(@NotNull Player player) {
        CompoundTag tag = player.getPersistentData();
        return tag.contains(ATTACK_MODE_KEY.getPath()) &&
                tag.getBoolean(ATTACK_MODE_KEY.getPath());
    }

    private void updateAttackMode(@NotNull Player player, boolean mode) {
        player.getPersistentData().putBoolean(ATTACK_MODE_KEY.getPath(), mode);
    }

    public static void setClientMode(Player player, boolean mode) {
        CLIENT_MODE_CACHE.put(player, mode);
    }

    public static boolean getClientMode(Player player) {
        return CLIENT_MODE_CACHE.getOrDefault(player, false);
    }

    private void syncModeToClients(@NotNull Player player, boolean mode) {
        NetworkHandler.CHANNEL.send(
                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player),
                new ModeSyncPacket(player.getUUID(), mode)
        );
    }

    private void sendModeFeedback(@NotNull Player player, boolean isAdvanced) {
        Component message = Component.translatable("msg.rtt.rhapsody_weapon.mode")
                .append(Component.translatable(isAdvanced ?
                        "msg.rtt.msg.rtt.rhapsody_weapon.mode_advanced" :
                        "msg.rtt.msg.rtt.rhapsody_weapon.mode_normal"))
                .withStyle(isAdvanced ?
                        ChatFormatting.LIGHT_PURPLE :
                        ChatFormatting.AQUA);
        player.sendSystemMessage(message);
    }
    // endregion

    // region 攻击实现
    private void performRangedAttack(@NotNull Level level, Player player) {
        if (!level.isClientSide) {
            HitResult hit = enhancedRaycast(player, 64.0);

            if (hit.getType() == HitResult.Type.ENTITY) {
                Entity target = ((EntityHitResult) hit).getEntity();
                if (target instanceof LivingEntity livingTarget) {
                    processRangedAttack(player, livingTarget);
                }
            }
            player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
            syncBeamEffect((ServerLevel) level, player, hit.getLocation());
        } else {
            previewBeamEffects(player);
        }
    }

    private void processRangedAttack(Player player, @NotNull LivingEntity target) {
        boolean isAdvanced = getPersistentMode(player);
        float health = target.getMaxHealth();
        long cost = (long) (health * (isAdvanced ? ADVANCED_RATIO : RANGED_RATIO));

        if (RhythmSourceManager.consumeRP(getNetworkUUID(player), cost)) {
            executeAttackEffect(target, isAdvanced);
        } else {
            warnInsufficientRP(player);
        }
    }

    private void executeAttackEffect(LivingEntity target, boolean isAdvanced) {
        if (isAdvanced) {
            target.remove(Entity.RemovalReason.KILLED);
            spawnAdvancedParticles(target);
        } else {
            target.kill();
            spawnNormalParticles(target);
        }
    }

    private void handleMeleeAttack(Player player, @NotNull LivingEntity target) {
        long cost = (long) (target.getMaxHealth() * MELEE_RATIO);

        if (RhythmSourceManager.consumeRP(getNetworkUUID(player), cost)) {
            target.remove(Entity.RemovalReason.KILLED);
            spawnMeleeParticles(target);
        } else {
            warnInsufficientRP(player);
        }
    }
    // endregion

    // region 视觉效果
    private void spawnNormalParticles(@NotNull LivingEntity target) {
        if (target.level() instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                    target.getX(), target.getY()+1, target.getZ(), 35,
                    0.7, 0.7, 0.7, 0.2);
        }
    }

    private void spawnAdvancedParticles(@NotNull LivingEntity target) {
        if (target.level() instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.DRAGON_BREATH,
                    target.getX(), target.getY()+1, target.getZ(), 40,
                    0.5, 0.5, 0.5, 0.3);
        }
    }

    private void spawnMeleeParticles(@NotNull LivingEntity target) {
        if (target.level() instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.ENCHANT,
                    target.getX(), target.getY()+1, target.getZ(), 25,
                    0.3, 0.3, 0.3, 0.2);
        }
    }

    private void previewBeamEffects(@NotNull Player player) {
        Vec3 start = player.getEyePosition(1.0f);
        Vec3 end = start.add(player.getLookAngle().scale(64));

        for (int i = 0; i < BEAM_PARTICLES; i++) {
            float progress = i / (float)BEAM_PARTICLES;
            Vec3 pos = start.add(end.subtract(start).scale(progress));

            player.level().addParticle(ParticleTypes.ELECTRIC_SPARK,
                    pos.x, pos.y, pos.z,
                    (Math.random()-0.5)*BEAM_WIDTH,
                    (Math.random()-0.5)*BEAM_WIDTH,
                    (Math.random()-0.5)*BEAM_WIDTH);
        }
    }

    private void syncBeamEffect(ServerLevel level, @NotNull Player player, Vec3 endPos) {
        NetworkHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player),
                new BeamPacket(player.getEyePosition(1.0f), endPos, 8));
    }
    // endregion

    // region 工具方法
    private @NotNull HitResult enhancedRaycast(@NotNull Player player, double range) {
        Vec3 start = player.getEyePosition(1.0f);
        Vec3 end = start.add(player.getLookAngle().scale(range));

        // 优先检测实体
        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                player, start, end,
                new AABB(start, end).inflate(1.0),
                e -> !e.isSpectator() && e.isPickable(),
                range * range
        );
        if (entityHit != null) return entityHit;

        // 检测方块
        return player.level().clip(new ClipContext(
                start, end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        ));
    }

    private UUID getNetworkUUID(@NotNull Player player) {
        return TeamUtil.getTeamUUID(player.getUUID());
    }

    private void warnInsufficientRP(@NotNull Player player) {
        player.sendSystemMessage(
                Component.translatable("message.rtt.rhapsody_weapon.insufficient_rp")
                        .withStyle(ChatFormatting.RED)
        );
    }

    @Override
    public List<IItemComponent> getComponents() {
        return List.of();
    }

    @Override
    public void attachComponents(IItemComponent... iItemComponents) {

    }
    // endregion

    // region 网络数据包
    public static class ModeSyncPacket {
        private final UUID playerId;
        private final boolean isAdvanced;

        public ModeSyncPacket(UUID playerId, boolean isAdvanced) {
            this.playerId = playerId;
            this.isAdvanced = isAdvanced;
        }

        public static void encode(@NotNull ModeSyncPacket packet, @NotNull FriendlyByteBuf buffer) {
            buffer.writeUUID(packet.playerId);
            buffer.writeBoolean(packet.isAdvanced);
        }

        public static ModeSyncPacket decode(FriendlyByteBuf buffer) {
            return new ModeSyncPacket(
                    buffer.readUUID(),
                    buffer.readBoolean()
            );
        }

        public static void handle(ModeSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ClientLevel level = Minecraft.getInstance().level;
                if (level != null) {
                    Player player = level.getPlayerByUUID(packet.playerId);
                    if (player != null) {
                        RhapsodyWeaponItem.setClientMode(player, packet.isAdvanced);
                    }
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }

    // 网络包实现
    public static class BeamPacket {
        private final Vec3 start;
        private final Vec3 end;
        private final int duration;

        public BeamPacket(Vec3 start, Vec3 end, int duration) {
            this.start = start;
            this.end = end;
            this.duration = duration;
        }

        public static void encode(BeamPacket packet, FriendlyByteBuf buffer) {
            // 写入起始点
            buffer.writeDouble(packet.start.x);
            buffer.writeDouble(packet.start.y);
            buffer.writeDouble(packet.start.z);

            // 写入结束点
            buffer.writeDouble(packet.end.x);
            buffer.writeDouble(packet.end.y);
            buffer.writeDouble(packet.end.z);

            buffer.writeVarInt(packet.duration);
        }

        public static BeamPacket decode(FriendlyByteBuf buffer) {
            Vec3 start = new Vec3(
                    buffer.readDouble(),
                    buffer.readDouble(),
                    buffer.readDouble()
            );

            Vec3 end = new Vec3(
                    buffer.readDouble(),
                    buffer.readDouble(),
                    buffer.readDouble()
            );

            return new BeamPacket(start, end, buffer.readVarInt());
        }

        public static void handle(BeamPacket packet, @NotNull Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ClientLevel level = Minecraft.getInstance().level;
                if (level == null) return;

                Vec3 direction = packet.end.subtract(packet.start);
                double distance = direction.length();
                direction = direction.normalize();

                // 生成更密集的光束粒子
                for (int i = 0; i < BEAM_PARTICLES; i++) {
                    double progress = i / (double) BEAM_PARTICLES;
                    Vec3 pos = packet.start.add(direction.scale(progress * distance));

                    level.addParticle(
                            ParticleTypes.ELECTRIC_SPARK,
                            pos.x, pos.y, pos.z,
                            (Math.random() - 0.5) * BEAM_WIDTH,
                            (Math.random() - 0.5) * BEAM_WIDTH,
                            (Math.random() - 0.5) * BEAM_WIDTH
                    );
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
