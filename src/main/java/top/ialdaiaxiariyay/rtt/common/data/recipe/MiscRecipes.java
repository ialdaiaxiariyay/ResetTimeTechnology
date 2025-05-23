package top.ialdaiaxiariyay.rtt.common.data.recipe;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;

import net.minecraft.data.recipes.FinishedRecipe;

import org.gtlcore.gtlcore.common.data.*;
import org.gtlcore.gtlcore.common.data.machines.AdvancedMultiBlockMachine;
import org.gtlcore.gtlcore.utils.Registries;
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
                .inputItems(Registries.getItem("avaritia:singularity"), 4)
                .inputFluids(GTLMaterials.Shirabon.getFluid(9732096))
                .inputFluids(GTLMaterials.Eternity.getFluid(110592))
                .inputFluids(GTLMaterials.TranscendentMetal.getFluid(18432))
                .outputItems(Registries.getItem("expatternprovider:fishbig"), 2)
                .duration(1600)
                .EUt(GTValues.VEX[GTValues.MAX] * 16384)
                .save(provider);

        new RTTRecipeBuilder(RTT.id("star_trip"), MULTIVERSE_INFORMATION_COLLECTOR)
                .RPt(GTValues.VEX[GTValues.MAX] * 16384)
                .circuitMeta(1)
                .inputItems(RTTItem.RECORD_SUBSTRATES)
                .outputItems(RTTItem.STAR_TRIP)
                .duration(160)
                .save(provider);

        new RTTRecipeBuilder(RTT.id("hoshi_no_umi"), MULTIVERSE_INFORMATION_COLLECTOR)
                .RPt(GTValues.VEX[GTValues.MAX] * 16384)
                .circuitMeta(2)
                .inputItems(RTTItem.RECORD_SUBSTRATES)
                .outputItems(RTTItem.HOSHI_NO_UMI)
                .duration(160)
                .save(provider);

        new RTTRecipeBuilder(RTT.id("a_place_in_the_sunshine"), MULTIVERSE_INFORMATION_COLLECTOR)
                .RPt(GTValues.VEX[GTValues.MAX] * 16384)
                .circuitMeta(3)
                .inputItems(RTTItem.RECORD_SUBSTRATES)
                .outputItems(RTTItem.A_PLACE_IN_THE_SUNSHINE)
                .duration(160)
                .save(provider);

        new RTTRecipeBuilder(RTT.id("yi_lirile"), MULTIVERSE_INFORMATION_COLLECTOR)
                .RPt(GTValues.VEX[GTValues.MAX] * 16384)
                .circuitMeta(4)
                .inputItems(RTTItem.RECORD_SUBSTRATES)
                .outputItems(RTTItem.YI_LIRILE)
                .duration(160)
                .save(provider);

        new RTTRecipeBuilder(RTT.id("harm_stimuli"), MULTIVERSE_INFORMATION_COLLECTOR)
                .RPt(GTValues.VEX[GTValues.MAX] * 16384)
                .circuitMeta(5)
                .inputItems(RTTItem.RECORD_SUBSTRATES)
                .outputItems(RTTItem.HARM_STIMULI)
                .duration(160)
                .save(provider);

        new RTTRecipeBuilder(RTT.id("harmonious"), MULTIVERSE_INFORMATION_COLLECTOR)
                .RPt(GTValues.VEX[GTValues.MAX] * 16384)
                .circuitMeta(6)
                .inputItems(RTTItem.RECORD_SUBSTRATES)
                .outputItems(RTTItem.HARMONIOUS)
                .duration(160)
                .save(provider);

        new RTTRecipeBuilder(RTT.id("resuscitated_hope"), MULTIVERSE_INFORMATION_COLLECTOR)
                .RPt(GTValues.VEX[GTValues.MAX] * 16384)
                .circuitMeta(7)
                .inputItems(RTTItem.RECORD_SUBSTRATES)
                .outputItems(RTTItem.RESUSCITATED_HOPE)
                .duration(160)
                .save(provider);

        new RTTRecipeBuilder(RTT.id("uragiri_alice"), MULTIVERSE_INFORMATION_COLLECTOR)
                .RPt(GTValues.VEX[GTValues.MAX] * 16384)
                .circuitMeta(8)
                .inputItems(RTTItem.RECORD_SUBSTRATES)
                .outputItems(RTTItem.URAGIRI_ALICE)
                .duration(160)
                .save(provider);
    }
}
