package catmoe.fallencrystal.limborule.trigger;

import catmoe.fallencrystal.limborule.util.MessageUtil;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Trigger implements Listener {

    String LoginServer = "Verify";
    String LoginedServer = "Lobby-1";
    String LobbyServer = "Lobby-1";

    ServerInfo lobby = ProxyServer.getInstance().getServerInfo(LobbyServer);

    public void triggerrule(ProxiedPlayer p) {
    }

    public void handle(ProxiedPlayer p) {
    }

    @EventHandler
    public void switchByPlugin(ServerSwitchEvent e) {
        ProxiedPlayer p = e.getPlayer();
        if (e.getFrom() == GetServer(LoginServer)) {
            if (e.getPlayer().getServer().getInfo() == GetServer(LoginedServer)) {
                triggerrule(p);
            } else {
                MessageUtil.prefixchat(p, "您不能在未阅读条款的情况下跳转服务器!");
                p.connect(GetServer(LoginedServer));
            }
        }
    }

    @EventHandler
    public void DirectLogin(ServerConnectEvent e) {
        if (e.getTarget().getName().equalsIgnoreCase(LoginedServer)) {
            triggerrule(e.getPlayer());
        }
    }

    public ServerInfo GetServer(String server) {
        return ProxyServer.getInstance().getServerInfo(server);
    }
}
