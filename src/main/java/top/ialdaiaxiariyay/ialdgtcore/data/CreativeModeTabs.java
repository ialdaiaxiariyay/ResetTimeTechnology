package top.ialdaiaxiariyay.ialdgtcore.data;

import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import top.ialdaiaxiariyay.ialdgtcore.common.items.item;
import top.ialdaiaxiariyay.ialdgtcore.ialdgtcore;
import top.ialdaiaxiariyay.ialdgtcore.common.data.SAMachines;
import top.ialdaiaxiariyay.ialdgtcore.api.registries.SARegistries;

public class CreativeModeTabs {
    @SuppressWarnings("null")
    public static RegistryEntry<CreativeModeTab> ITEM = SARegistries.REGISTRATE.defaultCreativeTab(ialdgtcore.MOD_ID,
                    builder -> builder.displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator(ialdgtcore.MOD_ID, SARegistries.REGISTRATE))
                            .icon(SAMachines.LARGE_SHAPE_WORLD_VOID_PUMP::asStack)
                            .title(Component.literal("Iald GTCore"))
                            .build())
            .register();

    public static void init() {

    }
}