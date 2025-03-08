package top.ialdaiaxiariyay.rtt.api.registries;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.FluidPipeProperties;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.gui.editor.EditableMachineUI;
import com.gregtechceu.gtceu.api.item.DrumMachineItem;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.SimpleGeneratorMachine;
import com.gregtechceu.gtceu.api.machine.SimpleTieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.steam.SimpleSteamMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.api.registry.registrate.CompassNode;
import com.gregtechceu.gtceu.api.registry.registrate.CompassSection;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gregtechceu.gtceu.api.registry.registrate.MultiblockMachineBuilder;
import com.gregtechceu.gtceu.client.renderer.machine.BatteryBufferRenderer;
import com.gregtechceu.gtceu.client.renderer.machine.ChargerRenderer;
import com.gregtechceu.gtceu.client.renderer.machine.SimpleGeneratorMachineRenderer;
import com.gregtechceu.gtceu.client.renderer.machine.TransformerRenderer;
import com.gregtechceu.gtceu.client.renderer.machine.WorkableSteamMachineRenderer;
import com.gregtechceu.gtceu.common.data.GTCompassNodes;
import com.gregtechceu.gtceu.common.data.GTCompassSections;
import com.gregtechceu.gtceu.common.data.GTMedicalConditions;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.machine.electric.BatteryBufferMachine;
import com.gregtechceu.gtceu.common.machine.electric.ChargerMachine;
import com.gregtechceu.gtceu.common.machine.electric.TransformerMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.part.FluidHatchPartMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.part.LaserHatchPartMachine;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import com.lowdragmc.lowdraglib.side.fluid.FluidStack;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.Int2LongFunction;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static com.gregtechceu.gtceu.common.data.GTMachines.*;
import static top.ialdaiaxiariyay.rtt.api.registries.RTTRegistration.REGISTRATE;

public class MachinesRegister {

    public MachinesRegister() {}

    public static BiConsumer<ItemStack, List<Component>> createCreativeTooltips(boolean share) {
        return (stack, list) -> {
            CREATIVE_TOOLTIPS.accept(stack, list);
            list.add(Component.translatable("gtceu.universal.%s".formatted(share ? "enabled" : "disabled")));
        };
    }

    public static BiConsumer<ItemStack, List<Component>> createTankTooltips(String nbtName, @Nullable Material material) {
        return (stack, list) -> {
            if (stack.hasTag()) {
                FluidStack tank = FluidStack.loadFromTag(stack.getOrCreateTagElement(nbtName));
                list.add(1, Component.translatable("gtceu.universal.tooltip.fluid_stored", new Object[] { tank.getDisplayName(), FormattingUtil.formatNumbers(tank.getAmount()) }));
            }

            Item item = stack.getItem();
            if (item instanceof DrumMachineItem drumItem) {
                if (material != null && material.hasProperty(PropertyKey.FLUID_PIPE)) {
                    FluidPipeProperties pipeprops = (FluidPipeProperties) material.getProperty(PropertyKey.FLUID_PIPE);
                    pipeprops.appendTooltips(list, true, true);
                }
            }

        };
    }

    public static Pair<MachineDefinition, MachineDefinition> registerSteamMachines(String name, BiFunction<IMachineBlockEntity, Boolean, MetaMachine> factory, BiFunction<Boolean, MachineBuilder<MachineDefinition>, MachineDefinition> builder) {
        MachineDefinition lowTier = (MachineDefinition) builder.apply(false, REGISTRATE.machine("lp_%s".formatted(name), (holder) -> {
            return (MetaMachine) factory.apply(holder, false);
        }).langValue("Low Pressure " + FormattingUtil.toEnglishName(name)).compassSections(new CompassSection[] { GTCompassSections.STEAM }).compassNode(name).compassPreNodes(new CompassNode[] { GTCompassNodes.STEAM }).tier(0));
        MachineDefinition highTier = (MachineDefinition) builder.apply(true, REGISTRATE.machine("hp_%s".formatted(name), (holder) -> {
            return (MetaMachine) factory.apply(holder, true);
        }).langValue("High Pressure " + FormattingUtil.toEnglishName(name)).compassSections(new CompassSection[] { GTCompassSections.STEAM }).compassNode(name).compassPreNodes(new CompassNode[] { GTCompassNodes.STEAM }).tier(1));
        return Pair.of(lowTier, highTier);
    }

    public static MachineDefinition[] registerTieredMachines(String name, BiFunction<IMachineBlockEntity, Integer, MetaMachine> factory, BiFunction<Integer, MachineBuilder<MachineDefinition>, MachineDefinition> builder, int... tiers) {
        MachineDefinition[] definitions = new MachineDefinition[GTValues.TIER_COUNT];
        int[] var5 = tiers;
        int var6 = tiers.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            int tier = var5[var7];
            MachineBuilder<MachineDefinition> register = REGISTRATE.machine(GTValues.VN[tier].toLowerCase(Locale.ROOT) + "_" + name, (holder) -> {
                return (MetaMachine) factory.apply(holder, tier);
            }).tier(tier);
            definitions[tier] = (MachineDefinition) builder.apply(tier, register);
        }

        return definitions;
    }

    private static MachineDefinition[] registerFluidHatches(String name, String displayname, String model, String tooltip, IO io, long initialCapacity, int slots, int[] tiers, PartAbility... abilities) {
        return registerTieredMachines(name, (holder, tier) -> {
            return new FluidHatchPartMachine(holder, tier, io, initialCapacity, slots, new Object[0]);
        }, (tier, builder) -> {
            String var10001 = GTValues.VNF[tier];
            builder.langValue(var10001 + " " + displayname).rotationState(RotationState.ALL).overlayTieredHullRenderer(model).abilities(abilities).compassNode("fluid_hatch").tooltips(new Component[] { Component.translatable("gtceu.machine." + tooltip + ".tooltip") });
            if (slots == 1) {
                builder.tooltips(new Component[] { Component.translatable("gtceu.universal.tooltip.fluid_storage_capacity", new Object[] { FormattingUtil.formatNumbers(FluidHatchPartMachine.getTankCapacity(initialCapacity, tier)) }) });
            } else {
                builder.tooltips(new Component[] { Component.translatable("gtceu.universal.tooltip.fluid_storage_capacity_mult", new Object[] { slots, FormattingUtil.formatNumbers(FluidHatchPartMachine.getTankCapacity(initialCapacity, tier)) }) });
            }

            return builder.register();
        }, tiers);
    }

    public static MachineDefinition[] registerTransformerMachines(String langName, int baseAmp) {
        return registerTieredMachines("transformer_%da".formatted(baseAmp), (holder, tier) -> {
            return new TransformerMachine(holder, tier, baseAmp, new Object[0]);
        }, (tier, builder) -> {
            return builder.rotationState(RotationState.ALL).itemColor((itemStack, index) -> {
                return index == 2 ? GTValues.VC[tier + 1] : (index == 3 ? GTValues.VC[tier] : (index == 1 ? Long.decode(ConfigHolder.INSTANCE.client.defaultPaintingColor).intValue() : -1));
            }).renderer(() -> {
                return new TransformerRenderer(tier, baseAmp);
            }).langValue("%s %sTransformer".formatted(GTValues.VOLTAGE_NAMES[tier], langName)).tooltips(new Component[] { Component.translatable("gtceu.machine.transformer.description"), Component.translatable("gtceu.machine.transformer.tooltip_tool_usage"), Component.translatable("gtceu.machine.transformer.tooltip_transform_down", new Object[] { baseAmp, FormattingUtil.formatNumbers(GTValues.V[tier + 1]), GTValues.VNF[tier + 1], baseAmp * 4, FormattingUtil.formatNumbers(GTValues.V[tier]), GTValues.VNF[tier] }), Component.translatable("gtceu.machine.transformer.tooltip_transform_up", new Object[] { baseAmp * 4, FormattingUtil.formatNumbers(GTValues.V[tier]), GTValues.VNF[tier], baseAmp, FormattingUtil.formatNumbers(GTValues.V[tier + 1]), GTValues.VNF[tier + 1] }) }).compassNode("transformer").register();
        }, GTValues.tiersBetween(0, GTCEuAPI.isHighTier() ? 13 : 8));
    }

    public static MachineDefinition[] registerSimpleMachines(String name, GTRecipeType recipeType, Int2LongFunction tankScalingFunction, boolean hasPollutionDebuff, int... tiers) {
        return registerTieredMachines(name, (holder, tier) -> {
            return new SimpleTieredMachine(holder, tier, tankScalingFunction, new Object[0]);
        }, (tier, builder) -> {
            if (hasPollutionDebuff) {
                builder.recipeModifiers(new RecipeModifier[] { (RecipeModifier) GTRecipeModifiers.ENVIRONMENT_REQUIREMENT.apply(GTMedicalConditions.CARBON_MONOXIDE_POISONING, 100 * tier), (RecipeModifier) GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK) }).tooltips(new Component[] { defaultEnvironmentRequirement() });
            } else {
                builder.recipeModifier((RecipeModifier) GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK));
            }

            return builder.langValue("%s %s %s".formatted(GTValues.VLVH[tier], FormattingUtil.toEnglishName(name), GTValues.VLVT[tier])).editableUI((EditableMachineUI) SimpleTieredMachine.EDITABLE_UI_CREATOR.apply(GTCEu.id(name), recipeType)).rotationState(RotationState.NON_Y_AXIS).recipeType(recipeType).workableTieredHullRenderer(GTCEu.id("block/machines/" + name)).tooltips(workableTiered(tier, GTValues.V[tier], GTValues.V[tier] * 64L, recipeType, (Long) tankScalingFunction.apply(tier), true)).compassNode(name).register();
        }, tiers);
    }

    public static MachineDefinition[] registerSimpleMachines(String name, GTRecipeType recipeType, Int2LongFunction tankScalingFunction, boolean hasPollutionDebuff) {
        return registerSimpleMachines(name, recipeType, tankScalingFunction, hasPollutionDebuff, ELECTRIC_TIERS);
    }

    public static MachineDefinition[] registerSimpleMachines(String name, GTRecipeType recipeType, Int2LongFunction tankScalingFunction) {
        return registerSimpleMachines(name, recipeType, tankScalingFunction, false);
    }

    public static MachineDefinition[] registerSimpleMachines(String name, GTRecipeType recipeType) {
        return registerSimpleMachines(name, recipeType, defaultTankSizeFunction);
    }

    public static MachineDefinition[] registerSimpleGenerator(String name, GTRecipeType recipeType, Int2LongFunction tankScalingFunction, float hazardStrengthPerOperation, int... tiers) {
        return registerTieredMachines(name, (holder, tier) -> {
            return new SimpleGeneratorMachine(holder, tier, hazardStrengthPerOperation * (float) tier, tankScalingFunction, new Object[0]);
        }, (tier, builder) -> {
            return builder.langValue("%s %s Generator %s".formatted(GTValues.VLVH[tier], FormattingUtil.toEnglishName(name), GTValues.VLVT[tier])).editableUI((EditableMachineUI) SimpleGeneratorMachine.EDITABLE_UI_CREATOR.apply(GTCEu.id(name), recipeType)).rotationState(RotationState.ALL).recipeType(recipeType).recipeModifier(SimpleGeneratorMachine::recipeModifier, true).addOutputLimit(ItemRecipeCapability.CAP, 0).addOutputLimit(FluidRecipeCapability.CAP, 0).renderer(() -> {
                return new SimpleGeneratorMachineRenderer(tier, GTCEu.id("block/generators/" + name));
            }).tooltips(workableTiered(tier, GTValues.V[tier], GTValues.V[tier] * 64L, recipeType, (Long) tankScalingFunction.apply(tier), false)).compassNode(name).register();
        }, tiers);
    }

    public static Pair<MachineDefinition, MachineDefinition> registerSimpleSteamMachines(String name, GTRecipeType recipeType) {
        return registerSteamMachines("steam_" + name, (x$0, x$1) -> {
            return new SimpleSteamMachine(x$0, x$1, new Object[0]);
        }, (pressure, builder) -> {
            return builder.rotationState(RotationState.ALL).recipeType(recipeType).recipeModifier(SimpleSteamMachine::recipeModifier).renderer(() -> {
                return new WorkableSteamMachineRenderer(pressure, GTCEu.id("block/machines/" + name));
            }).register();
        });
    }

    public static MachineDefinition[] registerBatteryBuffer(int batterySlotSize) {
        return registerTieredMachines("battery_buffer_" + batterySlotSize + "x", (holder, tier) -> {
            return new BatteryBufferMachine(holder, tier, batterySlotSize, new Object[0]);
        }, (tier, builder) -> {
            return builder.rotationState(RotationState.ALL).renderer(() -> {
                return new BatteryBufferRenderer(tier, batterySlotSize);
            }).langValue("%s %s%s".formatted(GTValues.VOLTAGE_NAMES[tier], batterySlotSize, "x Battery Buffer")).tooltips(new Component[] { Component.translatable("gtceu.universal.tooltip.item_storage_capacity", new Object[] { batterySlotSize }), Component.translatable("gtceu.universal.tooltip.voltage_in_out", new Object[] { FormattingUtil.formatNumbers(GTValues.V[tier]), GTValues.VNF[tier] }), Component.translatable("gtceu.universal.tooltip.amperage_in_till", new Object[] { (long) batterySlotSize * 2L }), Component.translatable("gtceu.universal.tooltip.amperage_out_till", new Object[] { batterySlotSize }) }).compassNode("battery_buffer").register();
        }, ALL_TIERS);
    }

    public static MachineDefinition[] registerCharger(int itemSlotSize) {
        return registerTieredMachines("charger_" + itemSlotSize + "x", (holder, tier) -> {
            return new ChargerMachine(holder, tier, itemSlotSize, new Object[0]);
        }, (tier, builder) -> {
            return builder.rotationState(RotationState.ALL).renderer(() -> {
                return new ChargerRenderer(tier);
            }).langValue("%s %s%s".formatted(GTValues.VOLTAGE_NAMES[tier], itemSlotSize, "x Turbo Charger")).tooltips(new Component[] { Component.translatable("gtceu.universal.tooltip.item_storage_capacity", new Object[] { itemSlotSize }), Component.translatable("gtceu.universal.tooltip.voltage_in_out", new Object[] { FormattingUtil.formatNumbers(GTValues.V[tier]), GTValues.VNF[tier] }), Component.translatable("gtceu.universal.tooltip.amperage_in_till", new Object[] { (long) itemSlotSize * 4L }) }).compassNode("charger").register();
        }, ALL_TIERS);
    }

    public static MachineDefinition[] registerLaserHatch(IO io, int amperage, PartAbility ability) {
        String name = io == IO.IN ? "target" : "source";
        return registerTieredMachines("" + amperage + "a_laser_" + name + "_hatch", (holder, tier) -> {
            return new LaserHatchPartMachine(holder, io, tier, amperage);
        }, (tier, builder) -> {
            String var10001 = GTValues.VNF[tier];
            return builder.langValue(var10001 + " " + FormattingUtil.formatNumbers(amperage) + "A Laser " + FormattingUtil.toEnglishName(name) + " Hatch").rotationState(RotationState.ALL).tooltips(new Component[] { Component.translatable("gtceu.machine.laser_hatch." + name + ".tooltip"), Component.translatable("gtceu.machine.laser_hatch.both.tooltip"), Component.translatable("gtceu.universal.disabled") }).abilities(new PartAbility[] { ability }).overlayTieredHullRenderer("laser_hatch." + name).register();
        }, HIGH_TIERS);
    }

    public static MultiblockMachineDefinition[] registerTieredMultis(String name, BiFunction<IMachineBlockEntity, Integer, MultiblockControllerMachine> factory, BiFunction<Integer, MultiblockMachineBuilder, MultiblockMachineDefinition> builder, int... tiers) {
        MultiblockMachineDefinition[] definitions = new MultiblockMachineDefinition[GTValues.TIER_COUNT];
        int[] var5 = tiers;
        int var6 = tiers.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            int tier = var5[var7];
            MultiblockMachineBuilder register = REGISTRATE.multiblock(GTValues.VN[tier].toLowerCase(Locale.ROOT) + "_" + name, (holder) -> {
                return (MultiblockControllerMachine) factory.apply(holder, tier);
            }).tier(tier);
            definitions[tier] = (MultiblockMachineDefinition) builder.apply(tier, register);
        }

        return definitions;
    }
}
