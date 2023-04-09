package catmoe.fallencrystal.akanelimbo.kick

import catmoe.fallencrystal.akanelimbo.util.MessageUtil.actionbar
import catmoe.fallencrystal.akanelimbo.util.ServerOnlineCheck.socketPing
import catmoe.fallencrystal.akanelimbo.util.menu.ForceFormatCode
import catmoe.fallencrystal.akanelimbo.util.menu.GUIBuilder
import catmoe.fallencrystal.akanelimbo.util.menu.ItemBuilder
import dev.simplix.protocolize.api.inventory.InventoryClick
import dev.simplix.protocolize.api.inventory.InventoryClose
import dev.simplix.protocolize.data.ItemType
import dev.simplix.protocolize.data.inventory.InventoryType
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.config.ServerInfo
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.ServerKickEvent

class KickMenu : GUIBuilder() {
    private var defaultserver: ServerInfo? = null
    private var kickfrom: ServerInfo? = null
    @JvmField
    var close = false
    private fun update() {
        clear()
        define(player)
    }

    override fun open(p: ProxiedPlayer) {
        clear()
        define(p)
        super.open(p)
    }

    private fun placeholderItem() {
        val slots: List<Int> = mutableListOf(
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 16, 17, 18, 19, 20, 21, 22, 23,
            24, 25, 26
        )
        for (slot in slots) {
            setItem(slot, ItemBuilder(ItemType.GRAY_STAINED_GLASS_PANE).name("").build())
        }
    }

    override fun define(p: ProxiedPlayer) {
        super.define(p)
        placeholderItem()
        this.type(InventoryType.GENERIC_9X3)
        setTitle(ca("&c连接已丢失"))
        setItem(
            13, ItemBuilder(ItemType.REDSTONE_BLOCK)
                .name(ca("&c您与此服务器的连接已丢失!"))
                .lore(ca(""))
                .lore(ca("&7其实你现在没有连接到任何服务器w!"))
                .lore(ca(""))
                .lore(ca("&b您可以选择 &a重新连接 &b或 &c回到大厅"))
                .build()
        )
        setItem(
            11, ItemBuilder(ItemType.REPEATER)
                .name(ca("&a重新连接"))
                .lore(ca(""))
                .lore(ca("&7只是尝试重新连接 没什么特别的"))
                .lore(ca(""))
                .lore(ca("&c如果服务器长时间离线 请报告给管理员!"))
                .lore(ca(""))
                .lore(ca("&7离线时所处服务器: " + kickfrom!!.name))
                .build()
        )
        setItem(
            15, ItemBuilder(ItemType.BEACON)
                .name(ca("&c回到大厅"))
                .lore(ca(""))
                .lore(ca("&7只是回到大厅 没什么特别的"))
                .build()
        )
    }

    override fun onClick(e: InventoryClick) {
        if (e.slot() == 11 && e.clickedItem().itemType() == ItemType.REPEATER) {
            if (notOnline(kickfrom)) {
                actionbar(player, "&c目标服务器似乎已离线 请稍后再试")
                return
            }
            player.connect(kickfrom)
            update()
            actionbar(player, "&a正在尝试重新连接 请稍后..")
        } else if (e.slot() == 15 && e.clickedItem().itemType() == ItemType.BEACON) {
            if (notOnline(defaultserver)) {
                actionbar(player, "&c目标服务器似乎已离线 请稍后再试")
                return
            }
            player.connect(defaultserver)
            update()
            actionbar(player, "&a正在将您传送到大厅..")
        } else {
            update()
        }
    }

    override fun onClose(e: InventoryClose) {
        try {
            if (!close) {
                open(player)
                actionbar(player, "&b别忘了这可是不存在的地方 关闭了就出不来了哦~")
            } else {
                close()
            }
            // 如果玩家离开造成的NullPointerException 则关闭菜单
        } catch (ignore: NullPointerException) {
        }
    }

    private fun ca(text: String?): String {
        return ForceFormatCode.replaceFormat(text)
    }

    private fun notOnline(server: ServerInfo?): Boolean {
        return !socketPing(server!!)
    }

    @Suppress("DEPRECATION")
    fun handleevent(e: ServerKickEvent) {
        kickfrom = e.kickedFrom
        defaultserver = ProxyServer.getInstance().getServerInfo(
            ProxyServer.getInstance().config.listeners.iterator().next().defaultServer
        )
    }
}
