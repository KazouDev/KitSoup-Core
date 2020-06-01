package fr.kazoudev.kitsoup.utils;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Location;
import cn.nukkit.level.particle.FloatingTextParticle;
import cn.nukkit.scheduler.PluginTask;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.managers.MySQL;
import sun.plugin2.main.server.Plugin;

import java.util.Random;
import java.util.UUID;

public class GlobalTask extends PluginTask<Main> {
    public static FloatingTextParticle topkill = new FloatingTextParticle(new Location(155, 110, 194), "§cTopKill of Season");
    private final String[] messageBroadcast = {
            "You can buy kit with the coins !",
            "For support the server, buy a rank on exodus.buycraft.fr",
            "Join the discord for all news",
            "All week there are a soup tournament",
            "Break mushroom and wood logs for refill"
    };

    public GlobalTask() {
        super(Main.getInstance());
    }

    @Override
    public void onRun(int i) {
        /*
        Broadcast
         */
        Random rand = new Random();
        Server.getInstance().broadcastMessage("§e[§cBroadcast§e]§e " + messageBroadcast[rand.nextInt(messageBroadcast.length)]);

        /*
        TopKill Update
         */
        int place = 0;
        String topParticle = "";
        for(String uid : MySQL.getTopKill()){
            if(place <= 10) {
                place++;
                if(Main.getInstance().getServer().getOfflinePlayer(UUID.fromString(uid)) != null) {
                    Main.getInstance().getServer().broadcastMessage(Main.getInstance().getServer().getOfflinePlayer(UUID.fromString(uid)).getName() + " #" + place + " " + MySQL.getPlayerKill(uid));
                    topParticle = topParticle + "\n"+ Main.getInstance().getServer().getOfflinePlayer(UUID.fromString(uid)).getName() + " #" + place + " " + MySQL.getPlayerKill(uid);
                } else {
                    topParticle = topParticle + "\n"+ "Unknown" + "#" + place;
                }
            }

        }
        Main.getInstance().getServer().broadcastMessage("TopKill Updated.");
        topkill.setText(topParticle);
        for(Player player : Main.getInstance().getServer().getOnlinePlayers().values()){
            player.getLevel().addParticle(topkill, player);
        }


    }
}
