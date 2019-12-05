package constants;

import java.util.Arrays;
import java.util.Optional;

public enum ValueType {
    WHITE_PHYSICAL(0),
    BLUE_MODIFIED(1),
    FIRE(4),
    COLD(5),
    LIGHTNING(6),
    CHAOS(7);

    public int key;

    ValueType(int key) {
        this.key = key;
    }

    public static Optional<ValueType> getByKey(int key) {
        return Arrays.stream(ValueType.values())
                .filter(valueType -> valueType.key == key)
                .findFirst();
    }
}