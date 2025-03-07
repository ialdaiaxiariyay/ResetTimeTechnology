package top.ialdaiaxiariyay.rttgtcore.common.machines.recipes;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static com.lowdragmc.lowdraglib.gui.texture.ProgressTexture.FillDirection.LEFT_TO_RIGHT;
import static top.ialdaiaxiariyay.rttgtcore.api.registries.RTTGTRecipeTypes.rttgtregister;

public class RTTGTRecipeTypes {

    public static void init() {}

    public final static GTRecipeType LARGE_SHAPE_WORLD_VOID_PUMP = rttgtregister("large_shape_world_void_pump", MULTIBLOCK)
            .setMaxIOSize(1, 0, 0, 1)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MOTOR);

    public final static GTRecipeType Compressed_Block_Transmutation_Chamber = rttgtregister("compressed_block_transmutation_chamber", MULTIBLOCK)
            .setMaxIOSize(2, 1, 0, 0)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MOTOR);

    public final static GTRecipeType THERMOMAGNETIC_COOLING_GENERATOR = rttgtregister("thermomagnetic_cooling_generator", MULTIBLOCK)
            .setMaxIOSize(2, 0, 0, 0)
            .setEUIO(IO.OUT)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MOTOR);

    public final static GTRecipeType FIGURE_FACTORY = rttgtregister("figure_factory", MULTIBLOCK)
            .setMaxIOSize(24, 1, 12, 0)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MOTOR);

    public final static GTRecipeType GENERAL_AE_PRODUCTION = rttgtregister("general_ae_production", MULTIBLOCK)
            .setMaxIOSize(10, 1, 10, 0)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MOTOR);
}
