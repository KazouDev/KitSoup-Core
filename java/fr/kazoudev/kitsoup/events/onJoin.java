package fr.kazoudev.kitsoup.events;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.player.PlayerRespawnEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Location;
import cn.nukkit.network.protocol.RespawnPacket;
import cn.nukkit.network.protocol.SetLocalPlayerAsInitializedPacket;
import cn.nukkit.scheduler.NukkitRunnable;
import cn.nukkit.utils.TextFormat;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.SoupPlayer;
import fr.kazoudev.kitsoup.managers.Language;
import fr.kazoudev.kitsoup.utils.GlobalTask;
import fr.kazoudev.kitsoup.utils.PlayerTask;

public class onJoin implements Listener {

    @EventHandler
    public void onJoin(DataPacketReceiveEvent e){
        if(e.getPacket() instanceof SetLocalPlayerAsInitializedPacket) {
            Player p = e.getPlayer();
            SoupPlayer sp = Main.getSPlayer(p);
            Main.getInstance().getServer().getScheduler().scheduleRepeatingTask(new PlayerTask(sp), 20);
            sp.setLatestPos(Main.getInstance().spawn);
            p.teleport(Main.getInstance().spawn);
            p.getInventory().clearAll();
            p.sendMessage("§a+§e");
            p.getInventory().setItem(0, Item.get(272).setCustomName(TextFormat.RED + "Kits"));
            p.getInventory().setItem(8, Item.get(356).setCustomName(TextFormat.RED + Language.SETTINGS.getLang(p)));
            p.getInventory().setItem(4, Item.get(266).setCustomName(TextFormat.GOLD + Language.SHOP.getLang(p)));
            p.getLevel().addParticle(GlobalTask.topkill, p);
        }
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if(Main.getSPlayer(e.getPlayer()) != null) {
            Main.getInstance().delPlayer(e.getPlayer());
        }
    }

    @EventHandler
    public void giveItemOnRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        new NukkitRunnable(){
            @Override
            public void run() {
                p.getInventory().clearAll();
                p.sendMessage("§aRespawn and TP");
                p.getInventory().setItem(0, Item.get(272).setCustomName("§cKits"));
                p.getInventory().setItem(8, Item.get(356).setCustomName(TextFormat.RED + Language.SETTINGS.getLang(p)));
                p.getInventory().setItem(4, Item.get(266).setCustomName(TextFormat.GOLD + Language.SHOP.getLang(p)));
                p.teleport(Main.getInstance().spawn);
            }
        }.runTaskLater(Main.getInstance(), 0);
    }
}
