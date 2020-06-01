package fr.kazoudev.kitsoup.cmd.player;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import fr.kazoudev.kitsoup.Main;

public class msgCommand extends Command {
    public msgCommand() {
        super("message");
        this.setAliases(new String[]{"m", "msg"});
        this.setDescription("View Player Ping");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(args.length >= 2){
            if(Main.getInstance().getServer().getPlayer(args[0]) != null){
                Player receiver = Main.getInstance().getServer().getPlayer(args[0]);
               if(receiver == sender){sender.sendMessage("§cTu ne peut pas te mp toi même."); return false;}
                if(Main.getInstance().getServer().getOnlinePlayers().containsKey(receiver.getUniqueId())){
                    StringBuilder s = new StringBuilder();
                    for(int i = 1; i<args.length; i++){
                        s.append(args[i]).append(" ");
                    }
                    sender.sendMessage(TextFormat.ITALIC + "§7(Send to §b" + receiver.getName() + "§7) " + s);
                    receiver.sendMessage(TextFormat.ITALIC + "§7(§b" + receiver.getName() + " §7send to you) " + s);

                    Main.getSPlayer((Player) sender).setLatestMsg(receiver);
                    Main.getSPlayer(receiver).setLatestMsg((Player) sender);

                    for(Player p : Main.getInstance().whisper){
                        if(p != sender && p != receiver){
                            p.sendMessage(TextFormat.ITALIC + "§5(§5" + sender.getName() + "§7 send to " + receiver.getName() + "§5) §7" + s);
                        }
                    }

                } else sender.sendMessage("§cThis player isn't online.");
            } else sender.sendMessage("§cThis player doesn't exist.");
        } else sender.sendMessage("§c/msg <player> <message>");
        return false;
    }
}
