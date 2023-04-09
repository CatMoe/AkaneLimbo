package catmoe.fallencrystal.akanelimbo;

import catmoe.fallencrystal.akanelimbo.command.CommandManager;
import catmoe.fallencrystal.akanelimbo.command.commands.SendLimbo;
import catmoe.fallencrystal.akanelimbo.kick.KickRedirect;
import catmoe.fallencrystal.akanelimbo.rule.RuleHandler;
import catmoe.fallencrystal.akanelimbo.serverlist.ServerListCommand;
import catmoe.fallencrystal.akanelimbo.util.MessageUtil;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class AkaneLimbo extends Plugin {
    private static AkaneLimbo instance;

    public void onEnable() {
        instance = this;
        RegisterListener();
        // proxy.getPluginManager().registerListener(instance, new Trigger());
        LoadCommand();
        MessageUtil.loginfo("&b偷偷摸摸载入 应该没人会发现的叭..");
    }

    public void RegisterListener() {
        ProxyServer proxy = ProxyServer.getInstance();
        proxy.getPluginManager().registerListener(instance, new KickRedirect());
        proxy.getPluginManager().registerListener(instance, new RuleHandler());
    }

    public void onDisable() {
        MessageUtil.loginfo("&b其实吧 这个插件轻到连卸载都不需要 w=");
    }

    public void LoadCommand() {
        CommandManager commandManager = new CommandManager("akanelimbo", "", "akanelimbo", "limbo");
        commandManager.register(new SendLimbo());
        commandManager.register(new ServerListCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(instance, commandManager);
    }
}
