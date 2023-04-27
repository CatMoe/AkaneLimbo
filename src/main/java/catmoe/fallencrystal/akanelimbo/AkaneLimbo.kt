package catmoe.fallencrystal.akanelimbo

import catmoe.fallencrystal.akanelimbo.command.CommandManager
import catmoe.fallencrystal.akanelimbo.command.commands.SendLimbo
import catmoe.fallencrystal.akanelimbo.hub.HubCommand
import catmoe.fallencrystal.akanelimbo.kick.KickRedirect
import catmoe.fallencrystal.akanelimbo.rule.RuleHandler
import catmoe.fallencrystal.akanelimbo.serverlist.ServerListCommand
import catmoe.fallencrystal.akanelimbo.util.MessageUtil.loginfo
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Plugin

class AkaneLimbo : Plugin() {

    private val proxy = ProxyServer.getInstance()
    override fun onEnable() {
        registerListener()
        loadCommand()
        loginfo("&b偷偷摸摸载入 应该没人会发现的叭..")
    }

    private fun registerListener() {
        proxy.pluginManager.registerListener(this, KickRedirect())
        if (StringManager.getEnableRule()) {proxy.pluginManager.registerListener(this, RuleHandler())}
    }

    override fun onDisable() {
        proxy.pluginManager.unregisterListener(KickRedirect())
        proxy.pluginManager.unregisterListener(RuleHandler())
        proxy.pluginManager.unregisterCommand(CommandManager("akanelimbo", "", "akanelimbo", "limbo"))
        proxy.pluginManager.unregisterCommand(HubCommand("hub", "", "hub", "lobby"))
    }

    private fun loadCommand() {
        val commandManager = CommandManager("akanelimbo", "", "akanelimbo", "limbo")
        commandManager.register(SendLimbo())
        commandManager.register(ServerListCommand())
        proxy.pluginManager.registerCommand(this, commandManager)
        proxy.pluginManager.registerCommand(this, HubCommand("hub", "", "hub", "lobby"))
    }
}
