package top.ialdaiaxiariyay.rtt;

import top.ialdaiaxiariyay.rtt.client.ClientProxy;
import top.ialdaiaxiariyay.rtt.common.CommonProxy;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(RTT.MOD_ID)
public class RTT {

    public static ResourceLocation id(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    public static final String MOD_ID = "rtt",
            NAME = "Reset Time Technology";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public RTT() {
        DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
        MinecraftForge.EVENT_BUS.register(this);
        RTT.init();
    }

    public static void init() {}
}
