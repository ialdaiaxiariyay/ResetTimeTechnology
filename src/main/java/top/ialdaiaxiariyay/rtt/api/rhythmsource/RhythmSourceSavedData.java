package top.ialdaiaxiariyay.rtt.api.rhythmsource;

import java.math.BigInteger;
import java.util.Map;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

public class RhythmSourceSavedData extends SavedData {
    public static RhythmSourceSavedData INSTANCE;
    private final ServerLevel serverLevel;

    // 单例获取方法
    public static RhythmSourceSavedData getOrCreate(ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(
                tag -> new RhythmSourceSavedData(serverLevel, tag),
                () -> new RhythmSourceSavedData(serverLevel),
                "gtmthings_rhythm_source" // 存档文件名
        );
    }

    // 构造方法（空数据初始化）
    public RhythmSourceSavedData(ServerLevel serverLevel) {
        this.serverLevel = serverLevel;
    }

    // 构造方法（从NBT加载数据）
    public RhythmSourceSavedData(ServerLevel serverLevel, CompoundTag tag) {
        this(serverLevel);
        ListTag allRhythmSource = tag.getList("allRhythmSource", 10); // 读取NBT列表

        for (int i = 0; i < allRhythmSource.size(); i++) {
            CompoundTag rpTag = allRhythmSource.getCompound(i);
            UUID teamUUID = rpTag.getUUID("uuid");
            BigInteger rhythmPoints = new BigInteger(rpTag.getString("rhythm_points").isEmpty()
                    ? "0"
                    : rpTag.getString("rhythm_points"));
            GlobalVariableStorage.GlobalRhythmSource.put(teamUUID, rhythmPoints);
        }
    }

    // 保存数据到NBT
    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag compoundTag) {
        ListTag allRhythmSource = new ListTag();

        // 遍历全局韵律源点数据
        for (Map.Entry<UUID, BigInteger> entry : GlobalVariableStorage.GlobalRhythmSource.entrySet()) {
            CompoundTag rpTag = new CompoundTag();
            rpTag.putUUID("uuid", entry.getKey());
            rpTag.putString("rhythm_points", entry.getValue().toString());
            allRhythmSource.add(rpTag);
        }

        compoundTag.put("allRhythmSource", allRhythmSource);
        return compoundTag;
    }

    /**
     * 扣除指定 RP 网络的资源
     */
    public boolean consumeRP(UUID networkUUID, long amount) {
        synchronized (GlobalVariableStorage.GlobalRhythmSource) {
            BigInteger currentRP = GlobalVariableStorage.GlobalRhythmSource.getOrDefault(networkUUID, BigInteger.ZERO);
            if (currentRP.compareTo(BigInteger.valueOf(amount)) >= 0) {
                GlobalVariableStorage.GlobalRhythmSource.put(networkUUID, currentRP.subtract(BigInteger.valueOf(amount)));
                this.setDirty(); // 标记数据需要保存
                return true;
            }
            return false;
        }
    }
}
