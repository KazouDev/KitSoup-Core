package fr.kazoudev.kitsoup.events;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.inventory.InventoryPickupItemEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerInteractEntityEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.TextFormat;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.SoupPlayer;
import fr.kazoudev.kitsoup.managers.Language;
import fr.kazoudev.kitsoup.utils.inventory.NewChestInventory;
import fr.kazoudev.kitsoup.utils.inventory.NewInventory;

import java.text.SimpleDateFormat;
import java.util.*;

import static cn.nukkit.event.player.PlayerInteractEvent.Action.RIGHT_CLICK_AIR;
import static cn.nukkit.event.player.PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK;

public class modEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void modInteract(PlayerInteractEvent e){
        if(Main.getInstance().mod.contains(e.getPlayer())){
            if(e.getAction() == RIGHT_CLICK_AIR || e.getAction() == RIGHT_CLICK_BLOCK){
                switch (e.getItem().getId()){
                    case Item.ENDER_EYE:
                        List<Player> pl = new ArrayList<>(Server.getInstance().getOnlinePlayers().values());
                        pl.remove(e.getPlayer());
                        Random rdm = new Random();
                        Player target = pl.get(rdm.nextInt(pl.size()));
                        e.getPlayer().teleport(target);
                        e.getPlayer().sendMessage(Language.TELEPORT_AT.getLang(e.getPlayer()) + target.getName());
                        break;

                    case Item.GLASS:
                        if(Main.getInstance().vanish.contains(e.getPlayer())) {
                            Server.getInstance().getOnlinePlayers().forEach((uuid, player) -> {
                                    player.showPlayer(e.getPlayer());
                            });
                            Main.getInstance().vanish.remove(e.getPlayer());

                            e.getPlayer().getInventory().setItemInHand(Item.get(Item.GLASS).setCustomName("§aVanish §cOFF"));
                            e.getPlayer().sendMessage(TextFormat.RED + Language.VANISH_REM.getLang(e.getPlayer()));
                            e.getPlayer().getInventory().setHelmet(Item.get(0));
                        } else {
                            Server.getInstance().getOnlinePlayers().forEach((uuid, player) -> {
                                if (!Main.getInstance().mod.contains(player)) {
                                    player.hidePlayer(e.getPlayer());
                                }
                            });
                            Main.getInstance().vanish.add(e.getPlayer());

                            e.getPlayer().getInventory().setItemInHand(Item.get(Item.GLASS).setCustomName("§aVanish ON"));
                            e.getPlayer().sendMessage(TextFormat.GREEN + Language.VANISH_SET.getLang(e.getPlayer()));
                            e.getPlayer().getInventory().setHelmet(Item.get(ItemID.DIAMOND_HELMET));
                        }
                        break;

                    case Item.LEVER:
                            if(Main.getInstance().whisper.contains(e.getPlayer())){
                                Main.getInstance().whisper.remove(e.getPlayer());
                                e.getPlayer().sendMessage(TextFormat.RED + Language.WHISPER_OFF.getLang(e.getPlayer()));
                                e.getPlayer().getInventory().setItemInHand(Item.get(BlockID.LEVER).setCustomName("§aWhisper §cOFF"));
                            } else {
                                Main.getInstance().whisper.add(e.getPlayer());
                                e.getPlayer().sendMessage(TextFormat.GREEN + Language.WHISPER_ON.getLang(e.getPlayer()));
                                e.getPlayer().getInventory().setItemInHand(Item.get(BlockID.LEVER).setCustomName("§aWhisper ON"));
                            }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void modInteractPlayer(PlayerInteractEntityEvent e){
        if(Main.getInstance().mod.contains(e.getPlayer())){
            if(e.getEntity() instanceof Player){
                Player mod = e.getPlayer();
                Player target = (Player) e.getEntity();
                switch (e.getItem().getId()){
                    case Item.ICE:
                        if(Main.getSPlayer(target).isFreeze()) {
                            Main.getSPlayer(target).setFreeze(false);
                            mod.sendMessage("§aYou have unfreeze: " + target.getName());
                            target.sendMessage("§aYou are now unfreeze.");
                        } else {
                            Main.getSPlayer(target).setFreeze(true);
                            mod.sendMessage("§aYou have freeze: " + target.getName());
                            target.sendMessage("§cYou are now freeze.");
                        }
                        break;
                    case Item.BOOK:
                        /*HashMap<Integer, Integer> inv = new HashMap<>();
                        for (Item item : target.getInventory().getContents().values()){
                            if(!inv.containsKey(item.getId())){
                                mod.sendMessage(item.getId() + " " + item.getCount());
                                inv.put(item.getId(), 1);
                            } else {
                                inv.put(item.getId(), inv.get(item.getId())+1);
                            }
                        }
                        FormWindowSimple form = new FormWindowSimple(target.getName() + "'s Inventory", "");
                        for(Integer item : inv.keySet()){
                            mod.sendMessage(item + " " );
                            form.addButton(new ElementButton(Item.get(item).getName() + " " + inv.get(item), new ElementButtonImageData("path", "textures/items/"+Item.get(item).getName().toLowerCase().replace(" ", "_")+".png")));
                        }
                        mod.showFormWindow(form);*/

                        NewInventory inv = new NewChestInventory(InventoryType.DOUBLE_CHEST, "InventoryView");
                        inv.setContents(target.getInventory().getContents());
                        inv.setItem(42, Item.get(ItemID.PAPER).setCustomName("§cHealth: " + target.getHealth() + "/20"));
                        Item effect = Item.get(ItemID.PAPER);
                        for(Effect eff : target.getEffects().values()){
                            effect.setLore(effect.getLore() + "\n" + eff.getName() + " " + eff.getAmplifier());
                        }
                        inv.setItem(43, effect);
                        mod.addWindow(inv);

                        break;
                    case Item.PAPER:
                        SoupPlayer sp = Main.getSPlayer(target);
                        mod.sendMessage("Name: " + sp.getPlayer().getName());
                        mod.sendMessage(sp.getNick() != null ? "Nick: " + sp.getNick() : "Unnicked");
                        mod.sendMessage("Coins: " + sp.getCoins());
                        mod.sendMessage(sp.getDeath() != 0 ? "KD: " + sp.getKill() / sp.getDeath() : "KD: " + sp.getKill());
                        mod.sendMessage("Kit: " + sp.getKit().getColorName());
                        mod.sendMessage("AllowFlight: " + sp.getPlayer().getAdventureSettings().get(AdventureSettings.Type.ALLOW_FLIGHT));
                        mod.sendMessage("Speed: " + sp.getPlayer().getMovementSpeed());
                        mod.sendMessage("Playtime: " + new SimpleDateFormat("HH:mm:ss").format(new Date(sp.getPlayTime() * 1000)));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void modPlace(BlockPlaceEvent e){
        if(Main.getInstance().mod.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void modBreak(BlockBreakEvent e){
        if(Main.getInstance().mod.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void modDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if(Main.getInstance().mod.contains(p)){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void modMakeDamage(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player){
            if(Main.getInstance().mod.contains((Player) e.getDamager())){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void modDeath(PlayerDeathEvent e){
        if(Main.getInstance().mod.contains(e.getEntity())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void modDrop(InventoryPickupItemEvent e){
        if(e.getInventory().getType() == InventoryType.PLAYER){
            Player p = (Player) e.getInventory().getHolder();
            if(Main.getInstance().mod.contains(p)){
                e.setCancelled(true);
            }
        }
    }

}
