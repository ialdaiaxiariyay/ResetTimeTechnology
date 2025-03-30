//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package top.ialdaiaxiariyay.rtt.api.machine;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableRecipeHandlerTrait;
import com.gregtechceu.gtceu.utils.GTUtil;

import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class NotifiableLRPContainer extends RTTNotifiableContainer implements IRPContainer {

    public static final ManagedFieldHolder MANAGED_FIELD_HOLDER;

    public NotifiableLRPContainer(MetaMachine machine, long maxCapacity, long maxInputVoltage, long maxInputAmperage, long maxOutputVoltage, long maxOutputAmperage) {
        super(machine, maxCapacity, maxInputVoltage, maxInputAmperage, maxOutputVoltage, maxOutputAmperage);
    }

    @Contract("_, _, _, _ -> new")
    public static @NotNull NotifiableLRPContainer emitterContainer(MetaMachine machine, long maxCapacity, long maxOutputVoltage, long maxOutputAmperage) {
        return new NotifiableLRPContainer(machine, maxCapacity, 0L, 0L, maxOutputVoltage, maxOutputAmperage);
    }

    @Contract("_, _, _, _ -> new")
    public static @NotNull NotifiableLRPContainer receiverContainer(MetaMachine machine, long maxCapacity, long maxInputVoltage, long maxInputAmperage) {
        return new NotifiableLRPContainer(machine, maxCapacity, maxInputVoltage, maxInputAmperage, 0L, 0L);
    }

    @Override
    public void serverTick() {
        this.amps = 0L;
        if (!Objects.requireNonNull(this.getMachine().getLevel()).isClientSide) {
            if (this.getRPStored() >= this.getOutputVoltage() && this.getOutputVoltage() > 0L && this.getOutputAmperage() > 0L) {
                long outputVoltage = this.getOutputVoltage();
                long outputAmperes = Math.min(this.getRPStored() / outputVoltage, this.getOutputAmperage());
                if (outputAmperes != 0L) {
                    long amperesUsed = 0L;
                    Direction[] var7 = GTUtil.DIRECTIONS;
                    int var8 = var7.length;

                    for (Direction side : var7) {
                        if (this.outputsRP(side)) {
                            BlockEntity tileEntity = this.getMachine().getLevel().getBlockEntity(this.getMachine().getPos().relative(side));
                            Direction oppositeSide = side.getOpposite();
                            IRPContainer laserContainer = RTTCapabilityHelper.getLRP(this.getMachine().getLevel(), this.getMachine().getPos().relative(side), oppositeSide);
                            if (tileEntity != null && laserContainer != null && laserContainer != null && laserContainer.inputsRP(oppositeSide)) {
                                amperesUsed += laserContainer.acceptRPFromNetwork(oppositeSide, outputVoltage, outputAmperes - amperesUsed);
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
    }

    static {
        MANAGED_FIELD_HOLDER = new ManagedFieldHolder(RTTNotifiableContainer.class, NotifiableRecipeHandlerTrait.MANAGED_FIELD_HOLDER);
    }
}
