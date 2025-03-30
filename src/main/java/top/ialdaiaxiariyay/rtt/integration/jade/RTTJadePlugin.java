package top.ialdaiaxiariyay.rtt.integration.jade;

import com.gregtechceu.gtceu.common.data.GTItems;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import snownee.jade.addon.harvest.HarvestToolProvider;
import snownee.jade.addon.harvest.SimpleToolHandler;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import top.ialdaiaxiariyay.rtt.integration.jade.provider.RecipeLogicProvider;

import java.util.Objects;

@WailaPlugin
public class RTTJadePlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(new RecipeLogicProvider(), BlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(new RecipeLogicProvider(), Block.class);
    }

    static {
        GTItems.TOOL_ITEMS.columnMap().forEach((type, map) -> {
            if (!type.harvestTags.isEmpty() && !type.harvestTags.get(0).location().getNamespace().equals("minecraft")) {
                HarvestToolProvider.registerHandler(new SimpleToolHandler(type.name, type.harvestTags.get(0), map.values().stream().filter(Objects::nonNull).filter(RegistryEntry::isPresent).map(ItemProviderEntry::asItem).toArray(Item[]::new)));
            }
        });
    }
}
