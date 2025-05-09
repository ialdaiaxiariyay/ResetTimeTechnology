package top.ialdaiaxiariyay.rtt.integration.jade.provider;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.integration.jade.provider.CapabilityBlockProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import top.ialdaiaxiariyay.rtt.RTT;
import top.ialdaiaxiariyay.rtt.api.machine.IPerformanceDisplayMachine;

public class TickTimeProvider extends CapabilityBlockProvider<IPerformanceDisplayMachine> {

    public TickTimeProvider() {
        super(RTT.id("tick_time_provider"));
    }

    @Nullable
    @Override
    protected IPerformanceDisplayMachine getCapability(Level level, BlockPos pos, @Nullable Direction side) {
        if (MetaMachine.getMachine(level, pos) instanceof IPerformanceDisplayMachine machine) {
            machine.rtt$observe();
            return machine;
        }
        return null;
    }

    @Override
    protected void write(CompoundTag data, IPerformanceDisplayMachine capability) {
        if (capability != null) data.putInt("tick_time", capability.rtt$getTickTime());
    }

    @Override
    protected void addTooltip(CompoundTag capData, ITooltip tooltip, Player player, BlockAccessor block, BlockEntity blockEntity, IPluginConfig config) {
        long time = capData.getInt("tick_time");
        if (time == 0) return;
        tooltip.add(Component.translatable("tooltip.jade.delay", time).append(" μs"));
    }
}
