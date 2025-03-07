package top.ialdaiaxiariyay.rttgtcore.common.data.recipe;

import org.gtlcore.gtlcore.utils.Registries;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraftforge.common.Tags;

import top.ialdaiaxiariyay.rttgtcore.common.data.GetRegistries;
import top.ialdaiaxiariyay.rttgtcore.common.machines.machines.RTTGTMachines;
import top.ialdaiaxiariyay.rttgtcore.common.materials.RTTGTMaterials;
import top.ialdaiaxiariyay.rttgtcore.config.RTTGTConfigHolder;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static top.ialdaiaxiariyay.rttgtcore.common.items.RTTGTItem.*;
import static top.ialdaiaxiariyay.rttgtcore.common.machines.recipes.RTTGTRecipeTypes.*;

public class CustomRecipe {

    public static void init(Consumer<FinishedRecipe> provider) {
        if (RTTGTConfigHolder.INSTANCE.EnableDeepvioletgleamAndCircuitRecipe) {
            VanillaRecipeHelper.addShapedRecipe(provider, true, "glimmer_lv_processor_mainfram",
                    GLIMMER_LV_PROCESSOR_MAINFRAME.asStack(),
                    "AAA",
                    "ABA",
                    "AAA",
                    'A', Tags.Items.SAND,
                    'B', new UnificationEntry(TagPrefix.gem, RTTGTMaterials.DeepvioletGleam));
            VanillaRecipeHelper.addShapedRecipe(provider, true, "glimmer_mv_processor_mainfram",
                    GLIMMER_MV_PROCESSOR_MAINFRAME.asStack(),
                    "   ",
                    "AB ",
                    "   ",
                    'A', GLIMMER_LV_PROCESSOR_MAINFRAME,
                    'B', new UnificationEntry(TagPrefix.gem, RTTGTMaterials.DeepvioletGleam));
            VanillaRecipeHelper.addShapedRecipe(provider, true, "glimmer_hv_processor_mainfram",
                    GLIMMER_HV_PROCESSOR_MAINFRAME.asStack(),
                    "   ",
                    "AB ",
                    "   ",
                    'A', GLIMMER_MV_PROCESSOR_MAINFRAME,
                    'B', new UnificationEntry(TagPrefix.gem, RTTGTMaterials.DeepvioletGleam));
            VanillaRecipeHelper.addShapedRecipe(provider, true, "glimmer_ev_processor_mainfram",
                    GLIMMER_EV_PROCESSOR_MAINFRAME.asStack(),
                    "   ",
                    "AB ",
                    "   ",
                    'A', GLIMMER_HV_PROCESSOR_MAINFRAME,
                    'B', new UnificationEntry(TagPrefix.gem, RTTGTMaterials.DeepvioletGleam));
            VanillaRecipeHelper.addShapedRecipe(provider, true, "glimmer_iv_processor_mainfram",
                    GLIMMER_IV_PROCESSOR_MAINFRAME.asStack(),
                    "   ",
                    "AB ",
                    "   ",
                    'A', GLIMMER_EV_PROCESSOR_MAINFRAME,
                    'B', new UnificationEntry(TagPrefix.gem, RTTGTMaterials.DeepvioletGleam));
            VanillaRecipeHelper.addShapedRecipe(provider, true, "glimmer_luv_processor_mainfram",
                    GLIMMER_LuV_PROCESSOR_MAINFRAME.asStack(),
                    "   ",
                    "AB ",
                    "   ",
                    'A', GLIMMER_IV_PROCESSOR_MAINFRAME,
                    'B', new UnificationEntry(TagPrefix.gem, RTTGTMaterials.DeepvioletGleam));
            VanillaRecipeHelper.addShapedRecipe(provider, true, "glimmer_zpm_processor_mainfram",
                    GLIMMER_ZPM_PROCESSOR_MAINFRAME.asStack(),
                    "   ",
                    "AB ",
                    "   ",
                    'A', GLIMMER_LuV_PROCESSOR_MAINFRAME,
                    'B', new UnificationEntry(TagPrefix.gem, RTTGTMaterials.DeepvioletGleam));
            VanillaRecipeHelper.addShapedRecipe(provider, true, "glimmer_uv_processor_mainfram",
                    GLIMMER_UV_PROCESSOR_MAINFRAME.asStack(),
                    "   ",
                    "AB ",
                    "   ",
                    'A', GLIMMER_ZPM_PROCESSOR_MAINFRAME,
                    'B', new UnificationEntry(TagPrefix.gem, RTTGTMaterials.DeepvioletGleam));
            VanillaRecipeHelper.addShapedRecipe(provider, true, "glimmer_uhv_processor_mainfram",
                    GLIMMER_UHV_PROCESSOR_MAINFRAME.asStack(),
                    "   ",
                    "AB ",
                    "   ",
                    'A', GLIMMER_UV_PROCESSOR_MAINFRAME,
                    'B', new UnificationEntry(TagPrefix.gem, RTTGTMaterials.DeepvioletGleam));
            VanillaRecipeHelper.addShapedRecipe(provider, true, "glimmer_uev_processor_mainfram",
                    GLIMMER_UEV_PROCESSOR_MAINFRAME.asStack(),
                    "   ",
                    "AB ",
                    "   ",
                    'A', GLIMMER_UHV_PROCESSOR_MAINFRAME,
                    'B', new UnificationEntry(TagPrefix.gem, RTTGTMaterials.DeepvioletGleam));
            VanillaRecipeHelper.addShapedRecipe(provider, true, "glimmer_uiv_processor_mainfram",
                    GLIMMER_UIV_PROCESSOR_MAINFRAME.asStack(),
                    "   ",
                    "AB ",
                    "   ",
                    'A', GLIMMER_UEV_PROCESSOR_MAINFRAME,
                    'B', new UnificationEntry(TagPrefix.gem, RTTGTMaterials.DeepvioletGleam));
            VanillaRecipeHelper.addShapedRecipe(provider, true, "glimmer_uxv_processor_mainfram",
                    GLIMMER_UXV_PROCESSOR_MAINFRAME.asStack(),
                    "   ",
                    "AB ",
                    "   ",
                    'A', GLIMMER_UIV_PROCESSOR_MAINFRAME,
                    'B', new UnificationEntry(TagPrefix.gem, RTTGTMaterials.DeepvioletGleam));
            VanillaRecipeHelper.addShapedRecipe(provider, true, "glimmer_opv_processor_mainfram",
                    GLIMMER_OpV_PROCESSOR_MAINFRAME.asStack(),
                    "   ",
                    "AB ",
                    "   ",
                    'A', GLIMMER_UXV_PROCESSOR_MAINFRAME,
                    'B', new UnificationEntry(TagPrefix.gem, RTTGTMaterials.DeepvioletGleam));
            VanillaRecipeHelper.addShapedRecipe(provider, true, "glimmer_max_processor_mainfram",
                    GLIMMER_MAX_PROCESSOR_MAINFRAME.asStack(),
                    "   ",
                    "AB ",
                    "   ",
                    'A', GLIMMER_OpV_PROCESSOR_MAINFRAME,
                    'B', new UnificationEntry(TagPrefix.gem, RTTGTMaterials.DeepvioletGleam));

            LARGE_SHAPE_WORLD_VOID_PUMP.recipeBuilder("deepvioletgleam_1")
                    .circuitMeta(2)
                    .outputFluids(RTTGTMaterials.DeepvioletGleam.getFluid(1296))
                    .duration(500)
                    .EUt(GTValues.VA[GTValues.LuV])
                    .save(provider);

        }
        if (RTTGTConfigHolder.INSTANCE.EnableTheGeneralSteamEngine) {
            VanillaRecipeHelper.addShapedRecipe(provider, true, "the_general_steam_engine",
                    RTTGTMachines.THE_GENERAL_STEAM_ENGINE.asStack(),
                    "ABA",
                    "BCB",
                    "ABA",
                    'A', GetRegistries.getItem("gtceu:steam_machine_casing"),
                    'B', GetRegistries.getItem("rttgtcore:void_world_block"),
                    'C', GetRegistries.getItem("kubejs:precision_steam_mechanism"));
        }
        if (RTTGTConfigHolder.INSTANCE.EnableFreeAe) {

            VanillaRecipeHelper.addShapedRecipe(provider, true, "general_ae_production",
                    RTTGTMachines.GENERAL_AE_PRODUCTION.asStack(),
                    "AAA",
                    "ABA",
                    "AAA",
                    'A', GetRegistries.getItem("ae2:sky_stone_block"),
                    'B', CustomTags.LV_CIRCUITS);

            EXTRACTOR_RECIPES.recipeBuilder("rtt_fluid_fluix")
                    .inputItems(Registries.getItem("ae2:fluix_dust"))
                    .outputFluids(RTTGTMaterials.FluixCrystal.getFluid(144))
                    .duration(200)
                    .EUt(GTValues.V[GTValues.LV])
                    .save(provider);

            GENERAL_AE_PRODUCTION.recipeBuilder("rtt_ae2_fluix_cable_1")
                    .circuitMeta(1)
                    .notConsumable(Registries.getItem("ae2:quartz_fiber"))
                    .inputFluids(RTTGTMaterials.FluixCrystal.getFluid(144))
                    .outputItems(Registries.getItem("ae2:fluix_glass_cable"), 32)
                    .duration(512)
                    .EUt(GTValues.V[GTValues.LV])
                    .save(provider);

            GENERAL_AE_PRODUCTION.recipeBuilder("rtt_ae2_fluix_cable_2")
                    .circuitMeta(2)
                    .notConsumable(Registries.getItem("ae2:quartz_fiber"))
                    .inputFluids(RTTGTMaterials.FluixCrystal.getFluid(144))
                    .outputItems(Registries.getItem("ae2:fluix_covered_cable"), 32)
                    .duration(512)
                    .EUt(GTValues.V[GTValues.LV])
                    .save(provider);

            GENERAL_AE_PRODUCTION.recipeBuilder("rtt_ae2_fluix_cable_3")
                    .circuitMeta(3)
                    .notConsumable(Registries.getItem("ae2:quartz_fiber"))
                    .inputFluids(RTTGTMaterials.FluixCrystal.getFluid(144))
                    .outputItems(Registries.getItem("ae2:fluix_smart_cable"), 32)
                    .duration(512)
                    .EUt(GTValues.V[GTValues.LV])
                    .save(provider);

            GENERAL_AE_PRODUCTION.recipeBuilder("rtt_ae2_fluix_cable_4")
                    .circuitMeta(4)
                    .notConsumable(Registries.getItem("ae2:quartz_fiber"))
                    .inputFluids(RTTGTMaterials.FluixCrystal.getFluid(144))
                    .outputItems(Registries.getItem("ae2:fluix_covered_dense_cable"), 32)
                    .duration(512)
                    .EUt(GTValues.V[GTValues.LV])
                    .save(provider);

            GENERAL_AE_PRODUCTION.recipeBuilder("rtt_ae2_fluix_cable_5")
                    .circuitMeta(5)
                    .notConsumable(Registries.getItem("ae2:quartz_fiber"))
                    .inputFluids(RTTGTMaterials.FluixCrystal.getFluid(144))
                    .outputItems(Registries.getItem("ae2:fluix_smart_dense_cable"), 32)
                    .duration(512)
                    .EUt(GTValues.V[GTValues.LV])
                    .save(provider);

            GENERAL_AE_PRODUCTION.recipeBuilder("rtt_ae2_blank_pattern")
                    .circuitMeta(1)
                    .notConsumable(Registries.getItem("ae2:blank_pattern"))
                    .inputFluids(RTTGTMaterials.FluixCrystal.getFluid(144))
                    .outputItems(Registries.getItem("ae2:blank_pattern"), 16)
                    .duration(512)
                    .EUt(GTValues.V[GTValues.LV])
                    .save(provider);
        }
    }
}
