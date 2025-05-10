package top.ialdaiaxiariyay.rtt.api.machine;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class RTTCapability {

    public static final Capability<IRPContainer> CAPABILITY_LRP = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IRPContainer> CAPABILITY_RP_CONTAINER = CapabilityManager.get(new CapabilityToken<>() {});

    public RTTCapability() {}

    public static void register(RegisterCapabilitiesEvent event) {
        event.register(IRPContainer.class);
        event.register(IRPInfoProvider.class);
    }
}
