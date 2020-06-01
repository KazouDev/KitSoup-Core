package fr.kazoudev.kitsoup.cmd.mod.mute;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import fr.kazoudev.kitsoup.managers.Language;
import fr.kazoudev.kitsoup.managers.MySQL;
import fr.kazoudev.kitsoup.rank.Rank;
import fr.kazoudev.kitsoup.rank.RankUtils;

public class unmuteCommand extends Command {
    public unmuteCommand() {
        super("unmute");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(Rank.HELPER.isInferior(RankUtils.getRank(p)) || p.isOp()){
                if(args.length > 0){
                    if(MySQL.isMute(args[0])){
                        MySQL.remMute(args[0]);
                        p.sendMessage("§aYou have unmute " + args[0]);
                    } else p.sendMessage("§cCe joueur n'est pas mute.");
                } else p.sendMessage("§c/unmute <player>");
            } else p.sendMessage("§c" + Language.NO_PERM.getLang(p));
        }
        return false;
    }
}
