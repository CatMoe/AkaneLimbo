package catmoe.fallencrystal.akanelimbo.rule

import com.github.benmanes.caffeine.cache.Caffeine
import net.md_5.bungee.api.connection.ProxiedPlayer

object ReadCache {
    private val cache = Caffeine.newBuilder().build<ProxiedPlayer, Boolean>()

    @Synchronized
    fun cachePut(player: ProxiedPlayer, value: Boolean) {cache.put(player,value)}

    @Synchronized
    fun cacheGet(player: ProxiedPlayer): Boolean? {return cache.getIfPresent(player)}

    @Synchronized
    fun cacheInvalidate(player: ProxiedPlayer) {cache.invalidate(player)}

    @Synchronized
    fun cacheInvalidateAll() {cache.invalidateAll()}
}