package fr.kazoudev.kitsoup.events;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerDropItemEvent;
import cn.nukkit.item.Item;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class noDrop implements Listener {
    List<Integer> kitItems = Arrays.asList(281, 39, 40, 282);

    @EventHandler
    public void cancelDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        if(p.getPosition().getY() > 176){
            e.setCancelled(true);
        }
        if(!kitItems.contains(e.getItem().getId())){
            e.setCancelled(true);
            p.sendActionBar("§cImpossible.", 20, 40, 20);
        }
    }

    @EventHandler
    public void noDeathDrop(PlayerDeathEvent e){
        e.setDrops(new Item[0]);
    }
}
