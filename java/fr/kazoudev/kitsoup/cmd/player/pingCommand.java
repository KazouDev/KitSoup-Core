package fr.kazoudev.kitsoup.cmd.player;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import fr.kazoudev.kitsoup.Main;

public class pingCommand extends Command {
    public pingCommand() {
        super("ping");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length <= 0){
                p.sendMessage("§aTon ping: §e" + p.getPing() + " §ams");
            } else {
                if(Main.getInstance().getServer().getOnlinePlayers().containsValue(Main.getInstance().getServer().getPlayer(args[0]))){
                    Player target = Main.getInstance().getServer().getPlayer(args[0]);
                    p.sendMessage("§aPing de " + target.getName() + "§a: §e" + target.getPing());
                }
            }
        }
        return false;
    }
}
