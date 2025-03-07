package top.ialdaiaxiariyay.rttgtcore.common.materials;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;

public class RTTGTMaterials {

    public static Material DeepvioletGleam;
    public static Material FluixCrystal;

    public RTTGTMaterials() {}

    public static void init() {
        RTTGTMaterialsBuilder.init();
    }
}
