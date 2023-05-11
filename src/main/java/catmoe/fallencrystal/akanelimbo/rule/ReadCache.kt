package catmoe.fallencrystal.akanelimbo.rule

import com.github.benmanes.caffeine.cache.Caffeine
import java.util.*

object ReadCache {
    private val readCache = Caffeine.newBuilder().build<UUID, Boolean>()

    fun cachePut(player: UUID, value: Boolean) {readCache.put(player,value)}
    fun cacheGet(player: UUID): Boolean? {return readCache.getIfPresent(player)}
    fun cacheInvalidate(player: UUID) {readCache.invalidate(player)}
    fun cacheInvalidateAll() {readCache.invalidateAll()}
}