package top.ialdaiaxiariyay.rttgtcore;

import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.addon.events.MaterialCasingCollectionEvent;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

import net.minecraft.data.recipes.FinishedRecipe;

import top.ialdaiaxiariyay.rttgtcore.api.registries.Registration;
import top.ialdaiaxiariyay.rttgtcore.common.blocks.RTTGTBlocks;
import top.ialdaiaxiariyay.rttgtcore.common.data.ore.RTTGTOres;
import top.ialdaiaxiariyay.rttgtcore.common.data.recipe.CustomRecipe;
import top.ialdaiaxiariyay.rttgtcore.common.items.RTTGTItem;
import top.ialdaiaxiariyay.rttgtcore.common.materials.RTTGTElements;

import java.util.function.Consumer;

@com.gregtechceu.gtceu.api.addon.GTAddon
public class RTTGTAddon implements IGTAddon {

    private static Consumer<FinishedRecipe> provider;

    @Override
    public GTRegistrate getRegistrate() {
        return Registration.REGISTRATE;
    }

    @Override
    public void initializeAddon() {
        RTTGTItem.init();
        RTTGTBlocks.init();
    }

    @Override
    public void registerElements() {
        RTTGTElements.init();
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
    public void registerOreVeins() {
        RTTGTOres.init();
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        CustomRecipe.init(provider);
    }
}
