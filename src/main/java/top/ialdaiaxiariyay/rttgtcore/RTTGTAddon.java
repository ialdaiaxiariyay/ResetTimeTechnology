package top.ialdaiaxiariyay.rttgtcore;

import net.minecraft.data.recipes.FinishedRecipe;
import top.ialdaiaxiariyay.rttgtcore.api.registries.Registries;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.addon.events.MaterialCasingCollectionEvent;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import top.ialdaiaxiariyay.rttgtcore.common.blocks.block;
import top.ialdaiaxiariyay.rttgtcore.common.items.item;
import top.ialdaiaxiariyay.rttgtcore.data.recipe.AddRecipes;

import java.util.function.Consumer;

@com.gregtechceu.gtceu.api.addon.GTAddon
public class RTTGTAddon implements IGTAddon {

    private static Consumer<FinishedRecipe> provider;

    @Override
    public GTRegistrate getRegistrate() {
        return Registries.REGISTRATE;
    }

    @Override
    public void initializeAddon() {
    }

    @Override
    public void registerElements() {
        item.init();
        block.init();
    }

    @Override
    public String addonModId() {
        return RTTGTCore.MOD_ID;
    }

    @Override
    public void collectMaterialCasings(MaterialCasingCollectionEvent event) {
        IGTAddon.super.collectMaterialCasings(event);
    }

    @Override
    public void registerSounds() {
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        AddRecipes.init(provider);
    }

}