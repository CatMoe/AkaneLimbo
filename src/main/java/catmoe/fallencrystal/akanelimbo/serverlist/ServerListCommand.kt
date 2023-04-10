package catmoe.fallencrystal.akanelimbo.serverlist

import catmoe.fallencrystal.akanelimbo.StringManager
import catmoe.fallencrystal.akanelimbo.command.SubCommand
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.ProxiedPlayer

class ServerListCommand : SubCommand {

    override val subCommandId: String get() = "serverlist"

    override fun execute(sender: CommandSender?, args: Array<String>) {
        val menu = ServerListMenu()
        menu.open((sender as ProxiedPlayer))
    }

    private fun getTabCompleterMap(): MutableMap<Int, List<String>> {
        val map : MutableMap<Int, List<String>> = HashMap()
        val tip1 = ArrayList<String>()
        tip1.add("?")
        map[1] = tip1
        return map
    }

    override val permission: String get() = StringManager.getServerListPermission()
    override val tabCompleter: MutableMap<Int, List<String>> get() = getTabCompleterMap()
    override fun allowedConsole(): Boolean { return false }
    override fun strictSizeLimit(): Boolean { return true }
    override fun strictSize(): Int { return 1 }
}
