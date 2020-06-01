package fr.kazoudev.kitsoup.events;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.utils.TextFormat;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.managers.Cooldown;
import fr.kazoudev.kitsoup.managers.Language;
import fr.kazoudev.kitsoup.managers.MySQL;
import fr.kazoudev.kitsoup.rank.Rank;
import fr.kazoudev.kitsoup.rank.RankUtils;

import javax.xml.soap.Text;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class chatEvent implements Listener {

    @EventHandler
    public void sendMessageEvent(PlayerChatEvent e){
        if(Main.getInstance().chatActive || Rank.MOD.isInferior(RankUtils.getRank(e.getPlayer())) || e.getPlayer().isOp()){
            if(MySQL.isMute(e.getPlayer().getName())){
                try {
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    dateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Europe/Paris")));
                    Date end = dateFormat.parse(MySQL.getMuteDate(e.getPlayer().getName()));
                    if(!end.before(dateFormat.parse(dateFormat.format(Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris")).getTime())))){
                        e.getPlayer().sendMessage("§cYou are muted for §e" + MySQL.getMuteReason(e.getPlayer().getName()) + "\n§cEnd: " + dateFormat.format(end).replace(" ", " at "));
                        e.setCancelled(true);
                    } else {
                        MySQL.remMute(e.getPlayer().getName());
                    }
                } catch (ParseException ex){ }
            }
            if(!Main.getSPlayer(e.getPlayer()).isCooldown(Cooldown.CHAT) || e.getPlayer().isOp() || Rank.LEGEND.isInferior(RankUtils.getRank(e.getPlayer()))){
                if (Main.getSPlayer(e.getPlayer()).getNick() != null) {
                    e.setFormat(Rank.PLAYER.chatFormat.replace("name", "{%0}").replace("msg", "{%1}").replace("prefix ", ""));
                } else {
                    e.setFormat(RankUtils.getRank(e.getPlayer()).chatFormat.replace("name", "{%0}").replace("msg", "{%1}").replace("prefix ", Main.getSPlayer(e.getPlayer()).getPrefix()));
                }
                Main.getSPlayer(e.getPlayer()).addCooldown(Cooldown.CHAT);
            } else {
                e.setCancelled(true);
                e.getPlayer().sendMessage(TextFormat.RED + Language.CHAT_COOLDOWN.getLang(e.getPlayer()).replace("[time]", ""+Main.getSPlayer(e.getPlayer()).getTimeCooldown(Cooldown.CHAT)));
            }
        } else {
            e.setCancelled(true);
            e.getPlayer().sendMessage(TextFormat.RED + Language.CHAT_OFF.getLang(e.getPlayer()).replace("[time]", ""+Main.getSPlayer(e.getPlayer()).getTimeCooldown(Cooldown.CHAT)));
        }
    }
}
