package catmoe.fallencrystal.limborule.command.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import catmoe.fallencrystal.limborule.command.SubCommand;
import catmoe.fallencrystal.limborule.util.LimboCreater;
import catmoe.fallencrystal.limborule.util.MessageUtil;
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
            limbo.CreateServer((ProxiedPlayer) sender);
            limbo.Connect((ProxiedPlayer) sender);
        }
        if (args[1].equalsIgnoreCase("all")) {
            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                limbo.CreateServer(p);
                limbo.Connect(p);
            }
        } else {
            ProxiedPlayer p = null;
            // 判定是否有这个玩家 如果非玩家通常会抛出错误
            try {
                p = ProxyServer.getInstance().getPlayer(args[1]);
            } catch (Exception e) {
                return; // 非玩家
            }
            limbo.CreateServer(p);
            limbo.Connect(p);
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
        return 3;
    }

}
