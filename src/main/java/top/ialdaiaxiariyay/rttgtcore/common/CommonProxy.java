package top.ialdaiaxiariyay.rttgtcore.common;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialRegistryEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.PostMaterialEvent;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import top.ialdaiaxiariyay.rttgtcore.RTTGTCore;
import top.ialdaiaxiariyay.rttgtcore.common.data.RTTGTCreativeModeTabs;
import top.ialdaiaxiariyay.rttgtcore.common.machines.machines.RTTGTMachines;
import top.ialdaiaxiariyay.rttgtcore.common.machines.recipes.RTTGTRecipeTypes;
import top.ialdaiaxiariyay.rttgtcore.common.materials.RTTGTMaterials;
import top.ialdaiaxiariyay.rttgtcore.config.RTTGTConfigHolder;

import static top.ialdaiaxiariyay.rttgtcore.api.registries.Registration.REGISTRATE;

public class CommonProxy {

    public CommonProxy() {
        CommonProxy.init();
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        REGISTRATE.registerEventListeners(eventBus);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::addMaterialRegistries);
        eventBus.addListener(this::addMaterials);
        eventBus.addListener(this::modifyMaterials);
        eventBus.addGenericListener(GTRecipeType.class, this::registerRecipeTypes);
        eventBus.addGenericListener(MachineDefinition.class, this::registerMachines);
    }

    public static void init() {
        RTTGTCreativeModeTabs.init();
        RTTGTConfigHolder.init();
    }

    private void clientSetup(final FMLClientSetupEvent event) {}

    private void addMaterialRegistries(MaterialRegistryEvent event) {
        GTCEuAPI.materialManager.createRegistry(RTTGTCore.MOD_ID);
    }

    private void addMaterials(MaterialEvent event) {
        RTTGTMaterials.init();
    }

    private void modifyMaterials(PostMaterialEvent event) {}

    private void registerRecipeTypes(GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeType> event) {
        RTTGTRecipeTypes.init();
    }

    private void registerMachines(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> event) {
        RTTGTMachines.init();
    }
}
