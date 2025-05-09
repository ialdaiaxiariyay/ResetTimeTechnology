package top.ialdaiaxiariyay.rtt.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.jetbrains.annotations.NotNull;
import top.ialdaiaxiariyay.rtt.RTT;

public class RTTCommands {

  public static void init(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(RTT.MOD_ID)
                .then(Commands.literal("stop_sound").executes(ctx -> {
                SoundManager soundManager = Minecraft.getInstance().getSoundManager();
                 soundManager.stop();
                return 1;}))
                );

  }
}
