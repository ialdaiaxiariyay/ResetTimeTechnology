package top.ialdaiaxiariyay.rttgtcore.common.data;

import net.minecraft.world.item.CreativeModeTab;

import com.tterrag.registrate.util.entry.RegistryEntry;
import top.ialdaiaxiariyay.rttgtcore.RTTGTCore;
import top.ialdaiaxiariyay.rttgtcore.api.registries.Registration;
import top.ialdaiaxiariyay.rttgtcore.common.blocks.RTTGTBlocks;
import top.ialdaiaxiariyay.rttgtcore.common.items.RTTGTItem;
import top.ialdaiaxiariyay.rttgtcore.common.machines.machines.RTTGTMachines;

import static com.gregtechceu.gtceu.common.data.GTCreativeModeTabs.*;

public class RTTGTCreativeModeTabs {

    public static RegistryEntry<CreativeModeTab> MACHINES_ITEM;
    public static RegistryEntry<CreativeModeTab> BLOCKS_ITEM;
    public static RegistryEntry<CreativeModeTab> RTTGT_ITEM;

    public RTTGTCreativeModeTabs() {}

    public static void init() {}

    static {
        MACHINES_ITEM = Registration.REGISTRATE.defaultCreativeTab("machines_item", (builder) -> {
            builder.displayItems(new RegistrateDisplayItemsGenerator("machines_item", Registration.REGISTRATE))
                    .icon(RTTGTMachines.LARGE_SHAPE_WORLD_VOID_PUMP::asStack)
                    .title(Registration.REGISTRATE.addLang("itemGroup", RTTGTCore.id("machines_item"), "RTT GregTechL Machines Items"))
                    .build();
        }).register();

        BLOCKS_ITEM = Registration.REGISTRATE.defaultCreativeTab("blocks_item", (builder) -> {
            builder.displayItems(new RegistrateDisplayItemsGenerator("blocks_item", Registration.REGISTRATE))
                    .icon(RTTGTBlocks.VOID_WORLD_BLOCK::asStack)
                    .title(Registration.REGISTRATE.addLang("itemGroup", RTTGTCore.id("blocks_item"), "RTT GregTechLCe Blocks Items"))
                    .build();
        }).register();

        RTTGT_ITEM = Registration.REGISTRATE.defaultCreativeTab("gtlce_item", (builder) -> {
            builder.displayItems(new RegistrateDisplayItemsGenerator("gtlce_item", Registration.REGISTRATE))
                    .icon(RTTGTItem.NEW_WORLD::asStack)
                    .title(Registration.REGISTRATE.addLang("itemGroup", RTTGTCore.id("gtlce_item"), "RTT GregTechLCe Items"))
                    .build();
        }).register();
    }
}
