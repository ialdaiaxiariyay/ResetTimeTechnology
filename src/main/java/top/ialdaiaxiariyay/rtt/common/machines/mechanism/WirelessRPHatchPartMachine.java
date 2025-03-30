package top.ialdaiaxiariyay.rtt.common.machines.mechanism;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.feature.IExplosionMachine;
import com.gregtechceu.gtceu.api.machine.feature.IInteractedMachine;
import com.gregtechceu.gtceu.api.machine.feature.IMachineLife;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import com.hepdd.gtmthings.api.capability.IBindable;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;
import top.ialdaiaxiariyay.rtt.api.machine.RTTNotifiableContainer;
import top.ialdaiaxiariyay.rtt.api.rhythmsource.RhythmSourceManager;

import java.util.UUID;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.hepdd.gtmthings.utils.TeamUtil.GetName;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WirelessRPHatchPartMachine extends TieredIOPartMachine implements IInteractedMachine, IBindable, IExplosionMachine, IMachineLife {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            WirelessRPHatchPartMachine.class, TieredIOPartMachine.MANAGED_FIELD_HOLDER);

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Persisted
    public UUID owner_uuid;
    @Persisted
    public final RTTNotifiableContainer RPContainer;
    @Getter
    protected int amperage;
    private TickableSubscription updRPSubs;

    public WirelessRPHatchPartMachine(IMachineBlockEntity holder, int tier, IO io, int amperage, Object... args) {
        super(holder, tier, io);
        this.amperage = amperage;
        this.RPContainer = createRPContainer(args);
    }

    protected RTTNotifiableContainer createRPContainer(Object... args) {
        RTTNotifiableContainer container;
        if (io == IO.OUT) {
            container = RTTNotifiableContainer.emitterContainer(this, GTValues.V[tier] * 64L * amperage,
                    GTValues.V[tier], amperage);
        } else {
            container = RTTNotifiableContainer.receiverContainer(this, GTValues.V[tier] * 64L * amperage,
                    GTValues.V[tier], amperage);
        }
        return container;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        updateRPSubscription();
    }

    @Override
    public void onUnload() {
        super.onUnload();
        if (updRPSubs != null) {
            updRPSubs.unsubscribe();
            updRPSubs = null;
        }
    }

    private void updateRPSubscription() {
        if (this.owner_uuid != null) {
            updRPSubs = subscribeServerTick(updRPSubs, this::updateRP);
        } else if (updRPSubs != null) {
            updRPSubs.unsubscribe();
            updRPSubs = null;
        }
    }

    private void updateRP() {
        if (this.owner_uuid == null) return;
        if (io == IO.IN) {
            useRP();
        } else {
            addRP();
        }
    }

    private void useRP() {
        var currentStored = RPContainer.getRPStored();
        var maxStored = RPContainer.getRPCapacity();
        var changeStored = Math.min(maxStored - currentStored, RPContainer.getInputVoltage() * RPContainer.getInputAmperage());
        if (changeStored <= 0) return;
        if (!RhythmSourceManager.addRPToGlobalSourceMap(this.owner_uuid, -changeStored, this)) return;
        RPContainer.setRPStored(currentStored + changeStored);
    }

    private void addRP() {
        var currentStored = RPContainer.getRPStored();
        if (currentStored <= 0) return;
        var changeStored = Math.min(RPContainer.getOutputVoltage() * RPContainer.getOutputAmperage(), currentStored);
        if (!RhythmSourceManager.addRPToGlobalSourceMap(this.owner_uuid, changeStored, this)) return;
        RPContainer.setRPStored(currentStored - changeStored);
    }

    @Override
    public boolean shouldOpenUI(Player player, InteractionHand hand, BlockHitResult hit) {
        return false;
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack is = player.getItemInHand(hand);
        if (is.isEmpty()) return InteractionResult.PASS;
        if (is.is(GTItems.TOOL_DATA_STICK.asItem())) {
            this.owner_uuid = player.getUUID();
            if (getLevel().isClientSide()) {
                player.sendSystemMessage(Component.translatable("rtt.machine.rp_hatch.bound", GetName(player)));
            }
            updateRPSubscription();
            return InteractionResult.SUCCESS;
        } else if (is.is(Items.STICK)) {
            if (io == IO.OUT) RPContainer.setRPStored(GTValues.V[tier] * 64L * amperage);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean onLeftClick(Player player, Level world, InteractionHand hand, BlockPos pos, Direction direction) {
        ItemStack is = player.getItemInHand(hand);
        if (is.isEmpty()) return false;
        if (is.is(GTItems.TOOL_DATA_STICK.asItem())) {
            this.owner_uuid = null;
            if (getLevel().isClientSide()) {
                player.sendSystemMessage(Component.translatable("rtt.machine.rp_hatch.unbound"));
            }
            updateRPSubscription();
            return true;
        }
        return false;
    }

    @Override
    public void onMachinePlaced(@Nullable LivingEntity player, ItemStack stack) {
        if (player != null) {
            this.owner_uuid = player.getUUID();
            updateRPSubscription();
        }
    }

    @Override
    public UUID getUUID() {
        return this.owner_uuid;
    }

    @Override
    public void setUUID(UUID uuid) {
        this.owner_uuid = uuid;
    }

    //////////////////////////////////////
    // ********** Misc **********//
    //////////////////////////////////////

    @Override
    public int tintColor(int index) {
        if (index == 2) {
            return GTValues.VC[getTier()];
        }
        return super.tintColor(index);
    }
}
