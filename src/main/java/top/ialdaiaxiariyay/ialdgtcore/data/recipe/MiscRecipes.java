package top.ialdaiaxiariyay.ialdgtcore.data.recipe;

import com.gregtechceu.gtceu.api.GTValues;
import net.minecraft.data.recipes.FinishedRecipe;
import top.ialdaiaxiariyay.ialdgtcore.common.data.Registries;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static top.ialdaiaxiariyay.ialdgtcore.common.data.SARecipes.*;

public class MiscRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        AllialdgtRecipe(provider);
    }
    public static void AllialdgtRecipe(Consumer<FinishedRecipe> provider) {
        COMPRESSOR_RECIPES.recipeBuilder("large_shape_world_void_pump")
                .inputItems(Registries.getItem("gtceu:hv_fluid_drilling_rig"),64)
                .outputItems(Registries.getItem("ialdgtcore:large_shape_world_void_pump"))
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
                .inputItems(Registries.getItem("gtceu:electric_blast_furnace"))
                .inputItems(Registries.getItem("gtceu:alloy_blast_smelter"))
                .inputItems(Registries.getItem("gtceu:heat_vent"),4)
                .inputItems(Registries.getItem("gtceu:high_temperature_smelting_casing"),4)
                .outputItems(Registries.getItem("ialdgtcore:general_energy_furnace"))
                .duration(150).EUt(GTValues.VA[GTValues.LuV])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("compressed_block_transmutation_chamber")
                .inputItems(Registries.getItem("gtceu:block_conversion_room"))
                .inputItems(Registries.getItem("gtlcore:aluminium_bronze_casing"), 138)
                .inputItems(Registries.getItem("gtceu:tempered_glass"),100)
                .inputItems(Registries.getItem("kubejs:shining_obsidian"),25)
                .outputItems(Registries.getItem("ialdgtcore:compressed_block_transmutation_chamber"))
                .duration(550).EUt(GTValues.VA[GTValues.IV])
                .save(provider);

        Compressed_Block_Transmutation_Chamber.recipeBuilder("essence_block_ialdgtcore")
                .circuitMeta(1)
                .inputItems(Registries.getItem("minecraft:bone_block"))
                .outputItems(Registries.getItem("kubejs:essence_block"))
                .duration(1000).EUt(GTValues.VA[GTValues.LV])
                .save(provider);

        THERMOMAGNETIC_COOLING_GENERATOR.recipeBuilder("thermomagnetic_cooling_generator_1")
                .inputItems(Registries.getItem("minecraft:redstone_block"),32)
                .inputItems(Registries.getItem("minecraft:iron_block"),32)
                .duration(1000).EUt(-4096)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("thermomagnetic_cooling_generator")
                .inputItems(Registries.getItem("minecraft:magma_block"),64)
                .inputItems(Registries.getItem("minecraft:blue_ice"), 64)
                .inputItems(Registries.getItem("gtceu:lv_electric_motor"),2)
                .inputItems(Registries.getItem("gtceu:lv_machine_hull"),2)
                .outputItems(Registries.getItem("ialdgtcore:thermomagnetic_cooling_generator"))
                .duration(140).EUt(GTValues.VA[GTValues.LV])
                .save(provider);

}
}
