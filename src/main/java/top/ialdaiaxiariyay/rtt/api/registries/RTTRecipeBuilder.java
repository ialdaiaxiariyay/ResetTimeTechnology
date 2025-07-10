package top.ialdaiaxiariyay.rtt.api.registries;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;

import com.gregtechceu.gtceu.utils.FormattingUtil;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;

import org.openjdk.nashorn.internal.objects.annotations.Getter;
import org.openjdk.nashorn.internal.objects.annotations.Setter;
import top.ialdaiaxiariyay.rtt.api.recipe.RPRecipeCapability;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RTTRecipeBuilder extends GTRecipeBuilder {

    public RTTRecipeBuilder(ResourceLocation id, GTRecipeType recipeType) {
        super(id, recipeType);
    }

    public RTTRecipeBuilder inputRP(long rp) {
        return (RTTRecipeBuilder) this.input(RPRecipeCapability.CAP, rp);
    }

    public RTTRecipeBuilder RPt(long rp) {
        boolean lastPerTick = this.perTick;
        this.perTick = true;
        if (rp > 0) {
            this.tickInput.remove(RPRecipeCapability.CAP);
            this.inputRP(rp);
        } else if (rp < 0) {
            this.tickOutput.remove(RPRecipeCapability.CAP);
            this.outputRP(-rp);
        }

        this.perTick = lastPerTick;
        return this;
    }

    public RTTRecipeBuilder outputRP(long rp) {
        return (RTTRecipeBuilder) this.output(RPRecipeCapability.CAP, rp);
    }
}
