package top.ialdaiaxiariyay.rtt.common.data;

import net.minecraft.world.item.CreativeModeTab;

import com.tterrag.registrate.util.entry.RegistryEntry;
import top.ialdaiaxiariyay.rtt.RTT;
import top.ialdaiaxiariyay.rtt.api.registries.RTTRegistration;
import top.ialdaiaxiariyay.rtt.common.data.machines.RTTMachines;

import static com.gregtechceu.gtceu.common.data.GTCreativeModeTabs.*;

public class RTTCreativeModeTabs {

    public static RegistryEntry<CreativeModeTab> MACHINES_ITEM;
    public static RegistryEntry<CreativeModeTab> BLOCKS_ITEM;
    public static RegistryEntry<CreativeModeTab> RTT_ITEM;

    public RTTCreativeModeTabs() {}

    public static void init() {}

    static {
        MACHINES_ITEM = RTTRegistration.REGISTRATE.defaultCreativeTab("machines_item", (builder) -> {
            builder.displayItems(new RegistrateDisplayItemsGenerator("machines_item", RTTRegistration.REGISTRATE))
                    .icon(RTTMachines.FIGURE_FACTORY::asStack)
                    .title(RTTRegistration.REGISTRATE.addLang("itemGroup", RTT.id("machines_item"), "RTT Machines Items"))
                    .build();
        }).register();

        BLOCKS_ITEM = RTTRegistration.REGISTRATE.defaultCreativeTab("blocks_item", (builder) -> {
            builder.displayItems(new RegistrateDisplayItemsGenerator("blocks_item", RTTRegistration.REGISTRATE))
                    .icon(RTTBlocks.VOID_WORLD_BLOCK::asStack)
                    .title(RTTRegistration.REGISTRATE.addLang("itemGroup", RTT.id("blocks_item"), "RTT Blocks Items"))
                    .build();
        }).register();

        RTT_ITEM = RTTRegistration.REGISTRATE.defaultCreativeTab("rtt_item", (builder) -> {
            builder.displayItems(new RegistrateDisplayItemsGenerator("rtt_item", RTTRegistration.REGISTRATE))
                    .icon(RTTItem.NEW_WORLD::asStack)
                    .title(RTTRegistration.REGISTRATE.addLang("itemGroup", RTT.id("rtt_item"), "RTT Items"))
                    .build();
        }).register();
    }
}
