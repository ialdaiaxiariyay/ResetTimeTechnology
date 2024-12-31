package top.ialdaiaxiariyay.rttgtcore.api.registries;

import com.gregtechceu.gtceu.api.recipe.GTRecipeSerializer;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;

import top.ialdaiaxiariyay.rttgtcore.RTTGTCore;

import java.util.Arrays;

public class RTTGTRecipeTypes {

    private GTRecipeBuilder recipeBuilder;

    public static GTRecipeType rttgtregister(String name, String group, RecipeType<?>... proxyRecipes) {
        GTRecipeType recipeType = new GTRecipeType(RTTGTCore.id(name), group, proxyRecipes);
        GTRegistries.register(BuiltInRegistries.RECIPE_TYPE, recipeType.registryName, recipeType);
        GTRegistries.register(BuiltInRegistries.RECIPE_SERIALIZER, recipeType.registryName, new GTRecipeSerializer());
        GTRegistries.RECIPE_TYPES.register(recipeType.registryName, recipeType);
        return recipeType;
    }

    public GTRecipeBuilder recipeBuilder(ResourceLocation id, Object... append) {
        if (append.length > 0) {
            GTRecipeBuilder var10000 = this.recipeBuilder;
            String var10003 = id.getNamespace();
            String var10004 = id.getPath();
            return var10000.copy(new ResourceLocation(var10003, var10004 + (String) Arrays.stream(append).map(Object::toString).map(FormattingUtil::toLowerCaseUnder).reduce("", (a, b) -> {
                return a + "_" + b;
            })));
        } else {
            return this.recipeBuilder.copy(id);
        }
    }

    public GTRecipeBuilder rttgtrecipeBuilder(String id, Object... append) {
        return this.recipeBuilder(RTTGTCore.id(id), append);
    }
}
