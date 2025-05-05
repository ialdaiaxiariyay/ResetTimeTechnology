package top.ialdaiaxiariyay.rtt.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.ialdaiaxiariyay.rtt.RTT;
import top.ialdaiaxiariyay.rtt.api.rhythmsource.RhythmSourceManager;

import java.math.BigInteger;
import java.util.Objects;
import java.util.UUID;

public class RTTCommands {

  public static void init(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(RTT.MOD_ID)
                .then(Commands.literal("stop_sound").executes(ctx -> {
                SoundManager soundManager = Minecraft.getInstance().getSoundManager();
                 soundManager.stop();
                return 1;}))
                .then(Commands.literal("get_rhythm_source")).executes((ctx -> {
                ServerPlayer player = ctx.getSource().getPlayer();
                Level level = ctx.getSource().getLevel();
                UUID uuid = Objects.requireNonNull(ctx.getSource().getPlayer()).getUUID();
                if (player != null) {
                getRp(player, level, uuid);
                }
                return 1;}))
                );

  }
    private static void getRp(Player player, Level level, UUID uuid) {
    if (level.isClientSide){
    player.sendSystemMessage(Component.literal("Rp:" + RhythmSourceManager.getTeamRP(uuid)));
    }
  }
}
