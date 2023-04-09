package catmoe.fallencrystal.akanelimbo.serverlist

import catmoe.fallencrystal.akanelimbo.StringManager
import catmoe.fallencrystal.akanelimbo.command.SubCommand
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.ProxiedPlayer

class ServerListCommand : SubCommand {
    override fun getSubCommandId(): String {
        return "serverlist"
    }

    override fun execute(sender: CommandSender, args: Array<String>) {
        val menu = ServerListMenu()
        menu.open((sender as ProxiedPlayer))
    }

    override fun getPermission(): String {
        return StringManager.getServerListPermission()
    }

    override fun getTabCompleter(): Map<Int, List<String>>? {
        return null
    }

    override fun allowedConsole(): Boolean {
        return false
    }

    override fun strictSizeLimit(): Boolean {
        return true
    }

    override fun strictSize(): Int {
        return 1
    }
}
