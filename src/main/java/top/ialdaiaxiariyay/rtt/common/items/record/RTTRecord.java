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

    public static IInteractionItem createSoundInteraction(String soundName, int permissionLevel) {
        return new IInteractionItem() {

            @Override
            public InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand usedHand) {
                ItemStack itemStack = player.getItemInHand(usedHand);
                if (!level.isClientSide()) {
                    String command = String.format("playsound rtt:%s music @s ~ ~ ~", soundName);
                    Objects.requireNonNull(player.getServer()).getCommands().performPrefixedCommand(
                            player.createCommandSourceStack().withPermission(permissionLevel),
                            command);
                    player.sendSystemMessage(Component.literal("正在播放：" + soundName));
                }
                return InteractionResultHolder.success(itemStack);
            }
        };
    }

    // 常用配置的快捷方法
    public static IInteractionItem defaultSoundInteraction(String soundName) {
        return createSoundInteraction(soundName, 4); // 默认使用4级权限
    }
}
