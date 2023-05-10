package catmoe.fallencrystal.akanelimbo.afk

import com.github.benmanes.caffeine.cache.Caffeine
import dev.simplix.protocolize.api.Location
import dev.simplix.protocolize.api.Protocolize
import net.md_5.bungee.api.config.ServerInfo
import net.md_5.bungee.api.connection.ProxiedPlayer

class AFKHandler(val player: ProxiedPlayer, val server: ServerInfo) {
    private val protocolize = Protocolize.playerProvider()
    private val protocolPlayer = PlayerPosition(protocolize.player(player.uniqueId))

    private val moveCache = Caffeine.newBuilder().build<Location, Int>()

    private val isStopped = player.server.info != server
}