package top.ialdaiaxiariyay.rtt.api.recipe;

import com.gregtechceu.gtceu.api.capability.recipe.IRecipeCapabilityHolder;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.content.SerializerLong;

import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.utils.LocalizationUtils;

import com.google.common.primitives.Ints;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class RPRecipeCapability extends RecipeCapability<Long> {

    public static final RPRecipeCapability CAP = new RPRecipeCapability();

    protected RPRecipeCapability() {
        super("rp", 0xCC99FF, false, 2, SerializerLong.INSTANCE);
    }

    @Override
    public Long copyInner(Long content) {
        return content;
    }

    @Override
    public Long copyWithModifier(Long content, ContentModifier modifier) {
        return modifier.apply(content).longValue();
    }

    @Override
    public List<Object> compressIngredients(Collection<Object> ingredients) {
        Stream<Object> list = ingredients.stream();
        Objects.requireNonNull(Long.class);
        return List.of(list.map(Long.class::cast).reduce(0L, Long::sum));
    }

    @Override
    public int limitParallel(GTRecipe recipe, IRecipeCapabilityHolder holder, int multiplier) {
        long recipeRPt = RTTRecipeHelper.getOutputRPt(recipe);
        long maxVoltage = Long.MAX_VALUE;
        return recipeRPt == 0L ? Integer.MAX_VALUE : Math.abs(Ints.saturatedCast(maxVoltage));
    }

    @Override
    public int getMaxParallelRatio(IRecipeCapabilityHolder holder, GTRecipe recipe, int parallelAmount) {
        long maxVoltage = Long.MAX_VALUE;
        long recipeRPt = RTTRecipeHelper.getInputRPt(recipe);
        return recipeRPt == 0L ? Integer.MAX_VALUE : Math.abs(Ints.saturatedCast(maxVoltage));
    }

    // 修复后的正确方法签名
    @Override
    public void addXEIInfo(WidgetGroup group, int xOffset, GTRecipe recipe, List<Content> contents, boolean perTick, boolean isInput, MutableInt yOffset) {
        // 正确的内容处理逻辑
        long rpValue = contents.stream()
                .map(Content::getContent)
                .mapToLong(CAP::of) // 使用mapToLong处理Long值
                .sum();

        // 构建基础显示文本（移除不必要的类型转换）
        String baseText = LocalizationUtils.format(
                isInput ? "rtt.machine.rp_input" : "rtt.machine.rp_output",
                rpValue) + (perTick ? " /t" : "");

        // 坐标计算调整（使用int参数）
        group.addWidget(new LabelWidget(
                3 - xOffset, // 直接使用int运算
                yOffset.addAndGet(10),
                baseText));

        if (perTick) {
            // 安全的长整型计算
            long total = rpValue * (long) recipe.duration; // 显式转换为long

            group.addWidget(new LabelWidget(
                    3 - xOffset,
                    yOffset.addAndGet(10),
                    LocalizationUtils.format("rtt.machine.rp_stored", total)));
        }
    }
}
