package fr.kazoudev.kitsoup.cmd.mod;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.level.Location;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.kits.KitEnum;
import fr.kazoudev.kitsoup.managers.Language;
import fr.kazoudev.kitsoup.managers.MySQL;
import fr.kazoudev.kitsoup.rank.Rank;
import fr.kazoudev.kitsoup.rank.RankUtils;

import java.util.UUID;

public class kitCommand extends Command {
    public kitCommand() {
        super("kit");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;
        if (Rank.MOD.isInferior(RankUtils.getRank(p)) || p.isOp()) {
            if (args.length >= 2) {
                if (Main.getInstance().getServer().getPlayerExact(args[0]) != null) {
                    IPlayer target = Main.getInstance().getServer().getPlayerExact(args[0]);
                    switch (args[1]) {
                        case "list":
                            openPlayerKitForm(target.getUniqueId().toString(), p);
                            break;

                        case "add":
                            if(Rank.ADMIN.isInferior(RankUtils.getRank(p)) || p.isOp()) {
                                if (args.length >= 3) {
                                    if (KitEnum.kitExist(args[2].toUpperCase())) {
                                        MySQL.addKits(target.getUniqueId().toString(), KitEnum.valueOf(args[2].toUpperCase()).getId(), 0);
                                        p.sendMessage("§aKit §e" + KitEnum.valueOf(args[2].toUpperCase()).getName() + "§a ajouté aux kits de §e" + target.getName());
                                    } else {
                                        p.sendMessage("§cCe kit n'existe pas.");
                                    }
                                }
                            }
                            break;
                        case "rem":
                            if(Rank.ADMIN.isInferior(RankUtils.getRank(p)) || p.isOp()) {
                                if (args.length >= 3) {
                                    if (KitEnum.kitExist(args[2].toUpperCase())) {
                                        if(MySQL.hasKit(target.getUniqueId().toString(), KitEnum.valueOf(args[2].toUpperCase()).getId())) {
                                            MySQL.remKits(target.getUniqueId().toString(), KitEnum.valueOf(args[2].toUpperCase()).getId());
                                            p.sendMessage("§aKit §e" + KitEnum.valueOf(args[2].toUpperCase()).getName() + "§a ajouté aux kits de §e" + target.getName());
                                        } else {
                                            p.sendMessage("§cCe joueur n'a pas ce kit.");
                                        }
                                    } else {
                                        p.sendMessage("§cCe kit n'existe pas.");
                                    }
                                }
                            }
                            break;
                    }
                } else {
                    p.sendMessage("§cJoueur Introuvable.");
                }
            } else p.sendMessage("§c/kit <player> <list/add/rem> (kit)");
        } else {p.sendMessage("§cNo Permissions");}
        return false;
    }

    public static void openPlayerKitForm(String uid, Player mod){
        FormWindowSimple form = new FormWindowSimple("Kit for " + Main.getInstance().getServer().getOfflinePlayer(UUID.fromString(uid)).getName(), "");

        for(KitEnum kits : KitEnum.values()){
            if(MySQL.hasKit(uid, kits.getId())) {
                form.addButton(new ElementButton(kits.getName() + MySQL.getIsLifeKit(uid, kits.getId())));
            } else {
                form.addButton(new ElementButton(kits.getName()));
            }
        }

        form.addButton(new ElementButton(Language.CLOSE.getLang(mod)));
        mod.showFormWindow(form);

    }
}

