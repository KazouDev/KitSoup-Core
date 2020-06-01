package fr.kazoudev.kitsoup;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.GameRule;
import cn.nukkit.level.Location;
import cn.nukkit.plugin.PluginBase;
import fr.kazoudev.kitsoup.cmd.admin.rankCommand;
import fr.kazoudev.kitsoup.cmd.admin.stopCommand;
import fr.kazoudev.kitsoup.cmd.admin.testCommand;
import fr.kazoudev.kitsoup.cmd.mod.*;
import fr.kazoudev.kitsoup.cmd.mod.ban.banCommand;
import fr.kazoudev.kitsoup.cmd.mod.ban.unbanCommand;
import fr.kazoudev.kitsoup.cmd.mod.mute.muteCommand;
import fr.kazoudev.kitsoup.cmd.mod.mute.unmuteCommand;
import fr.kazoudev.kitsoup.cmd.player.*;
import fr.kazoudev.kitsoup.events.*;
import fr.kazoudev.kitsoup.hostevent.EventManager;
import fr.kazoudev.kitsoup.hostevent.EventType;
import fr.kazoudev.kitsoup.kits.KitForm;
import fr.kazoudev.kitsoup.managers.MySQL;
import fr.kazoudev.kitsoup.parameter.settingsForm;
import fr.kazoudev.kitsoup.shop.shopForm;
import fr.kazoudev.kitsoup.utils.EventTask;
import fr.kazoudev.kitsoup.utils.GlobalTask;
import org.apache.logging.log4j.core.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends PluginBase {

    private static Main instance;
    public static HashMap<Player, SoupPlayer> soupplayer = new HashMap<Player, SoupPlayer>();
    public Location loc;
    public HashMap<String, Player> nickedName = new HashMap<>();
    public ArrayList<Player> whisper = new ArrayList<>();
    public ArrayList<Player> mod = new ArrayList<>();
    public ArrayList<Player> vanish = new ArrayList<>();
    public boolean chatActive = true;
    public Location spawn = new Location(155, 107, 194);
    public EventManager event = null;
    public Player kothPlayer = null;
    public int totemBreak = 0;
    public Player totemPlayer;
    public long lastEvent;
    public EventType lastEventType;

    @Override
    public void onEnable() {
        instance = this;
        getServer().getLevelByName("world").gameRules.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        registerEvents();
        registerAbilities();
        getServer().getCommandMap().register("rank", new rankCommand(this));
        getServer().getCommandMap().register("kit", new kitCommand());
        getServer().getCommandMap().register("ping", new pingCommand());
        getServer().getCommandMap().register("stats", new statsCommand());
        getServer().getCommandMap().register("chat", new chatCommand());
        getServer().getCommandMap().register("freeze", new freezeCommand());
        getServer().getCommandMap().register("nick", new nickCommand());
        getServer().getCommandMap().register("message", new msgCommand());
        getServer().getCommandMap().register("see", new seeCommand());
        getServer().getCommandMap().register("reply", new replyCommand());
        getServer().getCommandMap().register("kick", new kickCommand());
        getServer().getCommandMap().register("mod", new modCommand());
        getServer().getCommandMap().register("whisper", new whisperCommand());
        getServer().getCommandMap().register("info", new infoCommand());
        getServer().getCommandMap().register("stop", new stopCommand());
        getServer().getCommandMap().register("event", new eventCommand());
        getServer().getCommandMap().register("host", new testCommand());
        getServer().getCommandMap().register("checkcheat", new checkCheatCommand());
        getServer().getCommandMap().register("ban", new banCommand());
        getServer().getCommandMap().register("unban", new unbanCommand());
        getServer().getCommandMap().register("mute", new muteCommand());
        getServer().getCommandMap().register("unmute", new unmuteCommand());

        getServer().getScheduler().scheduleRepeatingTask(new GlobalTask(), 20 * 30);
        getServer().getScheduler().scheduleRepeatingTask(new EventTask(this), 20 * 60);
    }

    public static SoupPlayer getSPlayer(Player p) {
        return soupplayer.get(p);
    }

    public void newPlayer(Player p){
        String uid = p.getUniqueId().toString();
        soupplayer.put(p, new SoupPlayer(p, MySQL.getPlayerRank(uid), MySQL.getPlayerLang(uid), MySQL.getPlayerCoins(uid), MySQL.getPlayerKill(uid), MySQL.getPlayerDeath(uid), MySQL.getAutoDrop(uid), MySQL.getPrefix(uid)));
    }

    public void delPlayer(Player p){
        MySQL.savePlayer(p);
        soupplayer.remove(p);
    }

    @Override
    public void onDisable() {
        for(Player p : Server.getInstance().getOnlinePlayers().values()){
            MySQL.savePlayer(p);
            p.save();
            p.close("", "§cRestart...");
        }
        File[] fileNames = new File("/root/host/").listFiles();
        for(File file : fileNames){
            getServer().getLogger().info("File: " + file.getName());
            if(!(file.getName().equalsIgnoreCase("template"))){
                try {
                    deleteDirectoryRecursion(Paths.get(file.getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void deleteDirectoryRecursion(Path path) throws IOException {
        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                for (Path entry : entries) {
                    deleteDirectoryRecursion(entry);
                }
            }
            Files.delete(path);
        }
    }

    public void registerEvents(){
        getServer().getPluginManager().registerEvents(new noDrop(), this);
        getServer().getPluginManager().registerEvents(new openDefaultForm(), this);
        getServer().getPluginManager().registerEvents(new KitForm(), this);
        getServer().getPluginManager().registerEvents(new sqlListener(), this);
        getServer().getPluginManager().registerEvents(new boxEvent(), this);
        getServer().getPluginManager().registerEvents(new shopForm(), this);
        getServer().getPluginManager().registerEvents(new weatherEvent(), this);
        getServer().getPluginManager().registerEvents(new soupEvent(), this);
        getServer().getPluginManager().registerEvents(new chatEvent(), this);
        getServer().getPluginManager().registerEvents(new onJoin(), this);
        getServer().getPluginManager().registerEvents(new modEvent(), this);
        getServer().getPluginManager().registerEvents(new settingsForm(), this);
        getServer().getPluginManager().registerEvents(new jumpInArena(), this);
        getServer().getPluginManager().registerEvents(new eventFormEvent(), this);
        getServer().getPluginManager().registerEvents(new totemEvent(), this);
    }

    public void registerAbilities(){

    }

    public boolean eCooldown(int time){
        if((System.currentTimeMillis() - lastEvent) / 1000 > time){
            return true;
        } return false;
    }

    public void setLastEvent(long end, EventType t){
        Main.getInstance().event = null;
        this.lastEvent = end;
        this.lastEventType = t;
    }


    public static Main getInstance(){ return instance;}


}
