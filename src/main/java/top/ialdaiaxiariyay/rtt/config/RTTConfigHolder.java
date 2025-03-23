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
}
