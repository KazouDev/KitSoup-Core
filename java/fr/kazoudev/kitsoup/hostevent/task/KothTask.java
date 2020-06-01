package fr.kazoudev.kitsoup.hostevent.task;

import cn.nukkit.Player;
import cn.nukkit.scheduler.PluginTask;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.hostevent.EventManager;
import fr.kazoudev.kitsoup.hostevent.EventType;

public class KothTask extends PluginTask<Main> {

    public int kothTimer = 1800;
    public int captureTimer = 5;

    private int timeToCapture;

    public KothTask(Main owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {
        kothTimer--;
        if(Main.getInstance().event.getType() != EventType.KOTH){
            Main.getInstance().getServer().broadcastMessage("§e[§cKOTH§e] §cKoth Fini !");
            Main.getInstance().setLastEvent(System.currentTimeMillis(), EventType.KOTH);
            Main.getInstance().kothPlayer = null;
            this.getHandler().cancel();
        }
        if(kothTimer <= 0){
            Main.getInstance().getServer().broadcastMessage("§e[§cKOTH§e]§c Koth terminé, fin des 30 minutes, pas de gagnant.");
            Main.getInstance().event = null;
            Main.getInstance().setLastEvent(System.currentTimeMillis(), EventType.KOTH);
            getHandler().cancel();
        }

        if(Main.getInstance().kothPlayer != null){
            if(EventManager.isKoth(Main.getInstance().kothPlayer)) {
                if (captureTimer > 0) {
                    captureTimer--;
                    for(Player p : Main.getInstance().getServer().getOnlinePlayers().values()){
                        p.sendTip("§e[§cKOTH§e]§c " + Main.getInstance().kothPlayer.getName() + " §ecapture le KOTH (§c" + captureTimer + "§e)");
                    }
                } else {
                    Main.getInstance().getServer().broadcastMessage("§e[§cKOTH§e] §cKoth remporté par " + Main.getInstance().kothPlayer.getName());
                    Main.getInstance().setLastEvent(System.currentTimeMillis(), EventType.KOTH);
                    getHandler().cancel();
                }
            } else {
                Main.getInstance().getServer().broadcastMessage("§e[§cKOTH§e]§e " + Main.getInstance().kothPlayer.getName() + " §cperd le controle du KOTH");
                captureTimer = 120;
                Main.getInstance().kothPlayer = null;
            }
        }
    }
}
