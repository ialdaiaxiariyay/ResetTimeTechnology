package top.ialdaiaxiariyay.rtt.api.recipe;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;

public class RTTRecipeHelper {

    public RTTRecipeHelper() {}

    public static long getInputRP(GTRecipe recipe) {
        return recipe.getInputContents(RPRecipeCapability.CAP).stream()
                .mapToLong(content -> RPRecipeCapability.CAP.of(content.getContent()))
                .sum();
    }

    public static long getOutputRP(GTRecipe recipe) {
        return recipe.getOutputContents(RPRecipeCapability.CAP).stream()
                .mapToLong(content -> RPRecipeCapability.CAP.of(content.getContent()))
                .sum();
    }

    public static long getInputRPt(GTRecipe recipe) {
        return recipe.getTickInputContents(RPRecipeCapability.CAP).stream()
                .mapToLong(content -> RPRecipeCapability.CAP.of(content.getContent()))
                .sum();
    }

    public static long getOutputRPt(GTRecipe recipe) {
        return recipe.getTickOutputContents(RPRecipeCapability.CAP).stream()
                .mapToLong(content -> RPRecipeCapability.CAP.of(content.getContent()))
                .sum();
    }
}
