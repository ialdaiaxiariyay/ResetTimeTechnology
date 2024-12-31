package top.ialdaiaxiariyay.rttgtcore.common.data.items;

import net.minecraft.world.item.Item;

import com.tterrag.registrate.util.entry.ItemEntry;
import top.ialdaiaxiariyay.rttgtcore.common.data.RTTGTCreativeModeTabs;

import static top.ialdaiaxiariyay.rttgtcore.api.registries.Registration.*;

public class RTTGTItem {

    static {
        REGISTRATE.creativeModeTab(() -> RTTGTCreativeModeTabs.RTTGT_ITEM);
    }

    public static final ItemEntry<Item> NEW_WORLD = REGISTRATE.item("new_world", Item::new)
            .lang("New World")
            .register();

    public static void init() {}
}
