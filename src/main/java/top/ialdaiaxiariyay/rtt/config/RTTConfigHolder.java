package top.ialdaiaxiariyay.rtt.config;

import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.format.ConfigFormats;
import top.ialdaiaxiariyay.rtt.RTT;

@Config(id = RTT.MOD_ID)
public class RTTConfigHolder {

    public static RTTConfigHolder INSTANCE;
    private static final Object LOCK = new Object();

    public static void init() {
        synchronized (LOCK) {
            if (INSTANCE == null) {
                INSTANCE = Configuration.registerConfig(RTTConfigHolder.class, ConfigFormats.yaml()).getConfigInstance();
            }
        }
    }

    @Configurable
    @Configurable.Comment("开启湛紫微光和微光主机合成表的注册，注意这可能会破坏游戏平衡性")
    public boolean EnableDeepvioletgleamAndCircuitRecipe = false;
    @Configurable
    @Configurable.Comment("开启通用蒸汽机，这可能会有点小OP")
    public boolean EnableTheGeneralSteamEngine = false;
    @Configurable
    @Configurable.Comment("开启方便AE配方，比较OP")
    public boolean EnableFreeAe = false;
}
