package fr.kazoudev.kitsoup.rank;

public enum Rank{
    PLAYER("PLAYER", "§7name prefix §7> msg", "§7Player", 0),
    PRO("PRO", "§9[Pro]§f name prefix §7>§f msg", "§7Player", 1),
    LEGEND("LEGEND", "§5[Legend]§f name prefix §7>§f msg", "§7Player", 2),
    YT("YT", "§4Y§fT name prefix §4>§f msg", "§7Player", 3),
    HELPER("HELPER", "§6[Helper]§f name prefix §7>§a msg", "§7Player", 4),
    MOD("MOD", "§3[Mod]§f name prefix §7>§6 msg", "§7Player", 5),
    ADMIN("ADMIN", "§4[§cAdmin§4] §cname prefix §7>§c msg", "§cAdmin", 6);

    public String colorName;
    public String name;
    public String chatFormat;
    public int value;

    Rank(String name, String chatFormat, String colorName, int value){
        this.name = name;
        this.chatFormat = chatFormat;
        this.colorName = colorName;
        this.value = value;
    }

    public String getChatFormat() {
        return chatFormat;
    }

    public String getName() {
        return name;
    }

    public String getColorName() {
        return colorName;
    }

    public boolean isInferior(Rank rank){return this.value <= rank.value;}

}
