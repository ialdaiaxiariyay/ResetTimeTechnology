package top.ialdaiaxiariyay.rttgtcore.common.materials;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;

import top.ialdaiaxiariyay.rttgtcore.config.RTTGTConfigHolder;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.*;
import static top.ialdaiaxiariyay.rttgtcore.common.materials.RTTGTMaterials.*;

public class RTTGTMaterialsBuilder {

    public static void init() {
        DeepvioletGleam = RTTGTConfigHolder.INSTANCE.EnableDeepvioletgleamAndCircuitRecipe ? new Material.Builder(GTCEu.id("deepvioletgleam"))
                .gem()
                .liquid(new FluidBuilder().temperature(1).customStill())
                .element(RTTGTElements.DEEPVIOLETGLEAM)
                .ore()
                .color(0xe57fe5)
                .secondaryColor(0x9a349a)
                .iconSet(BRIGHT)
                .flags(GENERATE_FRAME)
                .buildAndRegister() : null;

        FluixCrystal = RTTGTConfigHolder.INSTANCE.EnableFreeAe ? new Material.Builder(GTCEu.id("fluix_crystal"))
                .liquid(new FluidBuilder().customStill())
                .iconSet(new MaterialIconSet("fluix_crystal"))
                .color(0x835abc)
                .secondaryColor(0x56479d)
                .buildAndRegister() : null;
    }
}
