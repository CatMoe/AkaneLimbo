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
    val serversCache = Caffeine.newBuilder().build<Int, ServerInfo>()

    override fun open(player: ProxiedPlayer) {
        clear()
        define(player)
        super.open(player)
    }

    private fun update() {
        clear()
        define(player)
    }

    private var proxy = ProxyServer.getInstance()
    private var servers: MutableMap<String, ServerInfo> = proxy.servers!!
    private var serverList: Collection<ServerInfo> = servers.values
    override fun define(p: ProxiedPlayer?) {
        super.define(p)
        this.type(InventoryType.GENERIC_9X6)
        setTitle(ca("&eServer List"))
        val clicktoconnect = "&7 > &b点击来连接到服务器"
        val addresscache: MutableMap<SocketAddress, ServerInfo> = HashMap()
        for ((slot, s) in serverList.withIndex()) {
            val name = s.name
            var motd = s.motd
            var conflictwarn = ""
            // Just another BungeeCord / Waterfall - Force Hosts
            // 深蓝色丑死 但又懒得改 直接进行一波暴力替换
            if (motd.contains("Just another")) {
                motd = "&7默认 &f- &b请前往config.yml设置"
            }
            val address = s.socketAddress
            if (addresscache.containsKey(address)) {
                conflictwarn = "&c(跟" + addresscache[address]!!.name + "冲突)"
            } else {
                addresscache[address] = s
            }
            val playing = s.players.size
            var isOnline: String
            var item: ItemType
            if (socketPing(s)) {
                isOnline = "&a(在线)"
                item = ItemType.EMERALD_BLOCK
            } else {
                isOnline = "&c(离线)"
                item = ItemType.REDSTONE_BLOCK
            }
            setItem(
                slot, ItemBuilder(item)
                    .name(ca("&b$name $isOnline")) // 有人喜欢给菜单全部物品上附魔 我不说是谁
                    .enchantment(GUIEnchantsList.UNBREAKING, playing) // 来玩找不同吧 ——看看哪个笨蛋填了俩地址一样的服务器(?)
                    .lore(ca("&b地址: " + s.socketAddress + " " + conflictwarn))
                    .lore(ca("&e$playing 在线玩家"))
                    .lore(ca("&dMOTD注释:"))
                    .lore(ca(motd))
                    .lore(ca(""))
                    .lore(ca(clicktoconnect))
                    .build()
            )
            serversCache.put(slot, s)
        }
    }

    override fun onClick(click: InventoryClick?) {
        if (player!!.hasPermission(StringManager.getServerListPermission())) {
            if (click!!.clickedItem().itemType() == null) {
                update()
            } else {
                toServer(player!!, click.slot())
                update()
            }
        } else {
            close()
        }
    }

    private fun ca(text: String?): String {
        return ForceFormatCode.replaceFormat(text!!)
    }

    private fun toServer(p: ProxiedPlayer, slot: Int) { p.connect(serversCache.getIfPresent(slot)) }
}
