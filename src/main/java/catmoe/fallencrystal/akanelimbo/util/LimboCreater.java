package catmoe.fallencrystal.akanelimbo.util;

import java.net.InetSocketAddress;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerKickEvent;

public class LimboCreater {
    String Address = "127.0.0.1";
    int port = 3;
    ServerInfo server = null;

    public void CreateServer(ProxiedPlayer p, String name) {
        String target = "AkaneLimbo -> " + name;
        server = ProxyServer.getInstance().constructServerInfo(target,
                new InetSocketAddress(Address, port),
                "Limbo for player" + p.getDisplayName() + "(" + p.getUniqueId() + ")", false);
    }

    public void Connect(ProxiedPlayer p) {
        p.connect(server);
    }

    public void Connect2(ServerKickEvent e) {
        e.setCancelled(true);
        e.setCancelServer(server);
    }

    public void RemoveServer(ProxiedPlayer p, String name) {
        String target = "AkaneLimbo -> " + name;
        if (ProxyServer.getInstance().getServers().containsKey(target)) {
            ProxyServer.getInstance().getServers().remove(target);
        }
    }
}
