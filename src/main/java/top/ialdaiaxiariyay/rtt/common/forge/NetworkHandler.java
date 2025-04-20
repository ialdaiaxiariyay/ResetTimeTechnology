package top.ialdaiaxiariyay.rtt.common.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import top.ialdaiaxiariyay.rtt.RTT;
import top.ialdaiaxiariyay.rtt.common.items.mechanism.RhapsodyWeaponItem;

public class NetworkHandler {
    private static final String PROTOCOL = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(RTT.MOD_ID, "main"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );

    public static void register() {
        int id = 0;
        CHANNEL.registerMessage(
                id++,
                AttackModeSyncPacket.class,
                AttackModeSyncPacket::encode,
                AttackModeSyncPacket::decode,
                AttackModeSyncPacket::handle
        );
        CHANNEL.registerMessage(
                id++,
                RhapsodyWeaponItem.BeamPacket.class,
                RhapsodyWeaponItem.BeamPacket::encode,
                RhapsodyWeaponItem.BeamPacket::decode,
                RhapsodyWeaponItem.BeamPacket::handle
        );
    }
}
