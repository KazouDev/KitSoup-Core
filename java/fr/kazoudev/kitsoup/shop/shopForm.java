package fr.kazoudev.kitsoup.shop;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;
import fr.kazoudev.kitsoup.managers.Language;

import javax.xml.bind.Element;

public class shopForm implements Listener {

    public static void openShopForm(Player p){
        FormWindowSimple form = new FormWindowSimple("§6" + Language.SHOP.getLang(p), "");

        form.addButton(new ElementButton("Kits"));
        form.addButton(new ElementButton(Language.PREFIX.getLang(p)));
        form.addButton(new ElementButton(Language.PETS.getLang(p)));

        form.addButton(new ElementButton("§c" + Language.CLOSE.getLang(p)));

        p.showFormWindow(form);
    }

    @EventHandler
    public void shopResponse(PlayerFormRespondedEvent e){
        FormWindow window = e.getWindow();
        if(e.getResponse() != null){
            if(e.getWindow() instanceof FormWindowSimple){
                String title = ((FormWindowSimple) e.getWindow()).getTitle();
                if(title.equalsIgnoreCase("§6" + Language.SHOP.getLang(e.getPlayer()))){
                    String clickedText = ((FormResponseSimple) e.getResponse()).getClickedButton().getText();
                    if(clickedText.equalsIgnoreCase(Language.BACK.getLang(e.getPlayer()))) return;
                    String prefix = Language.PREFIX.getLang(e.getPlayer());
                    String pets = Language.PETS.getLang(e.getPlayer());

                    if(clickedText.equalsIgnoreCase(prefix)){
                        openPrefixShop(e.getPlayer());
                    } else if(clickedText.equalsIgnoreCase(pets)){
                        openPetsShop(e.getPlayer());
                    } else if(clickedText.equalsIgnoreCase("Kits")){
                        openKitsShop(e.getPlayer());
                    }
                }
            }
        }
    }

    private void openKitsShop(Player p) {
        FormWindowModal form = new FormWindowModal(Language.KIT_BOX.getLang(p), "", Language.BUY_BOX.getLang(p), Language.BUY_FIVE_BOX.getLang(p));
        p.showFormWindow(form);
    }

    private void openPetsShop(Player p) {
    }

    private void openPrefixShop(Player p) {
        FormWindowSimple form = new FormWindowSimple(Language.PREFIX_SHOP.getLang(p), "");
        form.addButton(new ElementButton(TextFormat.RED + Language.BACK.getLang(p)));
        p.showFormWindow(form);
    }


}
