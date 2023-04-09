package catmoe.fallencrystal.akanelimbo.rule;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Timer;
import java.util.TimerTask;

public class RuleHandler implements Listener {

    ServerInfo switchfrom = ProxyServer.getInstance().getServerInfo("LoginLimbo");
    ServerInfo logined = ProxyServer.getInstance().getServerInfo("MainLimbo");

    @EventHandler
    public void ServerSwitchHandler(ServerSwitchEvent e) {
        ProxiedPlayer p = e.getPlayer();
        ServerInfo target = p.getServer().getInfo();
        try {
            // 从其它服务器跳转
            if (e.getFrom().equals(switchfrom) && target.equals(logined)) {
                Trigger(p);
            }
            // 使用try方法捕获null 表明玩家直接连接到该服务器
        } catch (NullPointerException ex) {
            // 如果目标是已经登录的服务器
            if (target.equals(logined)) {
                Trigger(p); // 触发
            }
        }
    }

    public void Trigger(ProxiedPlayer p) {
        if (hasBypassPermission(p)) {
            Skip(p);
            return;
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    RuleMenu menu = new RuleMenu();
                    menu.close = false;
                    menu.open(p);
                } catch (NullPointerException ignore) {}
            }
        }, 1500);
    }

    public boolean hasBypassPermission(ProxiedPlayer p) {
        return p.hasPermission("rule.bypass");
    }

    public void Skip(ProxiedPlayer p) {
        ServerInfo lobby = ProxyServer.getInstance().getServerInfo("Lobby-1");
        p.connect(lobby);
    }
}
