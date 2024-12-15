package top.ialdaiaxiariyay.rttgtcore.common.items;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import top.ialdaiaxiariyay.rttgtcore.data.CreativeModeTabs;
import static top.ialdaiaxiariyay.rttgtcore.api.registries.Registries.*;

public class item {
    static {
        REGISTRATE.creativeModeTab(() -> CreativeModeTabs.ITEM);
    }

    public static final ItemEntry<Item> NEW_WORLD = REGISTRATE.item("new_world", Item::new)
            .lang("New World")
            .register();

    public static void init() {}
}
