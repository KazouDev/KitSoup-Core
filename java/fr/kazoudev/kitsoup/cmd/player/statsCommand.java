package fr.kazoudev.kitsoup.cmd.player;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.entity.item.EntityFallingBlock;
import cn.nukkit.entity.passive.EntityPig;
import cn.nukkit.entity.passive.EntityVillager;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.network.protocol.types.EntityLink;
import cn.nukkit.utils.TextFormat;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.managers.Language;

import java.util.ArrayList;
import java.util.Arrays;

public class statsCommand extends Command {
    public statsCommand() {
        super("stats");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length < 1){
                p.sendMessage(TextFormat.GOLD + Language.YOUR_STATS.getLang(p));
                p.sendMessage("Kill: " + Main.getSPlayer(p).getKill());
                p.sendMessage("Death: " + Main.getSPlayer(p).getDeath());
                p.sendMessage("Ratio (K/D): " + (double) Main.getSPlayer(p).getKill() / (Main.getSPlayer(p).getDeath() == 0 ? 1 : Main.getSPlayer(p).getDeath()));
            } else {
                if(Main.getInstance().getServer().getPlayer(args[0]) != null){
                    Player target = Main.getInstance().getServer().getPlayer(args[0]);
                    p.sendMessage(TextFormat.GOLD + Language.STATS_OF.getLang(p));
                    p.sendMessage("Kill: " + Main.getSPlayer(target).getKill());
                    p.sendMessage("Death: " + Main.getSPlayer(target).getDeath());
                    p.sendMessage("Ratio (K/D): " + (double) Main.getSPlayer(target).getKill() / (Main.getSPlayer(p).getDeath() == 0 ? 1 : Main.getSPlayer(p).getDeath()));
                }
            }
        }
        return false;
    }
}
