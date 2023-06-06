package catmoe.fallencrystal.akanelimbo.hub

import catmoe.fallencrystal.akanelimbo.SharedPlugin
import catmoe.fallencrystal.akanelimbo.StringManager
import catmoe.fallencrystal.moefilter.util.message.MessageUtil
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command

class HubCommand(name: String, permission: String?, vararg aliases: String?) : Command(name, permission, *aliases) {

    private val proxy = ProxyServer.getInstance()

    private val bedwarsServer: List<String> = listOf("bedwars4x4", "bedwarsdm")

    private val bedwarsLobby = "bedwarslobby"

    override fun execute(sender: CommandSender, args: Array<String>) {
        if (sender !is ProxiedPlayer) { MessageUtil.logWarn("This command is disabled for console.") }
        if (args.isEmpty()) { sendToLobby(sender as ProxiedPlayer) }
    }

    private fun sendToLobby(player: ProxiedPlayer) {
        proxy.scheduler.runAsync(SharedPlugin.getLimboPlugin()) {
            val playerServer = player.server.info
            if (isMatch(bedwarsServer, playerServer.name)) { val lobby = proxy.getServerInfo(bedwarsLobby) ?: return@runAsync; player.connect(lobby) }
            if (playerServer == StringManager.getLobby()) { MessageUtil.sendActionbar(player, "&c您已经在大厅了! 搁这卡bug呢"); return@runAsync }
            player.connect(StringManager.getLobby())
        }
    }

    private fun isMatch(original: List<String>, target: String): Boolean { original.forEach { if (it.contains(target)) return true }; return false }
}
