package catmoe.fallencrystal.limborule.limbo;

import net.md_5.bungee.BungeeServerInfo;
import net.md_5.bungee.ServerConnector;
import net.md_5.bungee.UserConnection;
import net.md_5.bungee.api.ProxyServer;

public class LimboServerConnector extends ServerConnector {
    final UserConnection ucon;
    final BungeeServerInfo target;

    public LimboServerConnector(ProxyServer bungee, UserConnection user, BungeeServerInfo target) {
        super(bungee, user, target);
        this.ucon = user;
        this.target = target;
    }
}
