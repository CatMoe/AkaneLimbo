package catmoe.fallencrystal.akanelimbo.rule

import catmoe.fallencrystal.akanelimbo.StringManager
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.ServerSwitchEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import java.util.*

class RuleHandler : Listener {
    private var loginLimbo = StringManager.getLoginLimbo()!!
    private var mainLimbo = StringManager.getMainLimbo()!!
    @EventHandler
    fun serverSwitchHandler(e: ServerSwitchEvent) {
        val p = e.player
        val target = p.server.info
        try {
            // 从其它服务器跳转
            if (e.from == loginLimbo && target == mainLimbo) {
                trigger(p)
            }
            // 使用try方法捕获null 表明玩家直接连接到该服务器
        } catch (ex: NullPointerException) {
            // 如果目标是已经登录的服务器
            if (target == mainLimbo) {
                trigger(p) // 触发
            }
        }
    }

    private fun trigger(p: ProxiedPlayer) {
        if (checkIsRead(p)) {
            skip(p)
            return
        }
        if (checkPermission(p)) {
            skip(p)
            return
        }
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                try {
                    val menu = RuleMenu()
                    menu.close = false
                    menu.open(p)
                } catch (ignore: NullPointerException) {
                }
            }
        }, 1500)
    }

    private fun checkPermission(p: ProxiedPlayer): Boolean {
        return if (p.hasPermission(StringManager.getForceUnreadPermission())) {
            false
        } else p.hasPermission(StringManager.getForceReadPermission())
    }

    private fun checkIsRead(p: ProxiedPlayer?): Boolean {
        val file = SaveReadUtil()
        file.loadData()
        return file.getPlayerData(p!!)
    }

    private fun skip(p: ProxiedPlayer) {
        val lobby = ProxyServer.getInstance().getServerInfo("Lobby-1")
        p.connect(lobby)
    }
}
