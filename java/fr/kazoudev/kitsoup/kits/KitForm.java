package fr.kazoudev.kitsoup.kits;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemArmor;
import cn.nukkit.item.ItemChestplateDiamond;
import cn.nukkit.item.ItemTool;
import cn.nukkit.utils.TextFormat;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.managers.Language;
import fr.kazoudev.kitsoup.managers.MySQL;
import fr.kazoudev.kitsoup.rank.Rank;
import fr.kazoudev.kitsoup.rank.RankUtils;
import sun.misc.Launcher;

import java.awt.*;
import java.util.Arrays;
import java.util.List;


public class KitForm implements Listener {
    public static void openFormKit(Player p){
        FormWindowSimple form = new FormWindowSimple("Kits", Language.CHOOSE_KIT.getLang(p));

        for(KitEnum kits : KitEnum.values()){
            form.addButton(new ElementButton(kits.getColorName()));
        }
        form.addButton(new ElementButton("§cRetour"));
        p.showFormWindow(form);

    }

    @EventHandler
    public void kitFormRespond(PlayerFormRespondedEvent e){
        Player p = e.getPlayer();
        FormWindow window = e.getWindow();

        if(e.getResponse() != null){
            if(window instanceof FormWindowSimple){
                String title = ((FormWindowSimple) e.getWindow()).getTitle();
                Integer buttonId = ((FormResponseSimple) e.getResponse()).getClickedButtonId();
                ElementButton button = ((FormResponseSimple) e.getResponse()).getClickedButton();
                if(title.equalsIgnoreCase("Kits")){
                    if(!button.getText().equalsIgnoreCase("§cRetour")){
                        openFormConfirmKit(p, KitEnum.getFromId(buttonId));
                    }
                }
            }
        }
    }

    public void openFormConfirmKit(Player p, KitEnum kit){
        String s;
        if(kit.isPro() && RankUtils.getRank(p) == Rank.PRO) {
            s = Language.CHOOSE_KIT.getLang(p);
        } else if(!p.isOp() &&  !kit.isFree() && !MySQL.hasKit(p.getUniqueId().toString(), kit.getId()) && !Rank.LEGEND.isInferior(RankUtils.getRank(p))){
            s = Language.BUY_KITS.getLang(p);
        } else {
            s = Language.CHOOSE_KIT.getLang(p);
        }
        FormWindowModal form = new FormWindowModal("Kit: " + kit.getName(), kit.getDesc(), s, Language.BACK.getLang(p));

        p.showFormWindow(form);

    }

    @EventHandler
    public void confirmKitFormRespond(PlayerFormRespondedEvent e){
        Player p = e.getPlayer();
        FormWindow window = e.getWindow();
        if(e.getResponse() != null){
            if(window instanceof FormWindowModal){
                String title = ((FormWindowModal) e.getWindow()).getTitle();
                if(title.contains("Kit: ")) {
                    String kitName = title.split(": ")[1];
                    String button = ((FormResponseModal) e.getResponse()).getClickedButtonText();
                    if (!button.equalsIgnoreCase(Language.BACK.getLang(p))) {
                        if (KitEnum.kitExist(kitName.toUpperCase())) {
                            if(button.equalsIgnoreCase(Language.CHOOSE_KIT.getLang(p))){
                                Main.getSPlayer(p).setKit(KitEnum.valueOf(kitName.toUpperCase()));
                                p.sendMessage(TextFormat.GREEN + Language.SELECTED_KIT.getLang(p).replace("[kit]", KitEnum.valueOf(kitName.toUpperCase()).getColorName()));
                            } else {
                                p.sendMessage("§c You havn't this kit.");
                            }
                        } else p.sendMessage(TextFormat.RED + Language.ERROR_WITH_KIT.getLang(p));
                    } else openFormKit(p);
                }
            }
        }
    }

    public static void applyKit(Player p, KitEnum kit){
        p.getInventory().clearAll();
        giveBaseKit(p);
        for(int i = 0; i < kit.getItems().size(); i++) {
            Item item = kit.getItems().get(i);
                if (kit.getItems().get(i).isArmor()) {
                    if(item.isHelmet()){p.getInventory().setHelmet(item);}
                    if(item.isChestplate()){p.getInventory().setChestplate(item);}
                    if(item.isLeggings()){p.getInventory().setLeggings(item);}
                    if(item.isBoots()){p.getInventory().setBoots(item);}
                } else if(item.isAxe() || item.isSword()){
                    p.getInventory().setItem(0, item);
                } else {
                    for(int y = 1; y < p.getInventory().getSize(); y++) {
                        if(p.getInventory().getItem(y).getCount() < 2) {
                            p.getInventory().setItem(y, item);
                            return;
                        }
                    }
                }
        }
        Main.getSPlayer(p).setKit(kit);
    }

    public static void giveBaseKit(Player p){
        p.getInventory().setItem(0, Item.get(276));
        p.getInventory().setHelmet(Item.get(306));
        p.getInventory().setChestplate(Item.get(307));
        p.getInventory().setLeggings(Item.get(308));
        p.getInventory().setBoots(Item.get(309));
        if(Arrays.asList(new String[]{"Android", "iOS", "PlayStation", "Xbox"}).contains(getOS(p))){
            for(int i = 0; i < p.getInventory().getSize(); i++) {
                if(p.getInventory().getItem(i).getId() == 0) {
                    p.getInventory().setItem(i, Item.get(282, 0, 34));
                    return;
                }
            }
        }
        for(int i = 0; i < p.getInventory().getSize(); i++) {
            if(p.getInventory().getItem(i).getId() == 0) {
                p.getInventory().setItem(i, Item.get(282));
            }
        }
    }

    static String getOS(Player p) {
        switch (p.getLoginChainData().getDeviceOS()) {
            case 1:
                return "Android";
            case 2:
                return "iOS";
            case 3:
                return "Mac";
            case 4:
                return "Fire";
            case 5:
                return "Gear VR";
            case 6:
                return "HoloLens";
            case 7:
                return "Windows 10";
            case 8:
                return "Windows";
            case 9:
                return "Dedicated";
            case 10:
                return "tvOS";
            case 11:
                return "PlayStation";
            case 12:
                return "NX";
            case 13:
                return "Xbox";
            default:
                return "Unknown";
        }
    }

}
