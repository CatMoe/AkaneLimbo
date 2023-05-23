package catmoe.fallencrystal.akanelimbo.kick

import catmoe.fallencrystal.akanelimbo.StringManager
import catmoe.fallencrystal.akanelimbo.util.LimboCreater
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.config.ServerInfo
import net.md_5.bungee.api.event.ServerKickEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import java.util.*
import kotlin.concurrent.schedule

class KickRedirect : Listener {

    /*
     * Banned - Ban Reason
     * Cheating - Ban Reason
     * [Proxy] - [Proxy] lost connect to server.
     * Violations - Ban Reason
     * Null - ReadTimedOut : null
     */
    private var dontRedirectReason: List<String> = mutableListOf("Banned", "Cheating", "Proxy", "Violations")
    private var dontRedirectServer: List<String> = mutableListOf("LoginLimbo", "MainLimbo")
    private var limbo = LimboCreater()

    @EventHandler
    fun kicked(e: ServerKickEvent) {
        var shouldRedirect = true
        try {
            for (reason in dontRedirectReason) { if (Arrays.toString(e.kickReasonComponent).contains(reason)) { shouldRedirect = false } }
            for (server in dontRedirectServer) { if (e.kickedFrom == getServer(server)) { shouldRedirect = false } }
            if (!shouldRedirect) return
            limbo.createServer(e.player, StringManager.getKickRedirectLimbo())
            limbo.connect2(e)
            openMenu(e)
        } catch (_: NullPointerException) {
            // 如果在scheduleDelayedTask打开菜单前玩家就已经断开连接 则会抛出 ProxiedPlayer = null的错误
            // 在此处catch ignore.
        }
    }

    private fun getServer(server: String?): ServerInfo {
        return ProxyServer.getInstance().getServerInfo(server)
    } // String -> ServerInfo

    private fun openMenu(e: ServerKickEvent) {
        // 实例化对象 并指定一个玩家
        // 注册BungeeCord服务端的Listener后整个class其实也已经被实例化了
        // 所以这也是为什么要放在这里的原因
        // 如果在class被加载时就被实例化对象 最终就会导致多个玩家同时使用菜单时出现bug
        val menu = KickMenu()
        menu.handleEvent(e)
        // 发送title by @Shizoukia
        menu.sendTitle(e.player)
        Timer().schedule(1500L) { if (e.player.isConnected) { try { menu.open(e.player) } catch (_: NullPointerException) { } } } // 当玩家离开时会抛出NPE 在此处ignore.
    }
}
