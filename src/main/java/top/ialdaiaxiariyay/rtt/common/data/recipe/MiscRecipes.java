package top.ialdaiaxiariyay.rtt.common.data.recipe;

import net.minecraft.world.level.block.Blocks;
import org.gtlcore.gtlcore.common.data.*;
import org.gtlcore.gtlcore.common.data.machines.AdvancedMultiBlockMachine;
import org.gtlcore.gtlcore.utils.Registries;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;

import net.minecraft.data.recipes.FinishedRecipe;

import top.ialdaiaxiariyay.rtt.RTT;
import top.ialdaiaxiariyay.rtt.api.registries.RTTRecipeBuilder;
import top.ialdaiaxiariyay.rtt.common.items.RTTItem;
import top.ialdaiaxiariyay.rtt.common.machines.machines.RTTMachines;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static top.ialdaiaxiariyay.rtt.common.machines.recipes.RTTRecipeTypes.*;

public class MiscRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {

        ASSEMBLER_RECIPES.recipeBuilder(RTT.id("advanced_data_module"))
                .inputItems(Registries.getItem("gtceu:data_module"), 4)
                .inputItems(Registries.getItem("kubejs:supracausal_printed_circuit_board"), 2)
                .inputItems(Registries.getItem("kubejs:supracausal_processing_core"))
                .inputItems(Registries.getItem("gtceu:infinity_single_wire"), 8)
                .inputFluids(GTLMaterials.Infinity.getFluid(144))
                .outputItems(RTTItem.ADVANCED_DATA_MODULE)
                .duration(534)
                .EUt(GTValues.V[GTValues.MAX])
                .save(provider);

        new RTTRecipeBuilder(RTT.id("test"), ASSEMBLER_RECIPES)
                .inputRP(1)
                .inputItems(Blocks.IRON_BLOCK.asItem())
                .outputItems(Blocks.BLUE_ICE.asItem())
                .duration(2000)
                .save(provider);


        ASSEMBLER_RECIPES.recipeBuilder(RTT.id("figure_factory"))
                .inputItems(Registries.getItem("gtceu:door_of_create"), 64)
                .inputItems(Registries.getItem("gtceu:create_aggregation"), 64)
                .inputItems(Registries.getItem("gtceu:create_computation"), 64)
                .inputItems(Registries.getItem("kubejs:suprachronal_mainframe_complex"), 64)
                .inputItems(Registries.getItem("kubejs:create_ultimate_battery"), 4)
                .outputItems(RTTMachines.FIGURE_FACTORY)
                .duration(8080)
                .EUt(GTValues.VEX[GTValues.MAX] * 443)
                .save(provider);

        FIGURE_FACTORY.recipeBuilder(RTT.id("fishbig"))
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
                .inputItems(Registries.getItem("singularity"),4)
                .inputFluids(GTLMaterials.Shirabon.getFluid(9732096))
                .inputFluids(GTLMaterials.Eternity.getFluid(110592))
                .inputFluids(GTLMaterials.TranscendentMetal.getFluid(18432))
                .outputItems(Registries.getItem("expatternprovider:fishbig"), 2)
                .duration(1000)
                .EUt(GTValues.VEX[GTValues.MAX] * 4194303)
                .save(provider);
    }
}
