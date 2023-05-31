package catmoe.fallencrystal.akanelimbo.serverlist

import catmoe.fallencrystal.akanelimbo.util.MessageUtil
import catmoe.fallencrystal.akanelimbo.util.ServerOnlineCheck
import catmoe.fallencrystal.moefilter.api.user.displaycache.DisplayCache
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.config.ServerInfo
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.api.plugin.TabExecutor

class ServerCommand(name: String?, permission: String?, vararg aliases: String?, private val plugin: Plugin) : Command(name, permission, *aliases), TabExecutor {

    private val proxy = ProxyServer.getInstance()

    override fun execute(sender: CommandSender?, args: Array<out String>?) {
        proxy.scheduler.runAsync(plugin) {
            if (sender !is ProxiedPlayer) return@runAsync
            if (!sender.hasPermission("bungeecord.command.server")) return@runAsync
            if (args!!.size > 1) return@runAsync
            if (args.isEmpty()) {
                val menu = ServerListMenu()
                menu.open(sender)
                return@runAsync
            } else {
                if (proxy.getServerInfo(args[0]) != null) { toServer(sender, proxy.getServerInfo(args[0])) } else if (proxy.getPlayer(args[0]) != null) { toServer(sender, proxy.getPlayer(args[0]).server.info) } else { MessageUtil.actionbar(sender, "&c未找到名为 ${args[0]} 的服务器") }
            }
        }
    }

    private fun toServer(player: ProxiedPlayer, server: ServerInfo) {
        if (player.server.info == server) { MessageUtil.actionbar(player, "&c您已经连接到那个服务器了!"); return }
        if (ServerOnlineCheck.socketPing(server)) { player.connect(server) } else MessageUtil.actionbar(player, "&c${server.name} 服务器目前离线或不可用 请稍后再试.")
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<out String>?): MutableIterable<String> {
        if (sender?.hasPermission("bungeecord.command.server") == false) return mutableListOf()
        val input = args!!.getOrNull(0) ?: ""
        val matchedServer: MutableList<String> = mutableListOf()
        val matchedPlayer: MutableList<String> = mutableListOf()
        val result: MutableList<String> = mutableListOf()
        proxy.servers.forEach { if (it.value.name.startsWith(input, ignoreCase = true)) matchedServer.add(it.value.name) }
        proxy.players.forEach { if (proxy.getServerInfo(it.name) == null) { if (it.name.startsWith(input)) matchedPlayer.add(it.name) } }
        val tabComplete: MutableCollection<MutableList<String>> = mutableListOf(matchedServer, matchedPlayer)
        tabComplete.forEach { result.addAll(it); it.sort() }
        sendTips(sender, input, matchedServer, matchedPlayer, result)
        return if (args.size > 1) mutableListOf() else result
    }

    private fun sendTips(sender: CommandSender?, input: String, matchedServer: MutableList<String>, matchedPlayer: MutableList<String>, result: MutableList<String>) {
        val player = (sender ?: return) as ProxiedPlayer
        if (input.isEmpty()) { MessageUtil.actionbar(player, "&e请键入内容以开始过滤. &7[${matchedServer.size} 服务器  ${matchedPlayer.size} 玩家.]"); return }
        if (result.isEmpty()) { MessageUtil.actionbar(player, "&e没有匹配的玩家或服务器."); return }
        if (matchedServer.size>1 || matchedPlayer.size>1 && proxy.getServerInfo(input) == null && proxy.getPlayer(input) == null)
        { MessageUtil.actionbar(player, "&e将搜索范围缩小至 &f${matchedServer.size} &e个服务器和 &f${matchedPlayer.size} &e位玩家."); return }
        val server = getServer(matchedServer, matchedPlayer, input) ?: return
        val isConnected = if (player.server.info == server) "  &c[已连接到此服务器]" else ""
        val isOnline = if (ServerOnlineCheck.socketPing(server)) "&a✔" else "&c✖"
        val onlinePlayers = if (server.players.isEmpty()) "" else "(${server.players.size})"
        if (matchedServer.size > 0) { MessageUtil.actionbar(player, "&e服务器: ${server.name} &b在线状态: $isOnline $onlinePlayers$isConnected"); return }
        val targetPlayer = proxy.getPlayer(matchedPlayer[0]) ?: return
        val targetDisplay = DisplayCache.getDisplay(targetPlayer.uniqueId)
        val targetDisplayName = "${targetDisplay.displayPrefix}${targetPlayer.displayName}${targetDisplay.displaySuffix}"
        if (matchedPlayer.size > 0) { MessageUtil.actionbar(player, "&e玩家 $targetDisplayName &e服务器: ${server.name} &b在线状态: $isOnline $onlinePlayers$isConnected") }
    }

    private fun getServer(p0: MutableList<String>, p1: MutableList<String>, input: String): ServerInfo? {
        val exactMatchedServer = p0.find { it.equals(input, ignoreCase = true) }
        return if (exactMatchedServer != null) proxy.getServerInfo(exactMatchedServer) else if (p0.size == 1) proxy.getServerInfo(p0[0]) else if (p1.size == 1) (proxy.getPlayer(p1[0]) ?: return null).server.info else null
    }
}