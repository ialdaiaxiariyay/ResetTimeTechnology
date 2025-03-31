package top.ialdaiaxiariyay.rtt.common.items;

import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.common.data.GTCompassSections;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.item.DataItemBehavior;
import com.gregtechceu.gtceu.common.item.TooltipBehavior;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import top.ialdaiaxiariyay.rtt.common.data.RTTCreativeModeTabs;
import top.ialdaiaxiariyay.rtt.common.items.record.RTTRecord;
import top.ialdaiaxiariyay.rtt.common.items.structurewrite.StructureWriteBehavior;

import static com.gregtechceu.gtceu.common.data.GTItems.*;
import static top.ialdaiaxiariyay.rtt.api.registries.RTTRegistration.*;

public class RTTItem {

    static {
        REGISTRATE.creativeModeTab(() -> RTTCreativeModeTabs.RTT_ITEM);
    }

    public static final ItemEntry<Item> NEW_WORLD = REGISTRATE.item("new_world", Item::new)
            .lang("New World")
            .register();

    public static final ItemEntry<ComponentItem> NEWa_WORLD = REGISTRATE.item("newa_world", ComponentItem::create)
            .lang("New World")
            .onRegister(GTItems.attach(RTTRecord.defaultSoundInteraction("star_trip", 6)))
            .register();

    public static final ItemEntry<ComponentItem> DEBUG_STRUCTURE_WRITER = REGISTRATE
            .item("debug_structure_writer", ComponentItem::create)
            .onRegister(GTItems.attach(StructureWriteBehavior.INSTANCE))
            .onRegister(
                    attach(new TooltipBehavior(lines -> lines.add(Component.literal("按照时间创建")))))
            .onRegister(
                    attach(new TooltipBehavior(lines -> lines.add(Component.literal("文件导出在logs/rtt")))))
            .model(NonNullBiConsumer.noop())
            .register();

    public static final ItemEntry<ComponentItem> ADVANCED_DATA_MODULE = REGISTRATE
            .item("advanced_data_module", ComponentItem::create)
            .onRegister(attach(new DataItemBehavior(true)))
            .onRegister(compassNode(GTCompassSections.COMPONENTS))
            .register();

    public static void init() {}
}
