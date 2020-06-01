package fr.kazoudev.kitsoup.events;

import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Sound;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.EntityEventPacket;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.hostevent.EventType;

import java.util.ArrayList;
import java.util.Arrays;

public class totemEvent implements Listener {
    public ArrayList<Integer> swordlist = new ArrayList<>(Arrays.asList(268, 267, 272, 283, 276, 352));
    @EventHandler
    public void totemBreak(BlockBreakEvent e){
        if(Main.getInstance().event != null){
            if(Main.getInstance().event.getType() == EventType.TOTEM && Main.getInstance().event.getStart()){
                if(isTotem(e.getBlock())){
                    if(swordlist.contains(e.getItem().getId())){
                        e.setDropExp(0);
                        e.setDrops(new Item[]{Item.get(0)});
                        if(Main.getInstance().totemPlayer == null){
                                Main.getInstance().totemPlayer = e.getPlayer();
                                Main.getInstance().totemBreak = Main.getInstance().totemBreak + 1;
                                Main.getInstance().getServer().getLevelByName(e.getPlayer().getLevel().getName()).setBlock(e.getBlock(), Block.get(BlockID.AIR));
                                Main.getInstance().getServer().broadcastMessage("totem commencé a cassé");
                                return;
                        }
                        if(Main.getInstance().totemPlayer == e.getPlayer()){
                            Main.getInstance().totemBreak = Main.getInstance().totemBreak + 1;
                            Main.getInstance().getServer().getLevelByName(e.getPlayer().getLevel().getName()).setBlock(e.getBlock(), Block.get(BlockID.AIR));
                            Main.getInstance().getServer().broadcastMessage("§cTotem: +1 " + e.getPlayer().getName() + " (§e" + Main.getInstance().totemBreak + "§c/5)");
                            if(Main.getInstance().totemBreak == 5){
                                Server.getInstance().broadcastMessage("§cTotem cassé par " + e.getPlayer().getName() + " §6+500 Coins");
                                Main.getSPlayer(e.getPlayer()).setCoins(Main.getSPlayer(e.getPlayer()).getCoins() + 500);
                                e.getPlayer().getLevel().addSound(new Vector3(159, 111, 211), Sound.MOB_ENDERDRAGON_DEATH);
                                setTotem(false);

                                Main.getInstance().event = null;
                            }
                        } else {
                            e.setCancelled(true);
                            setTotem(true);
                            Server.getInstance().broadcastMessage("§cTotem recommencé par " + e.getPlayer().getName() + ".");
                            Item item = e.getItem();
                            e.getPlayer().getInventory().setItemInHand(Item.get(450));
                            EntityEventPacket packet = new EntityEventPacket();
                            packet.eid = e.getPlayer().getId();
                            packet.event = EntityEventPacket.CONSUME_TOTEM;
                            e.getPlayer().dataPacket(packet);
                            e.getPlayer().getInventory().setItemInHand(item);
                            Main.getInstance().totemPlayer = null;
                            Main.getInstance().totemBreak = 0;
                        }
                    } else {e.getPlayer().sendMessage("§cUtilisez une épée."); e.setCancelled(true);}
                }
            }
        }
    }

    public boolean isTotem(Block b){
        for(int i = 0; i <5; i++){
            if(b.equals(new Vector3(159, b.getY(), 211))){
                return true;
            }
        } return false;
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
