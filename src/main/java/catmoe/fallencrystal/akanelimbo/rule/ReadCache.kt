package catmoe.fallencrystal.akanelimbo.rule

import com.github.benmanes.caffeine.cache.Caffeine
import java.util.*

object ReadCache {
    private val cache = Caffeine.newBuilder().build<UUID, Boolean>()

    @Synchronized
    fun cachePut(uuid: UUID, value: Boolean) {cache.put(uuid,value)}

    @Synchronized
    fun cacheGet(uuid: UUID): Boolean? {return cache.getIfPresent(uuid)}

    @Synchronized
    fun cacheInvalidate(uuid: UUID) {cache.invalidate(uuid)}

    @Synchronized
    fun cacheInvalidateAll() {cache.invalidateAll()}
}