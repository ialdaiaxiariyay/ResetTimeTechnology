package top.ialdaiaxiariyay.rtt.api.rhythmsource;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.hepdd.gtmthings.utils.TeamUtil;
import com.mojang.datafixers.util.Pair;

import java.math.BigInteger;
import java.util.UUID;
import java.util.WeakHashMap;

public class RhythmSourceManager {
    // 存储玩家与机器关联的临时韵律源点数据（WeakHashMap 自动清理无引用对象）
    public static WeakHashMap<Pair<UUID, MetaMachine>, Long> MachineData = new WeakHashMap<>();

    // 初始化方法（空构造器）
    public RhythmSourceManager() {}

    // 确保团队存在初始韵律源点记录
    public static void strongCheckOrAddUser(UUID user_uuid) {
        if (!GlobalVariableStorage.GlobalRhythmSource.containsKey(user_uuid)) {
            GlobalVariableStorage.GlobalRhythmSource.put(user_uuid, BigInteger.ZERO);
        }
    }

    // 向团队全局韵律源池添加韵律源点（支持 BigInteger）
    public static boolean addRPToGlobalSourceMap(UUID user_uuid, BigInteger RP, MetaMachine machine) {
        try {
            RhythmSourceSavedData.INSTANCE.setDirty(true); // 标记数据需要保存
        } catch (Exception e) {
            System.err.println("无法标记韵律源点数据为脏状态（添加操作）");
            e.printStackTrace();
        }

        UUID teamUUID = TeamUtil.getTeamUUID(user_uuid);
        MachineData.put(Pair.of(user_uuid, machine), RP.longValue()); // 记录机器临时数据

        BigInteger totalRP = GlobalVariableStorage.GlobalRhythmSource.getOrDefault(teamUUID, BigInteger.ZERO);
        if (totalRP.signum() < 0) { // 防止负数
            totalRP = BigInteger.ZERO;
            GlobalVariableStorage.GlobalRhythmSource.put(teamUUID, totalRP);
        }

        totalRP = totalRP.add(RP);
        if (totalRP.signum() >= 0) { // 韵律源点不允许为负
            GlobalVariableStorage.GlobalRhythmSource.put(teamUUID, totalRP);
            return true;
        } else {
            return false;
        }
    }

    // 方法重载：支持 long 类型输入
    public static boolean addRPToGlobalSourceMap(UUID user_uuid, long RP, MetaMachine machine) {
        return addRPToGlobalSourceMap(user_uuid, BigInteger.valueOf(RP), machine);
    }

    // 方法重载：支持 int 类型输入
    public static boolean addRPToGlobalSourceMap(UUID user_uuid, int RP, MetaMachine machine) {
        return addRPToGlobalSourceMap(user_uuid, BigInteger.valueOf(RP), machine);
    }

    // 获取团队的当前韵律源点总量
    public static BigInteger getTeamRP(UUID user_uuid) {
        UUID teamUUID = TeamUtil.getTeamUUID(user_uuid);
        BigInteger totalRP = GlobalVariableStorage.GlobalRhythmSource.getOrDefault(teamUUID, BigInteger.ZERO);
        if (totalRP.signum() < 0) { // 自动修复负数
            RhythmSourceSavedData.INSTANCE.setDirty(true);
            totalRP = BigInteger.ZERO;
            GlobalVariableStorage.GlobalRhythmSource.put(teamUUID, totalRP);
        }
        return totalRP;
    }

    // 直接设置团队的韵律源点总量
    public static void setTeamRP(UUID user_uuid, BigInteger RP) {
        try {
            RhythmSourceSavedData.INSTANCE.setDirty(true);
        } catch (Exception e) {
            System.err.println("无法标记韵律源点数据为脏状态（设置操作）");
            e.printStackTrace();
        }
        GlobalVariableStorage.GlobalRhythmSource.put(TeamUtil.getTeamUUID(user_uuid), RP);
    }

    // 清空全局韵律源点数据（调试或重置用）
    public static void clearGlobalSourceMaps() {
        GlobalVariableStorage.GlobalRhythmSource.clear();
    }

}
