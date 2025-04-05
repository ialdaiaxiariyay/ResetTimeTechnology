package top.ialdaiaxiariyay.rtt.common.machines.mechanism;

import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import com.lowdragmc.lowdraglib.gui.texture.IGuiTexture;
import com.lowdragmc.lowdraglib.gui.util.ClickData;
import com.lowdragmc.lowdraglib.gui.widget.ComponentPanelWidget;
import com.lowdragmc.lowdraglib.gui.widget.DraggableScrollableWidgetGroup;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.HoverEvent.Action;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

import com.hepdd.gtmthings.utils.TeamUtil;
import com.mojang.datafixers.util.Pair;
import org.jetbrains.annotations.NotNull;
import top.ialdaiaxiariyay.rtt.api.rhythmsource.RhythmSourceManager;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RhythmSourceMonitor extends MetaMachine implements IFancyUIMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER;
    private static final BigInteger BIG_INTEGER_MAX_LONG;
    public static int p;
    public static BlockPos pPos;
    private UUID userid;
    private BigInteger beforeRhythmPoints;
    private ArrayList<BigInteger> longArrayList;
    private List<Map.Entry<Pair<UUID, MetaMachine>, Long>> sortedEntries = null;
    private boolean all = false;

    public RhythmSourceMonitor(IMachineBlockEntity holder) {
        super(holder);
    }

    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    public void onLoad() {
        super.onLoad();
    }

    public void onUnload() {
        super.onUnload();
    }

    private void handleDisplayClick(String componentData, ClickData clickData) {
        if (!clickData.isRemote) {
            if (componentData.equals("all")) {
                this.all = !this.all;
            } else {
                p = 100;
                String[] parts = componentData.split(", ");
                pPos = new BlockPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            }
        }
    }

    public Widget createUIWidget() {
        WidgetGroup group = new WidgetGroup(0, 0, 190, 125);
        group.addWidget((new DraggableScrollableWidgetGroup(4, 4, 182, 117))
                .setBackground(GuiTextures.DISPLAY)
                .addWidget(new LabelWidget(4, 5, this.self().getBlockState().getBlock().getDescriptionId()))
                .addWidget((new ComponentPanelWidget(4, 17, this::addDisplayText))
                        .setMaxWidthLimit(150)
                        .clickHandler(this::handleDisplayClick)));
        group.setBackground(new IGuiTexture[] { GuiTextures.BACKGROUND_INVERSE });
        return group;
    }

    public boolean shouldOpenUI(Player player, InteractionHand hand, BlockHitResult hit) {
        if (this.userid == null || !this.userid.equals(player.getUUID())) {
            this.userid = player.getUUID();
            this.longArrayList = new ArrayList<>();
        }
        this.beforeRhythmPoints = RhythmSourceManager.getTeamRP(this.userid);
        return true;
    }

    private void addDisplayText(@NotNull List<Component> textList) {
        BigInteger rhythmTotal = RhythmSourceManager.getTeamRP(this.userid);
        textList.add(Component.translatable("rtt.machine.rhythm_source_monitor.tooltip.team",
                TeamUtil.GetName(this.holder.level(), this.userid))
                .withStyle(ChatFormatting.AQUA));
        textList.add(Component.translatable("rtt.machine.rhythm_source_monitor.tooltip.total",
                FormattingUtil.formatNumbers(rhythmTotal))
                .withStyle(ChatFormatting.GRAY));

        BigDecimal avgUsage = this.getAvgUsage(rhythmTotal);
        if (avgUsage.compareTo(BigDecimal.ZERO) != 0) {
            textList.add(Component.translatable("rtt.machine.rhythm_source_monitor.tooltip.avg_usage",
                    FormattingUtil.formatNumbers(avgUsage.abs()))
                    .withStyle(avgUsage.signum() > 0 ? ChatFormatting.GREEN : ChatFormatting.RED));
        }

        textList.add(Component.translatable("rtt.machine.rhythm_source_monitor.tooltip.statistics")
                .append(ComponentPanelWidget.withButton(
                        this.all ? Component.translatable("rtt.machine.rhythm_source_monitor.tooltip.all") : Component.translatable("rtt.machine.rhythm_source_monitor.tooltip.team"),
                        "all")));

        for (Entry<Pair<UUID, MetaMachine>, Long> entry : this.getSortedEntries()) {
            Pair<UUID, MetaMachine> pair = entry.getKey();
            UUID uuid = pair.getFirst();
            if (!this.all && !TeamUtil.getTeamUUID(uuid).equals(TeamUtil.getTeamUUID(this.userid))) {
                continue;
            }

            MetaMachine machine = pair.getSecond();
            long rp = entry.getValue();
            String pos = machine.getPos().toShortString();

            MutableComponent machineName = Component.translatable(machine.getBlockState().getBlock().getDescriptionId())
                    .withStyle(Style.EMPTY.withHoverEvent(new HoverEvent(
                            Action.SHOW_TEXT,
                            Component.translatable("rtt.machine.rhythm_source_monitor.tooltip.position",
                                    pos,
                                    TeamUtil.GetName(this.holder.level(), uuid)))));

            String changeText = (rp > 0 ? "+" : "-") + FormattingUtil.formatNumbers(Math.abs(rp)) + " RP/s";
            textList.add(machineName.append(" ")
                    .append(Component.literal(changeText).withStyle(rp > 0 ? ChatFormatting.GREEN : ChatFormatting.RED))
                    .append(ComponentPanelWidget.withButton(Component.literal(" [ ] "), pos)));
        }
    }

    private List<Map.Entry<Pair<UUID, MetaMachine>, Long>> getSortedEntries() {
        if (this.sortedEntries == null || this.getOffsetTimer() % 20L == 0L) {
            this.sortedEntries = RhythmSourceManager.MachineData.entrySet().stream()
                    .sorted(Entry.comparingByValue())
                    .toList();
            RhythmSourceManager.MachineData.clear();
        }
        return this.sortedEntries;
    }

    private BigDecimal getAvgUsage(BigInteger now) {
        BigInteger changed = now.subtract(this.beforeRhythmPoints);
        this.beforeRhythmPoints = now;
        if (this.longArrayList.size() >= 20) {
            this.longArrayList.remove(0);
        }
        this.longArrayList.add(changed);
        return calculateAverage(this.longArrayList);
    }

    private static BigDecimal calculateAverage(ArrayList<BigInteger> values) {
        BigInteger sum = BigInteger.ZERO;
        for (BigInteger value : values) {
            sum = sum.add(value);
        }
        return new BigDecimal(sum).divide(new BigDecimal(values.size()), RoundingMode.HALF_UP);
    }

    static {
        MANAGED_FIELD_HOLDER = new ManagedFieldHolder(RhythmSourceMonitor.class, MetaMachine.MANAGED_FIELD_HOLDER);
        BIG_INTEGER_MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
    }
}
