package fr.kazoudev.kitsoup.cmd.admin;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.network.protocol.ScriptCustomEventPacket;
import cn.nukkit.utils.Binary;
import net.md_5.bungee.api.ProxyServer;
import org.iq80.leveldb.util.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class testCommand extends Command {
    public testCommand() {
        super("test");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        Player p = (Player) sender;
        ScriptCustomEventPacket pk = new ScriptCustomEventPacket();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream a = new DataOutputStream(out);
        try {
            a.writeUTF("Connect");
            a.writeUTF("lobby");
            pk.eventName = "bungeecord:main";
            pk.eventData = out.toByteArray();
            p.dataPacket(pk);
        } catch (Exception e) {
            Server.getInstance().getLogger().warning("Error while transferring ( PLAYER: " + sender.getName() + " | DEST: " + "lobby" + " )");
            Server.getInstance().getLogger().logException(e);
            return false;
        }
        return false;
    }

    public void createNewHost(String name, String id){
        File template = new File("/root/host/template");
        File file = new File("/root/host/" + name + "_" + id);
        boolean bool = file.mkdirs();
        if(bool){
            Server.getInstance().getLogger().info("[FOLDER] Host: " + name + " " + id);
        } else {
            Server.getInstance().getLogger().info("[FOLDER] Host: " + name + " " + id + " can't created.");
        }

        FileUtils.copyDirectoryContents(template, file);
    }
}
