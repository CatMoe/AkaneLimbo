package catmoe.fallencrystal.akanelimbo.util

import catmoe.fallencrystal.akanelimbo.StringManager
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.config.ServerInfo
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.ServerKickEvent
import java.net.InetSocketAddress

class LimboCreater {
    private var address = "127.0.0.1"
    private var port = 3
    private var server: ServerInfo? = null
    fun createServer(p: ProxiedPlayer, name: String) {
        val target = StringManager.getLimboPrefix() + StringManager.getLimboArrow() + name
        server = ProxyServer.getInstance().constructServerInfo(
            target, InetSocketAddress(address, port),
            "Limbo for player" + p.displayName + "(" + p.uniqueId + ")", false
        )
    }

    fun connect(p: ProxiedPlayer) {
        p.connect(server)
    }

    fun connect2(e: ServerKickEvent) {
        e.isCancelled = true
        e.cancelServer = server
    }
}
