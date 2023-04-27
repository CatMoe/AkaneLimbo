package catmoe.fallencrystal.akanelimbo.hub

import catmoe.fallencrystal.akanelimbo.StringManager
import catmoe.fallencrystal.akanelimbo.util.MessageUtil
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command

class HubCommand(name: String, permission: String?, vararg aliases: String?) : Command(name, permission, *aliases) {

    override fun execute(sender: CommandSender, args: Array<String>) {
        if (sender !is ProxiedPlayer) {
            MessageUtil.logwarn("This command is disabled for console.")
        }
        if (args.isEmpty()) { openGui(sender) }
    }

    private fun openGui (sender: CommandSender) {
        val player = sender as ProxiedPlayer
        if (player.server.info == StringManager.getLobby()) {
            MessageUtil.actionbar(player, "&c您已经在大厅了! 搁这卡bug呢")
            return
        }
        val hubGui = HubGui()
        hubGui.open(player)
    }
}
