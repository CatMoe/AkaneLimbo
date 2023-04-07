package catmoe.fallencrystal.akanelimbo.util;

import net.md_5.bungee.api.ProxyServer;

public class InstalledChecker {
    public static boolean isInstalled(String plugin) {
        if (ProxyServer.getInstance().getPluginManager().getPlugin(plugin) != null) {
            return true;
        } else {
            return false;
        }
    }
}
