package fr.kazoudev.kitsoup.cmd.player;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.hostevent.EventManager;
import fr.kazoudev.kitsoup.hostevent.EventType;
import fr.kazoudev.kitsoup.managers.Language;
import fr.kazoudev.kitsoup.rank.Rank;
import fr.kazoudev.kitsoup.rank.RankUtils;

import java.text.DateFormat;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class eventCommand extends Command {
    public eventCommand() {
        super("event");
        setDescription("Start event");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(Rank.LEGEND.isInferior(RankUtils.getRank(p)) || p.isOp()){
                if(Main.getInstance().event == null) {
                    if(Rank.ADMIN.isInferior(RankUtils.getRank(p))){
                        eventForm(p);
                        return false;
                    } else if(Main.getInstance().eCooldown(900)) {
                        eventForm(p);
                    } else {
                        Duration duration = Duration.ofMillis(System.currentTimeMillis() - Main.getInstance().lastEvent);

                        p.sendMessage(TextFormat.RED + Language.EVENT_COOLDOWN.getLang(p) + " " + (29 - duration.toMinutes()) + "m " +  (60 - duration.minusMinutes(duration.toMinutes()).getSeconds()) + "s");
                    }
                } else p.sendMessage("§c" + Language.EVENT_ALREADY.getLang(p));
            } else p.sendMessage("§c" + Language.NO_PERM.getLang(p));
        } else sender.sendMessage("§cImpossible via la console.");
        return false;
    }

    public void eventForm(Player player) {
        FormWindowSimple form = new FormWindowSimple("§cEvents", "Start event on the server");
        for (EventType event : EventType.values()) {
            form.addButton(new ElementButton(event.getName()));
        }
        form.addButton(new ElementButton("§c" + Language.CLOSE.getLang(player)));
        player.showFormWindow(form);
    }

}
