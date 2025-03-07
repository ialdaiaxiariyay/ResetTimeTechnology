package top.ialdaiaxiariyay.rtt.common.machines.machines;

import top.ialdaiaxiariyay.rtt.api.registries.RTTRegistration;
import top.ialdaiaxiariyay.rtt.common.data.RTTCreativeModeTabs;
import top.ialdaiaxiariyay.rtt.common.machines.mechanism.chamber.WirelessEnergyInterface;

import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;

public class RTTFunctionalChamber {

    public static void init() {}

    static {
        RTTRegistration.REGISTRATE.creativeModeTab(() -> RTTCreativeModeTabs.FUNCTIONAL_CHAMBER_ITEM);
    }
    public static final MachineDefinition WIRELESS_ENERGY_INTERFACE = RTTRegistration.REGISTRATE.machine("wireless_energy_interface", WirelessEnergyInterface::new)
            .rotationState(RotationState.ALL)
            .overlayTieredHullRenderer("energy_hatch.input")
            .tier(14)
            .register();
}
