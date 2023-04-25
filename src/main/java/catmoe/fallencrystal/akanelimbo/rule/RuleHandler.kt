package catmoe.fallencrystal.akanelimbo.rule

import catmoe.fallencrystal.akanelimbo.StringManager
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.ServerSwitchEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import java.util.*
import kotlin.concurrent.schedule

class RuleHandler : Listener {
    private var loginLimbo = StringManager.getLoginLimbo()
    private var mainLimbo = StringManager.getMainLimbo()
    @EventHandler
    fun serverSwitchHandler(e: ServerSwitchEvent) {
        val p = e.player
        val target = p.server.info
        try {
            if (e.from == null && target == mainLimbo) { trigger(p) }
            // 从其它服务器跳转
            if (e.from == loginLimbo && target == mainLimbo) { trigger(p) }
        } catch (_: NullPointerException) { }
    }

    private fun trigger(p: ProxiedPlayer) {
        if (!StringManager.getEnableRule()) {skip(p); return}
        val menu = RuleMenu()
        menu.closed = false
        Timer().schedule(1500L) {
            try { menu.open(p) } catch (_: NullPointerException) { } catch (e: Exception) { skip(p) }
        }
    }

    private fun skip(p: ProxiedPlayer) { p.connect(StringManager.getLobby()) }
}
