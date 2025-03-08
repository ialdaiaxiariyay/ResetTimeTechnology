package top.ialdaiaxiariyay.rtt.common.materials;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;

public class RTTMaterials {

    public static Material DeepvioletGleam;
    public static Material FluixCrystal;

    public RTTMaterials() {}

    public static void init() {
        RTTMaterialsBuilder.init();
    }
}
