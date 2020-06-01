package fr.kazoudev.kitsoup.cmd.player;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class infoCommand extends Command {
    public infoCommand() {
        super("info");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            p.sendMessage("§cAdmin Team: KazouSurLeNet");
            p.sendMessage("§cGamemode: Jump into the arena, kill player's and win coins for buy new kits with abilities.");
            p.sendMessage("§cDiscord: discord.gg/kitsoup");
            p.sendMessage("§cSeason: 1");
        }
        return false;
    }
}
