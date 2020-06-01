package fr.kazoudev.kitsoup.cmd.mod;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.managers.Language;
import fr.kazoudev.kitsoup.rank.Rank;
import fr.kazoudev.kitsoup.rank.RankUtils;

public class whisperCommand extends Command {
    public whisperCommand() {
        super("whisper");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(Rank.MOD.isInferior(RankUtils.getRank(p))){
                if(Main.getInstance().whisper.contains(p)){
                    Main.getInstance().whisper.remove(p);
                    p.sendMessage("Whisper: OFF");
                } else {
                    Main.getInstance().whisper.add(p);
                    p.sendMessage("Whisper: ON");
                }
            } else p.sendMessage("§c" + Language.NO_PERM.getLang(p));
        } else sender.sendMessage("§cVous devez en jeux.");
        return false;
    }
}
