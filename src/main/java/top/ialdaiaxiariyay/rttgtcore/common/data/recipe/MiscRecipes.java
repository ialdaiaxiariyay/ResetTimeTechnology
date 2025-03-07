package top.ialdaiaxiariyay.rttgtcore.common.data.recipe;

import org.gtlcore.gtlcore.common.data.*;
import org.gtlcore.gtlcore.common.data.machines.AdvancedMultiBlockMachine;
import org.gtlcore.gtlcore.utils.Registries;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.CustomTags;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.block.Blocks;

import committee.nova.mods.avaritia.init.registry.ModItems;
import top.ialdaiaxiariyay.rttgtcore.common.items.RTTGTItem;
import top.ialdaiaxiariyay.rttgtcore.common.machines.machines.RTTGTMachines;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.UXV;
import static com.gregtechceu.gtceu.api.GTValues.VA;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static top.ialdaiaxiariyay.rttgtcore.common.machines.recipes.RTTGTRecipeTypes.*;

public class MiscRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        COMPRESSOR_RECIPES.recipeBuilder("rtt_large_shape_world_void_pump")
                .inputItems(Registries.getItem("gtceu:hv_fluid_drilling_rig"), 64)
                .outputItems(RTTGTMachines.LARGE_SHAPE_WORLD_VOID_PUMP)
                .duration(2048)
                .EUt(GTValues.V[GTValues.EV])
                .save(provider);

        LARGE_SHAPE_WORLD_VOID_PUMP.recipeBuilder("rtt_oil")
                .circuitMeta(1)
                .outputFluids(GTMaterials.Oil.getFluid(100000))
                .duration(500)
                .EUt(GTValues.V[GTValues.EV])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("rtt_general_energy_furnace")
                .inputItems(Registries.getItem("gtceu:electric_blast_furnace"), 4)
                .inputItems(Registries.getItem("gtceu:alloy_blast_smelter"), 4)
                .inputItems(Registries.getItem("gtceu:ptfe_pipe_casing"), 64)
                .inputItems(Registries.getItem("gtceu:heat_vent"), 64)
                .inputItems(Registries.getItem("gtceu:high_temperature_smelting_casing"), 64)
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(16000))
                .outputItems(RTTGTMachines.GENERAL_ENERGY_FURNACE)
                .duration(4096)
                .EUt(GTValues.V[GTValues.IV])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("rtt_thermomagnetic_cooling_generator")
                .inputItems(Registries.getItem("gtceu:lv_machine_hull"), 4)
                .inputItems(Blocks.MAGMA_BLOCK.asItem(), 64)
                .inputItems(Blocks.REDSTONE_BLOCK.asItem(), 64)
                .inputItems(Blocks.BLUE_ICE.asItem(), 64)
                .inputFluids(GTMaterials.Tin.getFluid(8000))
                .outputItems(RTTGTMachines.THERMOMAGNETIC_COOLING_GENERATOR)
                .duration(2048)
                .EUt(GTValues.V[GTValues.LV])
                .save(provider);

        THERMOMAGNETIC_COOLING_GENERATOR.recipeBuilder("rtt_output_engrgy")
                .inputItems(Blocks.REDSTONE_BLOCK.asItem(), 32)
                .inputItems(Blocks.IRON_BLOCK.asItem(), 32)
                .EUt(-4096)
                .duration(8192)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("rtt_compressed_block_transmutation_chamber")
                .inputItems(AdvancedMultiBlockMachine.BLOCK_CONVERSION_ROOM)
                .inputItems(GTLBlocks.ALUMINIUM_BRONZE_CASING, 137)
                .inputItems(GTBlocks.CASING_TEMPERED_GLASS, 100)
                .inputItems(Registries.getItem("kubejs:shining_obsidian"), 25)
                .outputItems(RTTGTMachines.Compressed_Block_Transmutation_Chamber)
                .duration(4409)
                .EUt(GTValues.V[GTValues.IV])
                .save(provider);

        GTLRecipeTypes.SUPRACHRONAL_ASSEMBLY_LINE_RECIPES.recipeBuilder("rtt_dtpf_mk1")
                .inputItems(Registries.getItem("gtlcore:dimension_injection_casing"), 64)
                .inputItems(Registries.getItem("kubejs:spacetime_compression_field_generator"), 64)
                .inputItems(Registries.getItem("kubejs:dimensional_stability_casing"), 64)
                .inputItems(Registries.getItem("gtceu:dimensionally_transcendent_plasma_forge"), 4)
                .inputItems(Registries.getItem("gtceu:stellar_forge"), 8)
                .inputItems(Registries.getItem("gtceu:super_computation"), 64)
                .inputItems(Registries.getItem("kubejs:ctc_computational_unit"), 64)
                .inputItems(Registries.getItem("kubejs:stabilized_wormhole_generator"), 64)
                .inputItems(CustomTags.MAX_CIRCUITS, 32)
                .inputItems(Registries.getItem("gtceu:eternity_nanoswarm"), 8)
                .inputItems(Registries.getItem("gtlcore:max_emitter"), 64)
                .inputItems(Registries.getItem("gtlcore:max_sensor"), 64)
                .inputItems(Registries.getItem("gtlcore:max_robot_arm"), 64)
                .inputItems(Registries.getItem("kubejs:time_dilation_containment_unit"), 64)
                .inputItems(Registries.getItem("gtlcore:mega_max_battery"), 8)
                .inputItems(Registries.getItem("gtceu:double_chaos_plate"), 64)
                .inputFluids(GTLMaterials.SuperMutatedLivingSolder.getFluid(480000))
                .inputFluids(GTLMaterials.DegenerateRhenium.getFluid(100000))
                .inputFluids(GTMaterials.Neutronium.getFluid(100000))
                .inputFluids(GTLMaterials.Infinity.getFluid(16000))
                .outputItems(RTTGTMachines.DIMENSIONALLY_TRANSCENDENT_PLASMA_FORGE_MK1)
                .duration(4400)
                .EUt(GTValues.V[GTValues.MAX] * 16384)
                .stationResearch(b -> b.researchStack(AdvancedMultiBlockMachine.DIMENSIONALLY_TRANSCENDENT_PLASMA_FORGE.asStack())
                        .dataStack(RTTGTItem.ADVANCED_DATA_MODULE.asStack())
                        .EUt(VA[UXV])
                        .CWUt(8192))
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("rtt_advanced_data_module")
                .inputItems(Registries.getItem("gtceu:data_module"), 4)
                .inputItems(Registries.getItem("kubejs:supracausal_printed_circuit_board"), 2)
                .inputItems(Registries.getItem("kubejs:supracausal_processing_core"))
                .inputItems(Registries.getItem("gtceu:infinity_single_wire"), 8)
                .inputFluids(GTLMaterials.Infinity.getFluid(144))
                .outputItems(RTTGTItem.ADVANCED_DATA_MODULE)
                .duration(534)
                .EUt(GTValues.V[GTValues.MAX])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("rtt_figure_factory")
                .inputItems(Registries.getItem("gtceu:door_of_create"), 64)
                .inputItems(Registries.getItem("gtceu:create_aggregation"), 64)
                .inputItems(Registries.getItem("gtceu:create_computation"), 64)
                .inputItems(Registries.getItem("kubejs:suprachronal_mainframe_complex"), 64)
                .inputItems(Registries.getItem("kubejs:create_ultimate_battery"), 4)
                .outputItems(RTTGTMachines.FIGURE_FACTORY)
                .duration(8080)
                .EUt(GTValues.VEX[GTValues.MAX] * 443)
                .save(provider);

        FIGURE_FACTORY.recipeBuilder("rtt_fishbig")
                .inputItems(TagPrefix.foil, GTLMaterials.Shirabon, 169472)
                .inputItems(TagPrefix.foil, GTLMaterials.Cosmic, 169472)
                .inputItems(TagPrefix.foil, GTLMaterials.Eternity, 169472)
                .inputItems(TagPrefix.foil, GTLMaterials.CosmicNeutronium, 169472)
                .inputItems(TagPrefix.foil, GTLMaterials.MagnetohydrodynamicallyConstrainedStarMatter, 169472)
                .inputItems(Registries.getItem("kubejs:two_way_foil"), 169472)
                .inputItems(TagPrefix.plate, GTLMaterials.Cosmic, 33792)
                .inputItems(TagPrefix.plateDouble, GTLMaterials.Cosmic, 320)
                .inputItems(TagPrefix.rodLong, GTLMaterials.Cosmic, 33792)
                .inputItems(TagPrefix.rodLong, GTLMaterials.CosmicNeutronium, 33792)
                .inputItems(TagPrefix.rodLong, GTLMaterials.Magmatter, 33792)
                .inputItems(TagPrefix.rodLong, GTLMaterials.TranscendentMetal, 33792)
                .inputItems(TagPrefix.frameGt, GTLMaterials.MagnetohydrodynamicallyConstrainedStarMatter, 33792)
                .inputItems(TagPrefix.frameGt, GTLMaterials.Infinity, 33792)
                .inputItems(Registries.getItem("gtceu:eternity_nanoswarm"), 33792)
                .inputItems(GTLBlocks.calmap.get(14), 128)
                .inputItems(Registries.getItem("gtceu:max_buffer"), 128)
                .inputItems(GTLBlocks.INFINITY_GLASS, 64)
                .inputItems(AdvancedMultiBlockMachine.CREATE_COMPUTATION, 64)
                .inputItems(AdvancedMultiBlockMachine.CREATE_AGGREGATION, 64)
                .inputItems(GTLItems.SENSOR_MAX, 64)
                .inputItems(ModItems.singularity, 4)
                .inputFluids(GTLMaterials.Shirabon.getFluid(9732096))
                .inputFluids(GTLMaterials.Eternity.getFluid(110592))
                .inputFluids(GTLMaterials.TranscendentMetal.getFluid(18432))
                .outputItems(Registries.getItem("expatternprovider:fishbig"), 2)
                .duration(1000)
                .EUt(GTValues.VEX[GTValues.MAX] * 4194303)
                .save(provider);
    }
}
