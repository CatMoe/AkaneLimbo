package catmoe.fallencrystal.limborule.util;

import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.event.EventHandler;

public class LimboCreater {
    String Address = "127.0.0.1";
    int port = 3;
    ServerInfo server = null;

    public void CreateServer(ProxiedPlayer p) {
        String target = "Limbo-" + p.getUniqueId();
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

    public void RemoveServer(ProxiedPlayer p) {
        String target = "Limbo-" + p.getUniqueId();
        if (ProxyServer.getInstance().getServers().containsKey(target)) {
            ProxyServer.getInstance().getServers().remove(target);
        }
    }

    @EventHandler
    public void AutoRemoveServer(ServerSwitchEvent e) {
        if (server == null) {
            return;
        }
        // 确认是从Limbo断开连接
        if (e.getFrom().equals(server)) {
            // 延时执行 避免玩家刚连接到服务器就被踹
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    RemoveServer(e.getPlayer());
                }
            }, 3000);
        }
    }
}