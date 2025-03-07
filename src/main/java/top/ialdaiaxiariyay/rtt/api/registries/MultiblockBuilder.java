package top.ialdaiaxiariyay.rtt.api.registries;

import top.ialdaiaxiariyay.rtt.common.machines.mechanism.RTTRecipeModifiers;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.block.IMachineBlock;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.item.MetaMachineItem;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.api.registry.registrate.MultiblockMachineBuilder;
import com.gregtechceu.gtceu.client.util.TooltipHelper;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import com.gto.gtocore.api.recipe.JointRecipeType;
import com.gto.gtocore.common.data.GTORecipeModifiers;
import com.gto.gtocore.common.machine.multiblock.steam.LargeSteamParallelMultiblockMachine;
import com.gto.gtocore.data.lang.LangHandler;
import com.tterrag.registrate.Registrate;
import org.apache.commons.lang3.function.TriFunction;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class MultiblockBuilder extends MultiblockMachineBuilder {

    public static final Map<String, LangHandler.ENCN> TOOLTIPS_MAP = GTCEu.isDataGen() ? new HashMap<>() : null;
    private int tooltipsIndex;
    private final Set<String> recipes = new LinkedHashSet<>();

    public MultiblockBuilder(Registrate registrate, String name, Function<IMachineBlockEntity, ? extends MultiblockControllerMachine> metaMachine, BiFunction<BlockBehaviour.Properties, MultiblockMachineDefinition, IMachineBlock> blockFactory, BiFunction<IMachineBlock, Item.Properties, MetaMachineItem> itemFactory, TriFunction<BlockEntityType<?>, BlockPos, BlockState, IMachineBlockEntity> blockEntityFactory) {
        super(registrate, name, metaMachine, blockFactory, itemFactory, blockEntityFactory);
    }

    public MultiblockBuilder langValue(String langValue) {
        return (MultiblockBuilder) super.langValue(langValue);
    }

    public MultiblockBuilder tier(int tier) {
        return (MultiblockBuilder) super.tier(tier);
    }

    public MultiblockBuilder noRecipeModifier() {
        return (MultiblockBuilder) super.noRecipeModifier();
    }

    public MultiblockBuilder alwaysTryModifyRecipe(boolean alwaysTryModifyRecipe) {
        return (MultiblockBuilder) super.alwaysTryModifyRecipe(alwaysTryModifyRecipe);
    }

    public MultiblockBuilder recipeModifier(RecipeModifier recipeModifier) {
        this.alwaysTryModifyRecipe(true);
        return (MultiblockBuilder) super.recipeModifier(recipeModifier);
    }

    public MultiblockBuilder recipeModifiers(RecipeModifier... recipeModifiers) {
        this.alwaysTryModifyRecipe(true);
        return (MultiblockBuilder) super.recipeModifiers(recipeModifiers);
    }

    public MultiblockBuilder generator() {
        return (MultiblockBuilder) this.generator(true);
    }

    public MultiblockBuilder recipe(GTRecipeType recipeType) {
        Set var10000;
        String var10001;
        if (recipeType instanceof JointRecipeType jointRecipeType) {
            GTRecipeType[] var3 = jointRecipeType.getTypes();
            int var4 = var3.length;

            for (GTRecipeType type : var3) {
                var10000 = this.recipes;
                var10001 = type.registryName.getNamespace();
                var10000.add(var10001 + "." + type.registryName.getPath());
            }
        } else if (!Objects.equals(recipeType.group, "dummy")) {
            var10000 = this.recipes;
            var10001 = recipeType.registryName.getNamespace();
            var10000.add(var10001 + "." + recipeType.registryName.getPath());
        }

        return (MultiblockBuilder) this.recipeType(recipeType);
    }

    public MultiblockBuilder tooltipsKey(String key, Object... args) {
        return (MultiblockBuilder) this.tooltips(new Component[] { Component.translatable(key, args) });
    }

    public MultiblockBuilder tooltipsText(String en, String cn, Object... args) {
        String key = "gtocore.machine." + this.name + ".tooltip." + this.tooltipsIndex;
        if (TOOLTIPS_MAP != null) {
            TOOLTIPS_MAP.put(key, new LangHandler.ENCN(en, cn));
        }

        this.tooltipsKey(key, args);
        ++this.tooltipsIndex;
        return this;
    }

    public MultiblockBuilder block(Supplier<? extends Block> block) {
        if (!this.recipes.isEmpty()) {
            this.tooltipsKey("gtceu.machine.available_recipe_map_" + this.recipes.size() + ".tooltip", this.recipes.stream().map(Component::translatable).toArray());
        }

        this.recipes.clear();
        return (MultiblockBuilder) this.appearanceBlock(block);
    }

    public MultiblockBuilder nonYAxisRotation() {
        return (MultiblockBuilder) this.rotationState(RotationState.NON_Y_AXIS).allowExtendedFacing(false);
    }

    public MultiblockBuilder allRotation() {
        return (MultiblockBuilder) this.rotationState(RotationState.ALL);
    }

    public MultiblockBuilder noneRotation() {
        return (MultiblockBuilder) this.rotationState(RotationState.NONE).allowExtendedFacing(false).allowFlip(false);
    }

    public MultiblockBuilder customTooltipsBuilder(boolean perfectOC, boolean laser, boolean multipleRecipes) {
        return (MultiblockBuilder) this.tooltipBuilder((stack, components) -> {
            if (laser) {
                components.add(Component.translatable("gtocore.machine.laser.tooltip").withStyle((style) -> style.withColor(TooltipHelper.BLINKING_ORANGE.getCurrent())));
            }

            if (multipleRecipes) {
                components.add(Component.translatable("gtocore.machine.multiple_recipes.tooltip").withStyle((style) -> style.withColor(TooltipHelper.BLINKING_CYAN.getCurrent())));
            }

            if (perfectOC) {
                components.add(Component.translatable("gtceu.machine.perfect_oc").withStyle((style) -> style.withColor(TooltipHelper.BLINKING_RED.getCurrent())));
            }
        });
    }

    public MultiblockBuilder OPRecipeModifiers1() {
        return this.recipeModifier((machine, recipe) -> {
            GTRecipe recipe1 = recipe.copy();
            recipe1.duration = 0;
            recipe1 = RTTRecipeModifiers.fastParallel(machine, recipe1, 2147483647, false).getFirst();
            return (ModifierFunction) recipe1;
        }).tooltipsKey("rtt.op.recipe.modifiers.1");
    }

    public MultiblockBuilder OPRecipeModifiers2() {
        return this.recipeModifier((machine, recipe) -> {
            GTRecipe recipe1 = recipe.copy();
            recipe1.duration = 0;
            return (ModifierFunction) recipe1;
        }).tooltipsKey("rtt.op.recipe.modifiers.2");
    }

    public MultiblockBuilder eutMultiplierTooltips(double multiplier) {
        return this.tooltipsKey("gtocore.machine.eut_multiplier.tooltip", multiplier);
    }

    public MultiblockBuilder durationMultiplierTooltips(double multiplier) {
        return this.tooltipsKey("gtocore.machine.duration_multiplier.tooltip", multiplier);
    }

    public MultiblockBuilder coilParallelTooltips() {
        return this.tooltipsKey("gtocore.machine.coil_parallel");
    }

    public MultiblockBuilder parallelizableTooltips() {
        return this.tooltipsKey("gtceu.multiblock.parallelizable.tooltip");
    }

    public MultiblockBuilder overclock() {
        return (MultiblockBuilder) this.recipeModifier(GTORecipeModifiers.OVERCLOCKING, false);
    }

    public MultiblockBuilder perfectOverclock() {
        return (MultiblockBuilder) this.recipeModifier(GTORecipeModifiers.PERFECT_OVERCLOCKING, false);
    }

    public MultiblockBuilder parallelizableOverclock() {
        return this.recipeModifiers(GTORecipeModifiers.HATCH_PARALLEL, GTORecipeModifiers.OVERCLOCKING);
    }

    public MultiblockBuilder parallelizablePerfectOverclock() {
        return this.recipeModifiers(GTORecipeModifiers.HATCH_PARALLEL, GTORecipeModifiers.PERFECT_OVERCLOCKING);
    }

    public MultiblockBuilder steamOverclock() {
        this.tooltipsKey("gtocore.machine.steam.tooltip.0");
        this.tooltipsKey("gtocore.machine.steam.tooltip.1");
        this.tooltipsKey("gtocore.machine.steam.tooltip.2");
        return this.recipeModifier(LargeSteamParallelMultiblockMachine.recipeModifier(1.5));
    }
}
