package top.ialdaiaxiariyay.rtt.data.recipe;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class AddRecips {

    public static void init(Consumer<FinishedRecipe> provider) {
        MiscRecipes.init(provider);
        VanillaRecipe.init(provider);
    }
}
