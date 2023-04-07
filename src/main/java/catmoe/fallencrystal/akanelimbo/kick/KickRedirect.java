package catmoe.fallencrystal.akanelimbo.kick;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import catmoe.fallencrystal.akanelimbo.util.LimboCreater;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class KickRedirect implements Listener {
    ProxiedPlayer target = null;

    String LobbyServer = "Lobby-1";

    KickMenu menu = new KickMenu();

    /*
     * Banned - Ban Reason
     * Cheating - Ban Reason
     * [Proxy] - [Proxy] lost connect to server.
     * Violations - Ban Reason
     * Null - ReadTimedOut : null
     */
    static List<String> DontRedirectReason = Arrays.asList("Banned", "Cheating", "[Proxy]", "Violations", "Null");
    static List<String> DontRedirectServer = Arrays.asList("LoginLimbo", "MainLimbo");

    LimboCreater limbo = new LimboCreater();

    @EventHandler
    public void isSended(ServerConnectedEvent e) {
        try {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!e.getServer().getInfo().getName().equals("AkaneLimbo -> KickRedirect")) {
                        menu.isConnected(e);
                    }
                }
            }, 300);
        } catch (NullPointerException ex) {
            return; // 玩家可能离开了服务器
        }
    }

    @EventHandler
    public void Kicked(ServerKickEvent e) {
        boolean ShouldRedirect = true;
        try {
            for (String reason : DontRedirectReason) {
                if (e.getKickReasonComponent().toString().contains(reason)) {
                    ShouldRedirect = false;
                }
            }
            for (String server : DontRedirectServer) {
                if (e.getKickedFrom().equals(getServer(server))) {
                    ShouldRedirect = false;
                }
            }
            if (!ShouldRedirect) {
                return;
            }
            limbo.CreateServer(e.getPlayer(), "KickRedirect");
            limbo.Connect2(e);
            OpenMenu(e);
        } catch (NullPointerException ex) {
            return;
        }
    }

    public ServerInfo getServer(String server) {
        return ProxyServer.getInstance().getServerInfo(server);
    }

    public void OpenMenu(ServerKickEvent e) {
        menu.SetInfo(e.getKickedFrom(), getServer("Limbo-" + e.getPlayer().getUniqueId()), getServer(LobbyServer));
        menu.open(e.getPlayer());
        menu.close = false;
    }
}
