package top.ialdaiaxiariyay.rttgtcore;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ialdaiaxiariyay.rttgtcore.client.ClientProxy;
import top.ialdaiaxiariyay.rttgtcore.common.CommonProxy;

@Mod(RTTGTCore.MOD_ID)
public class RTTGTCore {

    public static final String MOD_ID = "rttgtcore",
            NAME = "RTT GTCore";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public RTTGTCore() {
        RTTGTCore.init();
        DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    }

    public static void init() {}

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
