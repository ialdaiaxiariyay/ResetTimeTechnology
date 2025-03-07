package top.ialdaiaxiariyay.rttgtcore.common.items;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.gui.misc.ProspectorMode;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.component.ElectricStats;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.gregtechceu.gtceu.common.data.GTCompassSections;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.item.DataItemBehavior;
import com.gregtechceu.gtceu.common.item.ProspectorScannerBehavior;
import com.gregtechceu.gtceu.common.item.TooltipBehavior;
import com.gregtechceu.gtceu.config.ConfigHolder;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import top.ialdaiaxiariyay.rttgtcore.common.data.RTTGTCreativeModeTabs;
import top.ialdaiaxiariyay.rttgtcore.common.items.structurewrite.StructureWriteBehavior;

import static com.gregtechceu.gtceu.common.data.GTItems.*;
import static top.ialdaiaxiariyay.rttgtcore.api.registries.Registration.*;

public class RTTGTItem {

    static {
        REGISTRATE.creativeModeTab(() -> RTTGTCreativeModeTabs.RTTGT_ITEM);
    }

    public static final ItemEntry<Item> GLIMMER_LV_PROCESSOR_MAINFRAME = REGISTRATE.item("glimmer_lv_processor_mainframe", Item::new)
            .register();

    public static final ItemEntry<Item> GLIMMER_MV_PROCESSOR_MAINFRAME = REGISTRATE.item("glimmer_mv_processor_mainframe", Item::new)
            .register();

    public static final ItemEntry<Item> GLIMMER_HV_PROCESSOR_MAINFRAME = REGISTRATE.item("glimmer_hv_processor_mainframe", Item::new)
            .register();

    public static final ItemEntry<Item> GLIMMER_EV_PROCESSOR_MAINFRAME = REGISTRATE.item("glimmer_ev_processor_mainframe", Item::new)
            .register();

    public static final ItemEntry<Item> GLIMMER_IV_PROCESSOR_MAINFRAME = REGISTRATE.item("glimmer_iv_processor_mainframe", Item::new)
            .register();

    public static final ItemEntry<Item> GLIMMER_LuV_PROCESSOR_MAINFRAME = REGISTRATE.item("glimmer_luv_processor_mainframe", Item::new)
            .register();

    public static final ItemEntry<Item> GLIMMER_ZPM_PROCESSOR_MAINFRAME = REGISTRATE.item("glimmer_zpm_processor_mainframe", Item::new)
            .register();

    public static final ItemEntry<Item> GLIMMER_UV_PROCESSOR_MAINFRAME = REGISTRATE.item("glimmer_uv_processor_mainframe", Item::new)
            .register();

    public static final ItemEntry<Item> GLIMMER_UHV_PROCESSOR_MAINFRAME = REGISTRATE.item("glimmer_uhv_processor_mainframe", Item::new)
            .register();

    public static final ItemEntry<Item> GLIMMER_UEV_PROCESSOR_MAINFRAME = REGISTRATE.item("glimmer_uev_processor_mainframe", Item::new)
            .register();

    public static final ItemEntry<Item> GLIMMER_UIV_PROCESSOR_MAINFRAME = REGISTRATE.item("glimmer_uiv_processor_mainframe", Item::new)
            .register();

    public static final ItemEntry<Item> GLIMMER_UXV_PROCESSOR_MAINFRAME = REGISTRATE.item("glimmer_uxv_processor_mainframe", Item::new)
            .register();

    public static final ItemEntry<Item> GLIMMER_OpV_PROCESSOR_MAINFRAME = REGISTRATE.item("glimmer_opv_processor_mainframe", Item::new)
            .register();

    public static final ItemEntry<Item> GLIMMER_MAX_PROCESSOR_MAINFRAME = REGISTRATE.item("glimmer_max_processor_mainframe", Item::new)
            .register();

    public static final ItemEntry<Item> NEW_WORLD = REGISTRATE.item("new_world", Item::new)
            .lang("New World")
            .register();

    public static final ItemEntry<ComponentItem> DEBUG_STRUCTURE_WRITER = REGISTRATE
            .item("debug_structure_writer", ComponentItem::create)
            .onRegister(GTItems.attach(StructureWriteBehavior.INSTANCE))
            .onRegister(
                    attach(new TooltipBehavior(lines -> lines.add(Component.literal("按照时间创建")))))
            .onRegister(
                    attach(new TooltipBehavior(lines -> lines.add(Component.literal("文件导出在logs/rttgtcore")))))
            .model(NonNullBiConsumer.noop())
            .register();

    public static ItemEntry<ComponentItem> PROSPECTOR_ULV = REGISTRATE.item("prospector_ulv", ComponentItem::create)
            .lang("Super Prospector (ulv)")
            .properties(p -> p.stacksTo(1))
            .onRegister(attach(ElectricStats.createElectricItem(1_000_000_000_000L, GTValues.LuV),
                    new ProspectorScannerBehavior(8, GTValues.V[GTValues.LuV] / 32L, ProspectorMode.ORE,
                            ProspectorMode.FLUID,
                            ConfigHolder.INSTANCE.machines.doBedrockOres ? ProspectorMode.BEDROCK_ORE : null)))
            .register();

    public static final ItemEntry<ComponentItem> ADVANCED_DATA_MODULE = REGISTRATE
            .item("advanced_data_module", ComponentItem::create)
            .onRegister(attach((IItemComponent) (new DataItemBehavior(true))))
            .onRegister(compassNode(GTCompassSections.COMPONENTS))
            .register();

    public static void init() {}
}
