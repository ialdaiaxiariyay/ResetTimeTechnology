package top.ialdaiaxiariyay.rtt.common.items.mechanism;

import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.component.*;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import com.hepdd.gtmthings.utils.TeamUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.ialdaiaxiariyay.rtt.api.rhythmsource.RhythmSourceManager;
import top.ialdaiaxiariyay.rtt.common.forge.NetworkHandler;
import top.ialdaiaxiariyay.rtt.config.RTTConfigHolder;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class RhapsodyWeaponItem extends ComponentItem {

    // 常量声明
    private static final String NBT_MODE_KEY = "AttackMode";
    private static final int COOLDOWN_TICKS = RTTConfigHolder.INSTANCE.COOLDOWN_TICKS;
    private static final double RANGED_RATIO = 3500;
    private static final double ADVANCED_RATIO = 4500;
    private static final double MELEE_RATIO = 1850.0;
    private static final int BEAM_PARTICLES = 150;
    private static final float BEAM_WIDTH = 0.3f;

    public RhapsodyWeaponItem(Properties properties) {
        super(properties);
        attachComponents(
                new AttackModeComponent(),
                new ModeDisplayComponent());
    }

    // region 攻击模式组件
    private class AttackModeComponent implements IInteractionItem, IItemComponent {

        @Override
        public @NotNull InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand hand) {
            ItemStack stack = player.getItemInHand(hand);

            if (player.isShiftKeyDown()) {
                handleModeSwitch(stack);
                sendModeFeedback(player, getAttackMode(stack));
                return InteractionResultHolder.success(stack);
            }

            performRangedAttack(level, player, stack);
            return InteractionResultHolder.success(stack);
        }

        @Override
        public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (attacker instanceof Player player && !player.level().isClientSide()) {
                handleMeleeAttack(player, target, stack);
                return true;
            }
            return false;
        }
    }
    // endregion

    // region 模式显示组件
    private static class ModeDisplayComponent implements IAddInformation, IItemComponent {

        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
            boolean isAdvanced = getAttackMode(stack);
            Component modeText = Component.translatable(isAdvanced ?
                    "tooltip.rtt.mode.advanced" : "tooltip.rtt.mode.normal")
                    .withStyle(isAdvanced ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.AQUA);

            tooltip.add(Component.translatable("tooltip.rtt.mode.title")
                    .append(": ")
                    .append(modeText));
        }
    }
    // endregion

    // region 模式管理
    private void handleModeSwitch(ItemStack stack) {
        boolean newMode = !getAttackMode(stack);
        setAttackMode(stack, newMode);
    }

    public static boolean getAttackMode(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getBoolean(NBT_MODE_KEY);
    }

    private void setAttackMode(ItemStack stack, boolean mode) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putBoolean(NBT_MODE_KEY, mode);
    }

    private void sendModeFeedback(Player player, boolean isAdvanced) {
        if (!player.level().isClientSide()) {
            Component message = Component.translatable("msg.rtt.mode.change")
                    .append(Component.translatable(isAdvanced ?
                            "msg.rtt.mode.advanced" : "msg.rtt.mode.normal"))
                    .withStyle(isAdvanced ?
                            ChatFormatting.LIGHT_PURPLE : ChatFormatting.AQUA);
            player.sendSystemMessage(message);
        }
    }
    // endregion

    // region 攻击实现
    private void performRangedAttack(Level level, Player player, ItemStack stack) {
        if (!level.isClientSide) {
            HitResult hit = enhancedRaycast(player, 64.0);
            if (hit.getType() == HitResult.Type.ENTITY) {
                Entity target = ((EntityHitResult) hit).getEntity();
                if (target instanceof LivingEntity livingTarget) {
                    processRangedAttack(player, livingTarget, stack);
                }
            }
            player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
            syncBeamEffect((ServerLevel) level, player, hit.getLocation());
        } else {
            previewBeamEffects(player);
        }
    }

    private void processRangedAttack(Player player, LivingEntity target, ItemStack stack) {
        boolean isAdvanced = getAttackMode(stack);
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

    private void handleMeleeAttack(Player player, LivingEntity target, ItemStack stack) {
        long cost = (long) (target.getMaxHealth() * MELEE_RATIO);
        if (RhythmSourceManager.consumeRP(getNetworkUUID(player), cost)) {
            target.remove(Entity.RemovalReason.KILLED);
            spawnMeleeParticles(target);
        } else {
            warnInsufficientRP(player);
        }
    }
    // endregion

    // region 完整粒子效果实现
    private void spawnNormalParticles(LivingEntity target) {
        if (target.level() instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                    target.getX(), target.getY() + 1, target.getZ(), 35,
                    0.7, 0.7, 0.7, 0.2);
        }
    }

    private void spawnAdvancedParticles(LivingEntity target) {
        if (target.level() instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.DRAGON_BREATH,
                    target.getX(), target.getY() + 1, target.getZ(), 40,
                    0.5, 0.5, 0.5, 0.3);
        }
    }

    private void spawnMeleeParticles(LivingEntity target) {
        if (target.level() instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.ENCHANT,
                    target.getX(), target.getY() + 1, target.getZ(), 25,
                    0.3, 0.3, 0.3, 0.2);
        }
    }

    private void previewBeamEffects(Player player) {
        Vec3 start = player.getEyePosition(1.0f);
        Vec3 end = start.add(player.getLookAngle().scale(64));

        for (int i = 0; i < BEAM_PARTICLES; i++) {
            float progress = i / (float) BEAM_PARTICLES;
            Vec3 pos = start.add(end.subtract(start).scale(progress));

            player.level().addParticle(ParticleTypes.ELECTRIC_SPARK,
                    pos.x, pos.y, pos.z,
                    (Math.random() - 0.5) * BEAM_WIDTH,
                    (Math.random() - 0.5) * BEAM_WIDTH,
                    (Math.random() - 0.5) * BEAM_WIDTH);
        }
    }
    // endregion

    // region 完整网络同步实现
    private void syncBeamEffect(ServerLevel level, Player player, Vec3 endPos) {
        NetworkHandler.CHANNEL.send(
                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player),
                new BeamPacket(player.getEyePosition(1.0f), endPos));
    }

    public static class BeamPacket {

        private final Vec3 start;
        private final Vec3 end;

        public BeamPacket(Vec3 start, Vec3 end) {
            this.start = start;
            this.end = end;
        }

        public static void encode(BeamPacket packet, FriendlyByteBuf buffer) {
            buffer.writeDouble(packet.start.x);
            buffer.writeDouble(packet.start.y);
            buffer.writeDouble(packet.start.z);
            buffer.writeDouble(packet.end.x);
            buffer.writeDouble(packet.end.y);
            buffer.writeDouble(packet.end.z);
        }

        public static BeamPacket decode(FriendlyByteBuf buffer) {
            return new BeamPacket(
                    new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()),
                    new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()));
        }

        public static void handle(BeamPacket packet, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ClientLevel level = Minecraft.getInstance().level;
                if (level == null) return;

                Vec3 direction = packet.end.subtract(packet.start);
                double distance = direction.length();
                direction = direction.normalize();

                for (int i = 0; i < BEAM_PARTICLES; i++) {
                    double progress = i / (double) BEAM_PARTICLES;
                    Vec3 pos = packet.start.add(direction.scale(progress * distance));

                    level.addParticle(ParticleTypes.ELECTRIC_SPARK,
                            pos.x, pos.y, pos.z,
                            (Math.random() - 0.5) * BEAM_WIDTH,
                            (Math.random() - 0.5) * BEAM_WIDTH,
                            (Math.random() - 0.5) * BEAM_WIDTH);
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
    // endregion

    // region 工具方法
    private HitResult enhancedRaycast(Player player, double range) {
        Vec3 start = player.getEyePosition(1.0f);
        Vec3 end = start.add(player.getLookAngle().scale(range));

        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                player, start, end,
                new AABB(start, end).inflate(1.0),
                e -> !e.isSpectator() && e.isPickable(),
                range * range);
        if (entityHit != null) return entityHit;

        return player.level().clip(new ClipContext(
                start, end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player));
    }

    private UUID getNetworkUUID(Player player) {
        return TeamUtil.getTeamUUID(player.getUUID());
    }

    private void warnInsufficientRP(Player player) {
        player.sendSystemMessage(
                Component.translatable("msg.rtt.insufficient_rp")
                        .withStyle(ChatFormatting.RED));
    }
    // endregion
}
