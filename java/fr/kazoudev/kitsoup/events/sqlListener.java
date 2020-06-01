package fr.kazoudev.kitsoup.events;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerPreLoginEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.level.Location;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.managers.MySQL;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class sqlListener implements Listener {

    @EventHandler
    public void onJoin(PlayerPreLoginEvent e) throws Exception{
        if(MySQL.isBanned(e.getPlayer().getName())){

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Europe/Paris")));
            Date end = dateFormat.parse(MySQL.getBanEnd(e.getPlayer().getName()));
            if(end.before(dateFormat.parse(dateFormat.format(Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris")).getTime())))){
                MySQL.remBan(e.getPlayer().getName());
            } else {
                String endstring = dateFormat.format(end);
                e.getPlayer().close("", "§cYou are now banned from this server for §e" + MySQL.getBanReason(e.getPlayer().getName()) + "you are unbanned the §e" +  endstring.replace(" ", " §cat§e "));
            }
        }
        if(MySQL.isPlayer(e.getPlayer().getUniqueId().toString()) != 1){
            MySQL.createPlayerProfile(e.getPlayer());
            Main.getInstance().getLogger().info("Création d'un nouvelle utilisateur.");
            Main.getInstance().newPlayer(e.getPlayer());
        } else {
            Main.getInstance().newPlayer(e.getPlayer());
        }
    }
}
