package top.ialdaiaxiariyay.rtt.common.machines.machines;

import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.common.data.GTLBlocks;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.client.util.TooltipHelper;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import top.ialdaiaxiariyay.rtt.api.registries.RTTRegistration;
import top.ialdaiaxiariyay.rtt.common.data.GetRegistries;
import top.ialdaiaxiariyay.rtt.common.data.RTTCreativeModeTabs;
import top.ialdaiaxiariyay.rtt.common.machines.recipes.RTTRecipeTypes;

import java.util.List;
import java.util.function.BiConsumer;

import static com.gregtechceu.gtceu.api.pattern.Predicates.blocks;
import static com.gregtechceu.gtceu.api.pattern.util.RelativeDirection.*;
import static top.ialdaiaxiariyay.rtt.api.registries.RTTRegistration.*;

@SuppressWarnings("unused")
public class RTTMachines {

    public static final BiConsumer<ItemStack, List<Component>> RTT_ADD = (stack, components) -> components
            .add(Component.translatable("rtt.registry.add")
                    .withStyle(style -> style.withColor(TooltipHelper.RAINBOW_SLOW.getCurrent())));
    static {
        RTTRegistration.REGISTRATE.creativeModeTab(() -> RTTCreativeModeTabs.MACHINES_ITEM);
    }

    public static void init() {
    RTTSamllMachines.init();
    }

    public static final MachineDefinition FIGURE_FACTORY = REGISTRATE.multiblock("figure_factory", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .appearanceBlock(GTLBlocks.DIMENSIONALLY_TRANSCENDENT_CASING)
            .recipeType(RTTRecipeTypes.FIGURE_FACTORY)
            .recipeModifiers(GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK))
            .recipeModifier((machine, recipe, params, result) -> {
                GTRecipe recipe1 = recipe.copy();
                recipe1 = GTRecipeModifiers.fastParallel(machine, recipe1, 64, false).getFirst();
                return recipe1;
            })
            .tooltips(Component.translatable("block.rtt.figure_factory.tooltip.1"))
            .tooltips(Component.translatable("block.rtt.figure_factory.tooltip.2"))
            .tooltips(Component.translatable("block.rtt.figure_factory.tooltip.3"))
            .tooltips(Component.translatable("block.rtt.figure_factory.tooltip.4"))
            .tooltips(Component.translatable("block.rtt.figure_factory.tooltip.5"))
            .tooltipBuilder(RTT_ADD)
            .pattern(definition -> FactoryBlockPattern.start(FRONT, UP, LEFT)
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAAAAA", "AABBBBBBBBBBBBBBBBBAA", "AABBBBBBBBBBBBBBBBBAA", "AABBBBBBBBBBBBBBBBBAA", "AABBBBBBBBBBBBBBBBBAA", "AABBBBBBBBBBBBBBBBBAA", "AABBBBBBBBBBBBBBBBBAA", "AABBBBBBBBBBBBBBBBBAA", "AAAAAAAAAAAAAAAAAAAAA", "                     ", "                     ", "                     ", "                     ")
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "ACCCCCCCCCCCCCCCCCCCA", "ACBBBBBBBBBBBBBBBBBCA", "ACBBBBBBBBBBBBBBBBBCA", "ACBBBBBBBBBBBBBBBBBCA", "ACBBBBBBBBBBBBBBBBBCA", "ACBBBBBBBBBBBBBBBBBCA", "ACBBBBBBBBBBBBBBBBBCA", "ACBBBBBBBBBBBBBBBBBCA", "ACCCCCCCCCCCCCCCCCCCA", "ADDDDDDDDDDDDDDDDDDDA", "                     ", "                     ", "                     ")
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "B D   D   D   D   D B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "ADDDDDDDDDDDDDDDDDDDA", "                     ", "                     ", "                     ")
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "B D   D   D   D   D B", "B D   D   D   D   D B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "ADDDDDDDDDDDDDDDDDDDA", "ADDDDDDDDDDDDDDDDDDDA", "                     ", "                     ")
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "B                   B", "B D   D   D   D   D B", "B D   D   D   D   D B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "ADDDDDDDDDDDDDDDDDDDA", "                     ", "                     ")
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "B                   B", "B                   B", "B D   D   D   D   D B", "B D   D   D   D   D B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "ADDDDDDDDDDDDDDDDDDDA", "ADDDDDDDDDDDDDDDDDDDA", "                     ")
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "B G   G   G   G   G B", "B G   G   G   G   G B", "B G   G   G   G   G B", "B D F D F D F D F D B", "B D   D   D   D   D B", "B                   B", "B                   B", "B                   B", "B H   H   H   H   H B", "B H   H   H   H   H B", "B H   H   H   H   H B", "ADDDDDDDDDDDDDDDDDDDA", "                     ")
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "B                   B", "B                   B", "B                   B", "B                   B", "B I   I   I   I   I B", "B                   B", "B                   B", "B H   H   H   H   H B", "B H   H   H   H   H B", "B                   B", "B                   B", "ADDDDDDDDDDDDDDDDDDDA", "ADDDJDDDJDDDJDDDJDDDA")
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "B  KKFFKKFFKKFFKKFFKK", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "B H   H   H   H   H B", "B H   H   H   H   H B", "B                   B", "B                   B", "B                   B", "B                   B", "ADDJDJDJDJDJDJDJDJDDA")
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "LKKIIKKIIKKIIKKIIKKFB", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "B H   H   H   H   H B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "ADDDJDDDJDDDJDDDJDDDA")
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "LKKKKKKKKKKKKKKKKKKKB", "B K   K   K   K   K B", "B K   K   K   K   K B", "B K   K   K   K   K B", "B M   M   M   M   M B", "B                   B", "B J F J F J F J F J B", "B                   B", "B F   F   F   F   F B", "B F   F   F   F   F B", "B F   F   F   F   F B", "B F   F   F   F   F B", "AJJJJJJJJJJJJJJJJJJJ~")
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "LKKIIKKIIKKIIKKIIKKFB", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "B H   H   H   H   H B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "ADDDJDDDJDDDJDDDJDDDA")
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "B  KKFFKKFFKKFFKKFFKK", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "B H   H   H   H   H B", "B H   H   H   H   H B", "B                   B", "B                   B", "B                   B", "B                   B", "ADDJDJDJDJDJDJDJDJDDA")
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "B                   B", "B                   B", "B                   B", "B                   B", "B I   I   I   I   I B", "B                   B", "B                   B", "B H   H   H   H   H B", "B H   H   H   H   H B", "B                   B", "B                   B", "ADDDDDDDDDDDDDDDDDDDA", "ADDDJDDDJDDDJDDDJDDDA")
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "B G   G   G   G   G B", "B G   G   G   G   G B", "B G   G   G   G   G B", "B E   E   E   E   E B", "B E   E   E   E   E B", "B                   B", "B                   B", "B                   B", "B H   H   H   H   H B", "B H   H   H   H   H B", "B H   H   H   H   H B", "ADDDDDDDDDDDDDDDDDDDA", "                     ")
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "B                   B", "B                   B", "B E   E   E   E   E B", "B E F E F E F E F E B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "ADDDDDDDDDDDDDDDDDDDA", "ADDDDDDDDDDDDDDDDDDDA", "                     ")
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "B                   B", "B E   E   E   E   E B", "B E   E   E   E   E B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "ADDDDDDDDDDDDDDDDDDDA", "                     ", "                     ")
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "B E   E   E   E   E B", "B E   E   E   E   E B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "ADDDDDDDDDDDDDDDDDDDA", "ADDDDDDDDDDDDDDDDDDDA", "                     ", "                     ")
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "B E   E   E   E   E B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "B                   B", "ADDDDDDDDDDDDDDDDDDDA", "                     ", "                     ", "                     ")
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "ACCCCCCCCCCCCCCCCCCCA", "ACBBBBBBBBBBBBBBBBBCA", "ACBBBBBBBBBBBBBBBBBCA", "ACBBBBBBBBBBBBBBBBBCA", "ACBBBBBBBBBBBBBBBBBCA", "ACBBBBBBBBBBBBBBBBBCA", "ACBBBBBBBBBBBBBBBBBCA", "ACBBBBBBBBBBBBBBBBBCA", "ACCCCCCCCCCCCCCCCCCCA", "ADDDDDDDDDDDDDDDDDDDA", "                     ", "                     ", "                     ")
                    .aisle("AAAAAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAAAAA", "AABBBBBBBBBBBBBBBBBAA", "AABBBBBBBBBBBBBBBBBAA", "AABBBBBBBBBBBBBBBBBAA", "AABBBBBBBBBBBBBBBBBAA", "AABBBBBBBBBBBBBBBBBAA", "AABBBBBBBBBBBBBBBBBAA", "AABBBBBBBBBBBBBBBBBAA", "AAAAAAAAAAAAAAAAAAAAA", "                     ", "                     ", "                     ", "                     ")
                    .where("~", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("G", Predicates.blocks(GetRegistries.getBlock("gtceu:magnetohydrodynamicallyconstrainedstarmatter_frame")))
                    .where("J", Predicates.blocks(GetRegistries.getBlock("gtlcore:component_assembly_line_casing_max")))
                    .where("L", Predicates.blocks(GetRegistries.getBlock("gtlcore:dimensionally_transcendent_casing"))
                            .or(Predicates.abilities(PartAbility.INPUT_LASER).setMaxGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(1)))
                    .where("M", Predicates.blocks(GetRegistries.getBlock("kubejs:create_aggregatione_core")))
                    .where("E", Predicates.blocks(GetRegistries.getBlock("gtlcore:molecular_casing")))
                    .where("H", Predicates.blocks(GetRegistries.getBlock("kubejs:neutronium_pipe_casing")))
                    .where("F", Predicates.blocks(GetRegistries.getBlock("kubejs:dimension_creation_casing")))
                    .where("K", Predicates.blocks(GetRegistries.getBlock("gtlcore:create_casing")))
                    .where("B", Predicates.blocks(GetRegistries.getBlock("gtlcore:infinity_glass")))
                    .where("C", Predicates.blocks(GetRegistries.getBlock("gtlcore:manipulator")))
                    .where("A", Predicates.blocks(GetRegistries.getBlock("gtlcore:dimensionally_transcendent_casing")))
                    .where("D", Predicates.blocks(GetRegistries.getBlock("gtlcore:dimension_connection_casing")))
                    .where("I", Predicates.blocks(GetRegistries.getBlock("kubejs:create_hpca_component")))
                    .where(' ', Predicates.any())
                    .build())
            .workableCasingRenderer(GTLCore.id("block/casings/dimensionally_transcendent_casing"),
                    GTCEu.id("block/multiblock/fusion_reactor"))
            .register();

}
