package top.ialdaiaxiariyay.rtt.mixin.gtl;

import net.minecraft.data.recipes.FinishedRecipe;

import org.gtlcore.gtlcore.GTLGTAddon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.ialdaiaxiariyay.rtt.common.data.recipe.AddRecips;

import java.util.function.Consumer;

@Mixin(GTLGTAddon.class)
public class GTLAddonMixin {

    @Inject(method = "addRecipes", at = @At(value = "HEAD"), remap = false)
    private void addRecipes(Consumer<FinishedRecipe> provider, CallbackInfo ci) {
        AddRecips.init(provider);
    }
}
