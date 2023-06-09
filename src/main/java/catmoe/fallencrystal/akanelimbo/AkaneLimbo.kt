package catmoe.fallencrystal.akanelimbo

import catmoe.fallencrystal.akanelimbo.command.CommandManager
import catmoe.fallencrystal.akanelimbo.command.commands.DebugLocationCommand
import catmoe.fallencrystal.akanelimbo.command.commands.SendLimbo
import catmoe.fallencrystal.akanelimbo.command.commands.ServerListCommand
import catmoe.fallencrystal.akanelimbo.hub.HubCommand
import catmoe.fallencrystal.akanelimbo.kick.KickRedirect
import catmoe.fallencrystal.akanelimbo.rule.ReadCache
import catmoe.fallencrystal.akanelimbo.rule.RuleHandler
import catmoe.fallencrystal.akanelimbo.serverlist.ServerCommand
import catmoe.fallencrystal.moefilter.api.event.EventManager
import catmoe.fallencrystal.moefilter.util.message.MessageUtil.logInfo
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Plugin

class AkaneLimbo : Plugin() {

    private val proxy = ProxyServer.getInstance()
    override fun onEnable() {
        registerListener()
        loadCommand()
        ReadCache
        SharedPlugin.setLimboPlugin(this)
        logInfo("&b偷偷摸摸载入 应该没人会发现的叭..")
        if (proxy.players.isNotEmpty()) { proxy.players.forEach { ReadCache.cachePut(it.uniqueId, true) } }
    }

    private fun registerListener() {
        proxy.pluginManager.registerListener(this, KickRedirect())
        if (StringManager.getEnableRule()) { EventManager.registerListener(RuleHandler()) }
    }

    override fun onDisable() {
        proxy.pluginManager.unregisterListener(KickRedirect())
        if (StringManager.getEnableRule()) { EventManager.unregisterListener(RuleHandler()) }
        proxy.pluginManager.unregisterCommand(CommandManager("akanelimbo", "", "akanelimbo", "limbo"))
        proxy.pluginManager.unregisterCommand(HubCommand("hub", "", "hub", "lobby"))
    }

    private fun loadCommand() {
        val commandManager = CommandManager("akanelimbo", "", "akanelimbo", "limbo")
        commandManager.register(SendLimbo())
        commandManager.register(ServerListCommand())
        commandManager.register(DebugLocationCommand())
        proxy.pluginManager.registerCommand(this, commandManager)
        proxy.pluginManager.registerCommand(this, HubCommand("hub", "", "hub", "lobby"))
        proxy.pluginManager.registerCommand(this, ServerCommand("server", "", "server", "s", plugin = this))
    }
}
