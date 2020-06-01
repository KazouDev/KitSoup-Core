package fr.kazoudev.kitsoup.cmd.mod;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.utils.TextFormat;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.rank.Rank;
import fr.kazoudev.kitsoup.rank.RankUtils;

import java.util.ArrayList;

public class freezeCommand extends Command {

    public freezeCommand() {
        super("freeze");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof ConsoleCommandSender || Rank.MOD.isInferior(RankUtils.getRank((Player) sender))){
            if(args.length >= 1){
                if(Main.getInstance().getServer().getPlayer(args[0]) != null){
                    Player frozen = Main.getInstance().getServer().getPlayer(args[0]);
                        if(Main.getSPlayer(frozen).isFreeze()){
                            Main.getSPlayer(frozen).setFreeze(false);
                            sender.sendMessage("§aYou have unfreeze: " + frozen.getName());
                            frozen.sendMessage("§aYou are now unfreeze.");
                        } else {
                            Main.getSPlayer(frozen).setFreeze(true);
                            sender.sendMessage("§aYou have freeze: " + frozen.getName());
                            frozen.sendMessage("§cYou are now freeze.");
                        }
                } else sender.sendMessage("§c" + args[0] + " not found");
            } else sender.sendMessage("§c/freeze <player>");
        } else sender.sendMessage(TextFormat.RED + "No permission's");
        return false;
    }
}
