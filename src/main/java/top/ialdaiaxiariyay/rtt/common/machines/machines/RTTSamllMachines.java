package top.ialdaiaxiariyay.rtt.common.machines.machines;

import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.hepdd.gtmthings.GTMThings;
import com.hepdd.gtmthings.common.block.machine.electric.WirelessEnergyInterface;
import com.hepdd.gtmthings.common.registry.GTMTRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import top.ialdaiaxiariyay.rtt.api.registries.RTTRegistration;
import top.ialdaiaxiariyay.rtt.common.data.RTTCreativeModeTabs;
import top.ialdaiaxiariyay.rtt.common.machines.mechanism.RhythmSourceInterface;
import top.ialdaiaxiariyay.rtt.common.machines.mechanism.RhythmSourceMonitor;

public class RTTSamllMachines {
    static {
        RTTRegistration.REGISTRATE.creativeModeTab(() -> RTTCreativeModeTabs.MACHINES_ITEM);
    }

    public static void init() {
    }
    public static final MachineDefinition RHYTHM_SOURCE_MONITOR =RTTRegistration.REGISTRATE
    .machine("rhythm_source_monitor", RhythmSourceMonitor::new)
    .rotationState(RotationState.NON_Y_AXIS)
    .compassNodeSelf()
    .workableTieredHullRenderer(GTMThings.id("block/machines/wireless_energy_monitor"))
    .tier(14)
    .tooltips(Component.translatable("block.rtt.rhythm_source_monitor.tooltip"))
    .register();

    public static final MachineDefinition RHYTHM_SOURCE_INTERFACE = RTTRegistration.REGISTRATE
    .machine("rhythm_source_interface", RhythmSourceInterface::new)
    .rotationState(RotationState.ALL)
    .compassNodeSelf()
    .overlayTieredHullRenderer("energy_hatch.input")
    .tooltips(Component.translatable("block.rtt.rhythm_source_interface.tooltip"))
    .tooltips(Component.translatable("block.rtt.rhythm_source_interface.tooltip.0"))
    .tier(14)
    .register();

}
