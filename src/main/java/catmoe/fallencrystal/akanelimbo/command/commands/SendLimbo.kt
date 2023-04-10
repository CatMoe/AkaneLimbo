package catmoe.fallencrystal.akanelimbo.command.commands

import catmoe.fallencrystal.akanelimbo.StringManager
import catmoe.fallencrystal.akanelimbo.command.SubCommand
import catmoe.fallencrystal.akanelimbo.util.LimboCreater
import catmoe.fallencrystal.akanelimbo.util.MessageUtil.prefixsender
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.connection.ProxiedPlayer

class SendLimbo : SubCommand {
    override val subCommandId: String
        get() = "sendlimbo"

    override fun execute(sender: CommandSender?, args: Array<String>) {
        // 创建一个LimboCreater
        val limbo = LimboCreater()
        // 将自己发送到Limbo
        if (args[1].equals("me", ignoreCase = true)) {
            if (sender !is ProxiedPlayer) {
                prefixsender(sender!!, "&cConsole is a invalid target.")
                return
            }
            limbo.createServer(sender, StringManager.getCommandLimbo())
            limbo.connect(sender)
        }
        if (args[1].equals("all", ignoreCase = true)) {
            for (p in ProxyServer.getInstance().players) {
                limbo.createServer(p!!, StringManager.getCommandLimbo())
                limbo.connect(p)
            }
        } else {
            try {
                val p = ProxyServer.getInstance().getPlayer(args[1])
                if (p != null) {
                    limbo.createServer(p, StringManager.getCommandLimbo())
                    limbo.connect(p)
                }
            } catch (ignore: Exception) {
            }
        }
    }

    override val permission: String
        get() = StringManager.getSendLimboPermission()

    override val tabCompleter: MutableMap<Int, List<String>>

        get() = getTabCompleterMap()

    private fun getTabCompleterMap(): MutableMap<Int, List<String>> {
        val map: MutableMap<Int, List<String>> = HashMap()
        val tip1 = ArrayList<String>()
        for (p in ProxyServer.getInstance().players) {
            tip1.add(p.toString())
        }
        tip1.add("me")
        tip1.add("all")
        map[1] = tip1
        return map
    }

    override fun allowedConsole(): Boolean {
        return true
    }

    override fun strictSizeLimit(): Boolean {
        return true
    }

    override fun strictSize(): Int {
        return 2
    }
}
