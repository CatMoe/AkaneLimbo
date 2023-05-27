package catmoe.fallencrystal.akanelimbo.serverlist

import catmoe.fallencrystal.akanelimbo.util.MessageUtil
import catmoe.fallencrystal.akanelimbo.util.ServerOnlineCheck
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
                if (proxy.getServerInfo(args[0]) != null) { toServer(sender, proxy.getServerInfo(args[0])) } else if (proxy.getPlayer(args[0]).server.info != null) { toServer(sender, proxy.getPlayer(args[0]).server.info) } else { MessageUtil.actionbar(sender, "&c未找到名为 ${args[0]} 的服务器") }
            }
        }
    }

    private fun toServer(player: ProxiedPlayer, server: ServerInfo) {
        if (player.server.info == server) { MessageUtil.actionbar(player, "&c您已经连接到哪个服务器了!"); return }
        if (ServerOnlineCheck.socketPing(server)) { player.connect(server) } else MessageUtil.actionbar(player, "&c${server} 服务器目前离线或不可用 请稍后再试.")
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<out String>?): MutableIterable<String> {
        if (sender?.hasPermission("bungeecord.command.server") == false) return mutableListOf()
        if (args?.size!! > 1) return mutableListOf()
        val input = args.getOrNull(0) ?: ""
        val matchedServer: MutableList<String> = mutableListOf()
        val nonMatchServer: MutableList<String> = mutableListOf()
        val matchedPlayer: MutableList<String> = mutableListOf()
        val nonMatchPlayer: MutableList<String> = mutableListOf()
        val result: MutableList<String> = mutableListOf()
        proxy.servers.forEach { if (it.value.name.startsWith(input, ignoreCase = true)) matchedServer.add(it.value.name) else nonMatchServer.add(it.value.name) }
        proxy.players.forEach { if (proxy.getServerInfo(it.name) == null) { if (it.name.startsWith(input)) matchedPlayer.add(it.name) else nonMatchPlayer.add(it.name) } }
        val tabComplete: MutableCollection<MutableList<String>> = mutableListOf(matchedServer, matchedPlayer, nonMatchServer, nonMatchPlayer)
        tabComplete.forEach { it.sort(); result.addAll(it); }
        return result
    }
}