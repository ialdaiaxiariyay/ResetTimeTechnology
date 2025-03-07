package top.ialdaiaxiariyay.rtt.api.registries;

import top.ialdaiaxiariyay.rtt.RTT;

import com.gregtechceu.gtceu.api.block.MetaMachineBlock;
import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.item.MetaMachineItem;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

import net.minecraft.resources.ResourceKey;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public final class RTTRegistration extends GTRegistrate {

    public static final RTTRegistration REGISTRATE = new RTTRegistration();

    private RTTRegistration() {
        super(RTT.MOD_ID);
    }

    public @NotNull MultiblockBuilder multiblock(@NotNull String name, @NotNull Function<IMachineBlockEntity, ? extends MultiblockControllerMachine> metaMachine) {
        return new MultiblockBuilder(this, name, metaMachine, MetaMachineBlock::new, MetaMachineItem::new, MetaMachineBlockEntity::createBlockEntity);
    }

    static {
        REGISTRATE.defaultCreativeTab((ResourceKey) null);
    }
}
