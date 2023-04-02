package catmoe.fallencrystal.limborule.kick;

import java.util.Arrays;
import java.util.List;

import catmoe.fallencrystal.limborule.util.MessageUtil;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class KickRedirect implements Listener {
    ProxiedPlayer target = null;

    String KickedLimbo = "LimboKick";
    String LobbyServer = "Lobby-1";

    KickMenu menu = new KickMenu();

    static List<String> DontRedirectReason = Arrays.asList("Banned", "Cheating");
    static List<String> DontRedirectServer = Arrays.asList("Verify");

    @EventHandler
    public void isSended(ServerConnectedEvent e) {
        if (e.getServer().getInfo().getName().equals(KickedLimbo)) {
            return;
        } else {
            menu.isConnected(e);
        }
    }

    @EventHandler
    public void Kicked(ServerKickEvent e) {
        MessageUtil.loginfo("" + e.getKickedFrom());
        boolean ShouldRedirect = true;
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
        OpenMenu(e);
        e.setCancelled(true);
        e.setCancelServer(getServer(KickedLimbo));
    }

    public ServerInfo getServer(String server) {
        return ProxyServer.getInstance().getServerInfo(server);
    }

    public void OpenMenu(ServerKickEvent e) {
        menu.SetInfo(e.getKickedFrom(), getServer(KickedLimbo), getServer(LobbyServer));
        menu.open(e.getPlayer());
    }
}
