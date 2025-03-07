package top.ialdaiaxiariyay.rtt.mixin.gto;

import com.gregtechceu.gtceu.api.machine.MetaMachine;

import com.gto.gtocore.common.wireless.ExtendTransferData;
import com.gto.gtocore.common.wireless.ExtendWirelessEnergyContainer;
import com.hepdd.gtmthings.data.WirelessEnergySavaedData;
import lombok.Generated;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.math.BigInteger;

import static com.hepdd.gtmthings.api.misc.WirelessEnergyContainer.TRANSFER_DATA;

@Mixin({ ExtendWirelessEnergyContainer.class })
public class ExtendWirelessEnergyContainerMixin {

    /**
     * @author ialdaiaxiariyay
     * @reason Overwrite getCapacity
     */
    @Generated
    @Overwrite(remap = false)
    public BigInteger getCapacity() {
        return new BigInteger("2").pow(1024); // 象征性极大值
    }

    /**
     * @author ialdaiaxiariyay
     * @reason Overwrite addEnergy
     */
    @Overwrite(remap = false)
    public long addEnergy(long energy, @Nullable MetaMachine machine) {
        if (energy <= 0L) {
            return 0L;
        }
        // 直接存储全部能量（无损耗）
        ((WirelessEnergyContainerAccessor) this).setStorage(
                ((WirelessEnergyContainerAccessor) this).getStorage().add(BigInteger.valueOf(energy)));
        WirelessEnergySavaedData.INSTANCE.setDirty(true);
        if (((WirelessEnergyContainerAccessor) this).isObserved() && machine != null) {
            // 假设 ExtendTransferData 构造器兼容新参数
            TRANSFER_DATA.put(machine, new ExtendTransferData(
                    ((WirelessEnergyContainerAccessor) this).getUuid(),
                    energy,
                    0 // 损耗固定为0
            ));
        }
        return energy;
    }

    /**
     * @author ialdaiaxiariyay
     * @reason Overwrite unrestrictedAddEnergy
     */
    @Overwrite(remap = false)
    public long unrestrictedAddEnergy(long energy) {
        if (energy <= 0L) {
            return 0L;
        }
        ((WirelessEnergyContainerAccessor) this).setStorage(
                ((WirelessEnergyContainerAccessor) this).getStorage().add(BigInteger.valueOf(energy)));
        WirelessEnergySavaedData.INSTANCE.setDirty(true);
        return energy;
    }

    // ==== 禁用动态容量调整 ====
    /**
     * @author ialdaiaxiariyay
     * @reason Overwrite setCapacity
     */
    @Overwrite(remap = false)
    public void setCapacity(BigInteger capacity) {
        // 空实现（禁止修改容量）
    }

    /**
     * @author ialdaiaxiariyay
     * @reason Overwrite setLoss
     */
    @Overwrite(remap = false)
    public void setLoss(int loss) {
        // 空实现（禁止修改损耗）
    }
}
