package top.ialdaiaxiariyay.rtt.api.recipe;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import java.util.Objects;
import java.util.stream.Stream;

public class RTTRecipeHelper {
    public RTTRecipeHelper() {
    }

    public static long getInputRPt(GTRecipe recipe) {
        Stream<Object> var10000 = recipe.getTickInputContents(RPRecipeCapability.CAP).stream().map(Content::getContent);
        RPRecipeCapability var10001 = RPRecipeCapability.CAP;
        Objects.requireNonNull(var10001);
        return var10000.mapToLong(var10001::of).sum();
    }

    public static long getOutputRPt(GTRecipe recipe) {
        Stream<Object> var10000 = recipe.getTickOutputContents(RPRecipeCapability.CAP).stream().map(Content::getContent);
        RPRecipeCapability var10001 = RPRecipeCapability.CAP;
        Objects.requireNonNull(var10001);
        return var10000.mapToLong(var10001::of).sum();
    }
}
