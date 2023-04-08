package catmoe.fallencrystal.akanelimbo.kick;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import catmoe.fallencrystal.akanelimbo.util.LimboCreater;
import catmoe.fallencrystal.akanelimbo.util.MessageUtil;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class KickRedirect implements Listener {

    KickMenu menu = new KickMenu();
    /*
     * Banned - Ban Reason
     * Cheating - Ban Reason
     * [Proxy] - [Proxy] lost connect to server.
     * Violations - Ban Reason
     * Null - ReadTimedOut : null
     */
    List<String> DontRedirectReason = Arrays.asList("Banned", "Cheating", "[Proxy]", "Violations", "Null");
    List<String> DontRedirectServer = Arrays.asList("LoginLimbo", "MainLimbo");

    LimboCreater limbo = new LimboCreater();

    @EventHandler
    public void Kicked(ServerKickEvent e) {
        boolean ShouldRedirect = true;
        try {
            for (String reason : DontRedirectReason) {if (Arrays.toString(e.getKickReasonComponent()).contains(reason)) {ShouldRedirect = false;}}
            for (String server : DontRedirectServer) {if (e.getKickedFrom().equals(getServer(server))) {ShouldRedirect = false; }}
            if (!ShouldRedirect) return;
            limbo.CreateServer(e.getPlayer(), "KickRedirect");
            limbo.Connect2(e);
            // 发送title by @Shizoukia
            MessageUtil.fulltitle(e.getPlayer(),
                    "&c连接已丢失",
                    "&f在菜单内选择 &b回到大厅 &f或 &b重新连接&f",
                    20,
                    2,
                    3);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    OpenMenu(e);
                }
            }, 1500); // 1.5秒 玩家加入limbo世界可能还需要一点时间
        } catch (NullPointerException ignore) {
            // 如果在scheduleDelayedTask打开菜单前玩家就已经断开连接 则会抛出 ProxiedPlayer = null的错误
            // 在此处catch ignore.
        }
    }

    public ServerInfo getServer(String server) {return ProxyServer.getInstance().getServerInfo(server);} // String -> ServerInfo

    public void OpenMenu(ServerKickEvent e) {
        menu.handleevent(e);
        menu.open(e.getPlayer());
    }
    @EventHandler
    public void NotInLimbo(ServerSwitchEvent e) {
        if (e.getPlayer().getServer().getInfo().getName().contains("AkaneLimbo")) return;
        try {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!e.getPlayer().getServer().getInfo().getName().contains("AkaneLimbo")) {
                        menu.close = false;
                        menu.close();
                    }
                }
            }, 300);
        } catch (NullPointerException ignore) {
        }
    }
}
