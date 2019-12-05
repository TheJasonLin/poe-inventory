package constants;

import java.util.Arrays;
import java.util.Optional;

public enum Rarity {
    NORMAL(0),
    MAGIC(1),
    RARE(2),
    UNIQUE(3),
    GEM(4),
    CURRENCY(5),
    DIVINATION_CARD(6),
    QUEST_ITEM(7),
    PROPHECY(8),
    RELIC(9);

    public int key;

    Rarity(int key) {
        this.key = key;
    }

    public static Optional<Rarity> getByKey(int key) {
        return Arrays.stream(Rarity.values())
                .filter(rarity -> rarity.key == key)
                .findFirst();
    }

//    public static Optional<Rarity> getByString(String key) {
//        Rarity rarity;
//        switch (key) {
//            case "Normal":
//                rarity = NORMAL;
//                break;
//            case "Magic":
//                rarity = MAGIC;
//                break;
//            case "Rare":
//                rarity = RARE;
//                break;
//            case "Unique":
//                rarity = UNIQUE;
//                break;
//            case "Gem":
//                rarity = GEM;
//                break;
//            case "Currency":
//                rarity = CURRENCY;
//                break;
//            case "Divination Card":
//                rarity = DIVINATION_CARD;
//                break;
//            case "Quest Item":
//                rarity = QUEST_ITEM;
//                break;
//            case "Prophecy":
//                rarity = PROPHECY;
//                break;
//            case "Relic":
//                rarity = RELIC;
//                break;
//            default:
//                return Optional.empty();
//        }
//
//        return Optional.of(rarity);
//    }
}
