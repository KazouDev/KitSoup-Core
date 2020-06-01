package fr.kazoudev.kitsoup.events;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.level.WeatherChangeEvent;

public class weatherEvent implements Listener {

    @EventHandler
    public void onWeatherChang(WeatherChangeEvent e){
        e.setCancelled(true);
    }
}
