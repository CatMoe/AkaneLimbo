package catmoe.fallencrystal.akanelimbo.kick

import catmoe.fallencrystal.akanelimbo.StringManager
import catmoe.fallencrystal.akanelimbo.util.LimboCreater
import catmoe.fallencrystal.akanelimbo.util.MessageUtil.fulltitle
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.config.ServerInfo
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.ServerKickEvent
import net.md_5.bungee.api.event.ServerSwitchEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

class KickRedirect : Listener {
    var menu = KickMenu()

    /*
     * Banned - Ban Reason
     * Cheating - Ban Reason
     * [Proxy] - [Proxy] lost connect to server.
     * Violations - Ban Reason
     * Null - ReadTimedOut : null
     */
    private var dontRedirectReason: List<String> = mutableListOf("Banned", "Cheating", "[Proxy]", "Violations", "Null")
    private var dontRedirectServer: List<String> = mutableListOf("LoginLimbo", "MainLimbo")
    private var limbo = LimboCreater()
    private val titlerun = AtomicBoolean(true)
    @EventHandler
    fun kicked(e: ServerKickEvent) {
        var shouldRedirect = true
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
            // 发送title by @Shizoukia
            sendTitle(e.player)
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    openMenu(e)
                }
            }, 1500) // 1.5秒 玩家加入limbo世界可能还需要一点时间
        } catch (ignore: NullPointerException) {
            // 如果在scheduleDelayedTask打开菜单前玩家就已经断开连接 则会抛出 ProxiedPlayer = null的错误
            // 在此处catch ignore.
        }
    }

    private fun getServer(server: String?): ServerInfo {
        return ProxyServer.getInstance().getServerInfo(server)
    } // String -> ServerInfo

    fun openMenu(e: ServerKickEvent) {
        menu.handleevent(e)
        menu.open(e.player)
    }

    @EventHandler
    fun notInLimbo(e: ServerSwitchEvent) {
        if (e.player.server.info.name.isNullOrEmpty()) return
        if (e.player.server.info.name.contains(StringManager.getLimboPrefix())) return
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                try {
                    if (!e.player.server.info.name.contains(StringManager.getLimboPrefix())) {
                        menu.close = false
                        menu.close()
                        titlerun.set(false)
                    }
                } catch (ignore: NullPointerException) {}
            }
        }, 300)
    }

    private fun sendTitle(p: ProxiedPlayer?) {
        val title = "&c连接已丢失"
        val subtitle = "&f在菜单内选择 &b回到大厅 &f或 &b重新连接&f"
        fulltitle(p!!, title, subtitle, 18, 2, 0)
        val scheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        // 创建循环
        scheduledExecutorService.scheduleAtFixedRate({
            // titlerun.get()  return boolean.
            // 如果为false 则终止线程运行
            if (!titlerun.get()) {
                fulltitle(p, title, subtitle, 18, 0, 2)
                scheduledExecutorService.shutdownNow()
                // 尽管else看起来有点像垃圾代码 但在这个循环里确实是必不可少的
            } else {
                fulltitle(p, title, subtitle, 20, 0, 0)
            }
        }, 1, 1, TimeUnit.SECONDS)
    }
}
