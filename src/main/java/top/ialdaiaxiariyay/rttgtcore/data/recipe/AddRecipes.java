package top.ialdaiaxiariyay.rttgtcore.data.recipe;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;


public class AddRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        MiscRecipes.init(provider);
    }
}