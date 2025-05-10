package top.ialdaiaxiariyay.rtt.data.generated;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;

import com.google.gson.JsonObject;
import com.mojang.math.MethodsReturnNonnullByDefault;
import top.ialdaiaxiariyay.rtt.RTT;
import top.ialdaiaxiariyay.rtt.data.generated.lang.*;

import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class LocalizationProvider implements DataProvider {

    @FunctionalInterface
    public interface LanguageProcessingStrategy {

        void process(JsonObject json);
    }

    private record Language(String en, String cn) {}

    private static final Map<String, Language> LANGUAGES = new TreeMap<>();

    enum LangCode {

        EN_US(Language::en),
        ZH_CN(Language::cn);

        private final Function<Language, String> valueExtractor;

        LangCode(Function<Language, String> valueExtractor) {
            this.valueExtractor = valueExtractor;
        }

        String getValue(Language language) {
            return valueExtractor.apply(language);
        }
    }

    private final DataGenerator generator;

    public LocalizationProvider(DataGenerator generator) {
        this.generator = generator;
    }

    public static void add(String id, String englishName, String chineseName) {
        if (LANGUAGES.containsKey(id)) {
            throw new IllegalArgumentException("Duplicate language id: " + id);
        }
        LANGUAGES.put(id, new Language(englishName, chineseName));
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cached) {
        BlockLang.init();
        ItemLang.init();
        Langlang.init();
        RecipeTypeLang.init();
        TooltipsLang.init();
        return CompletableFuture.allOf(save(cached, LangCode.ZH_CN), save(cached, LangCode.EN_US));
    }

    private CompletableFuture<?> save(CachedOutput cache, LangCode code) {
        Path path = buildLanguageFilePath(code);
        JsonObject json = new JsonObject();

        getProcessingStrategy(code).process(json);

        return DataProvider.saveStable(cache, json, path);
    }

    private LanguageProcessingStrategy getProcessingStrategy(LangCode code) {
        return json -> LANGUAGES
                .forEach((key, language) -> json.addProperty(key, code.getValue(language)));
    }

    private Path buildLanguageFilePath(LangCode code) {
        return this.generator.getPackOutput().getOutputFolder()
                .resolve(String.format("assets/" + RTT.MOD_ID + "/lang/%s.json", code.name().toLowerCase()));
    }

    @Override
    public String getName() {
        return "Localization (en,zh_cn)";
    }
}
