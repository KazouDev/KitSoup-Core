package fr.kazoudev.kitsoup.hostevent.task;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.hostevent.EventType;

public class TotemTask extends PluginTask<Main> implements Listener {
    public int passedTime = 0;

    public TotemTask() {
        super(Main.getInstance());
    }

    @Override
    public void onRun(int i) {
        if(Main.getInstance().event == null || Main.getInstance().event.getType() != EventType.TOTEM){
            Main.getInstance().setLastEvent(System.currentTimeMillis(), EventType.TOTEM);
            Main.getInstance().totemPlayer = null;
            Main.getInstance().totemBreak = 0;
            this.getHandler().cancel();
        }
        passedTime++;

        Server.getInstance().broadcastMessage(passedTime + "");
        if(passedTime > 60){
            Main.getInstance().getServer().broadcastMessage("§e[§cTOTEM§e] §cTotem Fini !");
            Main.getInstance().setLastEvent(System.currentTimeMillis(), EventType.TOTEM);
            this.getHandler().cancel();
        }
    }
}
