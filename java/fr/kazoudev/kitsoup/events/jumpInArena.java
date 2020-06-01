package fr.kazoudev.kitsoup.events;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageEvent;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.managers.Cooldown;

import javax.annotation.processing.SupportedSourceVersion;
import java.util.List;

public class jumpInArena implements Listener {

    @EventHandler
    public void noFallEnterArena(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            if(e.getCause() == EntityDamageEvent.DamageCause.FALL){
                if (Main.getSPlayer((Player) e.getEntity()).isCooldown(Cooldown.FALLDAMAGE)) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
