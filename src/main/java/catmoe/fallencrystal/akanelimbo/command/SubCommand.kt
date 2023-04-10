package catmoe.fallencrystal.akanelimbo.command

import net.md_5.bungee.api.CommandSender

interface SubCommand {
    val subCommandId: String?
    fun execute(sender: CommandSender?, args: Array<String>)
    val permission: String?
    val tabCompleter: MutableMap<Int, List<String>>?
    fun allowedConsole(): Boolean
    fun strictSizeLimit(): Boolean
    fun strictSize(): Int
}
