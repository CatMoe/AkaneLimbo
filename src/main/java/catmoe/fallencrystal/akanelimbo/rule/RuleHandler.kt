package catmoe.fallencrystal.akanelimbo.rule

import catmoe.fallencrystal.akanelimbo.StringManager
import catmoe.fallencrystal.moefilter.api.event.EventListener
import catmoe.fallencrystal.moefilter.api.event.FilterEvent
import catmoe.fallencrystal.moefilter.api.event.events.bungee.AsyncServerSwitchEvent
import net.md_5.bungee.api.connection.ProxiedPlayer
import java.util.*
import kotlin.concurrent.schedule

class RuleHandler : EventListener {
    private var loginLimbo = StringManager.getLoginLimbo()
    private var mainLimbo = StringManager.getMainLimbo()

    @FilterEvent
    fun serverSwitchHandler(e: AsyncServerSwitchEvent) {
        val p = e.player
        val target = p.server.info
        try {
            // 直接连接
            if (e.from == null && target == mainLimbo) { trigger(p) }
            // 从其它服务器跳转
            if (e.from == loginLimbo && target == mainLimbo) { trigger(p) }
        } catch (_: NullPointerException) { skip(p) }
    }

    private fun trigger(p: ProxiedPlayer) {
        // Enabled or Disabled
        if (!StringManager.getEnableRule()) {skip(p); return }
        // Cached read user from ReadCache.kt
        if (ReadCache.cacheGet(p.uniqueId) == true) {skip(p); return }
        val menu = RuleMenu()
        menu.closed = false
        menu.countdown(p)
        Timer().schedule(1500L) {
            try { menu.open(p) } catch (_: NullPointerException) { } catch (e: Exception) { skip(p) }
        }
    }

    private fun skip(p: ProxiedPlayer) { p.connect(StringManager.getLobby()) }
}
