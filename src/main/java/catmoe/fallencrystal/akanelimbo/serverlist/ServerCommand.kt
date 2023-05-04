package catmoe.fallencrystal.akanelimbo.serverlist

import catmoe.fallencrystal.akanelimbo.util.MessageUtil
import catmoe.fallencrystal.akanelimbo.util.ServerOnlineCheck
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
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
                val server = proxy.getServerInfo(args[0])
                if (server != null) { if (ServerOnlineCheck.socketPing(server)) {sender.connect(server) } else {
                    MessageUtil.actionbar(sender, "&c${args[0]} 服务器目前离线或不可用 请稍后再试.")}
                } else { MessageUtil.actionbar(sender, "&c未找到名为 ${args[0]} 的服务器") }
            }
        }
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<out String>?): MutableIterable<String> {
        if (sender?.hasPermission("bungeecord.command.server") == false) return mutableListOf("?")
        if (args?.size!! > 1) return mutableListOf("?")
        val servers: MutableList<String> = mutableListOf()
        proxy.servers.forEach { servers.add(it.value.name) }
        return servers
    }
}