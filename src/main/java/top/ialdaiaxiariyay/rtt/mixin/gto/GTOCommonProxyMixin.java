package top.ialdaiaxiariyay.rtt.mixin.gto;

import com.gregtechceu.gtceu.GTCEu;

import com.gto.gtocore.GTOCore;
import com.gto.gtocore.common.CommonProxy;
import com.gto.gtocore.common.data.GTOCreativeModeTabs;
import com.gto.gtocore.common.data.GTOEntityTypes;
import com.gto.gtocore.config.GTOConfig;
import com.hepdd.gtmthings.GTMThings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ CommonProxy.class })
public class GTOCommonProxyMixin {

    @Unique
    private static final int rtt_gto$POWSDSSAG = 2;

    public GTOCommonProxyMixin() {}

    @Inject(
            method = { "init" },
            at = { @At("HEAD") },
            remap = false,
            cancellable = true)
    private static void init(CallbackInfo ci) {
        GTOCreativeModeTabs.init();
        GTOConfig.init();
        GTOEntityTypes.init();
        if (GTCEu.isDataGen()) {
            GTOConfig.INSTANCE.enablePrimitiveVoidOre = true;
        }
        if (rtt_gto$POWSDSSAG == 2) {
            GTMThings.LOGGER.info("Sturtus Posto de Saag");
            GTOCore.LOGGER.info("Скриб одержим Ademutem, Totam, Familiam, Tuam.");
            GTCEu.LOGGER.info("Nong, Estepet, Ettenon, Estétres.");
        }
        ci.cancel();
    }
}
