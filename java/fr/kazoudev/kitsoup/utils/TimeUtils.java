package fr.kazoudev.kitsoup.utils;

public enum TimeUtils {
    MIN("min", "Minute(s)", 60),
    HOUR("h", "Hour(s)", 3600),
    DAY("d", "Day(s)", 86400),
    WEEK("w", "Week(s)", 604800),
    MONTH("mon", "Month(s)", 2592000),
    YEARS("y", "Year(s)", 12 * 2592000);

    public String shortcut;
    public String name;
    public int inSecondes;
    TimeUtils(String shortcut, String name, int inSeconds){
        this.shortcut = shortcut;
        this.name = name;
        this.inSecondes = inSeconds;
    }

    public String getName() {
        return name;
    }

    public String getShortcut() {
        return shortcut;
    }

    public int getInSecondes() {
        return inSecondes;
    }
}
