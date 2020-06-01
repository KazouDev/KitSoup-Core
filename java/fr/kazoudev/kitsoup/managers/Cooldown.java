package fr.kazoudev.kitsoup.managers;

import cn.nukkit.Player;
import fr.kazoudev.kitsoup.Main;

public enum Cooldown {
    GHAST(10),
    HULK(10),
    NINJA(10),
    FRREZER(10),
    PTIMECMD(30),
    CHAT(5),
    NICK_CMD(600),
    FALLDAMAGE(5);

    private int time;

    Cooldown(int time){
        this.time = time;
    }

    public int getTime(){
        return this.time;
    }

    public void setTime(int time){this.time = time;}
}
