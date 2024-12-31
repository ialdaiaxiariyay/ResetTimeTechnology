package top.ialdaiaxiariyay.rttgtcore.common.data.recipe;

import org.gtlcore.gtlcore.common.data.GTLMaterials;

import com.gregtechceu.gtceu.api.GTValues;

import net.minecraft.data.recipes.FinishedRecipe;

import top.ialdaiaxiariyay.rttgtcore.common.data.GetRegistries;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static top.ialdaiaxiariyay.rttgtcore.common.data.machines.recipes.RTTGTRecipeTypes.*;

public class MiscRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        AllialdgtRecipe(provider);
    }

    public static void AllialdgtRecipe(Consumer<FinishedRecipe> provider) {
        COMPRESSOR_RECIPES.recipeBuilder("large_shape_world_void_pump")
                .inputItems(GetRegistries.getItem("gtceu:hv_fluid_drilling_rig"), 64)
                .outputItems(GetRegistries.getItem("rttgtcore:large_shape_world_void_pump"))
                .duration(1000)
                .EUt(GTValues.VA[GTValues.HV])
                .save(provider);

        LARGE_SHAPE_WORLD_VOID_PUMP.recipeBuilder("world_void_pump_oil")
                .circuitMeta(1)
                .outputFluids(Oil.getFluid(1000000))
                .duration(1000)
                .EUt(GTValues.VA[GTValues.LuV])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("general_energy_furnace")
                .inputItems(GetRegistries.getItem("gtceu:electric_blast_furnace"))
                .inputItems(GetRegistries.getItem("gtceu:alloy_blast_smelter"))
                .inputItems(GetRegistries.getItem("gtceu:heat_vent"), 4)
                .inputItems(GetRegistries.getItem("gtceu:high_temperature_smelting_casing"), 4)
                .outputItems(GetRegistries.getItem("rttgtcore:general_energy_furnace"))
                .duration(150).EUt(GTValues.VA[GTValues.LuV])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("compressed_block_transmutation_chamber")
                .inputItems(GetRegistries.getItem("gtceu:block_conversion_room"))
                .inputItems(GetRegistries.getItem("gtlcore:aluminium_bronze_casing"), 138)
                .inputItems(GetRegistries.getItem("gtceu:tempered_glass"), 100)
                .inputItems(GetRegistries.getItem("kubejs:shining_obsidian"), 25)
                .outputItems(GetRegistries.getItem("rttgtcore:compressed_block_transmutation_chamber"))
                .duration(550).EUt(GTValues.VA[GTValues.IV])
                .save(provider);

        Compressed_Block_Transmutation_Chamber.recipeBuilder("essence_block_rttgtcore")
                .circuitMeta(1)
                .inputItems(GetRegistries.getItem("minecraft:bone_block"))
                .outputItems(GetRegistries.getItem("kubejs:essence_block"))
                .duration(1000).EUt(GTValues.VA[GTValues.LV])
                .save(provider);

        THERMOMAGNETIC_COOLING_GENERATOR.recipeBuilder("thermomagnetic_cooling_generator_1")
                .inputItems(GetRegistries.getItem("minecraft:redstone_block"), 32)
                .inputItems(GetRegistries.getItem("minecraft:iron_block"), 32)
                .duration(1000).EUt(-4096)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("thermomagnetic_cooling_generator")
                .inputItems(GetRegistries.getItem("minecraft:magma_block"), 64)
                .inputItems(GetRegistries.getItem("minecraft:blue_ice"), 64)
                .inputItems(GetRegistries.getItem("gtceu:lv_electric_motor"), 2)
                .inputItems(GetRegistries.getItem("gtceu:lv_machine_hull"), 2)
                .outputItems(GetRegistries.getItem("rttgtcore:thermomagnetic_cooling_generator"))
                .duration(140).EUt(GTValues.VA[GTValues.LV])
                .save(provider);

        FIGURE_FACTORY.recipeBuilder("Fishbig")
                .inputFluids(GTLMaterials.Eternity.getFluid(111))
                .inputFluids(GTLMaterials.TranscendentMetal.getFluid(19))
                .inputFluids(GTLMaterials.Shirabon.getFluid(9733))
                .inputItems(GetRegistries.getItem("gtlcore:component_assembly_line_casing_max"), 128)
                .inputItems(GetRegistries.getItem("gtceu:max_buffer"), 128)
                .inputItems(GetRegistries.getItem("gtlcore:max_sensor"), 128)
                .inputItems(GetRegistries.getItem("gtceu:create_aggregation"), 64)
                .inputItems(GetRegistries.getItem("gtceu:create_computation"), 64)
                .inputItems(GetRegistries.getItem("gtlcore:infinity_glass"), 64)
                .inputItems(GetRegistries.getItem("avaritia:singularity"), 64)
                .inputItems(GetRegistries.getItem("gtceu:shirabon_foil"), 169472)
                .inputItems(GetRegistries.getItem("gtceu:eternity_foil"), 169472)
                .inputItems(GetRegistries.getItem("gtceu:eternity_nanoswarm"), 169472)
                .inputItems(GetRegistries.getItem("kubejs:two_way_foil"), 169472)
                .inputItems(GetRegistries.getItem("gtceu:long_magmatter_rod"), 33792)
                .inputItems(GetRegistries.getItem("gtceu:cosmic_foil"), 169472)
                .inputItems(GetRegistries.getItem("gtceu:double_cosmic_plate"), 320)
                .inputItems(GetRegistries.getItem("gtceu:cosmic_plate"), 320)
                .inputItems(GetRegistries.getItem("gtceu:infinity_frame"), 33792)
                .inputItems(GetRegistries.getItem("gtceu:cosmicneutronium_foil"), 169472)
                .inputItems(GetRegistries.getItem("gtceu:long_transcendentmetal_rod"), 33792)
                .inputItems(GetRegistries.getItem("gtceu:long_cosmicneutronium_rod"), 33792)
                .inputItems(GetRegistries.getItem("gtceu:magnetohydrodynamicallyconstrainedstarmatter_foil"), 169472)
                .inputItems(GetRegistries.getItem("gtceu:magnetohydrodynamicallyconstrainedstarmatter_frame"), 169472)
                .outputItems(GetRegistries.getItem("expatternprovider:fishbig"))
                .duration(8192)
                .EUt(GTValues.VEX[GTValues.MAX] * 41943L)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("figure_factory")
                .inputItems(GetRegistries.getItem("rttgtcore:new_world"), 64)
                .inputItems(GetRegistries.getItem("kubejs:chaotic_energy_core"), 64)
                .inputItems(GetRegistries.getItem("gtceu:door_of_create"), 32)
                .inputItems(GetRegistries.getItem("gtceu:create_aggregation"), 32)
                .outputItems(GetRegistries.getItem("gtceu:create_computation"), 32)
                .duration(1024).EUt(GTValues.VEX[GTValues.MAX] * 2048)
                .save(provider);
    }
}
