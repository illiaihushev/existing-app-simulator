package app.repo;

import java.util.HashMap;
import java.util.Map;

public final class PositionsStore {
    public static byte EMP = 1;
    public static byte DM = 2;
    public static byte PMO = 3;
    public static byte LM = 4;

    private static Map<Byte, String> TITLES;

    static {
        TITLES = new HashMap<>();
        TITLES.put(EMP, "EMP");
        TITLES.put(DM, "DM");
        TITLES.put(PMO, "PMO");
        TITLES.put(LM, "LM");
    }

    public static String getName(byte positionId) {
        return TITLES.get(positionId);
    }
}
