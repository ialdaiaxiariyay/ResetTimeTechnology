package top.ialdaiaxiariyay.rttgtcore.data;

import top.ialdaiaxiariyay.rttgtcore.api.registries.Registries;
import com.tterrag.registrate.providers.ProviderType;
import top.ialdaiaxiariyay.rttgtcore.data.lang.LangHandler;

public class Datagen {
    public static void init() {
        Registries.REGISTRATE.addDataGenerator(ProviderType.LANG, LangHandler::init);
    }
}
