package fr.kazoudev.kitsoup.cmd.mod;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.level.Location;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.SoupPlayer;
import fr.kazoudev.kitsoup.rank.Rank;
import fr.kazoudev.kitsoup.rank.RankUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class seeCommand extends Command {
    public seeCommand() {
        super("see");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof ConsoleCommandSender || Rank.MOD.isInferior(RankUtils.getRank((Player) sender))){
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            if(args.length >= 1){
                if(Main.getInstance().nickedName.get(args[0]) != null){
                    SoupPlayer sp = Main.getSPlayer(Main.getInstance().nickedName.get(args[0]));
                    sender.sendMessage("Name: " + sp.getPlayer().getName());
                    sender.sendMessage("Nick: " + sp.getNick());
                    sender.sendMessage("Coins: " + sp.getCoins());
                    if(sp.getDeath() != 0) {
                        sender.sendMessage("KD: " + sp.getKill() / sp.getDeath());
                    } else {
                        sender.sendMessage("KD: " + sp.getKill());
                    }
                    sender.sendMessage("Kit: " + sp.getKit().getColorName());
                    sender.sendMessage("AllowFlight: " + sp.getPlayer().getAdventureSettings().get(AdventureSettings.Type.ALLOW_FLIGHT));
                    sender.sendMessage("Speed: " + sp.getPlayer().getMovementSpeed());
                    sender.sendMessage("Playtime: " + format.format(new Date(sp.getPlayTime() * 1000)));
                    return false;
                }
                if(Main.getInstance().getServer().getPlayer(args[0]) != null){
                    if(Main.getInstance().getServer().getPlayer(args[0]).isOnline()){
                        SoupPlayer sp = Main.getSPlayer(Main.getInstance().getServer().getPlayer(args[0]));
                        sender.sendMessage("Name: " + sp.getPlayer().getName());
                        sender.sendMessage("Nick: Unnick");
                        sender.sendMessage("Coins: " + sp.getCoins());
                        sender.sendMessage("Rank: " + Rank.valueOf(sp.getRank()).colorName);
                        if(sp.getDeath() != 0) {
                            sender.sendMessage("KD: " + sp.getKill() / sp.getDeath());
                        } else {
                            sender.sendMessage("KD: " + sp.getKill());
                        }
                        sender.sendMessage("Kit: " + sp.getKit().getColorName());
                        sender.sendMessage("AllowFlight: " + sp.getPlayer().getAdventureSettings().get(AdventureSettings.Type.ALLOW_FLIGHT));
                        sender.sendMessage("Speed: " + sp.getPlayer().getMovementSpeed());
                        sender.sendMessage("Playtime: " + format.format(new Date(sp.getPlayTime() * 1000)));

                    } else sender.sendMessage("§cThis player isn't online.");
                } else sender.sendMessage("§cPlayer not found.");
            } else sender.sendMessage("§c/see <player>");
        } else sender.sendMessage("§cNo permission's");
        return false;
    }
}
