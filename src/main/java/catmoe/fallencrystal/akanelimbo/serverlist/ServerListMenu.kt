package catmoe.fallencrystal.akanelimbo.serverlist

import catmoe.fallencrystal.akanelimbo.StringManager
import catmoe.fallencrystal.akanelimbo.util.ServerOnlineCheck.socketPing
import catmoe.fallencrystal.akanelimbo.util.menu.ForceFormatCode
import catmoe.fallencrystal.akanelimbo.util.menu.GUIBuilder
import catmoe.fallencrystal.akanelimbo.util.menu.GUIEnchantsList
import catmoe.fallencrystal.akanelimbo.util.menu.ItemBuilder
import com.github.benmanes.caffeine.cache.Caffeine
import dev.simplix.protocolize.api.inventory.InventoryClick
import dev.simplix.protocolize.data.ItemType
import dev.simplix.protocolize.data.inventory.InventoryType
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.config.ServerInfo
import net.md_5.bungee.api.connection.ProxiedPlayer
import java.net.SocketAddress

class ServerListMenu : GUIBuilder() {

    var executePlayer: ProxiedPlayer? = null

    // 用于存储遍历的服务器
    private val serversCache = Caffeine.newBuilder().build<Int, ServerInfo>()

    private var proxy = ProxyServer.getInstance()

    private val onlineServers = mutableListOf<ServerInfo>()
    private val offlineServers = mutableListOf<ServerInfo>()

    override fun open(player: ProxiedPlayer) {
        clear()
        define(player)
        super.open(player)
    }

    private fun update() {
        clear()
        define(player)
    }

    private val clicktoconnect = "&7 > &b点击来连接到服务器"
    private val addresscache: MutableMap<SocketAddress, ServerInfo> = HashMap()

    override fun define(p: ProxiedPlayer?) {
        super.define(p)
        this.type(InventoryType.GENERIC_9X6)
        setTitle(ca("&eServer List"))
        proxy.servers.forEach { if (socketPing(it.value)) { onlineServers.add(it.value) } else { offlineServers.add(it.value) } }
        var slots = 0
        onlineServers.forEach { setServerItem(slots, it, 1); slots++ }
        offlineServers.forEach { setServerItem(slots, it, 2); slots++ }
    }

    private fun setServerItem(slot: Int, server: ServerInfo, mode: Int) {
        // Mode 1 = Online, 2 = Offline
        val name = server.name
        var motd = server.motd
        var conflictWarn = ""
        // Just another $BungeeCord - Forced Hosts
        if (motd.contains("Just another ") && motd.contains(" - Forced Hosts")) { motd = "&7默认 &f- &b请前往config.yml设置" }
        val address = server.socketAddress
        if (addresscache.containsKey(address)) { conflictWarn = "&c(跟" + addresscache[address]!!.name + "冲突)" } else { addresscache[address] = server }
        val playing = server.players.size
        val isOnline: String
        val item: ItemType
        if (mode == 1) {
            isOnline = "&a(在线)"
            item = ItemType.EMERALD_BLOCK
        } else {
            isOnline = "&c(离线)"
            item = ItemType.REDSTONE_BLOCK
        }
        setItem(slot, ItemBuilder(item)
            .name(ca("&b$name $isOnline"))
            .enchantment(GUIEnchantsList.UNBREAKING, playing) // 有人喜欢给菜单全部物品上附魔 我不说是谁
            .lore(ca("&b地址: " + server.socketAddress + " " + conflictWarn)) // 来玩找不同吧 ——看看哪个笨蛋填了俩地址一样的服务器(?)
            .lore(ca("&e$playing 在线玩家"))
            .lore(ca("&dMOTD注释:"))
            .lore(ca(motd))
            .lore(ca(""))
            .lore(ca(clicktoconnect))
            .build()
        )
    }

    override fun onClick(click: InventoryClick?) {
        if (player!!.hasPermission(StringManager.getServerListPermission())) {
            if (click!!.clickedItem().itemType() == ItemType.EMERALD_BLOCK) {
                toServer(player!!, click.slot())
            } else { update() }
        } else {
            close()
        }
    }

    private fun ca(text: String?): String {
        return ForceFormatCode.replaceFormat(text!!)
    }

    private fun toServer(p: ProxiedPlayer, slot: Int) { p.connect(serversCache.getIfPresent(slot)) }
}
