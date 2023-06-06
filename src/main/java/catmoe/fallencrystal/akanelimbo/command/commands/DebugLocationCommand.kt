package catmoe.fallencrystal.akanelimbo.command.commands

import catmoe.fallencrystal.akanelimbo.afk.PlayerPosition
import catmoe.fallencrystal.akanelimbo.command.SubCommand
import catmoe.fallencrystal.moefilter.util.message.MessageUtil
import dev.simplix.protocolize.api.Protocolize
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.ProxiedPlayer

class DebugLocationCommand : SubCommand {
    override val subCommandId: String
        get() = "debug"

    override fun execute(sender: CommandSender?, args: Array<String>) {
        val player = sender as ProxiedPlayer
        val position = PlayerPosition(Protocolize.playerProvider().player(player.uniqueId)).position()
        MessageUtil.sendMessage(player, "x: ${position.x()} y: ${position.y()} z: ${position.z()} yaw: ${position.yaw()} pitch: ${position.pitch()}")
    }

    override val permission: String
        get() = ""

    private fun getTabCompleterMap(): MutableMap<Int, List<String>> { return mutableMapOf() }
    override val tabCompleter: MutableMap<Int, List<String>>
        get() = getTabCompleterMap()

    override fun allowedConsole(): Boolean { return false }

    override fun strictSizeLimit(): Boolean { return false }

    override fun strictSize(): Int { return 0 }
}