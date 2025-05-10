//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package top.ialdaiaxiariyay.rtt.api.machine;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.feature.IExplosionMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableRecipeHandlerTrait;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.utils.GTUtil;

import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.core.Direction;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.ialdaiaxiariyay.rtt.api.recipe.RPRecipeCapability;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class RTTNotifiableContainer extends NotifiableRecipeHandlerTrait<Long> implements IRPContainer {

    public static final ManagedFieldHolder MANAGED_FIELD_HOLDER;
    @Getter
    protected IO handlerIO;
    @Getter
    @Persisted
    @DescSynced
    protected long RPStored;
    @Getter
    private long RPCapacity;
    @Getter
    private long inputVoltage;
    @Getter
    private long inputAmperage;
    @Getter
    private long outputVoltage;
    @Getter
    private long outputAmperage;
    @Setter
    private Predicate<Direction> sideInputCondition;
    @Setter
    private Predicate<Direction> sideOutputCondition;
    protected long amps;
    protected long lastTimeStamp = Long.MIN_VALUE;
    protected @Nullable TickableSubscription outputSubs;
    protected @Nullable TickableSubscription updateSubs;
    protected long lastEnergyInputPerSec = 0L;
    protected long lastEnergyOutputPerSec = 0L;
    protected long energyInputPerSec = 0L;
    protected long energyOutputPerSec = 0L;

    public RTTNotifiableContainer(MetaMachine machine, long maxCapacity, long maxInputVoltage, long maxInputAmperage, long maxOutputVoltage, long maxOutputAmperage) {
        super(machine);
        this.RPCapacity = maxCapacity;
        this.inputVoltage = maxInputVoltage;
        this.inputAmperage = maxInputAmperage;
        this.outputVoltage = maxOutputVoltage;
        this.outputAmperage = maxOutputAmperage;
        boolean isIn = this.inputVoltage != 0L && this.inputAmperage != 0L;
        boolean isOut = this.outputVoltage != 0L && this.outputAmperage != 0L;
        this.handlerIO = isIn && isOut ? IO.BOTH : (isIn ? IO.IN : (isOut ? IO.OUT : IO.NONE));
    }

    @Contract("_, _, _, _ -> new")
    public static @NotNull RTTNotifiableContainer emitterContainer(MetaMachine machine, long maxCapacity, long maxOutputVoltage, long maxOutputAmperage) {
        return new RTTNotifiableContainer(machine, maxCapacity, 0L, 0L, maxOutputVoltage, maxOutputAmperage);
    }

    @Contract("_, _, _, _ -> new")
    public static @NotNull RTTNotifiableContainer receiverContainer(MetaMachine machine, long maxCapacity, long maxInputVoltage, long maxInputAmperage) {
        return new RTTNotifiableContainer(machine, maxCapacity, maxInputVoltage, maxInputAmperage, 0L, 0L);
    }

    public void resetBasicInfo(long maxCapacity, long maxInputVoltage, long maxInputAmperage, long maxOutputVoltage, long maxOutputAmperage) {
        this.RPCapacity = maxCapacity;
        this.inputVoltage = maxInputVoltage;
        this.inputAmperage = maxInputAmperage;
        this.outputVoltage = maxOutputVoltage;
        this.outputAmperage = maxOutputAmperage;
        boolean isIN = this.inputVoltage != 0L && this.inputAmperage != 0L;
        boolean isOUT = this.outputVoltage != 0L && this.outputAmperage != 0L;
        this.handlerIO = isIN && isOUT ? IO.BOTH : (isIN ? IO.IN : (isOUT ? IO.OUT : IO.NONE));
        this.checkOutputSubscription();
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public void onMachineLoad() {
        super.onMachineLoad();
        this.checkOutputSubscription();
        this.updateSubs = this.getMachine().subscribeServerTick(this.updateSubs, this::updateTick);
    }

    @Override
    public void onMachineUnLoad() {
        super.onMachineUnLoad();
        if (this.updateSubs != null) {
            this.updateSubs.unsubscribe();
            this.updateSubs = null;
        }
    }

    public void checkOutputSubscription() {
        if (this.getOutputVoltage() > 0L && this.getOutputAmperage() > 0L) {
            if (this.getRPStored() >= this.getOutputVoltage()) {
                this.outputSubs = this.getMachine().subscribeServerTick(this.outputSubs, this::serverTick);
            } else if (this.outputSubs != null) {
                this.outputSubs.unsubscribe();
                this.outputSubs = null;
            }
        }
    }

    @Override
    public long getInputPerSec() {
        return this.lastEnergyInputPerSec;
    }

    @Override
    public long getOutputPerSec() {
        return this.lastEnergyOutputPerSec;
    }

    public void setRPStored(long RPStored) {
        if (this.RPStored != RPStored) {
            if (RPStored > this.RPStored) {
                this.energyInputPerSec += RPStored - this.RPStored;
            } else {
                this.energyOutputPerSec += this.RPStored - RPStored;
            }

            this.RPStored = RPStored;
            this.checkOutputSubscription();
            this.notifyListeners();
        }
    }

    public void updateTick() {
        if (this.getMachine().getOffsetTimer() % 20L == 0L) {
            this.lastEnergyOutputPerSec = this.energyOutputPerSec;
            this.lastEnergyInputPerSec = this.energyInputPerSec;
            this.energyOutputPerSec = 0L;
            this.energyInputPerSec = 0L;
        }
    }

    public void serverTick() {
        if (!Objects.requireNonNull(this.getMachine().getLevel()).isClientSide) {
            if (this.getRPStored() >= this.getOutputVoltage() && this.getOutputVoltage() > 0L && this.getOutputAmperage() > 0L) {
                long outputVoltage = this.getOutputVoltage();
                long outputAmperes = Math.min(this.getRPStored() / outputVoltage, this.getOutputAmperage());
                if (outputAmperes == 0L) {
                    return;
                }

                long amperesUsed = 0L;
                Direction[] var7 = GTUtil.DIRECTIONS;

                for (Direction side : var7) {
                    if (this.outputsRP(side)) {
                        Direction oppositeSide = side.getOpposite();
                        IRPContainer energyContainer = RTTCapabilityHelper.getRPContainer(Objects.requireNonNull(this.machine.getLevel()), this.machine.getPos().relative(side), oppositeSide);
                        if (energyContainer != null && energyContainer.inputsRP(oppositeSide)) {
                            amperesUsed += energyContainer.acceptRPFromNetwork(oppositeSide, outputVoltage, outputAmperes - amperesUsed);
                            if (amperesUsed == outputAmperes) {
                                break;
                            }
                        }
                    }
                }

                if (amperesUsed > 0L) {
                    this.setRPStored(this.getRPStored() - amperesUsed * outputVoltage);
                }
            }

        }
    }

    @Override
    public long acceptRPFromNetwork(Direction side, long voltage, long amperage) {
        long latestTimeStamp = this.getMachine().getOffsetTimer();
        if (this.lastTimeStamp < latestTimeStamp) {
            this.amps = 0L;
            this.lastTimeStamp = latestTimeStamp;
        }

        if (this.amps < this.getInputAmperage()) {
            long canAccept = this.getRPCapacity() - this.getRPStored();
            if (voltage > 0L && (side == null || this.inputsRP(side))) {
                if (voltage > this.getInputVoltage()) {
                    if (this.machine instanceof IExplosionMachine explosionMachine) {
                        explosionMachine.doExplosion(GTUtil.getExplosionPower(voltage));
                        return Math.min(amperage, this.getInputAmperage() - this.amps);
                    }
                }

                if (canAccept >= voltage) {
                    long amperesAccepted = Math.min(canAccept / voltage, Math.min(amperage, this.getInputAmperage() - this.amps));
                    if (amperesAccepted > 0L) {
                        this.setRPStored(this.getRPStored() + voltage * amperesAccepted);
                        this.amps += amperesAccepted;
                        return amperesAccepted;
                    }
                }
            }

        }
        return 0L;
    }

    @Override
    public boolean inputsRP(Direction side) {
        return !this.outputsRP(side) && this.getInputVoltage() > 0L && (this.sideInputCondition == null || this.sideInputCondition.test(side));
    }

    @Override
    public boolean outputsRP(Direction side) {
        return this.getOutputVoltage() > 0L && (this.sideOutputCondition == null || this.sideOutputCondition.test(side));
    }

    @Override
    public long changeRP(long energyToAdd) {
        long oldRPStored = this.getRPStored();
        long newRPStored = this.RPCapacity - oldRPStored < energyToAdd ? this.RPCapacity : oldRPStored + energyToAdd;
        if (newRPStored < 0L) {
            newRPStored = 0L;
        }

        this.setRPStored(newRPStored);
        return newRPStored - oldRPStored;
    }

    @Override
    public List<Long> handleRecipeInner(IO io, GTRecipe recipe, @NotNull List<Long> left, @Nullable String slotName, boolean simulate) {
        IRPContainer capability = this;
        long sum = left.stream().reduce(0L, Long::sum);
        long canInput;
        if (io == IO.IN) {
            canInput = capability.getRPStored();
            if (!simulate) {
                capability.addRP(-Math.min(canInput, sum));
            }

            sum -= canInput;
        } else if (io == IO.OUT) {
            canInput = capability.getRPCapacity() - capability.getRPStored();
            if (!simulate) {
                capability.addRP(Math.min(canInput, sum));
            }

            sum -= canInput;
        }

        return sum <= 0L ? null : Collections.singletonList(sum);
    }

    @Override
    public List<Object> getContents() {
        return List.of(this.RPStored);
    }

    @Override
    public double getTotalContentAmount() {
        return (double) this.RPStored;
    }

    @Override
    public RecipeCapability<Long> getCapability() {
        return RPRecipeCapability.CAP;
    }

    static {
        MANAGED_FIELD_HOLDER = new ManagedFieldHolder(RTTNotifiableContainer.class, NotifiableRecipeHandlerTrait.MANAGED_FIELD_HOLDER);
    }
}
