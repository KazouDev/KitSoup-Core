package fr.kazoudev.kitsoup.events;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.metadata.MetadataValue;
import fr.kazoudev.kitsoup.Main;

public class boxEvent implements Listener {

    @EventHandler
    public void boxUse(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Block b = e.getBlock();

        if(Main.getInstance().loc == b.getLocation()){
            p.sendMessage("Open the box");
        }
    }

}
