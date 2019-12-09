package maps;

public enum MapCurrency {
    SCROLL_OF_WISDOM("Scroll of Wisdom"),
    ORB_OF_SCOURING("Orb of Scouring"),
    CARTOGRAPHERS_CHISEL("Cartographer's Chisel"),
    ORB_OF_TRANSMUTATION("Orb of Transmutation"),
    ORB_OF_ALCHEMY("Orb of Alchemy"),
    CHAOS_ORB("Chaos Orb"),
    ORB_OF_ALTERATION("Orb of Alteration"),
    ORB_OF_AUGMENTATION("Orb of Augmentation");

    private String name;

    MapCurrency(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
