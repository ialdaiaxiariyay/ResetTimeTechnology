package top.ialdaiaxiariyay.rtt.common.data.ore;

import top.ialdaiaxiariyay.rtt.common.materials.RTTMaterials;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.worldgen.GTOreDefinition;
import com.gregtechceu.gtceu.api.data.worldgen.WorldGenLayers;
import com.gregtechceu.gtceu.api.data.worldgen.generator.veins.VeinedVeinGenerator;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.common.data.GTMaterials.Chalcopyrite;
import static com.gregtechceu.gtceu.common.data.GTOres.blankOreDefinition;

public class RTTOres {

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
            .clusterSize(UniformInt.of(10, 25)).density(0.45f).weight(20)
            .layer(WorldGenLayers.STONE)
            .heightRangeUniform(-10, 160)
            .biomes(BiomeTags.IS_OVERWORLD)
            .veinedVeinGenerator(generator -> generator
                    .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(RTTMaterials.DeepvioletGleam, 5))
                    .rareBlockChance(0.05f)
                    .veininessThreshold(0.01f)
                    .maxRichnessThreshold(0.175f)
                    .minRichness(0.7f)
                    .maxRichness(1.0f)
                    .edgeRoundoffBegin(3)
                    .maxEdgeRoundoff(0.1f))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Chalcopyrite)));

    public static void init() {}
}
