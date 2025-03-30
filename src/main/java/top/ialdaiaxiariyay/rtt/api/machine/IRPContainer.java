package top.ialdaiaxiariyay.rtt.api.machine;

import net.minecraft.core.Direction;

import java.math.BigInteger;

public interface IRPContainer extends IRPInfoProvider {

    IRPContainer DEFAULT = new IRPContainer() {

        @Override
        public long acceptRPFromNetwork(Direction Direction, long l, long l1) {
            return 0L;
        }

        @Override
        public boolean inputsRP(Direction Direction) {
            return false;
        }

        @Override
        public long changeRP(long l) {
            return 0L;
        }

        @Override
        public long getRPStored() {
            return 0L;
        }

        @Override
        public long getRPCapacity() {
            return 0L;
        }

        @Override
        public long getInputAmperage() {
            return 0L;
        }

        @Override
        public long getInputVoltage() {
            return 0L;
        }
    };

    long acceptRPFromNetwork(Direction var1, long var2, long var4);

    boolean inputsRP(Direction var1);

    default boolean outputsRP(Direction side) {
        return false;
    }

    long changeRP(long var1);

    default long addRP(long RPToAdd) {
        return this.changeRP(RPToAdd);
    }

    default long removeRP(long RPToRemove) {
        return -this.changeRP(-RPToRemove);
    }

    default long getRPCanBeInserted() {
        return this.getRPCapacity() - this.getRPStored();
    }

    long getRPStored();

    long getRPCapacity();

    @Override
    default IRPInfoProvider.RPInfo getRPInfo() {
        return new IRPInfoProvider.RPInfo(BigInteger.valueOf(this.getRPCapacity()), BigInteger.valueOf(this.getRPStored()));
    }

    @Override
    default boolean supportsBigIntRPValues() {
        return false;
    }

    default long getOutputAmperage() {
        return 0L;
    }

    default long getOutputVoltage() {
        return 0L;
    }

    long getInputAmperage();

    long getInputVoltage();

    default long getInputPerSec() {
        return 0L;
    }

    default long getOutputPerSec() {
        return 0L;
    }

    default boolean isOneProbeHidden() {
        return false;
    }
}
