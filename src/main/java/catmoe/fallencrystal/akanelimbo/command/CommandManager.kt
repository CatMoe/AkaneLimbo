package catmoe.fallencrystal.akanelimbo.command

import catmoe.fallencrystal.moefilter.util.message.MessageUtil
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor

class CommandManager(name: String?, permission: String?, vararg aliases: String?) : Command(name, permission, *aliases),
    TabExecutor {
    private val loadedCommands: MutableList<SubCommand>
    private val tabComplete: MutableList<String>

    init {
        loadedCommands = ArrayList()
        tabComplete = ArrayList()
    }

    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            MessageUtil.sendMessage(sender, "idk")
            return
        }
        val cmd = getSubCommandFromArgs(args[0])
            ?: return  // 未找到命令
        if (args[0] == cmd.subCommandId) {
            if (!cmd.allowedConsole() && sender !is ProxiedPlayer) {
                return  // not allowed console
            }
            if (!sender.hasPermission(cmd.permission)) {
                return  // dont have permission
            }
            if (cmd.strictSizeLimit()) {
                if (args.size == cmd.strictSize()) {
                    cmd.execute(sender, args)
                }
            } else {
                cmd.execute(sender, args)
            }
        }
    }

    override fun onTabComplete(sender: CommandSender, args: Array<String>): List<String?> {
        val subCommand = getSubCommandFromArgs(args[0])
        val map: MutableMap<Int, List<String>> = HashMap()
        map[args.size - 1] = listOf("?")
        if (subCommand != null && args[0] == subCommand.subCommandId) {
            return if (subCommand.tabCompleter?.get(args.size - 1) != null) {
                subCommand.tabCompleter!![args.size - 1]!!
            } else {
                map[args.size - 1]!!
            }
        }
        return if (args.size == 1) {
            tabComplete
        } else map[args.size - 1]!!
    }

    private fun getSubCommandFromArgs(args0: String): SubCommand? {
        for (subCommand in loadedCommands) {
            if (subCommand.subCommandId == args0) {
                return subCommand
            }
        }
        return null
    }

    fun register(subCommand: SubCommand) {
        loadedCommands.add(subCommand)
        tabComplete.add(subCommand.subCommandId!!)
    }
}
