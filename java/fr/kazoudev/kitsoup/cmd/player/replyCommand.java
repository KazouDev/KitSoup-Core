package fr.kazoudev.kitsoup.cmd.player;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.SoupPlayer;

public class replyCommand extends Command {
    public replyCommand() {
        super("reply");
        this.setAliases(new String[]{"r"});
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player){
            if(args.length > 0) {
                SoupPlayer sp = Main.getSPlayer((Player) sender);
                if(sp.getLatestMsg() != null){
                    StringBuilder s = new StringBuilder();
                    for(int i = 1; i < args.length; i++){
                        s.append(args[i]).append(" ");
                    }

                    sender.sendMessage(TextFormat.ITALIC + "§7(Send to §b" + sp.getLatestMsg().getName() + "§7) " + s);
                    sp.getLatestMsg().sendMessage(TextFormat.ITALIC + "§7(§b" + sender.getName() + " §7send to you) " + s);

                    Main.getSPlayer((Player) sender).setLatestMsg(sp.getLatestMsg());
                    Main.getSPlayer(sp.getLatestMsg()).setLatestMsg((Player) sender);

                    for(Player p : Main.getInstance().whisper){
                        if(p != sender && p != sp.getPlayer()){
                            p.sendMessage(TextFormat.ITALIC + "§5(§5" + sender.getName() + "§7 send to " + sp.getPlayer().getName() + "§5)§7 " + s);
                        }
                    }

                } else sender.sendMessage("§cYou havn't player to respond.");
            }
        }
        return false;
    }
}
