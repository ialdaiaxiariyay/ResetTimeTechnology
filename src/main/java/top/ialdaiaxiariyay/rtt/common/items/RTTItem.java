package top.ialdaiaxiariyay.rtt.common.items;

import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.common.data.GTCompassSections;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.item.DataItemBehavior;
import com.gregtechceu.gtceu.common.item.TooltipBehavior;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import top.ialdaiaxiariyay.rtt.RTT;
import top.ialdaiaxiariyay.rtt.common.data.RTTCreativeModeTabs;
import top.ialdaiaxiariyay.rtt.common.items.mechanism.RTTRecord;
import top.ialdaiaxiariyay.rtt.common.items.mechanism.RhapsodyWeaponItem;
import top.ialdaiaxiariyay.rtt.common.items.mechanism.structurewrite.StructureWriteBehavior;

import static com.gregtechceu.gtceu.common.data.GTItems.*;
import static top.ialdaiaxiariyay.rtt.api.registries.RTTRegistration.*;

public class RTTItem {

    static {
        REGISTRATE.creativeModeTab(() -> RTTCreativeModeTabs.RTT_ITEM);
    }

    public static final ItemEntry<Item> NEW_WORLD = REGISTRATE.item("new_world", Item::new)
            .lang("New World")
            .register();

    public static final ItemEntry<Item> RECORD_SUBSTRATES = REGISTRATE
            .item("record_substrates", Item::new)
            .register();

    public static final ItemEntry<RhapsodyWeaponItem> WARP_SPINDLE = REGISTRATE
            .item("warp_spindle", RhapsodyWeaponItem::new)
            .onRegister(
                    attach(new TooltipBehavior(lines -> lines.add(Component.translatable("warp_spindle.tooltip.1")))))
            .onRegister(
                    attach(new TooltipBehavior(lines -> lines.add(Component.translatable("warp_spindle.tooltip.2")))))
            .onRegister(
                    attach(new TooltipBehavior(lines -> lines.add(Component.translatable("warp_spindle.tooltip.0")))))
            .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation(RTT.MOD_ID, "obj/key")))
            .properties(p -> p.stacksTo(1))
            .register();

    public static final ItemEntry<ComponentItem> STAR_TRIP = REGISTRATE.item("star_trip", ComponentItem::create)
            .onRegister(RTTRecord.RTTattach(RTTRecord.defaultSoundInteraction("star_trip", 5220, "スタートリップ")))
            .model(NonNullBiConsumer.noop())
            .register();

    public static final ItemEntry<ComponentItem> HOSHI_NO_UMI = REGISTRATE.item("hoshi_no_umi", ComponentItem::create)
            .onRegister(RTTRecord.RTTattach(RTTRecord.defaultSoundInteraction("hoshi_no_umi", 1820, "星の海")))
            .model(NonNullBiConsumer.noop())
            .register();

    public static final ItemEntry<ComponentItem> A_PLACE_IN_THE_SUNSHINE = REGISTRATE.item("a_place_in_the_sunshine", ComponentItem::create)
            .onRegister(RTTRecord.RTTattach(RTTRecord.defaultSoundInteraction("a_place_in_the_sunshine", 5800, "ひだまりの場所")))
            .model(NonNullBiConsumer.noop())
            .register();

    public static final ItemEntry<ComponentItem> YI_LIRILE = REGISTRATE.item("yi_lirile", ComponentItem::create)
            .onRegister(RTTRecord.RTTattach(RTTRecord.defaultSoundInteraction("yi_lirile", 17400, "儀 -lirile-")))
            .register();

    public static final ItemEntry<ComponentItem> HARM_STIMULI = REGISTRATE.item("harm_stimuli", ComponentItem::create)
            .onRegister(RTTRecord.RTTattach(RTTRecord.defaultSoundInteraction("harm_stimuli", 5380, "Harm Stimuli")))
            .register();

    public static final ItemEntry<ComponentItem> HARMONIOUS = REGISTRATE.item("harmonious", ComponentItem::create)
            .onRegister(RTTRecord.RTTattach(RTTRecord.defaultSoundInteraction("harmonious", 3760, "harmonious")))
            .register();

    public static final ItemEntry<ComponentItem> RESUSCITATED_HOPE = REGISTRATE.item("resuscitated_hope", ComponentItem::create)
            .onRegister(RTTRecord.RTTattach(RTTRecord.defaultSoundInteraction("resuscitated_hope", 5900, "⌈Resuscitated Hope⌋")))
            .register();

    public static final ItemEntry<ComponentItem> URAGIRI_ALICE = REGISTRATE.item("uragiri_alice", ComponentItem::create)
            .onRegister(RTTRecord.RTTattach(RTTRecord.defaultSoundInteraction("uragiri_alice", 3080, "裏切りアリス")))
            .register();

    public static final ItemEntry<ComponentItem> DEBUG_STRUCTURE_WRITER = REGISTRATE
            .item("debug_structure_writer", ComponentItem::create)
            .onRegister(GTItems.attach(StructureWriteBehavior.INSTANCE))
            .onRegister(
                    attach(new TooltipBehavior(lines -> lines.add(Component.translatable("rtt.debug_structure_writer.0")))))
            .onRegister(
                    attach(new TooltipBehavior(lines -> lines.add(Component.translatable("rtt.debug_structure_writer.1")))))
            .model(NonNullBiConsumer.noop())
            .register();

    public static final ItemEntry<ComponentItem> ADVANCED_DATA_MODULE = REGISTRATE
            .item("advanced_data_module", ComponentItem::create)
            .onRegister(attach(new DataItemBehavior(true)))
            .onRegister(compassNode(GTCompassSections.COMPONENTS))
            .register();

    public static void init() {}
}
