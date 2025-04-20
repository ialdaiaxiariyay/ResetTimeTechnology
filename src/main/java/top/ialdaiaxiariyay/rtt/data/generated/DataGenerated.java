package top.ialdaiaxiariyay.rtt.data.generated;

import com.tterrag.registrate.providers.ProviderType;
import top.ialdaiaxiariyay.rtt.data.generated.lang.LangHandler;
import top.ialdaiaxiariyay.rtt.data.generated.lang.lang.SimplifiedChineseLanguageProvider;
import top.ialdaiaxiariyay.rtt.data.generated.lang.lang.TraditionalChineseLanguageProvider;

import static top.ialdaiaxiariyay.rtt.api.registries.RTTRegistration.REGISTRATE;

public class DataGenerated {
   public static void init() {
       REGISTRATE.addDataGenerator(ProviderType.LANG, LangHandler::enInitialize);
       REGISTRATE.addDataGenerator(SimplifiedChineseLanguageProvider.LANG, LangHandler::cnInitialize);
       REGISTRATE.addDataGenerator(TraditionalChineseLanguageProvider.LANG, LangHandler::twInitialize);
   }
}
