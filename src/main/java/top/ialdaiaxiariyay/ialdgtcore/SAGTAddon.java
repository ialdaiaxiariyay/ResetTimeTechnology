package top.ialdaiaxiariyay.ialdgtcore;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.ialdaiaxiariyay.ialdgtcore.api.registries.SARegistries;
import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.addon.events.MaterialCasingCollectionEvent;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import top.ialdaiaxiariyay.ialdgtcore.common.blocks.block;
import top.ialdaiaxiariyay.ialdgtcore.common.data.SARecipes;
import top.ialdaiaxiariyay.ialdgtcore.common.items.item;
import top.ialdaiaxiariyay.ialdgtcore.data.recipe.AddRecipes;

import java.util.function.Consumer;

@GTAddon
public class SAGTAddon implements IGTAddon {

    private static Consumer<FinishedRecipe> provider;

    @Override
    public GTRegistrate getRegistrate() {
        return SARegistries.REGISTRATE;
    }

    @Override
    public void initializeAddon() {
    item.init();
    block.init();
    }

    @Override
    public String addonModId() {
        return ialdgtcore.MOD_ID;
    }

    @Override
    public void collectMaterialCasings(MaterialCasingCollectionEvent event) {
        IGTAddon.super.collectMaterialCasings(event);
    }

    @Override
    public void registerSounds() {
        SARecipes.init();
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        AddRecipes.init(provider);
    }

}