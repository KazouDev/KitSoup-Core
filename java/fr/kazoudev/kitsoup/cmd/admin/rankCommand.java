package fr.kazoudev.kitsoup.cmd.admin;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.managers.Language;
import fr.kazoudev.kitsoup.rank.Rank;
import fr.kazoudev.kitsoup.rank.RankUtils;

public class rankCommand extends Command {

    private Main main;

    public rankCommand(Main main) {
        super("rank");
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;
        if(RankUtils.getRank(p) == Rank.ADMIN || p.isOp()) {
            if (args.length > 0) {
                if(Main.getInstance().getServer().getPlayerExact(args[0]) != null) {
                    Player target = Main.getInstance().getServer().getPlayer(args[0]);
                    if (args.length >= 2 && args[1].equalsIgnoreCase("set")) {
                        if (args.length >= 3 && RankUtils.rankExist(args[2].toUpperCase())) {
                            RankUtils.updateRank(target, Rank.valueOf(args[2].toUpperCase()));
                            sender.sendMessage("§e[§eRank]§e Tu as mis §c" + target.getName() + " " + Rank.valueOf(args[2].toUpperCase()).colorName);
                        } else p.sendMessage(TextFormat.RED + "Ce rank n'existe pas.");

                    } else if (args.length >= 2 && args[1].equalsIgnoreCase("get")) {
                        Rank prank = Rank.valueOf(Main.getSPlayer(p).getRank());
                        p.sendMessage("§e[§eRank]§e §c" + target.getName() + "§e est " + prank.colorName);
                    } else p.sendMessage(TextFormat.RED + "/rank <Player> <set/get> (rank)");

                } else p.sendMessage(TextFormat.RED + "Player not found");

            } else p.sendMessage(TextFormat.RED + "/rank <Player> <set/get> (rank)");

        } else p.sendMessage(TextFormat.RED + Language.NO_PERM.getLang(p));

        return false;
    }
}
