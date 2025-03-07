package top.ialdaiaxiariyay.rttgtcore.common.data.ore;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.worldgen.*;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import lombok.Getter;
import top.ialdaiaxiariyay.rttgtcore.common.materials.RTTGTMaterials;

import java.util.*;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.worldgen.generator.veins.VeinedVeinGenerator.VeinBlockDefinition;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTOres.blankOreDefinition;

@SuppressWarnings("unused")
public class RTTGTOres {

    @Getter
    private static int largestVeinSize = 0;
    @Getter
    private static int largestIndicatorOffset = 0;
    private static final Map<ResourceLocation, GTOreDefinition> toReRegister = new HashMap<>();

    private static GTOreDefinition create(String name, Consumer<GTOreDefinition> config) {
        return create(GTCEu.id(name), config);
    }

    public static GTOreDefinition create(ResourceLocation name, Consumer<GTOreDefinition> config) {
        GTOreDefinition def = blankOreDefinition();
        config.accept(def);

        def.register(name);
        toReRegister.put(name, def);

        return def;
    }

    public static RuleTest[] OVERWORLD_RULES = new RuleTest[] { new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES) };
    public static final GTOreDefinition DEEPVIOLETGLEAM_ORE = create("deepvioletgleam_ore", vein -> vein
            .clusterSize(UniformInt.of(35, 45)).density(0.5f).weight(40)
            .layer(WorldGenLayers.STONE)
            .heightRangeUniform(-10, 160)
            .biomes(BiomeTags.IS_OVERWORLD)
            .veinedVeinGenerator(generator -> generator
                    .oreBlock(new VeinBlockDefinition(RTTGTMaterials.DeepvioletGleam, 10))
                    .rareBlockChance(0.5f)
                    .veininessThreshold(0.5f)
                    .maxRichnessThreshold(0.500f)
                    .minRichness(1.0f)
                    .maxRichness(1.0f)
                    .edgeRoundoffBegin(3)
                    .maxEdgeRoundoff(0.5f))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(RTTGTMaterials.DeepvioletGleam)));

    public static void init() {}
}
