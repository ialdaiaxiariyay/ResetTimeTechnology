package top.ialdaiaxiariyay.rtt.data.generated.lang;

import net.minecraftforge.common.data.LanguageProvider;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import top.ialdaiaxiariyay.rtt.data.generated.lang.lang.*;
import top.ialdaiaxiariyay.rtt.utils.ChineseConverter;

import java.util.Arrays;
import java.util.Map;

public final class LangHandler {

    private static final Map<String, ENCN> LANGS = new Object2ObjectOpenHashMap<>();

    private static void addENCN(String key, ENCN encn) {
        LANGS.put(key, encn);
    }

    public static void addENCN(String key, String en, String cn) {
        addENCN(key, new ENCN(en, cn));
    }

    private static void addCN(String key, String cn) {
        addENCN(key, null, cn);
    }

    public static void enInitialize(LanguageProvider provider) {
        LANGS.forEach((k, v) -> {
            if (v.en == null) return;
            provider.add(k, v.en);
        });
    }

    public static void cnInitialize(SimplifiedChineseLanguageProvider provider) {
        BlockLang.init();
        ItemLang.init();
        Langlang.init();
        RecipeTypeLang.init();
        TooltipLang.init();
        LANGS.forEach((k, v) -> {
            if (v.cn == null) return;
            provider.add(k, v.cn);
        });
    }

    public static void twInitialize(TraditionalChineseLanguageProvider provider) {
        LANGS.forEach((k, v) -> {
            if (v.cn == null) return;
            provider.add(k, ChineseConverter.convert(v.cn));
        });
    }

    public record ENCN(String en, String cn) {

        @Override
        public boolean equals(Object o) {
            if (o instanceof ENCN encn) {
                return encn.en.equals(en) && encn.cn.equals(cn);
            }
            return false;
        }
    }

    public record ENCNS(String[] ens, String[] cns) {

        @Override
        public boolean equals(Object o) {
            if (o instanceof ENCNS encn) {
                return Arrays.equals(encn.ens, ens) && Arrays.equals(encn.cns, cns);
            }
            return false;
        }

        public int length() {
            return ens.length;
        }
    }
}
