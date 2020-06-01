package fr.kazoudev.kitsoup.cmd.admin;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.managers.Language;

public class stopCommand extends Command {
    public stopCommand() {
        super("stop");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof ConsoleCommandSender || sender.isOp()){
            for(Player p : Main.getInstance().getServer().getOnlinePlayers().values()){
                p.close("", "§cRestart...");
            }
            Main.getInstance().getServer().shutdown();
        } else sender.sendMessage("§cWtf ?");
        return false;
    }
}
