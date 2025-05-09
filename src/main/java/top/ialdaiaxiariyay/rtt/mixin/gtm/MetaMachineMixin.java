package top.ialdaiaxiariyay.rtt.mixin.gtm;

import com.gregtechceu.gtceu.api.block.BlockProperties;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.integration.ae2.machine.feature.IGridConnectedMachine;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.ialdaiaxiariyay.rtt.api.machine.IPerformanceDisplayMachine;
import top.ialdaiaxiariyay.rtt.common.machines.mechanism.PerformanceMonitorMachine;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Mixin(MetaMachine.class)
public abstract class MetaMachineMixin implements IPerformanceDisplayMachine {

    @Unique
    private long rtt$lastExecutionTime;

    @Unique
    private int rtt$averageTickTime;

    @Unique
    private long rtt$totaTtickCount;

    @Unique
    private boolean rtt$observe;

    @Shadow(remap = false)
    public abstract boolean isRemote();

    @Shadow(remap = false)
    @Final
    public IMachineBlockEntity holder;

    @Shadow(remap = false)
    protected abstract void executeTick();

    @Shadow(remap = false)
    public abstract @Nullable Level getLevel();

    @Shadow(remap = false)
    public abstract BlockPos getPos();

    @Shadow(remap = false)
    public abstract BlockState getBlockState();

    @Shadow(remap = false)
    public abstract long getOffsetTimer();

    @Shadow(remap = false)
    @Final
    private List<TickableSubscription> serverTicks;

    @Shadow(remap = false)
    @Final
    private List<TickableSubscription> waitingToAdd;

    @Shadow(remap = false)
    public abstract boolean isInValid();

    @Override
    public int rtt$getTickTime() {
        return rtt$averageTickTime;
    }

    @Override
    public void rtt$observe() {
        rtt$observe = true;
    }

    @Override
    @SuppressWarnings("all")
    public void runTick() {
        executeTick();
    }

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public final void serverTick() {
        if (cancelTick()) return;
        long currentTime = System.currentTimeMillis();
        if (currentTime - rtt$lastExecutionTime < 40) {
            return;
        }
        rtt$lastExecutionTime = currentTime;
        boolean observe = PerformanceMonitorMachine.observe || rtt$observe;
        if (observe) currentTime = System.nanoTime();
        runTick();
        if (observe) {
            rtt$totaTtickCount += System.nanoTime() - currentTime;
            if (getOffsetTimer() % 40 == 0) {
                rtt$observe = false;
                rtt$averageTickTime = (int) (rtt$totaTtickCount / 40000);
                rtt$totaTtickCount = 0;
            }
            if (PerformanceMonitorMachine.observe) PerformanceMonitorMachine.PERFORMANCE_MAP.put((MetaMachine) (Object) this, rtt$averageTickTime);
        } else if (!keepTick() && serverTicks.isEmpty() && waitingToAdd.isEmpty() && !isInValid()) {
            rtt$averageTickTime = 0;
            rtt$totaTtickCount = 0;
            Objects.requireNonNull(getLevel()).setBlockAndUpdate(getPos(), getBlockState().setValue(BlockProperties.SERVER_TICK, false));
        }
    }

    @Inject(method = "onToolClick", at = @At("RETURN"), remap = false, cancellable = true)
    private void onToolClick(Set<@NotNull GTToolType> toolType, ItemStack itemStack, UseOnContext context, CallbackInfoReturnable<Pair<GTToolType, InteractionResult>> cir) {
        if (cir.getReturnValue().getSecond() == InteractionResult.PASS && toolType.contains(GTToolType.WIRE_CUTTER)) {
            Player player = context.getPlayer();
            if (player == null) return;
            if (holder.getMetaMachine() instanceof IGridConnectedMachine gridConnectedMachine) {
                cir.setReturnValue(Pair.of(GTToolType.WIRE_CUTTER, rtt$onWireCutterClick(player, context.getHand(), gridConnectedMachine)));
            }
        }
    }

    @Inject(method = "shouldRenderGrid", at = @At("HEAD"), remap = false, cancellable = true)
    private void shouldRenderGrid(Player player, BlockPos pos, BlockState state, ItemStack held, Set<GTToolType> toolTypes, CallbackInfoReturnable<Boolean> cir) {
        if (toolTypes.contains(GTToolType.WIRE_CUTTER)) {
            MetaMachine metaMachine = holder.getMetaMachine();
            if (metaMachine instanceof IGridConnectedMachine) cir.setReturnValue(true);
        }
    }

    @Unique
    private InteractionResult rtt$onWireCutterClick(Player playerIn, InteractionHand hand, IGridConnectedMachine machine) {
        playerIn.swing(hand);
        if (holder.self().getPersistentData().getBoolean("isAllFacing")) {
            machine.getMainNode().setExposedOnSides(EnumSet.of(((MetaMachine) machine).getFrontFacing()));
            if (isRemote()) {
                playerIn.displayClientMessage(Component.translatable("rtt.me_front"), true);
            }
            holder.self().getPersistentData().putBoolean("isAllFacing", false);
        } else {
            machine.getMainNode().setExposedOnSides(EnumSet.allOf(Direction.class));
            if (isRemote()) {
                playerIn.displayClientMessage(Component.translatable("rtt.me_any"), true);
            }
            holder.self().getPersistentData().putBoolean("isAllFacing", true);
        }
        return InteractionResult.CONSUME;
    }
}
