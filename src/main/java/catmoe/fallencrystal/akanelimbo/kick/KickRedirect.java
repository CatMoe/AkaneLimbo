package catmoe.fallencrystal.akanelimbo.kick;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import catmoe.fallencrystal.akanelimbo.util.LimboCreater;
import catmoe.fallencrystal.akanelimbo.util.MessageUtil;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
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

    private final AtomicBoolean titlerun = new AtomicBoolean(true);

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
            sendTitle(e.getPlayer());
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

    public void OpenMenu(ServerKickEvent e) {menu.handleevent(e); menu.open(e.getPlayer());}
    @EventHandler
    public void NotInLimbo(ServerSwitchEvent e) {
        if (e.getPlayer().getServer().getInfo().getName().contains("AkaneLimbo")) return;
        try {Timer timer = new Timer(); timer.schedule(new TimerTask() {
                @Override
                public void run() {if (!e.getPlayer().getServer().getInfo().getName().contains("AkaneLimbo"))
                    {menu.close = false; menu.close(); titlerun.set(false);}}}, 300);} catch (NullPointerException ignore) {}
    }

    public void sendTitle(ProxiedPlayer p) {
        String title = "&c连接已丢失";
        String subtitle = "&f在菜单内选择 &b回到大厅 &f或 &b重新连接&f";
        MessageUtil.fulltitle(p, title, subtitle, 18, 2, 0);
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 创建循环
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // titlerun.get()  return boolean.
            // 如果为false 则终止线程运行
            if (!titlerun.get()) {
                MessageUtil.fulltitle(p, title, subtitle, 18, 0, 2);
                scheduledExecutorService.shutdownNow();
                // 尽管else看起来有点像垃圾代码 但在这个循环里确实是必不可少的
            } else {MessageUtil.fulltitle(p, title, subtitle, 20, 0, 0);}
        },1 ,1, TimeUnit.SECONDS);
    }
}
