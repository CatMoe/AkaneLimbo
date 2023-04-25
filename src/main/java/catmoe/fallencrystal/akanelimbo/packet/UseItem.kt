package catmoe.fallencrystal.akanelimbo.packet

import catmoe.fallencrystal.akanelimbo.StringManager
import catmoe.fallencrystal.akanelimbo.rule.RuleMenu
import dev.simplix.protocolize.api.Direction
import dev.simplix.protocolize.api.listener.AbstractPacketListener
import dev.simplix.protocolize.api.listener.PacketReceiveEvent
import dev.simplix.protocolize.api.listener.PacketSendEvent
import dev.simplix.protocolize.data.packets.UseItem
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Plugin

class UseItem(private val plugin: Plugin, direction: Direction?, priority: Int) : AbstractPacketListener<UseItem>(UseItem::class.java, direction,
    priority
) {

    override fun packetReceive(p0: PacketReceiveEvent<UseItem>?) {
        val proxy = ProxyServer.getInstance()
        val playerUUID = p0!!.player().uniqueId()
        proxy.scheduler.runAsync(plugin) {
            val proxiedPlayer = proxy.getPlayer(playerUUID)
            if (proxiedPlayer.server.info.name.equals(StringManager.getMainLimbo())) {
                try {
                    val menu = RuleMenu()
                    menu.closed = false
                    menu.open(proxiedPlayer)
                } catch (_: NullPointerException) {}
            }
        }
    }

    override fun packetSend(p0: PacketSendEvent<UseItem>?) {
    }
}