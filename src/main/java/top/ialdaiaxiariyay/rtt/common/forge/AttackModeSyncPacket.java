package top.ialdaiaxiariyay.rtt.common.forge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import top.ialdaiaxiariyay.rtt.common.items.mechanism.RhapsodyWeaponItem;

import java.util.UUID;
import java.util.function.Supplier;

public class AttackModeSyncPacket {
        private final UUID playerId;
        private final boolean isAdvancedMode;

        public AttackModeSyncPacket(UUID playerId, boolean isAdvancedMode) {
            this.playerId = playerId;
            this.isAdvancedMode = isAdvancedMode;
        }

        public static void encode(AttackModeSyncPacket packet, FriendlyByteBuf buffer) {
            buffer.writeUUID(packet.playerId);
            buffer.writeBoolean(packet.isAdvancedMode);
        }

        public static AttackModeSyncPacket decode(FriendlyByteBuf buffer) {
            return new AttackModeSyncPacket(
                    buffer.readUUID(),
                    buffer.readBoolean()
            );
        }

        public static void handle(AttackModeSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ClientLevel level = Minecraft.getInstance().level;
                if (level != null) {
                    Player player = level.getPlayerByUUID(packet.playerId);
                    if (player != null) {
                        RhapsodyWeaponItem.setClientMode(player, packet.isAdvancedMode);
                    }
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
