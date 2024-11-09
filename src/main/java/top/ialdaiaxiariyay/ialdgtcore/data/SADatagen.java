package top.ialdaiaxiariyay.ialdgtcore.data;

import top.ialdaiaxiariyay.ialdgtcore.api.registries.SARegistries;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import top.ialdaiaxiariyay.ialdgtcore.data.lang.LangHandler;

public class SADatagen {
    public static void init() {

        SARegistries.REGISTRATE.addDataGenerator(ProviderType.LANG, LangHandler::init);
    }
}
