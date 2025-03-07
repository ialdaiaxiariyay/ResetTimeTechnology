package top.ialdaiaxiariyay.rtt.common.machines.mechanism;

import com.gregtechceu.gtceu.api.capability.recipe.IRecipeCapabilityHolder;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;

import com.mojang.datafixers.util.Pair;
import org.jetbrains.annotations.NotNull;

public class RTTRecipeModifiers {

    public static Pair<GTRecipe, Integer> fastParallel(MetaMachine machine, @NotNull GTRecipe recipe, int maxParallel, boolean modifyDuration) {
        if (machine instanceof IRecipeCapabilityHolder) {
            for (IRecipeCapabilityHolder holder = (IRecipeCapabilityHolder) machine; maxParallel > 0; maxParallel /= 2) {
                GTRecipe copied = recipe.copy(ContentModifier.multiplier((double) maxParallel), modifyDuration);
                if (copied.matchRecipe(holder).isSuccess() && copied.matchTickRecipe(holder).isSuccess()) {
                    return Pair.of(copied, maxParallel);
                }
            }
        }

        return Pair.of(recipe, 1);
    }
}
