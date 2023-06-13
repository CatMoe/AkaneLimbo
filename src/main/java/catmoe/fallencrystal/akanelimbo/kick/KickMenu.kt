@file:Suppress("SameParameterValue")

package catmoe.fallencrystal.akanelimbo.kick

import catmoe.fallencrystal.akanelimbo.SharedPlugin
import catmoe.fallencrystal.akanelimbo.util.ServerOnlineCheck.socketPing
import catmoe.fallencrystal.akanelimbo.util.menu.ForceFormatCode
import catmoe.fallencrystal.akanelimbo.util.menu.GUIBuilder
import catmoe.fallencrystal.akanelimbo.util.menu.ItemBuilder
import catmoe.fallencrystal.moefilter.util.message.MessageUtil
import catmoe.fallencrystal.moefilter.util.message.MessageUtil.colorizeMiniMessage
import catmoe.fallencrystal.moefilter.util.message.MessageUtil.sendActionbar
import catmoe.fallencrystal.moefilter.util.message.MessageUtil.sendTitle
import catmoe.fallencrystal.moefilter.util.plugin.util.Scheduler
import dev.simplix.protocolize.api.inventory.InventoryClick
import dev.simplix.protocolize.api.inventory.InventoryClose
import dev.simplix.protocolize.data.ItemType
import dev.simplix.protocolize.data.inventory.InventoryType
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.config.ServerInfo
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.ServerKickEvent
import java.util.concurrent.TimeUnit

class KickMenu : GUIBuilder() {
    private var defaultserver: ServerInfo? = null
    private var kickfrom: ServerInfo? = null
    private var handlePlayer: ProxiedPlayer? = null
    @JvmField
    var close = false

    override fun open(player: ProxiedPlayer) {
        clear()
        define(player)
        super.open(player)
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

    override fun define(p: ProxiedPlayer?) {
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
                .lore(ca("&c或者点击次物品离开服务器"))
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

    override fun onClick(click: InventoryClick?) {
        if (click!!.slot() == 11 && click.clickedItem().itemType() == ItemType.REPEATER) {
            if (notOnline(kickfrom)) {
                sendActionbar(player!!, colorizeMiniMessage("<red>目标服务器似乎已离线 请稍后再试</red>"))
                return
            }
            player!!.connect(kickfrom)
            update()
            sendActionbar(player!!, colorizeMiniMessage("<green>正在尝试重新连接 请稍后..</green>"))
        } else if (click.slot() == 15 && click.clickedItem().itemType() == ItemType.BEACON) {
            if (notOnline(defaultserver)) {
                sendActionbar(player!!, colorizeMiniMessage("<red>目标服务器似乎已离线 请稍后再试</red>"))
                return
            }
            player!!.connect(defaultserver)
            update()
            sendActionbar(player!!, colorizeMiniMessage("<green>正在将您传送到大厅..</green>"))
        } else if (click.slot() == 13 && click.clickedItem().itemType() == ItemType.REDSTONE_BLOCK) {
            kick(player, "")
        } else {
            update()
        }
    }

    override fun onClose(close: InventoryClose?) {
        try {
            if (player!!.server.info.name.startsWith("AkaneLimbo") && !this.close) {
                open(player!!)
                sendActionbar(player!!, colorizeMiniMessage("<aqua>别忘了这可是不存在的地方 关闭了就出不来了哦~</aqua>"))
            } else { close() }
            // 如果玩家离开造成的NullPointerException 则关闭菜单
        } catch (ignore: NullPointerException) { }
    }

    private fun ca(text: String?): String {
        return ForceFormatCode.replaceFormat(text!!)
    }

    private fun kick(p: ProxiedPlayer?, reason: String?) {
        p?.disconnect(TextComponent(ca(reason)))
    }

    private fun notOnline(server: ServerInfo?): Boolean {
        return !socketPing(server!!)
    }

    @Suppress("DEPRECATION")
    fun handleEvent(e: ServerKickEvent) {
        kickfrom = e.kickedFrom
        defaultserver = ProxyServer.getInstance().getServerInfo(ProxyServer.getInstance().config.listeners.iterator().next().defaultServer)
        handlePlayer = e.player
    }

    fun sendTitle(p: ProxiedPlayer?) {
        val title = "<red>连接已丢失</red>"
        val subtitle = "<white>在菜单内选择 <aqua>回到大厅</aqua> 或 <aqua>重新连接</aqua></white>"
        sendTitle(p!!, title, subtitle, 18, 5, 0)
        /*
        val scheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        // 创建循环
        scheduledExecutorService.scheduleAtFixedRate({
            if (!p.server.info.name.contains("AkaneLimbo")) {
                sendTitle(p, "", "", 1, 0, 0)
                scheduledExecutorService.shutdownNow()
            } else { sendTitle(p, title, subtitle, 21, 0, 0) }
        }, 1, 1, TimeUnit.SECONDS)
         */
        Scheduler(SharedPlugin.getLimboPlugin()!!).repeatScheduler(1, TimeUnit.SECONDS) {
            if (p.server.info.name.startsWith("AkaneLimbo")) {
                sendTitle(p, MessageUtil.colorizeMiniMessage(title), MessageUtil.colorizeMiniMessage(subtitle), 30, 0, 0)
            } else { sendTitle(p, "", "", 0, 0, 0); return@repeatScheduler }
        }
    }
}
