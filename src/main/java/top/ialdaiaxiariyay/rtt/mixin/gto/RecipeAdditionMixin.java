package top.ialdaiaxiariyay.rtt.mixin.gto;

import top.ialdaiaxiariyay.rtt.common.data.recipe.AddRecipes;

import com.gregtechceu.gtceu.data.recipe.configurable.RecipeAddition;

import net.minecraft.data.recipes.FinishedRecipe;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin({ RecipeAddition.class })
public class RecipeAdditionMixin {

    @Inject(
            method = { "init" },
            at = { @At("HEAD") },
            remap = false)
    private static void init(Consumer<FinishedRecipe> provider, CallbackInfo ci) {
        AddRecipes.init(provider);
    }
}
