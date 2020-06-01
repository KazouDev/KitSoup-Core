package fr.kazoudev.kitsoup;

import cn.nukkit.Player;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import fr.kazoudev.kitsoup.kits.KitEnum;
import fr.kazoudev.kitsoup.managers.Cooldown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SoupPlayer {
    private Player player;
    private String rank;
    private String lang;
    private KitEnum kit;
    private int coins;
    private int kill;
    private int death;
    private boolean autodrop;
    private boolean freeze;
    private String nick;
    private int playTime;
    private Player latestMsg;
    private Position latestPos;
    private Player latestView;
    private String prefix;
    private HashMap<Cooldown, Long> cooldownAbilities = new HashMap();


    public SoupPlayer(Player player, String rank, String lang, int coins, int kill, int death, boolean autodrop, String prefix) {
       this.player = player;
        this.rank = rank;
        this.lang = lang;
        this.coins = coins;
        this.kill = kill;
        this.death = death;
        this.autodrop = autodrop;
        this.kit = KitEnum.PVP;
        this.playTime = 0;
        this.prefix = prefix;
    }

    public Player getPlayer(){return player;}
    public String getRank() {
        return rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }
    public String getLang() {
        return lang;
    }
    public void setLang(String lang) {
        this.lang = lang;
    }
    public KitEnum getKit() {
        if(kit != null) {
            return kit;
        } else return KitEnum.PVP;
    }
    public void setKit(KitEnum kit){
        this.kit = kit;
    }
    public int getCoins() {
        return coins;
    }
    public void setCoins(int coins) {
        this.coins = coins;
    }
    public int getKill() {
        return kill;
    }
    public void setKill(int kill) {
        this.kill = kill;
    }
    public int getDeath() {
        return death;
    }
    public void setDeath(int death) {
        this.death = death;
    }
    public void addCooldown(Cooldown C) {
        this.cooldownAbilities.put(C, System.currentTimeMillis());
    }
    public void removeAllCooldown() {
        this.cooldownAbilities.clear();
    }
    public void removeCooldown(Cooldown C) {
        if (this.cooldownAbilities.containsKey(C)) {
            this.cooldownAbilities.remove(C);
        }

    }
    public double getTimeCooldown(Cooldown C) {
        if (!this.cooldownAbilities.containsKey(C)) {
            return 0;
        } else {
            Long precededTime = (Long)this.cooldownAbilities.get(C);
            double d = (double)((precededTime - System.currentTimeMillis()) / 1000L + (long)C.getTime());
            return d;
        }
    }
    public boolean isCooldown(Cooldown C) {
        if (!this.cooldownAbilities.containsKey(C)) {
            return false;
        } else {
            Long precededTime = (Long)this.cooldownAbilities.get(C);
            return precededTime + (long)(C.getTime() * 1000) > System.currentTimeMillis();
        }
    }
    public boolean getAutoDrop() {
        return autodrop;
    }
    public void setAutoDrop(boolean b){
        this.autodrop = b;
    }
    public boolean isFreeze(){return freeze;}
    public void setFreeze(boolean freeze){this.freeze = freeze; getPlayer().setImmobile(freeze);}
    public String getNick(){return nick;}
    public void setNick(String nick){
        this.nick = nick;
        Main.getInstance().nickedName.put(nick, player);
        addCooldown(Cooldown.NICK_CMD);
        player.setDisplayName(nick);
        player.setNameTag(nick);
        Main.getInstance().nickedName.put("Argga", player);
        Main.getInstance().nickedName.put("Kerabou", player);
        Main.getInstance().nickedName.put("Alph0", player);
        Main.getInstance().nickedName.put("BlueWater12", player);
    }
    public void unNicked(){
        Main.getInstance().nickedName.remove(nick);
        this.nick = null;
        player.setDisplayName(player.getName());
        player.setNameTag(player.getName());
    }
    public Player getLatestMsg(){return latestMsg;}
    public void setLatestMsg(Player latestMsg){this.latestMsg = latestMsg;}
    public Position getLatestPos(){return latestPos;}
    public void setLatestPos(Location loc){this.latestPos = loc;}
    public int getPlayTime(){return playTime;}
    public void addPlayTime(){playTime++;}
    public Player getLatestView(){return latestView;}
    public void setLatestView(Player latestView){this.latestView = latestView;}
    public String getPrefix(){return prefix;}
    public void setPrefix(String prefix){this.prefix = prefix;}

    public List<String> getOtherPrefix() {
        List<String> plist = new ArrayList<>(Arrays.asList("§d<3", "§cRRR", "§aGREEN", "§6Best§cEU", ""));
        if(plist.contains(prefix)){
            plist.remove(prefix);
        }
        plist.add(0, prefix);
        return plist;
    }
}
