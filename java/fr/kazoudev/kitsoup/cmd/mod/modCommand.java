package fr.kazoudev.kitsoup.cmd.mod;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentKnockback;
import cn.nukkit.utils.TextFormat;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.managers.Language;
import fr.kazoudev.kitsoup.rank.Rank;
import fr.kazoudev.kitsoup.rank.RankUtils;

import java.rmi.MarshalledObject;

public class modCommand extends Command {
    public modCommand() {
        super("mod");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(Rank.MOD.isInferior(RankUtils.getRank(p)) || p.isOp()){
                if(!Main.getInstance().mod.contains(p)) {
                    p.getInventory().clearAll();
                    Item kb = Item.get(ItemID.STICK);
                    kb.setCustomName("§eKB");
                    kb.addEnchantment(Enchantment.get(Enchantment.ID_KNOCKBACK).setLevel(2));
                    p.getInventory().setItem(0, Item.get(ItemID.BOOK).setCustomName("§cInvSee").setLore("Right Click on Player"));
                    p.getInventory().setItem(1, kb);
                    p.getInventory().setItem(2, Item.get(ItemID.ENDER_EYE).setCustomName("Random Player"));
                    p.getInventory().setItem(3, Item.get(Item.ICE).setCustomName("§bFreeze"));
                    p.getInventory().setItem(4, Item.get(Item.PAPER).setCustomName("§7See"));
                    p.getInventory().setItem(5, Item.get(Item.GLASS).setCustomName(Main.getInstance().vanish.contains(p) ? "§aVanish ON" : "§aVanish §cOFF"));
                    p.getInventory().setItem(8, Item.get(Item.LEVER).setCustomName(Main.getInstance().whisper.contains(p) ? "§aWhisper ON" : "§aWhisper §cOFF"));

                    p.teleport(Main.getInstance().spawn);

                    p.getAdventureSettings().set(AdventureSettings.Type.ALLOW_FLIGHT, true);
                    p.getAdventureSettings().update();
                    p.setCheckMovement(false);

                    Main.getInstance().mod.add(p);
                } else {
                    p.getInventory().clearAll();
                    p.getInventory().setItem(0, Item.get(272).setCustomName(TextFormat.RED + "Kits"));
                    p.getInventory().setItem(8, Item.get(356).setCustomName(TextFormat.RED + Language.SETTINGS.getLang(p)));
                    p.getInventory().setItem(4, Item.get(266).setCustomName(TextFormat.GOLD + Language.SHOP.getLang(p)));

                    Main.getInstance().mod.remove(p);

                    p.getAdventureSettings().set(AdventureSettings.Type.ALLOW_FLIGHT, false);
                    p.getAdventureSettings().update();


                }

            } else p.sendMessage(Language.NO_PERM.getLang(p));
        } else sender.sendMessage("§CYou must be a Player.");
        return false;
    }
}
