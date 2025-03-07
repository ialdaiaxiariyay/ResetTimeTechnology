package top.ialdaiaxiariyay.rttgtcore.config;

import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.format.ConfigFormats;
import top.ialdaiaxiariyay.rttgtcore.RTTGTCore;

@Config(id = RTTGTCore.MOD_ID)
public class RTTGTConfigHolder {

    public static RTTGTConfigHolder INSTANCE;
    private static final Object LOCK = new Object();

    public static void init() {
        synchronized (LOCK) {
            if (INSTANCE == null) {
                INSTANCE = Configuration.registerConfig(RTTGTConfigHolder.class, ConfigFormats.yaml()).getConfigInstance();
            }
        }
    }

    @Configurable
    @Configurable.Comment("开启湛紫微光和微光主机合成表的注册，注意这可能会破坏游戏平衡性")
    public boolean EnableDeepvioletgleamAndCircuitRecipe = false;
    @Configurable
    @Configurable.Comment("开启通用蒸汽机\n#提示:这可能会有点小OP")
    public boolean EnableTheGeneralSteamEngine = false;
    @Configurable
    @Configurable.Comment("开启方便AE配方，比较OP")
    public boolean EnableFreeAe = false;
}
