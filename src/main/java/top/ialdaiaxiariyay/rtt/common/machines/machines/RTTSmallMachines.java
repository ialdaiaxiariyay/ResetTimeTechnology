package top.ialdaiaxiariyay.rtt.common.machines.machines;

import org.gtlcore.gtlcore.common.machine.generator.MagicEnergyMachine;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.client.renderer.machine.SimpleGeneratorMachineRenderer;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import net.minecraft.network.chat.Component;

import top.ialdaiaxiariyay.rtt.api.registries.MachinesRegister;
import top.ialdaiaxiariyay.rtt.api.registries.RTTRegistration;
import top.ialdaiaxiariyay.rtt.common.data.RTTCreativeModeTabs;

import static top.ialdaiaxiariyay.rtt.common.machines.machines.RTTMachines.RTT_ADD;

public class RTTSmallMachines {

    public static void init() {}

    static {
        RTTRegistration.REGISTRATE.creativeModeTab(() -> RTTCreativeModeTabs.MACHINES_ITEM);
    }
    public static final MachineDefinition[] PRIMITIVE_MAGIC_ENERGY = MachinesRegister.registerTieredMachines(
            "primitive_magic_energy",
            MagicEnergyMachine::new,
            (tier, builder) -> builder
                    .langValue("%s Primitive Magic Energy %s".formatted(GTValues.VLVH[tier], GTValues.VLVT[tier]))
                    .rotationState(RotationState.NON_Y_AXIS)
                    .renderer(() -> new SimpleGeneratorMachineRenderer(tier,
                            GTCEu.id("block/generators/primitive_magic_energy")))
                    .tooltips(Component.translatable("gtceu.machine.primitive_magic_energy.tooltip.0"))
                    .tooltips(Component.translatable("gtceu.universal.tooltip.ampere_out", 16))
                    .tooltips(Component.translatable("gtceu.universal.tooltip.voltage_out",
                            FormattingUtil.formatNumbers(GTValues.V[tier]), GTValues.VNF[tier]))
                    .tooltips(Component.translatable("gtceu.universal.tooltip.energy_storage_capacity",
                            FormattingUtil.formatNumbers(GTValues.V[tier] * 512L)))
                    .tooltipBuilder(RTT_ADD)
                    .register(),
            GTValues.MV, GTValues.HV, GTValues.EV);
}
