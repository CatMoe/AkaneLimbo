package catmoe.fallencrystal.akanelimbo.command.commands

import catmoe.fallencrystal.akanelimbo.StringManager
import catmoe.fallencrystal.akanelimbo.command.SubCommand
import catmoe.fallencrystal.akanelimbo.serverlist.ServerListMenu
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.ProxiedPlayer

class ServerListCommand : SubCommand {

    override val subCommandId: String get() = "serverlist"

    override fun execute(sender: CommandSender?, args: Array<String>) {
        val menu = ServerListMenu()
        menu.executePlayer = (sender as ProxiedPlayer)
        menu.open(sender)
    }

    private fun getTabCompleterMap(): MutableMap<Int, List<String>> { return HashMap<Int, List<String>>() }

    override val permission: String get() = StringManager.getServerListPermission()
    override val tabCompleter: MutableMap<Int, List<String>> get() = getTabCompleterMap()
    override fun allowedConsole(): Boolean { return false }
    override fun strictSizeLimit(): Boolean { return true }
    override fun strictSize(): Int { return 1 }
}
