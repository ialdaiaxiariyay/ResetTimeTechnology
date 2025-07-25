package top.ialdaiaxiariyay.rtt.common.machines;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.ConditionalSubscriptionHandler;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

import com.hepdd.gtmthings.api.misc.WirelessEnergyManager;
import com.hepdd.gtmthings.utils.TeamUtil;
import org.gtlcore.gtlcore.api.machine.multiblock.NoEnergyMultiblockMachine;
import org.gtlcore.gtlcore.common.data.GTLMaterials;
import org.gtlcore.gtlcore.utils.MachineIO;
import org.gtlcore.gtlcore.utils.Registries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class EXHarmonyMachine extends NoEnergyMultiblockMachine {

    public static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            EXHarmonyMachine.class, NoEnergyMultiblockMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    private int oc = 0;
    @Persisted
    private long hydrogen = 0, helium = 0, spacetime;
    @Persisted
    private long SpaceTimeInput = 0;
    @Persisted
    private UUID userid;

    protected ConditionalSubscriptionHandler StartupSubs;

    public EXHarmonyMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
        this.StartupSubs = new ConditionalSubscriptionHandler(this, this::StartupUpdate, this::isFormed);
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    protected void StartupUpdate() {
        if (getOffsetTimer() % 20 == 0) {
            oc = 0;
            if (MachineIO.inputFluid(this, GTMaterials.Hydrogen.getFluid(100000000))) {
                hydrogen += 100000000;
            }
            if (MachineIO.inputFluid(this, GTMaterials.Helium.getFluid(100000000))) {
                helium += 100000000;
            }
            if (MachineIO.inputFluid(this, GTLMaterials.SpaceTime.getFluid(SpaceTimeInput))) {
                spacetime += SpaceTimeInput;
            }
            if (MachineIO.notConsumableCircuit(this, 9)) {
                oc = 9;
            }
            if (MachineIO.notConsumableCircuit(this, 8)) {
                oc = 8;
            }
            if (MachineIO.notConsumableCircuit(this, 7)) {
                oc = 7;
            }
            if (MachineIO.notConsumableCircuit(this, 6)) {
                oc = 6;
            }
            if (MachineIO.notConsumableCircuit(this, 5)) {
                oc = 5;
            }
            if (MachineIO.notConsumableCircuit(this, 4)) {
                oc = 4;
            }
            if (MachineIO.notConsumableCircuit(this, 3)) {
                oc = 3;
            }
            if (MachineIO.notConsumableCircuit(this, 2)) {
                oc = 2;
            }
            if (MachineIO.notConsumableCircuit(this, 1)) {
                oc = 1;
            }
        }
    }

    private long getStartupEnergy() {
        return oc == 0 ? 0 : (long) (5277655810867200L * Math.pow(8, oc - 1));
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        StartupSubs.initialize(getLevel());
    }

    private long outputEut() {
        if (oc == 1) return 35L * (1000L << 30);
        else if (oc == 2) return 56L * (1000L << 30);
        else if (oc == 3) return 89L * (1000L << 30);
        else if (oc == 4) return 139L * (1000L << 30);
        else if (oc == 5) return 214L * (1000L << 30);
        else if (oc == 6) return 327L * (1000L << 30);
        else if (oc == 7) return 495L * (1000L << 30);
        else if (oc == 8) return 745L * (1000L << 30);
        else if (oc == 9) return 1100000000000000000L;
        return 0;
    }

    @Nullable
    public static GTRecipe recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe, @NotNull OCParams params,
                                          @NotNull OCResult result) {
        if (machine instanceof EXHarmonyMachine harmonyMachine && harmonyMachine.userid != null && harmonyMachine.hydrogen >= 1024000000 && harmonyMachine.helium >= 1024000000 && harmonyMachine.oc > 0) {
            if (harmonyMachine.checkRecipeSuccess()) {
                harmonyMachine.hydrogen -= 1024000000;
                harmonyMachine.helium -= 1024000000;
                harmonyMachine.spacetime -= harmonyMachine.SpaceTimeInput;
                if (WirelessEnergyManager.addEUToGlobalEnergyMap(harmonyMachine.userid, -harmonyMachine.getStartupEnergy(), machine)) {
                    GTRecipe recipe1 = recipe.copy();
                    recipe1.duration = (int) (4800 / Math.pow(2, harmonyMachine.oc));
                    WirelessEnergyManager.addEUToGlobalEnergyMap(harmonyMachine.userid, harmonyMachine.outputEut(), machine);
                    return recipe1;
                }
            } else {
                WirelessEnergyManager.addEUToGlobalEnergyMap(harmonyMachine.userid, harmonyMachine.getStartupEnergy() / 2,
                        machine);
            }
        }
        return null;
    }

    private static boolean OutputEU(WorkableMultiblockMachine machine, long eu) {
        GTRecipe recipe = (new GTRecipeBuilder(GTCEu.id(String.valueOf(eu)), GTRecipeTypes.DUMMY_RECIPES)).outputEU(eu).buildRawRecipe();
        return recipe.matchRecipe(machine).isSuccess() && recipe.handleRecipeIO(IO.IN, machine, machine.recipeLogic.getChanceCaches());
    }

    @Override
    public boolean shouldOpenUI(Player player, InteractionHand hand, BlockHitResult hit) {
        if (this.userid == null || !this.userid.equals(player.getUUID())) {
            this.userid = player.getUUID();
        }
        return true;
    }

    private static final int MIN_RANDOM = 1;
    private static final int MAX_RANDOM = 20000;

    /**
     * 检查配方是否成功
     * 当前OC(超频)等级 (1-9)
     * 
     * @return 配方是否成功
     */
    public boolean checkRecipeSuccess() {
        int baseRandom = generateBaseRandom();
        long requiredFluid = calculateRequiredFluid(baseRandom, oc);
        SpaceTimeInput = requiredFluid;
        return SpaceTimeInput >= requiredFluid;
    }

    /**
     * 生成基础随机数
     */
    private static int generateBaseRandom() {
        Random rand = new Random();
        return MIN_RANDOM + rand.nextInt(MAX_RANDOM - MIN_RANDOM + 1);
    }

    /**
     * 计算需要的流体阈值
     */
    private long calculateRequiredFluid(int baseRandom, int ocLevel) {
        // 基础计算: b = ocLevel * (random / 4)
        long threshold = (long) ocLevel * (baseRandom / 4);
        if (InoutItem()) {
            threshold /= 2;
        }
        return threshold;
    }

    private boolean InoutItem() {
        return MachineIO.inputItem(this, Registries.getItemStack("kubejs:eternity_catalyst", 64));
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        if (this.isFormed()) {
            if (userid != null) {
                textList.add(Component.translatable("gtmthings.machine.wireless_energy_monitor.tooltip.0",
                        TeamUtil.GetName(getLevel(), userid)));
                textList.add(Component.translatable("gtmthings.machine.wireless_energy_monitor.tooltip.1",
                        FormattingUtil.formatNumbers(WirelessEnergyManager.getUserEU(userid))));
            }
            textList.add(Component.literal("启动耗能：" + FormattingUtil.formatNumbers(getStartupEnergy()) + "EU"));
            textList.add(Component.literal("氢储量：" + FormattingUtil.formatNumbers(hydrogen) + "mb"));
            textList.add(Component.literal("氦储量：" + FormattingUtil.formatNumbers(helium) + "mb"));
        }
    }
}
