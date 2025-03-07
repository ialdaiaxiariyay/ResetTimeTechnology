package top.ialdaiaxiariyay.rtt.mixin.gto;

import top.ialdaiaxiariyay.rtt.RTT;

import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import com.lowdragmc.lowdraglib.gui.factory.HeldItemUIFactory;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.loading.FMLPaths;

import com.google.common.base.Joiner;
import com.gto.gtocore.api.pattern.DebugBlockPattern;
import com.gto.gtocore.common.data.GTOBlocks;
import com.gto.gtocore.common.item.StructureWriteBehavior;
import com.gto.gtocore.utils.ItemUtils;
import com.gto.gtocore.utils.RegistriesUtils;
import com.gto.gtocore.utils.StringIndex;
import com.gto.gtocore.utils.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.gto.gtocore.common.item.StructureWriteBehavior.getPos;

@Mixin({ StructureWriteBehavior.class })
public class StructureWriteBehaviorMixin {

    public StructureWriteBehaviorMixin() {}

    @Inject(
            method = { "exportLog" },
            at = { @At("HEAD") },
            remap = false,
            cancellable = true)

    private static void exportLog(HeldItemUIFactory.HeldItemHolder playerInventoryHolder, CallbackInfo ci) {
        if (getPos(playerInventoryHolder.getHeld()) != null) {
            Player var2 = playerInventoryHolder.getPlayer();
            if (var2 instanceof ServerPlayer player) {
                ItemStack itemStack = playerInventoryHolder.getHeld();
                String part = itemStack.getOrCreateTag().getString("part");
                if (part.isEmpty()) {
                    player.displayClientMessage(Component.literal("未绑定仓室方块"), false);
                    return;
                }

                BlockPos[] blockPos = getPos(playerInventoryHolder.getHeld());
                Direction direction = rtt_gto$getDir(playerInventoryHolder.getHeld());
                StringBuilder builder = new StringBuilder();
                DebugBlockPattern blockPattern = new DebugBlockPattern(playerInventoryHolder.getPlayer().level(), blockPos[0].getX(), blockPos[0].getY(), blockPos[0].getZ(), blockPos[1].getX(), blockPos[1].getY(), blockPos[1].getZ());
                RelativeDirection[] dirs = DebugBlockPattern.getDir(direction);
                blockPattern.changeDir(dirs[0], dirs[1], dirs[2]);
                builder.append("\n.block(").append(rtt_gto$convertBlockToString(RegistriesUtils.getBlock(part), part, StringUtils.decompose(part), true)).append(")\n");
                builder.append(".pattern(definition -> FactoryBlockPattern.start()\n");

                for (int i = 0; i < blockPattern.pattern.length; ++i) {
                    String[] strings = blockPattern.pattern[i];
                    builder.append(".aisle(\"%s\")\n".formatted(Joiner.on("\", \"").join(strings)));
                }

                blockPattern.legend.forEach((b, c) -> {
                    if (!c.equals(' ')) {
                        if (b == Blocks.OAK_LOG) {
                            builder.append(".where('").append(c).append("', controller(blocks(definition.get())))\n");
                        } else if (b == Blocks.DIRT) {
                            builder.append(".where('").append(c).append("', heatingCoils())\n");
                        } else if (b == Blocks.WHITE_WOOL) {
                            builder.append(".where('").append(c).append("', air())\n");
                        } else if (b == Blocks.COBBLESTONE) {
                            builder.append(".where('").append(c).append("', blocks(").append(rtt_gto$convertBlockToString(RegistriesUtils.getBlock(part), part, StringUtils.decompose(part), false)).append(")\n").append(itemStack.getOrCreateTag().getBoolean("laser") ? ".or(GTOPredicates.autoLaserAbilities(definition.getRecipeTypes()))\n.or(abilities(MAINTENANCE).setExactLimit(1)))\n" : ".or(autoAbilities(definition.getRecipeTypes()))\n.or(abilities(MAINTENANCE).setExactLimit(1)))\n");
                        } else if (b == GTOBlocks.ABS_WHITE_CASING.get()) {
                            builder.append(".where('").append(c).append("', GTOPredicates.absBlocks())\n");
                        } else {
                            String id = ItemUtils.getId(b);
                            String[] parts = StringUtils.decompose(id);
                            boolean isGT = Objects.equals(parts[0], "gtceu");
                            boolean isGTO = Objects.equals(parts[0], "gtocore");
                            if ((isGT || isGTO) && parts[1].contains("_frame")) {
                                builder.append(".where('").append(c).append("', blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, ").append(isGT ? "GTMaterials." : "GTPMaterials.").append(FormattingUtil.lowerUnderscoreToUpperCamel(StringUtils.lastDecompose('_', parts[1])[0])).append(")))\n");
                            } else {
                                builder.append(".where('").append(c).append("', blocks(").append(rtt_gto$convertBlockToString(b, id, parts, false)).append("))\n");
                            }
                        }
                    }
                });
                if (blockPattern.hasAir) {
                    builder.append(".where(' ', any())\n");
                }
                builder.append(".build())\n");
                // 获取当前日期时间
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
                String fileName = now.format(formatter) + ".stf";
                // 创建文件路径
                File logDir = new File(FMLPaths.GAMEDIR.get().toFile(), "logs/rtt");
                if (!logDir.exists()) {
                    logDir.mkdirs();
                }
                File logFile = new File(logDir, fileName);
                // 写入文件
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile))) {
                    writer.write(builder.toString());
                } catch (IOException e) {
                    RTT.LOGGER.error("Error writing to log file: {}", e.getMessage());
                }
                ci.cancel();
            }
        }
    }

    @Unique
    private static Direction rtt_gto$getDir(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTagElement("structure_writer");
        return !tag.contains("dir") ? Direction.WEST : Direction.byName(tag.getString("dir"));
    }

    @Unique
    private static String rtt_gto$convertBlockToString(Block b, String id, String[] parts, boolean supplier) {
        String var10000;
        if (StringIndex.BLOCK_LINK_MAP.containsKey(b)) {
            var10000 = StringIndex.BLOCK_LINK_MAP.get(b);
            return var10000 + (supplier ? "" : ".get()");
        } else if (Objects.equals(parts[0], "gtocore")) {
            var10000 = parts[1].toUpperCase();
            return "GTOBlocks." + var10000 + (supplier ? "" : ".get()");
        } else {
            return Objects.equals(parts[0], "minecraft") ? (supplier ? "() -> " : "") + "Blocks." + parts[1].toUpperCase() : "RegistriesUtils.get" + (supplier ? "Supplier" : "") + "Block(\"" + id + "\")";
        }
    }
}
