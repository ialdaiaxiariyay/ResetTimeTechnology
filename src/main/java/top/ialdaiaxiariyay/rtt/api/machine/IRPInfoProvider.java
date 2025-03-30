//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package top.ialdaiaxiariyay.rtt.api.machine;

import java.math.BigInteger;

public interface IRPInfoProvider {

    RPInfo getRPInfo();

    boolean supportsBigIntRPValues();

    public static record RPInfo(BigInteger capacity, BigInteger stored) {

        public RPInfo(BigInteger capacity, BigInteger stored) {
            this.capacity = capacity;
            this.stored = stored;
        }

        public BigInteger capacity() {
            return this.capacity;
        }

        public BigInteger stored() {
            return this.stored;
        }
    }
}
