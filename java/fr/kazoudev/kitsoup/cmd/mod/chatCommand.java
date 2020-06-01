package fr.kazoudev.kitsoup.cmd.mod;

import cn.nukkit.Nukkit;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.utils.TextFormat;
import com.sun.deploy.util.StringUtils;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.managers.Cooldown;
import fr.kazoudev.kitsoup.managers.Language;
import fr.kazoudev.kitsoup.rank.Rank;
import fr.kazoudev.kitsoup.rank.RankUtils;

public class chatCommand extends Command {
    public chatCommand() {
        super("chat");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        String chatAlready = "";
        String chatSet = "";
        if (sender instanceof ConsoleCommandSender) {
            chatAlready = Language.CHAT_ALREADY.english;
            chatSet = Language.CHAT_SET.english;
        } else if (sender instanceof Player) {
            Player p = (Player) sender;
            if (Rank.MOD.isInferior(RankUtils.getRank(p)) || p.isOp()) {
                chatAlready = Language.CHAT_ALREADY.getLang(p);
                chatSet = Language.CHAT_SET.getLang(p);
            } else {
                p.sendMessage(TextFormat.RED + Language.NO_PERM.getLang(p));
                return false;
            }
            if (args.length <= 0) {
                sender.sendMessage("§c/chat on/off");
                return false;
            }

            switch (args[0]) {
                case "on":
                    if (!Main.getInstance().chatActive) {
                        Main.getInstance().chatActive = true;
                        sender.sendMessage(TextFormat.GREEN + chatSet + args[0]);
                        return true;
                    }
                    sender.sendMessage(TextFormat.RED + chatAlready + args[0]);
                    break;
                case "off":
                    if (Main.getInstance().chatActive) {
                        Main.getInstance().chatActive = false;
                        sender.sendMessage(TextFormat.GREEN + chatSet + args[0]);
                        return true;
                    }
                    sender.sendMessage(TextFormat.RED + chatAlready + args[0]);
                    break;
                case "clear":
                    for(int i = 0; i < 100; i++){
                        Main.getInstance().getServer().broadcastMessage(" ");
                    }
                    Main.getInstance().getServer().broadcastMessage("§aChat clear by " + p.getName());
                    break;
                case "slowmod":
                    if(!(args.length < 2)){
                        try{
                            int s = Integer.parseInt(args[1]);
                            Cooldown.CHAT.setTime(s);
                            p.sendMessage("§a" + Language.CHAT_COOLDOWN_SET.getLang(p).replace("[time]", args[1]));
                        } catch(NumberFormatException e){
                            p.sendMessage("§c/chat slowmod <number>");
                        }
                    } else {
                        p.sendMessage("§c/chat slowmod <number>");
                    }
                default:
                    break;
            }

        }
        return false;
    }
}
