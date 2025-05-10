package top.ialdaiaxiariyay.rtt;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ialdaiaxiariyay.rtt.client.ClientProxy;
import top.ialdaiaxiariyay.rtt.common.CommonProxy;

@Mod(RTT.MOD_ID)
public class RTT {

    public static final String MOD_ID = "rtt",
            NAME = "Reset Time Technology";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public RTT() {
        RTT.init();
        DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    }

    public static void init() {}

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static boolean isDataGen() {
        return FMLLoader.getLaunchHandler().isData();
    }
}
