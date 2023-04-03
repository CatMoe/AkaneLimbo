package catmoe.fallencrystal.limborule;

import catmoe.fallencrystal.limborule.kick.KickRedirect;
import catmoe.fallencrystal.limborule.util.MessageUtil;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class AkaneLimbo extends Plugin {
    private static AkaneLimbo instance;

    public void onEnable() {
        instance = this;
        ProxyServer proxy = ProxyServer.getInstance();
        proxy.getPluginManager().registerListener(instance, new KickRedirect());
        // proxy.getPluginManager().registerListener(instance, new Trigger());
        MessageUtil.loginfo("&bLimbo&dRule &7> &b偷偷摸摸载入 应该没人会发现的叭..");
    }

    public static AkaneLimbo getInstance() {
        return instance;
    }

    public void onDisable() {
        MessageUtil.loginfo("&bLimbo&dRule &7> &b其实吧 这个插件轻到连卸载都不需要 w=");
    }
}
