package fr.kazoudev.kitsoup.events;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import fr.kazoudev.kitsoup.kits.KitForm;
import fr.kazoudev.kitsoup.managers.Language;
import fr.kazoudev.kitsoup.parameter.settingsForm;
import fr.kazoudev.kitsoup.shop.shopForm;

public class openDefaultForm implements Listener {

    @EventHandler
    public void openKitForm(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(p.getInventory().getItemInHand().getId() == 272 && p.getInventory().getItemInHand().getCustomName().equalsIgnoreCase("§cKits")){
            KitForm.openFormKit(p);
            e.setCancelled(true);
        }

        if(p.getInventory().getItemInHand().getId() == 266 && p.getInventory().getItemInHand().getCustomName().equalsIgnoreCase("§6" + Language.SHOP.getLang(p))){
            shopForm.openShopForm(p);
            e.setCancelled(true);
        }

        if(p.getInventory().getItemInHand().getId() == 356 && p.getInventory().getItemInHand().getCustomName().equalsIgnoreCase(TextFormat.RED + Language.SETTINGS.getLang(p))){
            settingsForm.openSettingsForm(p);
            e.setCancelled(true);
        }
    }

}
