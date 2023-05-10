package catmoe.fallencrystal.akanelimbo

import net.md_5.bungee.api.plugin.Plugin

object SharedPlugin {
    private var LimboPlugin: Plugin? = null
    fun setLimboPlugin(target: Plugin) { LimboPlugin = target }

    fun getLimboPlugin(): Plugin? { return LimboPlugin }
}