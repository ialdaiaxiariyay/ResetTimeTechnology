package top.ialdaiaxiariyay.rttgtcore.data;

import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import top.ialdaiaxiariyay.rttgtcore.RTTGTCore;
import top.ialdaiaxiariyay.rttgtcore.common.data.machines.Machines;
import top.ialdaiaxiariyay.rttgtcore.api.registries.Registries;

public class CreativeModeTabs {
    @SuppressWarnings("null")
    public static RegistryEntry<CreativeModeTab> ITEM = Registries.REGISTRATE.defaultCreativeTab(RTTGTCore.MOD_ID,
                    builder -> builder.displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator(RTTGTCore.MOD_ID, Registries.REGISTRATE))
                            .icon(Machines.LARGE_SHAPE_WORLD_VOID_PUMP::asStack)
                            .title(Component.literal("Iald GTCore"))
                            .build())
            .register();

    public static void init() {

    }
}