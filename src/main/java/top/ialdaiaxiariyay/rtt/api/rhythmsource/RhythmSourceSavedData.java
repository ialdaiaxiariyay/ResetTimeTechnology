package top.ialdaiaxiariyay.rtt.api.rhythmsource;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Map;
import java.util.UUID;

@Getter
public class RhythmSourceSavedData extends SavedData {

    public static RhythmSourceSavedData INSTANCE;
    private final ServerLevel serverLevel;

    public static RhythmSourceSavedData getOrCreate(ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(
                tag -> new RhythmSourceSavedData(serverLevel, tag),
                () -> new RhythmSourceSavedData(serverLevel),
                "rtt_rhythm_source");
    }

    public RhythmSourceSavedData(ServerLevel serverLevel) {
        this.serverLevel = serverLevel;
    }

    public RhythmSourceSavedData(ServerLevel serverLevel, CompoundTag tag) {
        this(serverLevel);
        ListTag allRhythmSource = tag.getList("allRhythmSource", 10);
        for (int i = 0; i < allRhythmSource.size(); i++) {
            CompoundTag rpTag = allRhythmSource.getCompound(i);
            if (!rpTag.hasUUID("uuid")) {
                continue;
            }
            UUID teamUUID = rpTag.getUUID("uuid");
            String pointsStr = rpTag.getString("rhythm_points");
            if (!pointsStr.isEmpty()) {
                GlobalVariableStorage.GlobalRhythmSource.put(teamUUID, new BigInteger(pointsStr));
            }
        }
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag compoundTag) {
        ListTag allRhythmSource = new ListTag();
        for (Map.Entry<UUID, BigInteger> entry : GlobalVariableStorage.GlobalRhythmSource.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                continue;
            }
            CompoundTag rpTag = new CompoundTag();
            rpTag.putUUID("uuid", entry.getKey());
            rpTag.putString("rhythm_points", entry.getValue().toString());
            allRhythmSource.add(rpTag);
        }
        compoundTag.put("allRhythmSource", allRhythmSource);
        return compoundTag;
    }
}
