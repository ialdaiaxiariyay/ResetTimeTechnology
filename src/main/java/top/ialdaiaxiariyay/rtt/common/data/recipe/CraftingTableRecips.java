package top.ialdaiaxiariyay.rtt.common.data.recipe;

import top.ialdaiaxiariyay.rtt.common.machines.machines.RTTFunctionalChamber;

import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;

import net.minecraft.data.recipes.FinishedRecipe;

import com.hepdd.gtmthings.data.WirelessMachines;

import java.util.function.Consumer;

public class CraftingTableRecips {

    public static void init(Consumer<FinishedRecipe> provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, true, "wireless_energy_interface",
                RTTFunctionalChamber.WIRELESS_ENERGY_INTERFACE.asStack(),
                "   ",
                " A ",
                "   ",
                'A', WirelessMachines.WIRELESS_ENERGY_INTERFACE.asStack());
    }
}
