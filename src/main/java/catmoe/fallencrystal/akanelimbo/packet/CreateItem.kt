package catmoe.fallencrystal.akanelimbo.packet

import dev.simplix.protocolize.api.Protocolize
import dev.simplix.protocolize.api.item.ItemStack
import dev.simplix.protocolize.data.ItemType
import dev.simplix.protocolize.data.packets.HeldItemChange
import dev.simplix.protocolize.data.packets.SetSlot
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.connection.ProxiedPlayer

class CreateItem {
    fun ruleItem(p: ProxiedPlayer) {
        val protocolize = Protocolize.playerProvider().player(p.uniqueId)
        val item = ItemStack(ItemType.PAPER, 1)
        protocolize.sendPacketToServer(HeldItemChange().newSlot(0))
        protocolize.sendPacket(HeldItemChange().newSlot(0))
        item.displayName(ca("&bRule &7(右键打开)"))
        protocolize.sendPacket(SetSlot().slot(36).itemStack(item))
    }

    private fun ca(text: String?): String {
        return ChatColor.translateAlternateColorCodes('&', text)
    }
}