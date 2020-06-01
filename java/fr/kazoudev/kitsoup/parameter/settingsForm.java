package fr.kazoudev.kitsoup.parameter;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.managers.Language;
import fr.kazoudev.kitsoup.managers.MySQL;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class settingsForm implements Listener {

    public static void openSettingsForm(Player p){
        FormWindowCustom form = new FormWindowCustom(TextFormat.RED + Language.SETTINGS.getLang(p));

        ElementDropdown prefix = new ElementDropdown("Prefix", Main.getSPlayer(p).getOtherPrefix());
        ElementToggle autodrop = new ElementToggle("AutoDrop", Main.getSPlayer(p).getAutoDrop());
        List<String> langList = new ArrayList<>(Arrays.asList("FR", "EN"));
        p.sendMessage(Main.getSPlayer(p).getLang());
        if(langList.contains(Main.getSPlayer(p).getLang())){
            langList.remove(Main.getSPlayer(p).getLang());
        }
        langList.add(0, Main.getSPlayer(p).getLang());
        ElementDropdown lang = new ElementDropdown("Lang", langList);
        form.addElement(prefix);
        form.addElement(autodrop);
        form.addElement(lang);

        p.showFormWindow(form);

    }

    @EventHandler
    public void parameterListener(PlayerFormRespondedEvent e){
        if(e.getResponse() != null){
            if(e.getWindow() instanceof FormWindowCustom){
                if(((FormWindowCustom) e.getWindow()).getTitle().equalsIgnoreCase(TextFormat.RED + Language.SETTINGS.getLang(e.getPlayer()))){
                    HashMap<Integer, Object> responses = ((FormResponseCustom) e.getResponse()).getResponses();
                    Main.getSPlayer(e.getPlayer()).setPrefix((String) responses.get(0));
                    Main.getSPlayer(e.getPlayer()).setAutoDrop((Boolean) responses.get(1));
                    if(responses.get(2) != Main.getSPlayer(e.getPlayer()).getLang()){
                        Main.getSPlayer(e.getPlayer()).setLang((String) responses.get(2));
                        e.getPlayer().getInventory().clearAll();
                        e.getPlayer().getInventory().setItem(0, Item.get(272).setCustomName("§cKits"));
                        e.getPlayer().getInventory().setItem(8, Item.get(356).setCustomName(TextFormat.RED + Language.SETTINGS.getLang(e.getPlayer())));
                        e.getPlayer().getInventory().setItem(4, Item.get(266).setCustomName(TextFormat.GOLD + Language.SHOP.getLang(e.getPlayer())));
                    }
                }
            }
        }
    }
}
