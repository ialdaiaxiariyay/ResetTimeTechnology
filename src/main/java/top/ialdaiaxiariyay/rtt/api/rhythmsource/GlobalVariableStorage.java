package top.ialdaiaxiariyay.rtt.api.rhythmsource;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.UUID;

public class GlobalVariableStorage {

    public static HashMap<UUID, BigInteger> GlobalRhythmSource = new HashMap<>(100, 0.9F);

    public GlobalVariableStorage() {}
}
