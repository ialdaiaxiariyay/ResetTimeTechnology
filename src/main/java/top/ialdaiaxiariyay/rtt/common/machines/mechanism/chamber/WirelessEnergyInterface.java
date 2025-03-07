package top.ialdaiaxiariyay.rtt.common.machines.mechanism.chamber;

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
import com.hepdd.gtmthings.api.machine.IWirelessEnergyContainerHolder;
import com.hepdd.gtmthings.api.misc.WirelessEnergyContainer;
import com.hepdd.gtmthings.utils.TeamUtil;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WirelessEnergyInterface extends TieredIOPartMachine implements IBindable, IInteractedMachine, IMachineLife, IWirelessEnergyContainerHolder {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER;
    private TickableSubscription updEnergySubs;
    @Getter
    @Setter
    private WirelessEnergyContainer WirelessEnergyContainerCache;
    @Persisted
    public UUID owner_uuid;
    @Persisted
    public final NotifiableEnergyContainer energyContainer = this.createEnergyContainer();

    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    public WirelessEnergyInterface(IMachineBlockEntity holder) {
        super(holder, 14, IO.IN);
    }

    protected NotifiableEnergyContainer createEnergyContainer() {
        NotifiableEnergyContainer container = NotifiableEnergyContainer.receiverContainer(this, Long.MAX_VALUE, GTValues.VEX[this.tier], 67108864L);
        container.setSideInputCondition((s) -> {
            return s == this.getFrontFacing() && this.isWorkingEnabled();
        });
        container.setCapabilityValidator((s) -> {
            return s == null || s == this.getFrontFacing();
        });
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
            long changeEnergy = this.getWirelessEnergyContainer().addEnergy(currentStored, this);
            if (changeEnergy > 0L) {
                this.energyContainer.setEnergyStored(currentStored - changeEnergy);
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
            if (this.getLevel().isClientSide()) {
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
            if (this.getLevel().isClientSide()) {
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
        MANAGED_FIELD_HOLDER = new ManagedFieldHolder(WirelessEnergyInterface.class, MetaMachine.MANAGED_FIELD_HOLDER);
    }
}
