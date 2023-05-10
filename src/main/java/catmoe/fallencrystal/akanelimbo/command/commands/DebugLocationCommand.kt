package catmoe.fallencrystal.akanelimbo.command.commands

import catmoe.fallencrystal.akanelimbo.afk.PlayerPosition
import catmoe.fallencrystal.akanelimbo.command.SubCommand
import catmoe.fallencrystal.akanelimbo.util.MessageUtil
import dev.simplix.protocolize.api.Protocolize
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.ProxiedPlayer

class DebugLocationCommand : SubCommand {
    override val subCommandId: String
        get() = "debug"

    override fun execute(sender: CommandSender?, args: Array<String>) {
        val player = sender as ProxiedPlayer
        val position = PlayerPosition(Protocolize.playerProvider().player(player.uniqueId)).position()
        MessageUtil.prefixchat(player, position.toString())
    }

    override val permission: String
        get() = ""

    private fun getTabCompleterMap(): MutableMap<Int, List<String>> {
        val map : MutableMap<Int, List<String>> = HashMap()
        val tip1 = ArrayList<String>()
        tip1.add("?")
        map[1] = tip1
        return map
    }
    override val tabCompleter: MutableMap<Int, List<String>>
        get() = getTabCompleterMap()

    override fun allowedConsole(): Boolean { return false }

    override fun strictSizeLimit(): Boolean { return false }

    override fun strictSize(): Int { return 0 }
}