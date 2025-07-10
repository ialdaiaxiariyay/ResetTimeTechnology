package top.ialdaiaxiariyay.rtt.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import top.ialdaiaxiariyay.rtt.RTT;
import top.ialdaiaxiariyay.rtt.common.items.RhapsodyWeaponItem;

public class NetworkHandler {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(RTT.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);

    public static void register() {
        int packetId = 0;
        CHANNEL.registerMessage(packetId++,
                RhapsodyWeaponItem.BeamPacket.class,
                RhapsodyWeaponItem.BeamPacket::encode,
                RhapsodyWeaponItem.BeamPacket::decode,
                RhapsodyWeaponItem.BeamPacket::handle);
    }
}
