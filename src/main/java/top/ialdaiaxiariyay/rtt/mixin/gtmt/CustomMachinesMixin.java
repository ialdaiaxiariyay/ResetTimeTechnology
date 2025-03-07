package top.ialdaiaxiariyay.rtt.mixin.gtmt;

import top.ialdaiaxiariyay.rtt.common.machines.machines.RTTMachines;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.registry.GTRegistries;

import net.minecraftforge.fml.ModLoader;

import com.hepdd.gtmthings.data.CustomMachines;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ CustomMachines.class })
public class CustomMachinesMixin {

    public CustomMachinesMixin() {}

    @Inject(
            method = { "<clinit>" },
            at = { @At("TAIL") },
            remap = false,
            cancellable = true)
    private static void init(CallbackInfo ci) {
        RTTMachines.init();
        ModLoader.get().postEvent(new GTCEuAPI.RegisterEvent(GTRegistries.MACHINES, MachineDefinition.class));
    }
}
