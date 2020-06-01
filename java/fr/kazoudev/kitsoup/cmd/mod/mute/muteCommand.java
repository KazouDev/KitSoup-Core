package fr.kazoudev.kitsoup.cmd.mod.mute;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.managers.MySQL;
import fr.kazoudev.kitsoup.rank.Rank;
import fr.kazoudev.kitsoup.rank.RankUtils;
import fr.kazoudev.kitsoup.utils.TimeUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

public class muteCommand extends Command {
    public muteCommand() {
        super("mute");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(Rank.HELPER.isInferior(RankUtils.getRank(p)) || p.isOp()){
                if(args.length >= 2){
                    String target = args[0];
                    for(TimeUtils t : TimeUtils.values()){
                        if(args[1].contains(t.shortcut)){
                            String time = args[1].split(t.shortcut)[0];
                            if(StringUtils.isNumeric(time)){
                                StringBuilder reason = new StringBuilder();
                                if(args.length > 2) {
                                    for (int i = 2; i < args.length; i++) {
                                        reason.append(args[i]).append(" ");
                                    }
                                } else {reason.append("Reason Undefined ");}
                                if(Main.getInstance().getServer().getPlayer(target) != null){
                                    MySQL.mutePlayer(Main.getInstance().getServer().getPlayer(target).getName(), reason.toString(), p.getName(), Integer.parseInt(time) * t.getInSecondes());
                                    Calendar cal = Calendar.getInstance();
                                    cal.add(Calendar.SECOND, Integer.parseInt(time) * t.getInSecondes());
                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                    dateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Europe/Paris")));
                                    String date = dateFormat.format(cal.getTime());
                                    Main.getInstance().getServer().getPlayer(target).sendMessage("§cYou are now muted for " + reason + "End: " +  date.replace(" ", " at "));
                                    return true;
                                }
                                MySQL.mutePlayer(target, reason.toString(), p.getName(), Integer.parseInt(time) * t.getInSecondes());
                                return true;
                            }
                        }
                    }
                } else p.sendMessage("§c/mute <player> <time> <reason>");
            }
        }
        return false;
    }
}
