package catmoe.fallencrystal.akanelimbo.command.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import catmoe.fallencrystal.akanelimbo.command.SubCommand;
import catmoe.fallencrystal.akanelimbo.util.LimboCreater;
import catmoe.fallencrystal.akanelimbo.util.MessageUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SendLimbo implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        // 创建一个LimboCreater
        LimboCreater limbo = new LimboCreater();
        // 将自己发送到Limbo
        if (args[1].equalsIgnoreCase("me")) {
            if (!(sender instanceof ProxiedPlayer)) {
                MessageUtil.prefixsender(sender, "&cConsole is a invalid target.");
                return;
            }
            limbo.CreateServer((ProxiedPlayer) sender, "CmdCreate");
            limbo.Connect((ProxiedPlayer) sender);
        }
        if (args[1].equalsIgnoreCase("all")) {
            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                limbo.CreateServer(p, "CmdCreate");
                limbo.Connect(p);
            }
        } else {
            try {
                ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[1]);
                if (p != null) {
                    limbo.CreateServer(p, "CmdCreate");
                    limbo.Connect(p);
                }
            } catch (Exception e) {
                return;
            }
        }
    }

    @Override
    public String getSubCommandId() {
        return "sendlimbo";
    }

    @Override
    public String getPermission() {
        return "akanelimbo.sendlimbo";
    }

    @Override
    public Map<Integer, List<String>> getTabCompleter() {
        Map<Integer, List<String>> map = new HashMap<>();
        ArrayList<String> Tip1 = new ArrayList<>();
        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            Tip1.add(p.toString());
        }
        Tip1.add("me");
        Tip1.add("all");
        map.put(1, Tip1);
        return map;
    }

    @Override
    public boolean allowedConsole() {
        return true;
    }

    @Override
    public boolean StrictSizeLimit() {
        return true;
    }

    @Override
    public int StrictSize() {
        return 2;
    }

}
