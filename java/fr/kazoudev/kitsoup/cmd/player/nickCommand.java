package fr.kazoudev.kitsoup.cmd.player;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.managers.Cooldown;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class nickCommand extends Command {
    public nickCommand() {
        super("nick");
        this.setDescription("Nick yourself");
    }

    private final List<String> nicked = Arrays.asList("Argga", "Kerabou", "Alph0", "BlueWater12");

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player){
                    if (Main.getSPlayer((Player) sender).getNick() != null) {
                        sender.sendMessage("§aYou are now unnicked.");
                        Main.getSPlayer((Player) sender).unNicked();
                        return false;
                    }
                    if(!Main.getSPlayer((Player) sender).isCooldown(Cooldown.NICK_CMD)) {
                        int i = 0;
                        while (Main.getInstance().nickedName.containsKey(nicked.get(i))) {
                            sender.sendMessage(nicked.get(i) + " est déjà pris.");
                            i++;
                            if (nicked.size() <= i) {
                                sender.sendMessage(i + " n'existe pas");
                                return false;
                            }
                        }
                        Main.getSPlayer((Player) sender).setNick(nicked.get(i));
                        sender.sendMessage("§aYou are now nicked: §e" + nicked.get(i));
                    } else {
                        int seconds = (int) Main.getSPlayer((Player) sender).getTimeCooldown(Cooldown.NICK_CMD);
                        int minutes = (seconds % 3600) / 60;
                        seconds = seconds % 60;
                        sender.sendMessage("§cYou are in cooldown, " + minutes + "m " + (seconds) );
                    }
            } else sender.sendMessage("§c/nick <name>");
        return false;
        }
    }
