package top.ialdaiaxiariyay.rtt.common.items.record;

import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class RTTRecord {
public static class StarTrip implements IInteractionItem{
    public static final IInteractionItem PLAY_SOUND_INTERACTION = new IInteractionItem() {
        @Override
        public InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand usedHand) {
            ItemStack itemStack = player.getItemInHand(usedHand);
            if (!level.isClientSide()) { // 确保只在服务端执行指令
                // 执行声音指令（需要服务端OP权限）
                Objects.requireNonNull(player.getServer()).getCommands().performPrefixedCommand(
                        player.createCommandSourceStack().withPermission(4), // 赋予4级权限
                        "playsound rtt:star_trip music @s ~ ~ ~" // 完整指令参数
                );
                player.sendSystemMessage(Component.literal("正在播放：star trip"));
            }
            // 返回 SUCCESS 并标记为客户端侧成功，避免消耗物品
            return InteractionResultHolder.success(itemStack);
        }
    };
}
}
