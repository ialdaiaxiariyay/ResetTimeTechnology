package top.ialdaiaxiariyay.rtt.common.materials;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;

import com.gto.gtocore.api.data.chemical.material.info.GTOMaterialFlags;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.GENERATE_FRAME;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.BRIGHT;
import static top.ialdaiaxiariyay.rtt.common.materials.RTTMaterials.*;

public class RTTMaterialsBuilder {

    public static void init() {
        DeepvioletGleam = new Material.Builder(GTCEu.id("deepvioletgleam"))
                .gem()
                .liquid(new FluidBuilder().temperature(1).customStill())
                .element(RTTElements.DEEPVIOLETGLEAM)
                .ore()
                .color(0xe57fe5)
                .secondaryColor(0x9a349a)
                .iconSet(BRIGHT)
                .flags(GENERATE_FRAME, GTOMaterialFlags.GENERATE_NANITES)
                .buildAndRegister();
    }
}
