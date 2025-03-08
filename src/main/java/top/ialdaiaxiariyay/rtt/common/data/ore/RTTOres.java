package top.ialdaiaxiariyay.rtt.common.data.ore;

import com.gregtechceu.gtceu.api.data.worldgen.*;
import com.gregtechceu.gtceu.common.data.GTOres;

import net.minecraft.tags.BiomeTags;
import net.minecraft.util.valueproviders.UniformInt;

import top.ialdaiaxiariyay.rtt.RTT;
import top.ialdaiaxiariyay.rtt.common.materials.RTTMaterials;
import top.ialdaiaxiariyay.rtt.config.RTTConfigHolder;

@SuppressWarnings("unused")
public class RTTOres extends GTOres {

    public static void init() {}

    public static final GTOreDefinition DeepvioletGleam_VEIN = RTTConfigHolder.INSTANCE.EnableDeepvioletgleamAndCircuitRecipe ?
            create(RTT.id("deepvioletgleam_vein"), (vein) -> {
                vein
                        .clusterSize(UniformInt.of(25, 29))
                        .density(0.25F)
                        .weight(40)
                        .layer(WorldGenLayers.STONE)
                        .heightRangeUniform(0, 50)
                        .biomes(BiomeTags.IS_OVERWORLD)
                        .layeredVeinGenerator((generator) -> generator
                                .withLayerPattern(() -> GTLayerPattern.builder(OVERWORLD_RULES)
                                        .layer((l) -> l.weight(3).mat(RTTMaterials.DeepvioletGleam).size(2, 4))
                                        .build()))
                        .surfaceIndicatorGenerator((indicator) -> indicator
                                .surfaceRock(RTTMaterials.DeepvioletGleam));
            }) : null;
}
