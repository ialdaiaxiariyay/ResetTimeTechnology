package top.ialdaiaxiariyay.rtt.api.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.Nullable;

public class RTTCapabilityHelper {

    public RTTCapabilityHelper() {}

    public static @Nullable IRPContainer getLRP(Level level, BlockPos pos, @Nullable Direction side) {
        return getBlockEntityCapability(RTTCapability.CAPABILITY_LRP, level, pos, side);
    }

    public static @Nullable IRPContainer getRPContainer(Level level, BlockPos pos, @Nullable Direction side) {
        return getBlockEntityCapability(RTTCapability.CAPABILITY_RP_CONTAINER, level, pos, side);
    }

    private static <T> @Nullable T getBlockEntityCapability(Capability<T> capability, Level level, BlockPos pos, @Nullable Direction side) {
        if (level.getBlockState(pos).hasBlockEntity()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity != null) {
                return blockEntity.getCapability(capability, side).resolve().orElse(null);
            }
        }

        return null;
    }
}
