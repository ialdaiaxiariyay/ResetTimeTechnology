package top.ialdaiaxiariyay.rtt.common.data;

import top.ialdaiaxiariyay.rtt.RTT;
import top.ialdaiaxiariyay.rtt.common.items.RTTItem;
import top.ialdaiaxiariyay.rtt.common.machines.machines.RTTFunctionalChamber;
import top.ialdaiaxiariyay.rtt.common.machines.machines.RTTMachines;

import net.minecraft.world.item.CreativeModeTab;

import com.gto.gtocore.common.data.GTOBlocks;
import com.tterrag.registrate.util.entry.RegistryEntry;

import static com.gregtechceu.gtceu.common.data.GTCreativeModeTabs.*;
import static top.ialdaiaxiariyay.rtt.api.registries.RTTRegistration.REGISTRATE;

public class RTTCreativeModeTabs {

    public RTTCreativeModeTabs() {}

    public static void init() {}

    public static RegistryEntry<CreativeModeTab> RTT_ITEM = REGISTRATE.defaultCreativeTab("item",
            builder -> builder.displayItems(new RegistrateDisplayItemsGenerator("item", REGISTRATE))
                    .icon(RTTItem.NEW_WORLD::asStack)
                    .title(REGISTRATE.addLang("itemGroup", RTT.id("item"), RTT.NAME + " Items"))
                    .build())
            .register();

    public static RegistryEntry<CreativeModeTab> MACHINES_ITEM = REGISTRATE.defaultCreativeTab("machines_item",
            builder -> builder.displayItems(new RegistrateDisplayItemsGenerator("machines_item", REGISTRATE))
                    .icon(RTTMachines.DIMENSIONALLY_TRANSCENDENT_PLASMA_FORGE_MK1::asStack)
                    .title(REGISTRATE.addLang("itemGroup", RTT.id("machines_item"), RTT.NAME + "Machines Items"))
                    .build())
            .register();

    public static RegistryEntry<CreativeModeTab> FUNCTIONAL_CHAMBER_ITEM = REGISTRATE.defaultCreativeTab("functional_chamber_item",
            builder -> builder.displayItems(new RegistrateDisplayItemsGenerator("functional_chamber_item", REGISTRATE))
                    .icon(RTTFunctionalChamber.WIRELESS_ENERGY_INTERFACE::asStack)
                    .title(REGISTRATE.addLang("itemGroup", RTT.id("functional_chamber_item"), RTT.NAME + "Functional Chamber Items"))
                    .build())
            .register();

    public static RegistryEntry<CreativeModeTab> BLOCK = REGISTRATE.defaultCreativeTab("block",
            builder -> builder.displayItems(new RegistrateDisplayItemsGenerator("block", REGISTRATE))
                    .icon(GTOBlocks.NEUTRONIUM_STABLE_CASING::asStack)
                    .title(REGISTRATE.addLang("itemGroup", RTT.id("block"), RTT.NAME + " Blocks"))
                    .build())
            .register();
}
