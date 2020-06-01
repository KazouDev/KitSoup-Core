package fr.kazoudev.kitsoup.utils;

import cn.nukkit.Server;
import cn.nukkit.scheduler.PluginTask;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.hostevent.EventManager;
import fr.kazoudev.kitsoup.hostevent.EventType;
import sun.plugin2.main.server.Plugin;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EventTask extends PluginTask<Main> {
    public EventTask(Main owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Europe/Paris"));

        System.out.println(zdt.getDayOfWeek()  + " " + zdt.getHour() + " " + zdt.getMinute());
            if(zdt.getHour() > 12 && zdt.getHour() < 24 && zdt.getMinute() == 0){
                if(Main.getInstance().event == null && Main.getInstance().eCooldown(5)) {
                    Server.getInstance().broadcastMessage("§bLancement d'un event Automatique.");
                    Main.getInstance().event = new EventManager(EventType.KOTH, "Console", "§bLancement du KOTH dans [time]s.");
                } else {
                    Server.getInstance().broadcastMessage("§cUn event est déjà en cours (ou, a eu lieu il y as peu de temps).\nEvent Automatique dans 1H.");
                }
            }
        }
}
