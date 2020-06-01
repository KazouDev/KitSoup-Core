package fr.kazoudev.kitsoup.hostevent.task;

import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.math.Vector3;
import cn.nukkit.scheduler.PluginTask;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.hostevent.EventManager;
import fr.kazoudev.kitsoup.hostevent.EventType;

public class EventTask extends PluginTask<Main> {

    private EventManager event;
    private String msg;
    private int timeToStart;

    public EventTask(EventManager event, String msg) {
        super(Main.getInstance());
        this.msg = msg;
        this.event = event;
        this.timeToStart = 60;
    }

    public EventTask(EventManager event, String msg, int timeToStart) {
        super(Main.getInstance());
        this.msg = msg;
        this.event = event;
        this.timeToStart = timeToStart;

        Main.getInstance().getServer().getScheduler().scheduleRepeatingTask(Main.getInstance(), this, 20);
    }

    public int getTimeToStart() {
        return timeToStart;
    }

    public String getMsg() {
        return msg;
    }

    public void setTimeToStart(int timeToStart) {
        this.timeToStart = timeToStart;
    }

    public EventManager getEvent() {
        return event;
    }

    @Override
    public void onRun(int i) {
        if(timeToStart > 10){
            if(timeToStart % 15 == 0){
                Main.getInstance().getServer().broadcastMessage(msg.replace("[time]", Integer.toString(timeToStart)).replace("[host]", event.getHost()));
            }
        } else {
            switch (timeToStart){
                case 10:
                case 5:
                case 3:
                case 2:
                case 1:
                    Main.getInstance().getServer().broadcastMessage(msg.replace("[time]", Integer.toString(timeToStart)).replace("[host]", event.getHost()));
                    break;
                case 0:
                    Main.getInstance().getServer().broadcastMessage("Event lancé !");
                    event.startEvent();
                    break;

            }
        }
            timeToStart--;
    }

}
