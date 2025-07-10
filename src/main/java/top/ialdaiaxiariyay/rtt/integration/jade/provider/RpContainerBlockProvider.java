package top.ialdaiaxiariyay.rtt.integration.jade.provider;

import com.gregtechceu.gtceu.integration.jade.provider.CapabilityBlockProvider;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IElementHelper;
import top.ialdaiaxiariyay.rtt.RTT;
import top.ialdaiaxiariyay.rtt.api.machine.RTTCapabilityHelper;

public class RpContainerBlockProvider extends CapabilityBlockProvider<IRPContainer> {

    public RpContainerBlockProvider() {
        super(RTT.id("rp_container_provider"));
    }

    @Override
    protected @Nullable IRPContainer getCapability(Level level, BlockPos pos, @Nullable Direction side) {
        return RTTCapabilityHelper.getRPContainer(level, pos, side);
    }

    @Override
    protected void write(@NotNull CompoundTag compoundTag, @NotNull IRPContainer irpContainer) {
        compoundTag.putLong("RP", irpContainer.getRPStored());
        compoundTag.putLong("MaxRP", irpContainer.getRPCapacity());
    }

    @Override
    protected void addTooltip(@NotNull CompoundTag capData, ITooltip tooltip, Player player, BlockAccessor block, BlockEntity blockEntity, IPluginConfig config) {
        long maxStorage = capData.getLong("MaxRP");
        if (maxStorage != 0L) {
            long stored = capData.getLong("RP");
            IElementHelper helper = tooltip.getElementHelper();
            tooltip.add(helper.progress(this.getProgress(stored, maxStorage), Component.translatable("rtt.jade.rp_stored", FormattingUtil.formatNumbers(stored), FormattingUtil.formatNumbers(maxStorage)),
                    helper.progressStyle().color(-1120768, -1120768).textColor(-1), Util.make(BoxStyle.DEFAULT, (style) -> style.borderColor = -11184811), true));
        }
    }

    @Override
    protected boolean allowDisplaying(@NotNull IRPContainer capability) {
        return !capability.isOneProbeHidden();
    }
}
