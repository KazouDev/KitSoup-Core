package fr.kazoudev.kitsoup.cmd.mod.ban;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import fr.kazoudev.kitsoup.managers.Language;
import fr.kazoudev.kitsoup.managers.MySQL;
import fr.kazoudev.kitsoup.rank.Rank;
import fr.kazoudev.kitsoup.rank.RankUtils;

public class unbanCommand extends Command {
    public unbanCommand() {
        super("unban");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(Rank.MOD.isInferior(RankUtils.getRank(p)) || p.isOp()){
                if(args.length > 0){
                    if(MySQL.isBanned(args[0])){
                        MySQL.remBan(args[0]);
                        p.sendMessage("§aYou have unban " + args[0]);
                    } else p.sendMessage("§cCe joueur n'est pas banni.");
                } else p.sendMessage("§c/unban <player>");
            } else p.sendMessage("§c" + Language.NO_PERM.getLang(p));
        }
        return false;
    }
}
