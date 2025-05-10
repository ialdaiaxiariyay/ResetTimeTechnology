package top.ialdaiaxiariyay.rtt.integration.jade;

import com.gregtechceu.gtceu.api.block.MetaMachineBlock;
import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.jetbrains.annotations.NotNull;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import top.ialdaiaxiariyay.rtt.integration.jade.provider.RecipeLogicProvider;
import top.ialdaiaxiariyay.rtt.integration.jade.provider.RpContainerBlockProvider;
import top.ialdaiaxiariyay.rtt.integration.jade.provider.TickTimeProvider;

@WailaPlugin
public class RTTJadePlugin implements IWailaPlugin {

    @Override
    public void register(@NotNull IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(new RecipeLogicProvider(), BlockEntity.class);
        registration.registerBlockDataProvider(new RpContainerBlockProvider(), BlockEntity.class);
        registration.registerBlockDataProvider(new TickTimeProvider(), MetaMachineBlockEntity.class);
    }

    @Override
    public void registerClient(@NotNull IWailaClientRegistration registration) {
        registration.registerBlockComponent(new RecipeLogicProvider(), Block.class);
        registration.registerBlockComponent(new RpContainerBlockProvider(), Block.class);
        registration.registerBlockComponent(new TickTimeProvider(), MetaMachineBlock.class);
    }
}
