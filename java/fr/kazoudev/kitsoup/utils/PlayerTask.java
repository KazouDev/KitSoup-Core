package fr.kazoudev.kitsoup.utils;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.passive.EntityPig;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.SimpleAxisAlignedBB;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.DummyBossBar;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.SoupPlayer;
import fr.kazoudev.kitsoup.hostevent.EventManager;
import fr.kazoudev.kitsoup.hostevent.task.KothTask;
import fr.kazoudev.kitsoup.kits.KitEnum;
import fr.kazoudev.kitsoup.kits.KitForm;
import fr.kazoudev.kitsoup.managers.Cooldown;

import java.awt.*;
import java.util.HashMap;

public class PlayerTask extends PluginTask<Main> {
    private SoupPlayer sp;
    private String bossbar = "player: kit";
    private long id = -1;

    private static HashMap<SoupPlayer, PlayerTask> taskmap = new HashMap<>();
    private SimpleAxisAlignedBB boundingBox;

    public PlayerTask(SoupPlayer sp){
        super(Main.getInstance());
        this.sp = sp;
        this.boundingBox = new SimpleAxisAlignedBB(0, 0, 0, 0, 0, 0);
        taskmap.put(sp, this);
    }

    @Override
    public void onRun(int time) {

        if(!sp.getPlayer().isOnline()){
            Server.getInstance().getLogger().alert("[TASK] " + sp.getPlayer().getName() + " disconnect.");
            this.cancel();
        }
        if(sp.getKit() == KitEnum.VOYANT) {
            Position pos = sp.getPlayer().getPosition();
            for (Entity p : sp.getPlayer().level.getNearbyEntities(new SimpleAxisAlignedBB(pos.getX() - 3, pos.getY(), pos.getZ() - 3, pos.getX() + 3, pos.getY() + 2, pos.getZ() + 3), sp.getPlayer())) {
                if (p instanceof Player) {
                    if(this.id != -1){
                        sp.getPlayer().updateBossBar(p.getName()+": "+Main.getSPlayer((Player) p).getKit().getColorName(), 100, id);
                        break;
                    } else {
                        this.id = sp.getPlayer().createBossBar(p.getName()+": "+Main.getSPlayer((Player) p).getKit().getColorName(), 100);
                        break;
                    }
                }
            }
        } else this.id = -1;

        if(Main.getInstance().event != null){
            if(Main.getInstance().event.getStart()){
                if(Main.getInstance().kothPlayer == null) {
                    if (EventManager.isKoth(sp.getPlayer())) {
                        Main.getInstance().kothPlayer = sp.getPlayer();
                    }
                }
            }
        }

        if(sp.getLatestPos().y >= 105 && !Main.getInstance().mod.contains(sp.getPlayer())){
            sp.getPlayer().sendTip(sp.getPlayer().getX() + " " + sp.getPlayer().getY() +" "+ sp.getPlayer().getZ());
            if(sp.getPlayer().getLocation().y < 105){
                if(sp.getKit() != null){
                    KitForm.applyKit(sp.getPlayer(), sp.getKit());
                    sp.addCooldown(Cooldown.FALLDAMAGE);
                } else {
                    sp.getPlayer().teleport(new Location(155, 107, 194));
                    sp.getPlayer().sendTitle("§eError with you'r kit !");
                }
            }
        }

        sp.setLatestPos(sp.getPlayer().getLocation());
        sp.addPlayTime();
    }

    public static PlayerTask getTask(SoupPlayer sp){
        return taskmap.get(sp);
    }


    public void remove() {
        taskmap.remove(this.sp);
        this.cancel();
    }
}
