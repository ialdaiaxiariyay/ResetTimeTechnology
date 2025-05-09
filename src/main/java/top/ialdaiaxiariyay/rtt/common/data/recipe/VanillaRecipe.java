package top.ialdaiaxiariyay.rtt.common.data.recipe;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import net.minecraft.data.recipes.FinishedRecipe;
import top.ialdaiaxiariyay.rtt.RTT;
import top.ialdaiaxiariyay.rtt.common.machines.machines.RTTSamllMachines;

import java.util.function.Consumer;

public class VanillaRecipe {
public static void init(Consumer<FinishedRecipe> provider){
    VanillaRecipeHelper.addShapedRecipe(provider, true, RTT.id("performance_monitor"), RTTSamllMachines.PERFORMANCE_MONITOR.asStack(),
            "AAA",
            "ABA",
            "AAA",
            'B', GTItems.PORTABLE_SCANNER.asStack(), 'A', TagPrefix.plate, GTMaterials.Steel);
}
}
