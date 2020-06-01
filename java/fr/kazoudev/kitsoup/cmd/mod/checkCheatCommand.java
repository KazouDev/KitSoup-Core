package fr.kazoudev.kitsoup.cmd.mod;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Location;
import cn.nukkit.scheduler.NukkitRunnable;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.rank.Rank;
import fr.kazoudev.kitsoup.rank.RankUtils;

public class checkCheatCommand extends Command {
    public checkCheatCommand() {
        super("checkcheat");
        this.setAliases(new String[]{"cc", "ccheat"});
        this.setDescription("Cheat cheat command.");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(Rank.MOD.isInferior(RankUtils.getRank(p))){
                if(args.length >= 2){
                    if(Main.getInstance().getServer().getPlayer(args[1]) != null){
                        if(args[0].equalsIgnoreCase("v")){
                            Player target = Main.getInstance().getServer().getPlayer(args[1]);
                            Location loc = target.getLocation();
                            target.setMotion(target.getLocation().getDirectionVector().add(0.0D, 5D, 0.0D));
                            new NukkitRunnable() {
                                @Override
                                public void run() {
                                    target.teleport(loc);
                                }
                            }.runTaskLater(Main.getInstance(), 40);
                        }
                    } else p.sendMessage("§c/cc <cheat> <player>");
                }
            }
        }
        return false;
    }
}
