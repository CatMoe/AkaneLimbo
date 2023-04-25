package catmoe.fallencrystal.akanelimbo

import catmoe.fallencrystal.akanelimbo.command.CommandManager
import catmoe.fallencrystal.akanelimbo.command.commands.SendLimbo
import catmoe.fallencrystal.akanelimbo.kick.KickRedirect
import catmoe.fallencrystal.akanelimbo.rule.RuleHandler
import catmoe.fallencrystal.akanelimbo.serverlist.ServerListCommand
import catmoe.fallencrystal.akanelimbo.util.MessageUtil.loginfo
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Plugin

class AkaneLimbo : Plugin() {

    private val proxy = ProxyServer.getInstance()
    override fun onEnable() {
        instance = this
        registerListener()
        // proxy.getPluginManager().registerListener(instance, new Trigger());
        loadCommand()
        loginfo("&b偷偷摸摸载入 应该没人会发现的叭..")
    }

    private fun registerListener() {
        proxy.pluginManager.registerListener(this, KickRedirect())
        proxy.pluginManager.registerListener(this, RuleHandler())
    }

    override fun onDisable() {
        proxy.pluginManager.unregisterListener(KickRedirect())
        proxy.pluginManager.unregisterListener(RuleHandler())
        loginfo("&b其实吧 这个插件轻到连卸载都不需要 w=")
    }

    private fun loadCommand() {
        val commandManager = CommandManager("akanelimbo", "", "akanelimbo", "limbo")
        commandManager.register(SendLimbo())
        commandManager.register(ServerListCommand())
        ProxyServer.getInstance().pluginManager.registerCommand(instance, commandManager)
    }

    companion object {
        private var instance: AkaneLimbo? = null
    }
}
