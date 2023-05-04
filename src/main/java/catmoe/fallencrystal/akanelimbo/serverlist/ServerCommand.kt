package catmoe.fallencrystal.akanelimbo.serverlist

import catmoe.fallencrystal.akanelimbo.util.MessageUtil
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor

class ServerCommand(name: String?, permission: String?, vararg aliases: String?) : Command(name, permission, *aliases), TabExecutor {

    private val proxy = ProxyServer.getInstance()

    override fun execute(sender: CommandSender?, args: Array<out String>?) {
        if (sender !is ProxiedPlayer) return
        if (!sender.hasPermission("bungeecord.command.server")) return
        if (args!!.size > 1) return
        if (args.isEmpty()) {
            val menu = ServerListMenu()
            menu.open(sender)
            return
        } else {
            val server = proxy.getServerInfo(args[0])
            if (server != null) { sender.connect(server) } else { MessageUtil.actionbar(sender, "未找到名为 ${args[0]} 的服务器") }
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