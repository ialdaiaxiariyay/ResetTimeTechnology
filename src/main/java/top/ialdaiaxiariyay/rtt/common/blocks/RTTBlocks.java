package top.ialdaiaxiariyay.rtt.common.blocks;

import top.ialdaiaxiariyay.rtt.RTT;
import top.ialdaiaxiariyay.rtt.api.registries.RTTRegistration;
import top.ialdaiaxiariyay.rtt.common.data.RTTCreativeModeTabs;

import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.common.data.GTModels;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import com.tterrag.registrate.util.entry.BlockEntry;

public class RTTBlocks {

    public static void init() {}

    static {
        RTTRegistration.REGISTRATE.creativeModeTab(() -> RTTCreativeModeTabs.BLOCK);
    }

    @SuppressWarnings({ "SameParameterValue", "removal" })
    private static BlockEntry<Block> createSidedCasingBlock(String name, ResourceLocation texture) {
        return RTTRegistration.REGISTRATE.block(name, Block::new)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(GTModels.createSidedCasingModel(name, texture))
                .tag(GTToolType.WRENCH.harvestTags.get(0), BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .build()
                .register();
    }

    public static final BlockEntry<Block> VOID_WORLD_BLOCK = createSidedCasingBlock("void_world_block",
            RTT.id("block/casings/void_world_block"));
}
