package top.ialdaiaxiariyay.rtt;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.addon.events.MaterialCasingCollectionEvent;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

import net.minecraft.data.recipes.FinishedRecipe;

import top.ialdaiaxiariyay.rtt.api.recipe.RTTRecipeCapabilities;
import top.ialdaiaxiariyay.rtt.api.registries.RTTRegistration;
import top.ialdaiaxiariyay.rtt.common.data.RTTBlocks;
import top.ialdaiaxiariyay.rtt.common.data.RTTItem;
import top.ialdaiaxiariyay.rtt.common.data.materials.RTTElements;

import java.util.function.Consumer;

@GTAddon
public class RTTAddon implements IGTAddon {

    @Override
    public GTRegistrate getRegistrate() {
        return RTTRegistration.REGISTRATE;
    }

    @Override
    public void initializeAddon() {
        RTTItem.init();
        RTTBlocks.init();
    }

    @Override
    public void registerElements() {
        RTTElements.init();
    }

    @Override
    public String addonModId() {
        return RTT.MOD_ID;
    }

    @Override
    public void collectMaterialCasings(MaterialCasingCollectionEvent event) {
        IGTAddon.super.collectMaterialCasings(event);
    }

    @Override
    public void registerSounds() {}

    @Override
    public void registerOreVeins() {}

    @Override
    public void registerRecipeCapabilities() {
        RTTRecipeCapabilities.init();
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {}
}
