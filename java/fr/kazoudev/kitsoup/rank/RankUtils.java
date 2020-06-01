package fr.kazoudev.kitsoup.rank;

import cn.nukkit.Player;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.kits.KitEnum;
import fr.kazoudev.kitsoup.managers.MySQL;

public class RankUtils {
    public static void updateRank(Player p, Rank rank){
        if(Main.getSPlayer(p).getRank() != null){
            Main.getSPlayer(p).setRank(rank.name);
            MySQL.updatePlayerRank(p, rank.name);
        }
    }

    public static Rank getRank(Player p){
        return Rank.valueOf(Main.getSPlayer(p).getRank());
    }

    public static boolean rankExist(String string){
        try {
            Enum.valueOf(Rank.class, string);
            return true;
        } catch (IllegalArgumentException ex){
            return false;
        }
    }
}
