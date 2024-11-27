package classes.enums;

import static java.time.chrono.JapaneseEra.values;

public enum Level {

    LOW(1, "Low"),
    MEDIUM(2, "Medium"),
    HIGH(3, "High");

    private final int VALUE;
    private String levelName;

    private Level(int VALUE, String levelName) {
        this.VALUE = VALUE;
        this.levelName = levelName;
    }

    public int getVALUE() {
        return VALUE;
    }

    public String getLevelName() {
        return levelName;
    }

    public static Level findByValue(int value) {
        for (Level level : values()) {
            if (level.getVALUE() == value) {
                return level;
            }
        }
        return null;
    }
}
