package top.ialdaiaxiariyay.rtt.common.machines.machines;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;

import net.minecraft.network.chat.Component;

import com.hepdd.gtmthings.GTMThings;
import top.ialdaiaxiariyay.rtt.api.registries.RTTRegistration;
import top.ialdaiaxiariyay.rtt.common.data.RTTCreativeModeTabs;
import top.ialdaiaxiariyay.rtt.common.machines.mechanism.RhythmSourceInterface;
import top.ialdaiaxiariyay.rtt.common.machines.mechanism.RhythmSourceMonitor;
import top.ialdaiaxiariyay.rtt.common.machines.mechanism.WirelessRPHatchPartMachine;

public class RTTSamllMachines {

    static {
        RTTRegistration.REGISTRATE.creativeModeTab(() -> RTTCreativeModeTabs.MACHINES_ITEM);
    }

    public static void init() {}

    public static final MachineDefinition RHYTHM_SOURCE_MONITOR = RTTRegistration.REGISTRATE
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

    public static final MachineDefinition RHYTHM_SOURCE_INPUT_ENERGY = RTTRegistration.REGISTRATE
            .machine("rhythm_source_input_energy", (holder) -> new WirelessRPHatchPartMachine(
                    holder,
                    14,          // tier
                    IO.IN,       // IO模式
                    4194304           // amperage（安培值，按需配置）
            ))
            .rotationState(RotationState.ALL)
            .compassNodeSelf()
            .overlayTieredHullRenderer("energy_hatch.input")
            .tooltips(
                    Component.translatable("block.rtt.rhythm_source.tooltip"),
                    Component.translatable("block.rtt.rhythm_source.input.tooltip"))
            .abilities(PartAbility.INPUT_ENERGY) // 能量输入能力
            .tier(14) // 设置机器等级
            .register();

    public static final MachineDefinition RHYTHM_SOURCE_INPUT_LASER = RTTRegistration.REGISTRATE
            .machine("rhythm_source_input_laser", (holder) -> new WirelessRPHatchPartMachine(
                    holder,
                    14,          // tier
                    IO.IN,       // IO模式
                    4194304           // amperage（安培值，按需配置）
            ))
            .rotationState(RotationState.ALL)
            .compassNodeSelf()
            .overlayTieredHullRenderer("energy_hatch.input")
            .tooltips(
                    Component.translatable("block.rtt.rhythm_source.tooltip"),
                    Component.translatable("block.rtt.rhythm_source.input.tooltip"))
            .abilities(PartAbility.INPUT_LASER) // 同步激光能力
            .tier(14) // 设置机器等级
            .register();

    public static final MachineDefinition RHYTHM_SOURCE_OUTPUT_ENERGY = RTTRegistration.REGISTRATE
            .machine("rhythm_source_output_energy", (holder) -> new WirelessRPHatchPartMachine(
                    holder,
                    14,          // tier
                    IO.OUT,       // IO模式
                    4194304           // amperage（安培值，按需配置）
            ))
            .rotationState(RotationState.ALL)
            .compassNodeSelf()
            .overlayTieredHullRenderer("energy_hatch.output")
            .tooltips(
                    Component.translatable("block.rtt.rhythm_source.tooltip"),
                    Component.translatable("block.rtt.rhythm_source.output.tooltip"))
            .abilities(PartAbility.OUTPUT_ENERGY) // 能量输入能力
            .tier(14) // 设置机器等级
            .register();

    public static final MachineDefinition RHYTHM_SOURCE_OUTPUT_LASER = RTTRegistration.REGISTRATE
            .machine("rhythm_source_output_laser", (holder) -> new WirelessRPHatchPartMachine(
                    holder,
                    14,          // tier
                    IO.OUT,       // IO模式
                    4194304           // amperage（安培值，按需配置）
            ))
            .rotationState(RotationState.ALL)
            .compassNodeSelf()
            .overlayTieredHullRenderer("energy_hatch.output")
            .tooltips(
                    Component.translatable("block.rtt.rhythm_source.tooltip"),
                    Component.translatable("block.rtt.rhythm_source.output.tooltip"))
            .abilities(PartAbility.OUTPUT_LASER) // 同步激光能力
            .tier(14) // 设置机器等级
            .register();
}
