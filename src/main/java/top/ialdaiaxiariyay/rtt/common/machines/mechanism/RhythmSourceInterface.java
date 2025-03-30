package top.ialdaiaxiariyay.rtt.common.machines.mechanism;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.feature.IInteractedMachine;
import com.gregtechceu.gtceu.api.machine.feature.IMachineLife;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableEnergyContainer;
import com.gregtechceu.gtceu.common.data.GTItems;

import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import com.hepdd.gtmthings.api.capability.IBindable;
import com.hepdd.gtmthings.utils.TeamUtil;
import org.jetbrains.annotations.Nullable;
import top.ialdaiaxiariyay.rtt.api.rhythmsource.RhythmSourceManager;

import java.util.Objects;
import java.util.UUID;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RhythmSourceInterface extends TieredIOPartMachine implements IBindable, IInteractedMachine, IMachineLife {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER;
    private TickableSubscription updEnergySubs;
    @Persisted
    public UUID owner_uuid;
    @Persisted
    public final NotifiableEnergyContainer energyContainer = this.createEnergyContainer();

    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    public RhythmSourceInterface(IMachineBlockEntity holder, Object... args) {
        super(holder, 14, IO.IN);
    }

    protected NotifiableEnergyContainer createEnergyContainer() {
        NotifiableEnergyContainer container = NotifiableEnergyContainer.receiverContainer(this, Long.MAX_VALUE, GTValues.V[this.tier], 67108864L);
        container.setSideInputCondition((s) -> s == this.getFrontFacing() && this.isWorkingEnabled());
        container.setCapabilityValidator((s) -> s == null || s == this.getFrontFacing());
        return container;
    }

    public void onLoad() {
        super.onLoad();
        this.updateEnergySubscription();
    }

    public void onUnload() {
        super.onUnload();
        if (this.updEnergySubs != null) {
            this.updEnergySubs.unsubscribe();
            this.updEnergySubs = null;
        }
    }

    private void updateEnergySubscription() {
        if (this.owner_uuid != null) {
            this.updEnergySubs = this.subscribeServerTick(this.updEnergySubs, this::updateEnergy);
        } else if (this.updEnergySubs != null) {
            this.updEnergySubs.unsubscribe();
            this.updEnergySubs = null;
        }
    }

    private void updateEnergy() {
        long currentStored = this.energyContainer.getEnergyStored();
        if (currentStored > 0L) {
            // 计算韵律源点：EU 按 8:1 转换，不足 8 EU 按 1 RP 处理
            long rp = currentStored >= 8 ? currentStored / 8 : 1;
            // 计算实际需要扣除的 EU（确保不超过当前存储量）
            long euToDeduct = Math.min(rp * 8, currentStored);

            // 将 RP 添加到全局存储
            if (RhythmSourceManager.addRPToGlobalSourceMap(this.owner_uuid, rp, this)) {
                // 扣除对应的 EU
                this.energyContainer.setEnergyStored(currentStored - euToDeduct);
            }
        }
    }

    public boolean shouldOpenUI(Player player, InteractionHand hand, BlockHitResult hit) {
        return false;
    }

    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack is = player.getItemInHand(hand);
        if (is.isEmpty()) {
            return InteractionResult.PASS;
        } else if (is.is(GTItems.TOOL_DATA_STICK.asItem())) {
            this.owner_uuid = player.getUUID();
            if (Objects.requireNonNull(this.getLevel()).isClientSide()) {
                player.sendSystemMessage(Component.translatable("gtmthings.machine.wireless_energy_hatch.tooltip.bind", new Object[] { TeamUtil.GetName(player) }));
            }

            this.updateEnergySubscription();
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    public boolean onLeftClick(Player player, Level world, InteractionHand hand, BlockPos pos, Direction direction) {
        ItemStack is = player.getItemInHand(hand);
        if (is.isEmpty()) {
            return false;
        } else if (is.is(GTItems.TOOL_DATA_STICK.asItem())) {
            this.owner_uuid = null;
            if (Objects.requireNonNull(this.getLevel()).isClientSide()) {
                player.sendSystemMessage(Component.translatable("gtmthings.machine.wireless_energy_hatch.tooltip.unbind"));
            }

            this.updateEnergySubscription();
            return true;
        } else {
            return false;
        }
    }

    public void onMachinePlaced(@Nullable LivingEntity player, ItemStack stack) {
        if (player != null) {
            this.owner_uuid = player.getUUID();
            this.updateEnergySubscription();
        }
    }

    public UUID getUUID() {
        return this.owner_uuid;
    }

    public void setUUID(UUID uuid) {
        this.owner_uuid = uuid;
    }

    public int tintColor(int index) {
        return index == 2 ? GTValues.VC[this.getTier()] : super.tintColor(index);
    }

    static {
        MANAGED_FIELD_HOLDER = new ManagedFieldHolder(RhythmSourceInterface.class, MetaMachine.MANAGED_FIELD_HOLDER);
    }
}
