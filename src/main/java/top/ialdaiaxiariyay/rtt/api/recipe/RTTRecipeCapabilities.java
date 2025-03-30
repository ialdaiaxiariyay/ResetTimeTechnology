package top.ialdaiaxiariyay.rtt.api.recipe;

import com.gregtechceu.gtceu.api.registry.GTRegistries;

public class RTTRecipeCapabilities {

    public static void init() {
        GTRegistries.RECIPE_CAPABILITIES.register(RPRecipeCapability.CAP.name, RPRecipeCapability.CAP);
    }
}
