//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package top.ialdaiaxiariyay.rtt.api.recipe;

import com.google.common.primitives.Ints;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeCapabilityHolder;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.feature.IOverclockMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.content.SerializerLong;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class RPRecipeCapability extends RecipeCapability<Long> {
    public static final RPRecipeCapability CAP = new RPRecipeCapability();

    protected RPRecipeCapability() {
        super("rp", -256, false, 2, SerializerLong.INSTANCE);
    }

    public Long copyInner(Long content) {
        return content;
    }

    public Long copyWithModifier(Long content, ContentModifier modifier) {
        return modifier.apply(content).longValue();
    }

    public List<Object> compressIngredients(Collection<Object> ingredients) {
        Stream<Object> var10000 = ingredients.stream();
        Objects.requireNonNull(Long.class);
        return List.of(var10000.map(Long.class::cast).reduce(0L, Long::sum));
    }

    public int limitParallel(GTRecipe recipe, IRecipeCapabilityHolder holder, int multiplier) {
        long maxVoltage = Long.MAX_VALUE;
        if (holder instanceof IOverclockMachine overclockMachine) {
            maxVoltage = overclockMachine.getOverclockVoltage();
        } else if (holder instanceof ITieredMachine tieredMachine) {
            maxVoltage = tieredMachine.getMaxVoltage();
        }

        long recipeRPt = RTTRecipeHelper.getInputRPt(recipe);
        return recipeRPt == 0L ? Integer.MAX_VALUE : Math.abs(Ints.saturatedCast(maxVoltage / recipeRPt));
    }

    public int getMaxParallelRatio(IRecipeCapabilityHolder holder, GTRecipe recipe, int parallelAmount) {
        long maxVoltage = Long.MAX_VALUE;
        if (holder instanceof IOverclockMachine overclockMachine) {
            maxVoltage = overclockMachine.getOverclockVoltage();
        } else if (holder instanceof ITieredMachine tieredMachine) {
            maxVoltage = tieredMachine.getMaxVoltage();
        }

        long recipeRPt = RTTRecipeHelper.getInputRPt(recipe);
        return recipeRPt == 0L ? Integer.MAX_VALUE : Math.abs(Ints.saturatedCast(maxVoltage / recipeRPt));
    }
}
