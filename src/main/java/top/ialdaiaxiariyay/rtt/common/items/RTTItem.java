package top.ialdaiaxiariyay.rtt.common.items;

import top.ialdaiaxiariyay.rtt.api.registries.RTTRegistration;
import top.ialdaiaxiariyay.rtt.common.data.RTTCreativeModeTabs;

import net.minecraft.world.item.Item;

import com.tterrag.registrate.util.entry.ItemEntry;

public class RTTItem {

    static {
        RTTRegistration.REGISTRATE.creativeModeTab(() -> RTTCreativeModeTabs.RTT_ITEM);
    }

    public static final ItemEntry<Item> NEW_WORLD = RTTRegistration.REGISTRATE
            .item("new_world", Item::new)
            .lang("New World")
            .register();

    public static void init() {}
}
