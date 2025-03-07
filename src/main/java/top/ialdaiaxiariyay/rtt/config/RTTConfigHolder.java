package top.ialdaiaxiariyay.rtt.config;

import top.ialdaiaxiariyay.rtt.RTT;

import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.format.ConfigFormats;

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
    @Configurable.Comment("开启原版gtmt无线电网行为")
    public boolean EnableOriginalGTMTUnlimitedPowerGrids = false;
}
