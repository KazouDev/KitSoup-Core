package fr.kazoudev.kitsoup.cmd.mod;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.utils.TextFormat;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.rank.Rank;
import fr.kazoudev.kitsoup.rank.RankUtils;

public class kickCommand extends Command {
    public kickCommand() {
        super("kick");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof ConsoleCommandSender || Rank.MOD.isInferior(RankUtils.getRank((Player) sender))){
            if(args.length >= 1){
                if(Main.getInstance().getServer().getPlayerExact(args[0]) != null){
                    Player target = Main.getInstance().getServer().getPlayerExact(args[0]);
                    if(Main.getInstance().getServer().getOnlinePlayers().containsKey(target.getUniqueId())){
                        if (args.length > 1) {
                            StringBuilder reason = new StringBuilder();
                            for(int i = 1; i<args.length; i++){
                                reason.append(args[i]).append(" ");
                            }
                            target.close("",
                                    "                  §e[§cSanctions§e]\n" +
                                    "You are been kicked by: §e" + sender.getName() +
                                    "\n§cReason: " + reason);
                        } else target.close("",
                                "                  §e[§cSanctions§e]\n" +
                                        "You are been kicked by: §e" + sender.getName() +
                                        "\n§cReason Undefined");
                    }
                }
            }
        }
        return false;
    }
}
