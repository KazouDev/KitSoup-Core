package fr.kazoudev.kitsoup.hostevent;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.math.Vector3;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.hostevent.task.EventTask;
import fr.kazoudev.kitsoup.hostevent.task.KothTask;
import fr.kazoudev.kitsoup.hostevent.task.TotemTask;

import java.awt.*;

public class EventManager {
    private EventType type;
    private String host;
    private boolean start;
    private EventTask task;

    public EventManager(EventType type, String host, String msg){
        this.type = type;
        this.host = host;
        this.start = false;

        this.task = new EventTask(this, msg != null ? msg : "Lancement du " + type.name() + " dans [time]s. Host: [host]", 5);
    }

    public void startEvent(){
        task.cancel();
        start = true;
        switch (type){
            case KOTH:
                Server.getInstance().getScheduler().scheduleRepeatingTask(new KothTask(Main.getInstance()), 20);
                break;
            case TOTEM:
                Server.getInstance().getScheduler().scheduleRepeatingTask(new TotemTask(), 20);
                setTotem(true);
                break;
        }
    }

    public EventType getType() {
        return type;
    }

    public boolean getStart() {
        return start;
    }

    public void setStart(){this.start = true;}

    public String getHost() {
        return host;
    }

    public EventTask getTask() {
        return task;
    }

    public static boolean eventExist(String event){
        try{
            Enum.valueOf(EventType.class, event);
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }

    public static boolean isKoth(Player player){
        double x = player.x;
        double y = player.y;
        double z = player.z;

        if(player.getLevel().getFolderName().equalsIgnoreCase("world")){
            if(x >= 156 && x <= 162){
                if(z >= 208 && z <= 214){
                    return true;
                }
            } return false;
        }
        return false;
    }

    public void setTotem(boolean b){
        if(b){
            for(int i =0; i < 5; i++){
                Server.getInstance().getLevelByName("world").setBlock(new Vector3(159, 106+i, 211), Block.get(BlockID.QUARTZ_BLOCK));
            }
        } else {
            for(int i =0; i < 5; i++){
                Server.getInstance().getLevelByName("world").setBlock(new Vector3(159, 106+i, 211), Block.get(BlockID.AIR));
            }
        }
    }

}
