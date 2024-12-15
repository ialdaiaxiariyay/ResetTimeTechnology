package top.ialdaiaxiariyay.rttgtcore;

import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import top.ialdaiaxiariyay.rttgtcore.common.data.recipes.RTTGTRecipes;
import top.ialdaiaxiariyay.rttgtcore.common.data.machines.Machines;
import top.ialdaiaxiariyay.rttgtcore.data.Datagen;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialRegistryEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.registry.MaterialRegistry;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.config.ConfigHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(RTTGTCore.MOD_ID)
public class RTTGTCore {
	public static final String
			MOD_ID = "rttgtcore",
			NAME = "RTT GTCore";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
	public static MaterialRegistry MATERIAL_REGISTRY;
	public RTTGTCore() {
		RTTGTCore.init();
		var bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.register(this);
		bus.addGenericListener(GTRecipeType.class, this::registerRecipeTypes);
		bus.addGenericListener(MachineDefinition.class, this::registerMachines);
	}
	public static void init() {
		ConfigHolder.init();
		Datagen.init();
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	@SubscribeEvent
	public void registerMaterialRegistry(MaterialRegistryEvent event) {
		MATERIAL_REGISTRY = GTCEuAPI.materialManager.createRegistry(RTTGTCore.MOD_ID);
	}
	@SubscribeEvent
	public void registerRecipeTypes(GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeType> event) {
		RTTGTRecipes.init();
	}
	@SubscribeEvent
	public void registerMachines(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> event) {
		Machines.init();
	}
}