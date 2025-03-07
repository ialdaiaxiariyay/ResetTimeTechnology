package top.ialdaiaxiariyay.rtt.common;

import top.ialdaiaxiariyay.rtt.RTT;
import top.ialdaiaxiariyay.rtt.api.registries.RTTRegistration;
import top.ialdaiaxiariyay.rtt.common.blocks.RTTBlocks;
import top.ialdaiaxiariyay.rtt.common.data.RTTCreativeModeTabs;
import top.ialdaiaxiariyay.rtt.common.items.RTTItem;
import top.ialdaiaxiariyay.rtt.common.materials.RTTMaterials;
import top.ialdaiaxiariyay.rtt.config.RTTConfigHolder;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.cover.CoverDefinition;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialRegistryEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.PostMaterialEvent;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CommonProxy {

    public CommonProxy() {
        CommonProxy.init();
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        RTTRegistration.REGISTRATE.registerEventListeners(eventBus);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::addMaterialRegistries);
        eventBus.addListener(this::modifyMaterials);
        eventBus.addListener(this::addMaterials);
        eventBus.addGenericListener(GTRecipeType.class, this::registerRecipeTypes);
        eventBus.addGenericListener(CoverDefinition.class, this::registerCovers);
    }

    public static void init() {
        RTTConfigHolder.init();
        RTTCreativeModeTabs.init();
    }

    private void clientSetup(final FMLClientSetupEvent event) {}

    private void addMaterialRegistries(MaterialRegistryEvent event) {
        GTCEuAPI.materialManager.createRegistry(RTT.MOD_ID);
    }

    private void modifyMaterials(PostMaterialEvent event) {}

    private void registerRecipeTypes(GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeType> event) {}

    private void addMaterials(MaterialEvent event) {
        RTTMaterials.init();
    }

    private void registerCovers(GTCEuAPI.RegisterEvent<ResourceLocation, CoverDefinition> event) {
        RTTBlocks.init();
        RTTItem.init();
    }
}
