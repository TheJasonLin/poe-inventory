package constants;

import java.util.Arrays;
import java.util.Optional;

public enum StashType {
    NORMAL("NormalStash"),
    PREMIUM("PremiumStash"),
    QUAD("QuadStash"),
    ESSENCE("EssenceStash"),
    CURRENCY("CurrencyStash"),
    DIVINATION("DivinationStash");

    public String key;

    StashType(String key) {
        this.key = key;
    }

    public static Optional<StashType> getByKey(String key) {
        return Arrays.stream(StashType.values())
                .filter(stashType -> stashType.key.equals(key))
                .findFirst();
    }
}
