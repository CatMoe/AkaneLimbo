@file:Suppress("DEPRECATION")

package catmoe.fallencrystal.akanelimbo.kick

import catmoe.fallencrystal.akanelimbo.StringManager
import catmoe.fallencrystal.akanelimbo.util.LimboCreater
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.config.ServerInfo
import net.md_5.bungee.api.event.ServerKickEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import java.util.*

class KickRedirect : Listener {

    /*
     * Banned - Ban Reason
     * Cheating - Ban Reason
     * [Proxy] - [Proxy] lost connect to server.
     * Violations - Ban Reason
     * Null - ReadTimedOut : null
     */
    private var dontRedirectReason: List<String> = mutableListOf("Banned", "Cheating", "Proxy", "Violations", "Null")
    private var dontRedirectServer: List<String> = mutableListOf("LoginLimbo", "MainLimbo")
    private var limbo = LimboCreater()
    @EventHandler
    fun kicked(e: ServerKickEvent) {
        var shouldRedirect = true
        if (e.kickReason.isNullOrEmpty()) return
        try {
            for (reason in dontRedirectReason) {
                if (Arrays.toString(e.kickReasonComponent).contains(reason)) {
                    shouldRedirect = false
                }
            }
            for (server in dontRedirectServer) {
                if (e.kickedFrom == getServer(server)) {
                    shouldRedirect = false
                }
            }
            if (!shouldRedirect) return
            limbo.createServer(e.player, StringManager.getKickRedirectLimbo())
            limbo.connect2(e)
            openMenu(e)
        } catch (ignore: NullPointerException) {
            // 如果在scheduleDelayedTask打开菜单前玩家就已经断开连接 则会抛出 ProxiedPlayer = null的错误
            // 在此处catch ignore.
        }
    }

    private fun getServer(server: String?): ServerInfo {
        return ProxyServer.getInstance().getServerInfo(server)
    } // String -> ServerInfo

    private fun openMenu(e: ServerKickEvent) {
        val menu = KickMenu()
        menu.handleEvent(e)
        // 发送title by @Shizoukia
        menu.sendTitle(e.player)
        val timer = Timer()
        timer.schedule(object : TimerTask(){
            override fun run() {
                try {
                    menu.open(e.player)
                } catch (_: NullPointerException) { }
            }
        } ,1500)
    }
}
