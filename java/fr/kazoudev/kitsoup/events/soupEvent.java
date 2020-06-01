package fr.kazoudev.kitsoup.events;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFoodLevelChangeEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import fr.kazoudev.kitsoup.Main;

public class soupEvent implements Listener {

    @EventHandler
    public void soupEvent(PlayerInteractEvent e){
        if(e.getItem().getId() == 282){
            if(e.getAction() == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || e.getAction() == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK){
                if(e.getPlayer().getGamemode() == Player.SURVIVAL && e.getPlayer().getHealth() != 20){
                    if(e.getPlayer().getHealth() >= 12.5){
                        e.getPlayer().setHealth(20);
                            if(e.getItem().getCount() >= 2){
                                e.getPlayer().getInventory().setItemInHand(Item.get(282, 0, e.getItem().getCount() - 1));
                                e.getPlayer().dropItem(Item.get(281));
                                return;
                            } else {
                                if(Main.getSPlayer(e.getPlayer()).getAutoDrop()){
                                    e.getPlayer().dropItem(Item.get(281));
                                    e.getPlayer().getInventory().setItemInHand(Item.get(0));
                                    return;
                                }
                            }
                        e.getPlayer().getInventory().setItemInHand(Item.get(281));
                    } else {
                        e.getPlayer().setHealth(e.getPlayer().getHealth() + 7);
                            if(e.getItem().getCount() >= 2){
                                e.getPlayer().getInventory().setItemInHand(Item.get(282, 0, e.getItem().getCount() - 1));
                                e.getPlayer().dropItem(Item.get(281));
                                return;
                            } else {
                                if(Main.getSPlayer(e.getPlayer()).getAutoDrop()) {
                                    e.getPlayer().dropItem(Item.get(281));
                                    e.getPlayer().getInventory().setItemInHand(Item.get(0));
                                    return;
                                }
                            }
                            e.getPlayer().getInventory().setItemInHand(Item.get(281));
                        }
                    }
                } else {
                    e.setCancelled(true);
                }
            }
        }

    @EventHandler
    public void noFoodEvent(PlayerFoodLevelChangeEvent e){
        if(e.getPlayer().getLevel().getName().equalsIgnoreCase("world")) {
            if(e.getPlayer().getGamemode() == Player.SURVIVAL) {
                e.setCancelled(true);
            }
        }
    }
}
