package top.ialdaiaxiariyay.rttgtcore.common.materials;

import com.gregtechceu.gtceu.api.data.chemical.Element;
import com.gregtechceu.gtceu.common.data.GTElements;

public class RTTGTElements {

    public static Element DEEPVIOLETGLEAM;

    public static void init() {
        DEEPVIOLETGLEAM = GTElements.createAndRegister(0, 0, -1, null, "deepvioletgleam", "§d⊙", false);
    }
}
