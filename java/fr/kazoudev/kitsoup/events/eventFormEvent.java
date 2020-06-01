package fr.kazoudev.kitsoup.events;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowSimple;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.hostevent.EventManager;
import fr.kazoudev.kitsoup.hostevent.EventType;


public class eventFormEvent implements Listener {

    @EventHandler
    public void eventFormResponded(PlayerFormRespondedEvent e){
        Player p = e.getPlayer();
        if(e.getWindow() instanceof FormWindowSimple){
            if(e.getResponse() != null){
            FormWindowSimple window = (FormWindowSimple) e.getWindow();
            if(window.getTitle().equalsIgnoreCase("§cEvents")) {
                String[] button = ((FormResponseSimple) e.getResponse()).getClickedButton().getText().split(" ");
                if (EventManager.eventExist(button[0].toUpperCase())) {
                    Main.getInstance().event = new EventManager(EventType.valueOf(button[0].toUpperCase()), "Console", null);
                } else p.sendMessage("§cInternal Error !");
              }
            }
        }
    }
}
