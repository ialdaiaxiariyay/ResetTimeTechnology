package top.ialdaiaxiariyay.rtt.mixin.gto;

import com.hepdd.gtmthings.api.misc.WirelessEnergyContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.math.BigInteger;
import java.util.UUID;

@Mixin(WirelessEnergyContainer.class)
public interface WirelessEnergyContainerAccessor {

    @Accessor("storage")
    BigInteger getStorage();

    @Accessor("storage")
    void setStorage(BigInteger storage);

    @Accessor("rate")
    long getRate();

    @Accessor("uuid")
    UUID getUuid();

    @Accessor("observed")
    boolean isObserved();
}
