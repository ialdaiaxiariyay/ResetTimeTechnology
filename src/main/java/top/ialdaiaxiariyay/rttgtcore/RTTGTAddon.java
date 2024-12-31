package top.ialdaiaxiariyay.rttgtcore;

import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.addon.events.MaterialCasingCollectionEvent;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

import net.minecraft.data.recipes.FinishedRecipe;

import top.ialdaiaxiariyay.rttgtcore.api.registries.Registration;
import top.ialdaiaxiariyay.rttgtcore.common.data.blocks.RTTGTBlocks;
import top.ialdaiaxiariyay.rttgtcore.common.data.items.RTTGTItem;
import top.ialdaiaxiariyay.rttgtcore.common.data.recipe.AddRecipes;

import java.util.function.Consumer;

@com.gregtechceu.gtceu.api.addon.GTAddon
public class RTTGTAddon implements IGTAddon {

    private static Consumer<FinishedRecipe> provider;

    @Override
    public GTRegistrate getRegistrate() {
        return Registration.REGISTRATE;
    }

    @Override
    public void initializeAddon() {}

    @Override
    public void registerElements() {
        RTTGTItem.init();
        RTTGTBlocks.init();
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
    public void registerSounds() {}

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        AddRecipes.init(provider);
    }
}
