package top.ialdaiaxiariyay.rtt.integration.jade.provider;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.integration.jade.provider.CapabilityBlockProvider;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.gregtechceu.gtceu.utils.GTUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import top.ialdaiaxiariyay.rtt.RTT;
import top.ialdaiaxiariyay.rtt.api.recipe.RTTRecipeHelper;

public class RecipeLogicProvider extends CapabilityBlockProvider<RecipeLogic> {

    public RecipeLogicProvider() {
        super(RTT.id("recipe_logic_provider"));
    }

    @Nullable
    @Override
    protected RecipeLogic getCapability(Level level, BlockPos pos, @Nullable Direction side) {
        return GTCapabilityHelper.getRecipeLogic(level, pos, side);
    }

    @Override
    protected void write(CompoundTag data, RecipeLogic capability) {
        data.putBoolean("Working", capability.isWorking());
        var recipeInfo = new CompoundTag();
        var recipe = capability.getLastRecipe();
        if (recipe != null) {
            var RPt = RTTRecipeHelper.getInputRPt(recipe);
            var isInput = true;
            if (RPt == 0) {
                isInput = false;
                RPt = RTTRecipeHelper.getOutputRPt(recipe);
            }

            recipeInfo.putLong("RPt", RPt);
            recipeInfo.putBoolean("isInput", isInput);
        }

        if (!recipeInfo.isEmpty()) {
            data.put("Recipe", recipeInfo);
        }
    }

    @Override
    protected void addTooltip(@NotNull CompoundTag capData, ITooltip tooltip, Player player, BlockAccessor block,
                              BlockEntity blockEntity, IPluginConfig config) {
        if (capData.getBoolean("Working")) {
            var recipeInfo = capData.getCompound("Recipe");
            if (!recipeInfo.isEmpty()) {
                var RPt = recipeInfo.getLong("RPt");
                var isInput = recipeInfo.getBoolean("isInput");
                long absRPt = Math.abs(RPt);
                var tier = GTUtil.getTierByVoltage(absRPt);
                Component text = Component.literal(FormattingUtil.formatNumbers(absRPt)).withStyle(ChatFormatting.LIGHT_PURPLE)
                        .append(Component.literal(" RP/t").withStyle(ChatFormatting.RESET)
                                .append(Component.literal(" (").withStyle(ChatFormatting.GREEN)
                                        .append(Component
                                                .translatable("gtceu.top.electricity",
                                                        FormattingUtil.formatNumber2Places(absRPt / ((float) GTValues.V[tier])),
                                                        GTValues.VNF[tier])
                                                .withStyle(style -> style.withColor(GTValues.VC[tier])))
                                        .append(Component.literal(")").withStyle(ChatFormatting.GREEN))));

                if (RPt > 0) {
                    if (isInput) {
                        tooltip.add(Component.translatable("gtceu.top.energy_consumption").append(" ").append(text));
                    } else {
                        tooltip.add(Component.translatable("gtceu.top.energy_production").append(" ").append(text));
                    }
                }
            }
        }
    }
}
