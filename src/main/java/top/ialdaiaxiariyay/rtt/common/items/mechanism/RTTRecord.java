package top.ialdaiaxiariyay.rtt.common.items.mechanism;

import com.gregtechceu.gtceu.api.item.IComponentItem;
import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import com.tterrag.registrate.util.nullness.NonNullConsumer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RTTRecord {

    public static final Set<Item> REGISTERED_RECORDS = new HashSet<>();

    @Contract(pure = true)
    public static <T extends IComponentItem> @NotNull NonNullConsumer<T> RTTattach(IItemComponent components) {
        return (item) -> {
            // 附加组件
            item.attachComponents(components);
            // 将物品添加到注册集合
            if (item instanceof Item) { // 确保类型兼容
                REGISTERED_RECORDS.add((Item) item);
            }
        };
    }

    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull IInteractionItem createSoundInteraction(String soundName, int permissionLevel, int cooldownTicks, String name) {
        return new IInteractionItem() {

            @Override
            public InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand usedHand) {
                ItemStack stack = player.getItemInHand(usedHand);

                // 检查当前唱片冷却
                if (player.getCooldowns().isOnCooldown(item)) {
                    if (!level.isClientSide()) {
                        player.sendSystemMessage(Component.translatable("rtt.record.0"));
                    }
                    return InteractionResultHolder.fail(stack);
                }
                if (!level.isClientSide()) {
                    // 停止所有声音
                    SoundManager soundManager = Minecraft.getInstance().getSoundManager();
                    soundManager.stop();
                    // 清除其他唱片的冷却
                    REGISTERED_RECORDS.stream()
                            .filter(record -> record != item) // 排除当前唱片
                            .forEach(record -> player.getCooldowns().removeCooldown(record));

                    // 播放新声音
                    String command = String.format("playsound rtt:%s music @s ~ ~ ~", soundName);
                    Objects.requireNonNull(player.getServer()).getCommands().performPrefixedCommand(
                            player.createCommandSourceStack().withPermission(permissionLevel),
                            command);
                    player.sendSystemMessage(Component.translatable("rtt.record.1" + name));
                    // 设置当前唱片冷却
                    player.getCooldowns().addCooldown(item, cooldownTicks);
                }
                return InteractionResultHolder.success(stack);
            }
        };
    }

    // 常用配置的快捷方法
    public static IInteractionItem defaultSoundInteraction(String soundName, int cooldownTicks, String name) {
        return createSoundInteraction(soundName, 4, cooldownTicks, name); // 默认使用4级权限
    }
}
