package fr.kazoudev.kitsoup.hostevent;

import cn.nukkit.scheduler.NukkitRunnable;
import fr.kazoudev.kitsoup.rank.Rank;

public enum EventType {
    KOTH("Koth", Rank.LEGEND),
    TOTEM("Totem", Rank.LEGEND),
    private String name;
    private Rank rank;

    EventType(String name, Rank rank){
        this.name = name;
        this.rank = rank;
    }

    public String getName() {return name;}
    public Rank getRank(){return rank;}
}
